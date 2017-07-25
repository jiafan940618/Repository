package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Information;
import com.hy.gf.util.*;
import com.hy.gf.mapper.InformationMapper;
import com.hy.gf.model.Page;

@Service
public class InformationBiz {

@Resource
InformationMapper informationMapper;

	public int delete(Long id) {
		return informationMapper.delete(id);
	}
	public int insert(Information record) {
		return informationMapper.insert(record);
	}
	public Information select(Long id) {
		return informationMapper.select(id);
	}
	public int update(Information record) {
		return informationMapper.update(record);
	}
	public Information selectOneByExample(Information record) {
		return informationMapper.selectOneByExample(record);
	}
	public Page<Information> selectByExample(Page<Information> page) {
Object example = page.getExample();
Information information = null;
if (example != null) {
information = (Information) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(information);
objectMap.putAll(objectMap2);List<Information> selectByExample = informationMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = informationMapper.selectByExampleCount(information);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		informationMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return informationMapper.deleteBatch(ids);
}}