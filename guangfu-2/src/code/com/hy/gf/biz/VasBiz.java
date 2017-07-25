package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Vas;
import com.hy.gf.util.*;
import com.hy.gf.mapper.VasMapper;
import com.hy.gf.model.Page;

@Service
public class VasBiz {

@Resource
VasMapper vasMapper;

	public int delete(Long id) {
		return vasMapper.delete(id);
	}
	public int insert(Vas record) {
		return vasMapper.insert(record);
	}
	public Vas select(Long id) {
		return vasMapper.select(id);
	}
	public int update(Vas record) {
		return vasMapper.update(record);
	}
	public Vas selectOneByExample(Vas record) {
		return vasMapper.selectOneByExample(record);
	}
	public Page<Vas> selectByExample(Page<Vas> page) {
Object example = page.getExample();
Vas vas = null;
if (example != null) {
vas = (Vas) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(vas);
objectMap.putAll(objectMap2);List<Vas> selectByExample = vasMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = vasMapper.selectByExampleCount(vas);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		vasMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return vasMapper.deleteBatch(ids);
}}