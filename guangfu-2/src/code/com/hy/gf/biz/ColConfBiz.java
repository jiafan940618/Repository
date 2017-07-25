package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.ColConf;
import com.hy.gf.util.*;
import com.hy.gf.mapper.ColConfMapper;
import com.hy.gf.model.Page;

@Service
public class ColConfBiz {

@Resource
ColConfMapper colConfMapper;

	public int delete(Long id) {
		return colConfMapper.delete(id);
	}
	public int insert(ColConf record) {
		return colConfMapper.insert(record);
	}
	public ColConf select(Long id) {
		return colConfMapper.select(id);
	}
	public int update(ColConf record) {
		return colConfMapper.update(record);
	}
	public ColConf selectOneByExample(ColConf record) {
		return colConfMapper.selectOneByExample(record);
	}
	public Page<ColConf> selectByExample(Page<ColConf> page) {
Object example = page.getExample();
ColConf colConf = null;
if (example != null) {
colConf = (ColConf) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(colConf);
objectMap.putAll(objectMap2);List<ColConf> selectByExample = colConfMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = colConfMapper.selectByExampleCount(colConf);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		colConfMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return colConfMapper.deleteBatch(ids);
}}