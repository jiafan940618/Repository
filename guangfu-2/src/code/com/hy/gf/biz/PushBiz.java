package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Push;
import com.hy.gf.util.*;
import com.hy.gf.mapper.PushMapper;
import com.hy.gf.model.Page;

@Service
public class PushBiz {

@Resource
PushMapper pushMapper;

	public int delete(Long id) {
		return pushMapper.delete(id);
	}
	public int insert(Push record) {
		return pushMapper.insert(record);
	}
	public Push select(Long id) {
		return pushMapper.select(id);
	}
	public int update(Push record) {
		return pushMapper.update(record);
	}
	public Push selectOneByExample(Push record) {
		return pushMapper.selectOneByExample(record);
	}
	public Page<Push> selectByExample(Page<Push> page) {
Object example = page.getExample();
Push push = null;
if (example != null) {
push = (Push) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(push);
objectMap.putAll(objectMap2);List<Push> selectByExample = pushMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = pushMapper.selectByExampleCount(push);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		pushMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return pushMapper.deleteBatch(ids);
}}