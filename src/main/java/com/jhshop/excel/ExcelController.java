package com.jhshop.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
    public ResponseEntity addNewGoods(MultipartHttpServletRequest multipartRequest,  HttpServletResponse response) throws Exception {
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
        resEnt = new ResponseEntity("add_excel_success", HttpStatus.CREATED);
        return resEnt;
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
            if(mFile.getSize()!=0){ //File Null Check
                if(! file.exists()){ //경로상에 파일이 존재하지 않을 경우
                    if(file.getParentFile().mkdirs()){ //경로에 해당하는 디렉토리들을 생성
                        file.createNewFile(); //이후 파일 생성
                    }
                }
                mFile.transferTo(new File(EXCEL_REPO +"\\" + orgName)); //임시로 저장된 multipartFile을 실제 파일로 전송
            }
        }
        return orgName;
    }



    private List<?> getGoodsCellData(String excelFile){  //업로드한 엑셀 파일에서 정보를 얻습니다.
        Map<String, Object> newGoodsMap = new HashMap<String, Object>();
        List<?> list = null;
        List<Map> totalGoodsList = null;

        try {
            Workbook wbs = ExcelUtil.getWorkbook(excelFile);

            Sheet sheet = (Sheet) wbs.getSheetAt(0);

            //excel file 첫번째 줄부터 셀의 데이터를 가지고 옵니다.
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

    private List<?> getImageCellData(String excelFile){  //업로드한 엑셀 파일에서 정보를 얻습니다.
        Map<String, Object> newGoodsMap = new HashMap<String, Object>();
        List<?> list = null;
        List<Map> totalGoodsList = null;

        try {
            Workbook wbs = ExcelUtil.getWorkbook(excelFile);

            Sheet sheet = (Sheet) wbs.getSheetAt(0);

            //excel file 첫번째 줄부터 셀의 데이터를 가지고 옵니다.
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

                //신규삽입
                // homeMapper.insertDB(map);
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