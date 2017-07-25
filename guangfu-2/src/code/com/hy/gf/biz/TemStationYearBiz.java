package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.TemStationYear;
import com.hy.gf.util.*;
import com.hy.gf.mapper.TemStationYearMapper;
import com.hy.gf.model.Page;

@Service
public class TemStationYearBiz {

@Resource
TemStationYearMapper temStationYearMapper;

	public int delete(Long id) {
		return temStationYearMapper.delete(id);
	}
	public int insert(TemStationYear record) {
		return temStationYearMapper.insert(record);
	}
	public TemStationYear select(Long id) {
		return temStationYearMapper.select(id);
	}
	public int update(TemStationYear record) {
		return temStationYearMapper.update(record);
	}
	public TemStationYear selectOneByExample(TemStationYear record) {
		return temStationYearMapper.selectOneByExample(record);
	}
	public Page<TemStationYear> selectByExample(Page<TemStationYear> page) {
Object example = page.getExample();
TemStationYear temStationYear = null;
if (example != null) {
temStationYear = (TemStationYear) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(temStationYear);
objectMap.putAll(objectMap2);List<TemStationYear> selectByExample = temStationYearMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = temStationYearMapper.selectByExampleCount(temStationYear);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		temStationYearMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return temStationYearMapper.deleteBatch(ids);
}

	public TemStationYear getThisMonthTotalPower(String stationCode) {
		return temStationYearMapper.getThisMonthTotalPower(stationCode);
	}
	public List<TemStationYear> findEveryMonth(String stationCode) {
		return temStationYearMapper.findEveryMonth(stationCode);
	}
	public TemStationYear findEachMonth(Map<String, Object> map) {
		return temStationYearMapper.findEachMonth(map);
	}
	public TemStationYear findHuanbao(Map<String, Object> map) {
		return temStationYearMapper.findHuanbao(map);
	}
	public List<TemStationYear> findYesterdayPower() {
		return temStationYearMapper.findYesterdayPower();
	}
	public List<TemStationYear> eachdayKWH(Map<String, Object> paramMap) {
		return temStationYearMapper.eachdayKWH(paramMap);
	}
	public List<TemStationYear> kwhByTime(Map<String, Object> map) {
		return temStationYearMapper.kwhByTime(map);
	}

}