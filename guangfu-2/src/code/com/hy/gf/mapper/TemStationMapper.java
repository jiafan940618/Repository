package com.hy.gf.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hy.gf.model.TemStation;

@Component
public interface TemStationMapper {

	int delete(Long id);

	TemStation select(Long id);

	int insert(TemStation record);

	TemStation selectByPK(Long id);

	int update(TemStation record);

	TemStation selectOneByExample(TemStation record);

	List<TemStation> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Map<String, Object> objectMap);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	List<TemStation> getTotalPower(Map<String, Object> map);

	List<TemStation> getOneDayPower(Map<String, Object> paramMap);

	List<TemStation> yesterdayTotalPower();

	List<Map> findPowerByDate(Map<String, Object> map);

	TemStation findMomentByStationCode(String stationCode);

	Double findTotalKwh(Map<String, Object> map);

	List<TemStation> eachDayByTime(Map<String, Object> map);

	List<Map> energyInfoEcharts(Map<String, Object> map);
	
}