package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Province;
import com.hy.gf.util.*;
import com.hy.gf.mapper.ProvinceMapper;
import com.hy.gf.model.Page;

@Service
public class ProvinceBiz {

	@Resource
	ProvinceMapper provinceMapper;

	public int delete(Long id) {
		return provinceMapper.delete(id);
	}

	public int insert(Province record) {
		return provinceMapper.insert(record);
	}

	public Province select(Long id) {
		return provinceMapper.select(id);
	}

	public int update(Province record) {
		return provinceMapper.update(record);
	}

	public Province selectOneByExample(Province record) {
		return provinceMapper.selectOneByExample(record);
	}

	public Page<Province> selectByExample(Page<Province> page) {
		Object example = page.getExample();
		Province province = null;
		if (example != null) {
			province = (Province) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(province);
		objectMap.putAll(objectMap2);
		List<Province> selectByExample = provinceMapper.selectByExample(objectMap);
		page.setList(selectByExample);
		page.setExample(null);
		Integer selectByExampleCount = provinceMapper.selectByExampleCount(province);
		page.setTotal(selectByExampleCount);
		return page;
	}

	public List insertBatch(List list) {
		provinceMapper.insertBatch(list);
		return list;
	}

	public int deleteBatch(List ids) {
		return provinceMapper.deleteBatch(ids);
	}

	public List<Province> findProvinceList() {
		return provinceMapper.findProvinceList();
	}

	public String selectName(Integer province_id) {
		return provinceMapper.selectName(province_id);
	}
}