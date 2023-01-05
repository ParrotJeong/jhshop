package com.jhshop.admin2.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jhshop.goods.vo.GoodsVO;
import com.jhshop.goods.vo.ImageFileVO;
import com.jhshop.order.vo.OrderVO;

public interface Admin2GoodsService {
	public int  addNewGoods(Map newGoodsMap) throws Exception;
	public List<GoodsVO> listNewGoods(Map condMap) throws Exception;
	public Map goodsDetail(int goods_id) throws Exception;
	public List goodsImageFile(int goods_id) throws Exception;
	public void addNewGoodsImage(List imageFileList) throws Exception;
	
}
