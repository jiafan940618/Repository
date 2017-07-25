package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Evaluate;
import com.hy.gf.util.*;
import com.hy.gf.mapper.EvaluateMapper;
import com.hy.gf.model.Page;

@Service
public class EvaluateBiz {

@Resource
EvaluateMapper evaluateMapper;

	public int delete(Long id) {
		return evaluateMapper.delete(id);
	}
	public int insert(Evaluate record) {
		return evaluateMapper.insert(record);
	}
	public Evaluate select(Long id) {
		return evaluateMapper.select(id);
	}
	public int update(Evaluate record) {
		return evaluateMapper.update(record);
	}
	public Evaluate selectOneByExample(Evaluate record) {
		return evaluateMapper.selectOneByExample(record);
	}
	public Page<Evaluate> selectByExample(Page<Evaluate> page) {
Object example = page.getExample();
Evaluate evaluate = null;
if (example != null) {
evaluate = (Evaluate) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(evaluate);
objectMap.putAll(objectMap2);List<Evaluate> selectByExample = evaluateMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = evaluateMapper.selectByExampleCount(evaluate);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		evaluateMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return evaluateMapper.deleteBatch(ids);
}}