package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Device;
import com.hy.gf.util.*;
import com.hy.gf.mapper.DeviceMapper;
import com.hy.gf.model.Page;

@Service
public class DeviceBiz {

@Resource
DeviceMapper deviceMapper;

	public int delete(Long id) {
		return deviceMapper.delete(id);
	}
	public int insert(Device record) {
		return deviceMapper.insert(record);
	}
	public Device select(Long id) {
		return deviceMapper.select(id);
	}
	public int update(Device record) {
		return deviceMapper.update(record);
	}
	public Device selectOneByExample(Device record) {
		return deviceMapper.selectOneByExample(record);
	}
	public Page<Device> selectByExample(Page<Device> page) {
Object example = page.getExample();
Device device = null;
if (example != null) {
device = (Device) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(device);
objectMap.putAll(objectMap2);List<Device> selectByExample = deviceMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = deviceMapper.selectByExampleCount(device);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		deviceMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return deviceMapper.deleteBatch(ids);
}}