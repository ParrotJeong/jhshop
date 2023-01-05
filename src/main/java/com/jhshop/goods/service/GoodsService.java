package com.jhshop.goods.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jhshop.goods.vo.GoodsVO;

public interface GoodsService {
	
	public Map<String,List<GoodsVO>> listGoods() throws Exception;
	public Map goodsDetail(String _goods_id) throws Exception;
	public Map<String,List<GoodsVO>> listItGoods() throws Exception;
	
	public List<String> keywordSearch(String keyword) throws Exception;
	public List<GoodsVO> searchGoods(String searchWord) throws Exception;
}
