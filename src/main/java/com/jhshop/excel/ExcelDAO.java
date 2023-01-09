package com.jhshop.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhshop.goods.vo.GoodsVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("excelDAO")
public class ExcelDAO {
    @Autowired
    private SqlSession sqlSession;

    public int insertExcelGoods(List<Map> totalGoodsList)  throws DataAccessException{
        int result = 0;
        for(Map goodsMap : totalGoodsList){
            result = sqlSession.insert("mapper.admin2.goods.insertExcelGoods", goodsMap);
        }
        return result;
    }

    public int insertExcelGoodsImage(List<Map> totalImageList)  throws DataAccessException{
        int result = 0;
        for(Map goodsImageMap : totalImageList){
            result = sqlSession.insert("mapper.admin2.goods.insertExcelGoodsImage", goodsImageMap);
        }
        return result;
    }

    public List<GoodsVO> selectExcelfileinfo(List goods_isbnList) throws DataAccessException {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("goods_isbnList",goods_isbnList);
        return sqlSession.selectList("mapper.admin2.goods.selectExcelfileinfoList",goods_isbnList);
    }



}