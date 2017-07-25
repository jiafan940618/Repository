package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.SystemConfig;
import com.hy.gf.util.*;
import com.hy.gf.mapper.SystemConfigMapper;
import com.hy.gf.model.Page;

@Service
public class SystemConfigBiz {

	@Resource
	SystemConfigMapper systemConfigMapper;

	public int delete(Long id) {
		return systemConfigMapper.delete(id);
	}

	public int insert(SystemConfig record) {
		return systemConfigMapper.insert(record);
	}

	public SystemConfig select(Long id) {
		return systemConfigMapper.select(id);
	}

	public int update(SystemConfig record) {
		return systemConfigMapper.update(record);
	}

	public SystemConfig selectOneByExample(SystemConfig record) {
		return systemConfigMapper.selectOneByExample(record);
	}

	public Page<SystemConfig> selectByExample(Page<SystemConfig> page) {
		Object example = page.getExample();
		SystemConfig systemConfig = null;
		if (example != null) {
			systemConfig = (SystemConfig) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(systemConfig);
		objectMap.putAll(objectMap2);
		List<SystemConfig> selectByExample = systemConfigMapper.selectByExample(objectMap);
		page.setList(selectByExample);
		page.setExample(null);
		Integer selectByExampleCount = systemConfigMapper.selectByExampleCount(systemConfig);
		page.setTotal(selectByExampleCount);
		return page;
	}

	public List insertBatch(List list) {
		systemConfigMapper.insertBatch(list);
		return list;
	}

	public int deleteBatch(List ids) {
		return systemConfigMapper.deleteBatch(ids);
	}
	
	public String findValueByKey(String propertyKey) {
		return systemConfigMapper.findValueByKey(propertyKey);
	}

}