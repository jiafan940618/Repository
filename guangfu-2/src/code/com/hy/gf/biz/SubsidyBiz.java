package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Subsidy;
import com.hy.gf.util.*;
import com.hy.gf.mapper.SubsidyMapper;
import com.hy.gf.model.Page;

@Service
public class SubsidyBiz {

	@Resource
	SubsidyMapper subsidyMapper;

	public int delete(Long id) {
		return subsidyMapper.delete(id);
	}

	public int insert(Subsidy record) {
		return subsidyMapper.insert(record);
	}

	public Subsidy select(Long id) {
		return subsidyMapper.select(id);
	}

	public int update(Subsidy record) {
		return subsidyMapper.update(record);
	}

	public Subsidy selectOneByExample(Subsidy record) {
		return subsidyMapper.selectOneByExample(record);
	}

	public Page<Subsidy> selectByExample(Page<Subsidy> page) {
		Object example = page.getExample();
		Subsidy subsidy = null;
		if (example != null) {
			subsidy = (Subsidy) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(subsidy);
		objectMap.putAll(objectMap2);
		List<Subsidy> selectByExample = subsidyMapper.selectByExample(objectMap);
		page.setList(selectByExample);
		page.setExample(null);
		Integer selectByExampleCount = subsidyMapper.selectByExampleCount(subsidy);
		page.setTotal(selectByExampleCount);
		return page;
	}

	public List insertBatch(List list) {
		subsidyMapper.insertBatch(list);
		return list;
	}

	public int deleteBatch(List ids) {
		return subsidyMapper.deleteBatch(ids);
	}

	public List<Long> selectProvince() {
		return subsidyMapper.selectProvince();
	}

	public List<Long> selectCityByProvinceId(Long province_id) {
		return subsidyMapper.selectCityByProvinceId(province_id);
	}
}