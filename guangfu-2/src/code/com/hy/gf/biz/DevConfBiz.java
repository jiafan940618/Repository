package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.DevConf;
import com.hy.gf.util.*;
import com.hy.gf.mapper.DevConfMapper;
import com.hy.gf.model.Page;

@Service
public class DevConfBiz {

@Resource
DevConfMapper devConfMapper;

	public int delete(Long id) {
		return devConfMapper.delete(id);
	}
	public int insert(DevConf record) {
		return devConfMapper.insert(record);
	}
	public DevConf select(Long id) {
		return devConfMapper.select(id);
	}
	public int update(DevConf record) {
		return devConfMapper.update(record);
	}
	public DevConf selectOneByExample(DevConf record) {
		return devConfMapper.selectOneByExample(record);
	}
	public Page<DevConf> selectByExample(Page<DevConf> page) {
		Object example = page.getExample();
		DevConf devConf = null;
		if (example != null) {
		devConf = (DevConf) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(devConf);
		objectMap.putAll(objectMap2);
		List<DevConf> selectByExample = devConfMapper.selectByExample(objectMap);
		page.setList(selectByExample);
		page.setExample(null);
		Integer selectByExampleCount = devConfMapper.selectByExampleCount(devConf);
		page.setTotal(selectByExampleCount);
		return page;
	}
	public List insertBatch(List list) {
		devConfMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return devConfMapper.deleteBatch(ids);
}}