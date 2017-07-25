package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.District;
import com.hy.gf.util.*;
import com.hy.gf.mapper.DistrictMapper;
import com.hy.gf.model.Page;

@Service
public class DistrictBiz {

@Resource
DistrictMapper districtMapper;

	public int delete(Long id) {
		return districtMapper.delete(id);
	}
	public int insert(District record) {
		return districtMapper.insert(record);
	}
	public District select(Long id) {
		return districtMapper.select(id);
	}
	public int update(District record) {
		return districtMapper.update(record);
	}
	public District selectOneByExample(District record) {
		return districtMapper.selectOneByExample(record);
	}
	public Page<District> selectByExample(Page<District> page) {
Object example = page.getExample();
District district = null;
if (example != null) {
district = (District) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(district);
objectMap.putAll(objectMap2);List<District> selectByExample = districtMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = districtMapper.selectByExampleCount(district);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		districtMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return districtMapper.deleteBatch(ids);
}}