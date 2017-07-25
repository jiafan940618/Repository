package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Withdraw;
import com.hy.gf.util.*;
import com.hy.gf.mapper.WithdrawMapper;
import com.hy.gf.model.Page;

@Service
public class WithdrawBiz {

@Resource
WithdrawMapper withdrawMapper;

	public int delete(Long id) {
		return withdrawMapper.delete(id);
	}
	public int insert(Withdraw record) {
		return withdrawMapper.insert(record);
	}
	public Withdraw select(Long id) {
		return withdrawMapper.select(id);
	}
	public int update(Withdraw record) {
		return withdrawMapper.update(record);
	}
	public Withdraw selectOneByExample(Withdraw record) {
		return withdrawMapper.selectOneByExample(record);
	}
	public Page<Withdraw> selectByExample(Page<Withdraw> page) {
Object example = page.getExample();
Withdraw withdraw = null;
if (example != null) {
withdraw = (Withdraw) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(withdraw);
objectMap.putAll(objectMap2);List<Withdraw> selectByExample = withdrawMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = withdrawMapper.selectByExampleCount(withdraw);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		withdrawMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return withdrawMapper.deleteBatch(ids);
}
	public List<Withdraw> dixianFilter(Map<String, Object> map) {
		return withdrawMapper.dixianFilter(map);
	}
	public int dixianFilterCount(Map<String, Object> map) {
		return withdrawMapper.dixianFilterCount(map);
	}
}