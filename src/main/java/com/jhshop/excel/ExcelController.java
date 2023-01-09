package com.jhshop.excel;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping(value="/admin2/goods")
@Controller
public class ExcelController {
    private static final String EXCEL_REPO = "C:\\shopping\\excel";

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);
    @Autowired
    ExcelService excelService;

    @RequestMapping(value = "/excelForm.do", method = RequestMethod.GET)
    public String excelForm(Locale locale, Model model) {

        return "excelUploadForm";
    }

    @RequestMapping(value = "/excelGoodsUpload.do", method = RequestMethod.POST)
    @ResponseBody
    public List<Map> addNewGoods(MultipartHttpServletRequest multipartRequest,  HttpServletResponse response,ModelMap model) throws Exception {
        ResponseEntity resEnt=null;
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String,Object> paramMap = new HashMap<String, Object>();
        Enumeration enu=multipartRequest.getParameterNames();
        while(enu.hasMoreElements()){
            String name=(String)enu.nextElement();
            String value=multipartRequest.getParameter(name);
            paramMap.put(name,value);
        }

        String orgName= excelUpload(multipartRequest);
        List totalGoodsList = getGoodsCellData(EXCEL_REPO  + "\\" + orgName);
        excelService.addExcelGoods(totalGoodsList);
        List<Map> excelGoodsList = totalGoodsList;
        List<Map> infoList = null;
        Map excelMap = new HashMap();
        List<String> goods_isbnList = new ArrayList<String>();

        for(int i=0;i<excelGoodsList.size();i++){
           goods_isbnList.add((String)excelGoodsList.get(i).get("goods_isbn"));
        }
        System.out.println("tetst14:" + goods_isbnList);
            infoList = excelService.selectExcelfileisbn(goods_isbnList);

        System.out.println("exceltest:" + infoList);

        return infoList;
    }

    @RequestMapping(value = "/addNewManyGoodsForm.do")
    public String selectInfo(ModelMap model,MultipartHttpServletRequest multipartRequest,HttpServletResponse response) throws Exception {
        multipartRequest.setCharacterEncoding("utf-8");

        String orgName= excelUpload(multipartRequest);
        List totalGoodsList = getGoodsCellData(EXCEL_REPO  + "\\" + orgName);

        List<Map> excelGoodsList = totalGoodsList;

        Map excelMap = new HashMap();

        for(Map goodsMap :excelGoodsList){
            int goods_id = Integer.parseInt(goodsMap.get("goods_id").toString());
            String goods_sort = goodsMap.get("goods_sort").toString();
            String goods_title = goodsMap.get("goods_title").toString();
            String goods_writer = goodsMap.get("goods_writer").toString();
            String goods_publisher = goodsMap.get("goods_publisher").toString();
            int goods_price = Integer.parseInt(goodsMap.get("goods_price").toString());
            int goods_sales_price = Integer.parseInt(goodsMap.get("goods_sales_price").toString());
            int goods_point = Integer.parseInt(goodsMap.get("goods_point").toString());
            String goods_published_date = goodsMap.get("goods_published_date").toString();
            int goods_total_page = Integer.parseInt(goodsMap.get("goods_total_page").toString());
            String goods_isbn = goodsMap.get("goods_isbn").toString();
            int goods_delivery_price = Integer.parseInt(goodsMap.get("goods_delivery_price").toString());
            String goods_delivery_date = goodsMap.get("goods_delivery_date").toString();
            String goods_status = goodsMap.get("goods_status").toString();
            String goods_writer_intro = goodsMap.get("goods_writer_intro").toString();
            String goods_intro = goodsMap.get("goods_intro").toString();
            String goods_publisher_comment = goodsMap.get("goods_publisher_comment").toString();
            String goods_recommendation = goodsMap.get("goods_recommendation").toString();
            String goods_contents_order = goodsMap.get("goods_contents_order").toString();
            excelMap.put("goods_id",goods_id);
            excelMap.put("goods_sort",goods_sort);
        }

        model.addAttribute("excelGoodsList", excelGoodsList);
        model.addAttribute("excelMap", excelMap);
        return "/admin2/goods/addNewManyGoodsForm";
    }


    @RequestMapping(value = "/excelImageUpload.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addNewGoodsImage(MultipartHttpServletRequest multipartRequest,  HttpServletResponse response) throws Exception {
        ResponseEntity resEnt=null;
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String,Object> paramMap = new HashMap<String, Object>();
        Enumeration enu=multipartRequest.getParameterNames();
        while(enu.hasMoreElements()){
            String name=(String)enu.nextElement();
            String value=multipartRequest.getParameter(name);
            paramMap.put(name,value);
        }

        String orgName= excelUpload(multipartRequest);
        List totalImageList = getImageCellData(EXCEL_REPO  + "\\" + orgName);
        excelService.addExcelGoodsImage(totalImageList);
        resEnt = new ResponseEntity("add_excel_success", HttpStatus.CREATED);
        return resEnt;
    }





    //엑셀 파일 업로드하기
    private String excelUpload(MultipartHttpServletRequest multipartRequest) throws Exception{
        String orgName= null;
        Iterator<String> fileNames = multipartRequest.getFileNames();

        while(fileNames.hasNext()){
            String fileName = fileNames.next();
            MultipartFile mFile = multipartRequest.getFile(fileName);
            orgName=mFile.getOriginalFilename();
            File file = new File(EXCEL_REPO +"\\"+ fileName);
            if(mFile.getSize()!=0){
                if(! file.exists()){
                    if(file.getParentFile().mkdirs()){
                        file.createNewFile();
                    }
                }
                mFile.transferTo(new File(EXCEL_REPO +"\\" + orgName));
            }
        }
        return orgName;
    }



    private List<?> getGoodsCellData(String excelFile){
        Map<String, Object> newGoodsMap = new HashMap<String, Object>();
        List<?> list = null;
        List<Map> totalGoodsList = null;

        try {
            Workbook wbs = ExcelUtil.getWorkbook(excelFile);

            Sheet sheet = (Sheet) wbs.getSheetAt(0);

            for (int i = sheet.getFirstRowNum()  ; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                List cellList  = new ArrayList();

                logger.info("첫번째 셀값:" + ExcelUtil.cellValue(row.getCell(0)).toLowerCase());
                logger.info("두번째 셀값:" + ExcelUtil.cellValue(row.getCell(1)).toLowerCase());
                logger.info("세번째 셀값:" + ExcelUtil.cellValue(row.getCell(2)).toLowerCase());
                logger.info("네번째 셀값:" + ExcelUtil.cellValue(row.getCell(3)).toLowerCase());
                logger.info("다섯번째 셀값:" + ExcelUtil.cellValue(row.getCell(4)).toLowerCase());
                logger.info("여섯번째 셀값:" + ExcelUtil.cellValue(row.getCell(5)).toLowerCase());

if( ExcelUtil.cellValue(row.getCell(0)).toLowerCase() != null || ExcelUtil.cellValue(row.getCell(0)).toLowerCase() !=""){
    cellList.add(ExcelUtil.cellValue(row.getCell(0)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(1)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(2)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(3)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(4)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(5)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(6)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(7)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(8)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(9)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(10)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(11)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(12)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(13)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(14)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(15)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(16)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(17)).toLowerCase());
    cellList.add(ExcelUtil.cellValue(row.getCell(18)).toLowerCase());
}


                if(i == 0) {
                    newGoodsMap.put("colName", cellList);
                }
                newGoodsMap.put(Integer.toString(i), cellList);

            }


            int goodsNum = newGoodsMap.size()-1;
            List colNameList =(ArrayList) newGoodsMap.get("colName");
            totalGoodsList = new ArrayList<Map>();

            for(int i = 1;  i<goodsNum; i++) {
                list = (ArrayList)newGoodsMap.get(Integer.toString(i));
                //GoodsVO vo = new GoodsVO();
                Map gMap = new HashMap();
                for(int j=0; j < list.size(); j++) {
                    gMap.put(colNameList.get(j), list.get(j));
                }
                totalGoodsList.add(gMap);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return totalGoodsList;

    }

    private List<?> getImageCellData(String excelFile){
        Map<String, Object> newGoodsMap = new HashMap<String, Object>();
        List<?> list = null;
        List<Map> totalGoodsList = null;

        try {
            Workbook wbs = ExcelUtil.getWorkbook(excelFile);

            Sheet sheet = (Sheet) wbs.getSheetAt(0);

            for (int i = sheet.getFirstRowNum()  ; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                List cellList  = new ArrayList();

                logger.info("첫번째 셀값:" + ExcelUtil.cellValue(row.getCell(0)).toLowerCase());
                logger.info("두번째 셀값:" + ExcelUtil.cellValue(row.getCell(1)).toLowerCase());
                logger.info("세번째 셀값:" + ExcelUtil.cellValue(row.getCell(2)).toLowerCase());
                logger.info("네번째 셀값:" + ExcelUtil.cellValue(row.getCell(3)).toLowerCase());
                logger.info("다섯번째 셀값:" + ExcelUtil.cellValue(row.getCell(4)).toLowerCase());
                logger.info("여섯번째 셀값:" + ExcelUtil.cellValue(row.getCell(5)).toLowerCase());


                cellList.add(ExcelUtil.cellValue(row.getCell(0)).toLowerCase());
                cellList.add(ExcelUtil.cellValue(row.getCell(1)).toLowerCase());
                cellList.add(ExcelUtil.cellValue(row.getCell(2)).toLowerCase());
                cellList.add(ExcelUtil.cellValue(row.getCell(3)).toLowerCase());
                cellList.add(ExcelUtil.cellValue(row.getCell(4)).toLowerCase());
                cellList.add(ExcelUtil.cellValue(row.getCell(5)).toLowerCase());

                if(i == 0) {
                    newGoodsMap.put("colName", cellList);
                }
                newGoodsMap.put(Integer.toString(i), cellList);

            }


            int goodsNum = newGoodsMap.size()-1; //엑셀의 첫번째 행은 컬럼명이므로 1을 뺍니다.
            List colNameList =(ArrayList) newGoodsMap.get("colName");
            totalGoodsList = new ArrayList<Map>();

            for(int i = 1;  i<goodsNum; i++) {
                list = (ArrayList)newGoodsMap.get(Integer.toString(i));
                //GoodsVO vo = new GoodsVO();
                Map gMap = new HashMap();
                for(int j=0; j < list.size(); j++) {
                    gMap.put(colNameList.get(j), list.get(j));
                }
                totalGoodsList.add(gMap);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return totalGoodsList;

    }


}