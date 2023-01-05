package com.jhshop.admin2.goods.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jhshop.admin2.goods.dao.Admin2GoodsDAO;
import com.jhshop.goods.vo.GoodsVO;
import com.jhshop.goods.vo.ImageFileVO;
import com.jhshop.order.vo.OrderVO;


@Service("admin2GoodsService")
@Transactional(propagation=Propagation.REQUIRED)
public class Admin2GoodsServiceImpl implements Admin2GoodsService {
	@Autowired
	private Admin2GoodsDAO admin2GoodsDAO;
	
	@Override
	public int addNewGoods(Map newGoodsMap) throws Exception{
		int goods_id = admin2GoodsDAO.insertNewGoods(newGoodsMap);
		ArrayList<ImageFileVO> imageFileList = (ArrayList)newGoodsMap.get("imageFileList");
		for(ImageFileVO imageFileVO : imageFileList) {
			imageFileVO.setGoods_id(goods_id);
		}
		admin2GoodsDAO.insertGoodsImageFile(imageFileList);
		return goods_id;
	}
	
	@Override
	public List<GoodsVO> listNewGoods(Map condMap) throws Exception{
		return admin2GoodsDAO.selectNewGoodsList(condMap);
	}
	@Override
	public Map goodsDetail(int goods_id) throws Exception {
		Map goodsMap = new HashMap();
		GoodsVO goodsVO=admin2GoodsDAO.selectGoodsDetail(goods_id);
		List imageFileList =admin2GoodsDAO.selectGoodsImageFileList(goods_id);
		goodsMap.put("goods", goodsVO);
		goodsMap.put("imageFileList", imageFileList);
		return goodsMap;
	}
	@Override
	public List goodsImageFile(int goods_id) throws Exception{
		List imageList =admin2GoodsDAO.selectGoodsImageFileList(goods_id);
		return imageList;
	}
	
	
	
	
	@Override
	public void addNewGoodsImage(List imageFileList) throws Exception{
		admin2GoodsDAO.insertGoodsImageFile(imageFileList);
	}
	

	
}
