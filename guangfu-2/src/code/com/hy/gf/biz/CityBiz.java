package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.City;
import com.hy.gf.util.*;
import com.hy.gf.vo.CityVO;
import com.hy.gf.mapper.CityMapper;
import com.hy.gf.model.Page;

@Service
public class CityBiz {

@Resource
CityMapper cityMapper;

	public int delete(Long id) {
		return cityMapper.delete(id);
	}
	public int insert(City record) {
		return cityMapper.insert(record);
	}
	public City select(Long id) {
		return cityMapper.select(id);
	}
	public int update(City record) {
		return cityMapper.update(record);
	}
	public City selectOneByExample(City record) {
		return cityMapper.selectOneByExample(record);
	}
	public Page<City> selectByExample(Page<City> page) {
Object example = page.getExample();
City city = null;
if (example != null) {
city = (City) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(city);
objectMap.putAll(objectMap2);List<City> selectByExample = cityMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = cityMapper.selectByExampleCount(city);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		cityMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return cityMapper.deleteBatch(ids);
}

	public List<City> findCityListByProvinceId(Long provinceId) {
		return cityMapper.findCityListByProvinceId(provinceId);
	}
	
	public List<City> findByProvinceId(Long provinceId) {
		return cityMapper.findByProvinceId(provinceId);
	}
	
	public CityVO findProAndCity(Long id) {
		return cityMapper.findProAndCity(id);
	}
	public String selectName(Integer city_id) {
		return cityMapper.selectName(city_id);
	}

}