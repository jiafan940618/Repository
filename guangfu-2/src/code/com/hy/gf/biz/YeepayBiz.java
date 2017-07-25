package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Yeepay;
import com.hy.gf.util.*;
import com.hy.gf.mapper.YeepayMapper;
import com.hy.gf.model.Page;

@Service
public class YeepayBiz {

@Resource
YeepayMapper yeepayMapper;

	public int delete(Long id) {
		return yeepayMapper.delete(id);
	}
	public int insert(Yeepay record) {
		return yeepayMapper.insert(record);
	}
	public Yeepay select(Long id) {
		return yeepayMapper.select(id);
	}
	public int update(Yeepay record) {
		return yeepayMapper.update(record);
	}
	public Yeepay selectOneByExample(Yeepay record) {
		return yeepayMapper.selectOneByExample(record);
	}
	public Page<Yeepay> selectByExample(Page<Yeepay> page) {
Object example = page.getExample();
Yeepay yeepay = null;
if (example != null) {
yeepay = (Yeepay) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(yeepay);
objectMap.putAll(objectMap2);List<Yeepay> selectByExample = yeepayMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = yeepayMapper.selectByExampleCount(yeepay);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		yeepayMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return yeepayMapper.deleteBatch(ids);
}}