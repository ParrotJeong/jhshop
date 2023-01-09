package com.jhshop.excel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Service("excelService")
public class ExcelService {
    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);
    @Autowired
    private ExcelDAO excelDAO;

    public int addExcelGoods(List totalGoodsList)  throws DataAccessException{
        int result = excelDAO.insertExcelGoods(totalGoodsList);
        return result;
    }


    public int addExcelGoodsImage(List totalImageList)  throws DataAccessException{
        int result = excelDAO.insertExcelGoodsImage(totalImageList);
        return result;
    }

    public List selectExcelfileisbn(List goods_isbnList) throws Exception{
        List isbnLIst =excelDAO.selectExcelfileinfo(goods_isbnList);
        return isbnLIst;
    }

}