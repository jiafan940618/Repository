package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Income;
import com.hy.gf.util.*;
import com.hy.gf.mapper.IncomeMapper;
import com.hy.gf.model.Page;

@Service
public class IncomeBiz {

@Resource
IncomeMapper incomeMapper;

	public int delete(Long id) {
		return incomeMapper.delete(id);
	}
	public int insert(Income record) {
		return incomeMapper.insert(record);
	}
	public Income select(Long id) {
		return incomeMapper.select(id);
	}
	public int update(Income record) {
		return incomeMapper.update(record);
	}
	public Income selectOneByExample(Income record) {
		return incomeMapper.selectOneByExample(record);
	}
	public Page<Income> selectByExample(Page<Income> page) {
Object example = page.getExample();
Income income = null;
if (example != null) {
income = (Income) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(income);
objectMap.putAll(objectMap2);List<Income> selectByExample = incomeMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = incomeMapper.selectByExampleCount(income);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		incomeMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return incomeMapper.deleteBatch(ids);
}
public List<Income> find(Map<String, Object> map) {
	return incomeMapper.find(map);
}
public int findCount(Map<String, Object> map) {
	return incomeMapper.findCount(map);
}
public List<Income> forIncomeEcharts(Long id) {
	return incomeMapper.forIncomeEcharts(id);
}}