package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.TemStation;
import com.hy.gf.util.*;
import com.hy.gf.mapper.TemStationMapper;
import com.hy.gf.model.Page;

@Service
public class TemStationBiz {

@Resource
TemStationMapper temStationMapper;

	public int delete(Long id) {
		return temStationMapper.delete(id);
	}
	public int insert(TemStation record) {
		return temStationMapper.insert(record);
	}
	public TemStation select(Long id) {
		return temStationMapper.select(id);
	}
	public int update(TemStation record) {
		return temStationMapper.update(record);
	}
	public TemStation selectOneByExample(TemStation record) {
		return temStationMapper.selectOneByExample(record);
	}
	public Page<TemStation> selectByExample(Page<TemStation> page) {
Object example = page.getExample();
TemStation temStation = null;
if (example != null) {
temStation = (TemStation) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(temStation);
objectMap.putAll(objectMap2);List<TemStation> selectByExample = temStationMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = temStationMapper.selectByExampleCount(objectMap);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		temStationMapper.insertBatch(list);
		return list;
	}	
	public int deleteBatch(List ids) {
		return temStationMapper.deleteBatch(ids);
	}
	
	
	public List<TemStation> getTotalPower(Map<String, Object> map) {
		return temStationMapper.getTotalPower(map);
	}
	public List<TemStation> getOneDayPower(Map<String, Object> paramMap) {
		return temStationMapper.getOneDayPower(paramMap);
	}
	/**
	 * 根据日期查找电站发电情况
	 */
	public List<Map> findPowerByDate(Map<String, Object> map) {
		return temStationMapper.findPowerByDate(map);
	}
	public TemStation findMomentByStationCode(String stationCode) {
		return temStationMapper.findMomentByStationCode(stationCode);
	}
	/**
	 * 全网累计发电量：
	 * 传station_code查找某电站的全网累计发电量，不传查所有电站的全网累计发电量
	 */
	public Double findTotalKwh(Map<String, Object> map) {
		return temStationMapper.findTotalKwh(map);
	}
	/**
	 * 根据时间段，返回这段时间内每天的发电量，以天为单位
	 */
	public List<TemStation> eachDayByTime(Map<String, Object> map) {
		return temStationMapper.eachDayByTime(map);
	}
	/**
	 * 昨日发电量列表
	 */
	public List<TemStation> yesterdayTotalPower() {
		return temStationMapper.yesterdayTotalPower();
	}
	public List<Map> energyInfoEcharts(Map<String, Object> map) {
		return temStationMapper.energyInfoEcharts(map);
	}
}