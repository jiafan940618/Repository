package com.hy.gf.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.DeviceType;
import com.hy.gf.model.Menu;
import com.hy.gf.util.*;
import com.hy.gf.vo.MenuVO;
import com.hy.gf.mapper.DeviceTypeMapper;
import com.hy.gf.model.Page;

@Service
public class DeviceTypeBiz {

@Resource
DeviceTypeMapper deviceTypeMapper;

	public int delete(Long id) {
		return deviceTypeMapper.delete(id);
	}
	public int insert(DeviceType record) {
		return deviceTypeMapper.insert(record);
	}
	public DeviceType select(Long id) {
		return deviceTypeMapper.select(id);
	}
	public int update(DeviceType record) {
		return deviceTypeMapper.update(record);
	}
	public DeviceType selectOneByExample(DeviceType record) {
		return deviceTypeMapper.selectOneByExample(record);
	}
	public Page<DeviceType> selectByExample(Page<DeviceType> page) {
Object example = page.getExample();
DeviceType deviceType = null;
if (example != null) {
deviceType = (DeviceType) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(deviceType);
objectMap.putAll(objectMap2);List<DeviceType> selectByExample = deviceTypeMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = deviceTypeMapper.selectByExampleCount(deviceType);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		deviceTypeMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return deviceTypeMapper.deleteBatch(ids);
}
public List<DeviceType> selectByExample2(Page<DeviceType> page, int type) {
	Object example = page.getExample();
	DeviceType deviceType = null;
	if (example != null) {
	deviceType = (DeviceType) example;
	}
	Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
	Map<String, Object> objectMap2 = ObjToMap.getObjectMap(deviceType);
	objectMap.putAll(objectMap2);
	List<DeviceType> selectByExample = deviceTypeMapper.selectByExample(objectMap);
	List<DeviceType> firstDevice = new ArrayList <DeviceType>();
	for (int i = 0; i < selectByExample.size(); i++) {
		DeviceType devicetype2 = selectByExample.get(i);
		if (Integer.parseInt(devicetype2.getParentId()) == type) {
			firstDevice.add(devicetype2);
		}
	}
	List<Object> devices = new ArrayList<Object>();
	for (int i = 0; i < firstDevice.size(); i++) {
		List<DeviceType> secondevice = new ArrayList<>();
		Long id = firstDevice.get(i).getId();
		for (int j = 0; j < selectByExample.size(); j++) {
			DeviceType device = selectByExample.get(j);
			if (Integer.parseInt(device.getParentId()) == id ) {
				secondevice.add(device);
			}
		}
		firstDevice.get(i).setDevicesList(secondevice);;
		devices.add(firstDevice.get(i));
	}
	
	return firstDevice;
}
}