package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.TemStationYear;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface TemStationYearMapper {

	int delete(Long id);

	TemStationYear select(Long id);

	int insert(TemStationYear record);

	TemStationYear selectByPK(Long id);

	int update(TemStationYear record);

	TemStationYear selectOneByExample(TemStationYear record);

	List<TemStationYear> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(TemStationYear temStationYear);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	TemStationYear getThisMonthTotalPower(String stationCode);

	List<TemStationYear> findEveryMonth(String stationCode);
	
//	用来计算优能补贴
	List<TemStationYear> findYesterdayPower();

	TemStationYear findEachMonth(Map<String, Object> map);

	TemStationYear findHuanbao(Map<String, Object> map);

	List<TemStationYear> eachdayKWH(Map<String, Object> paramMap);
	
	List<TemStationYear> kwhByTime(Map<String, Object> map);
}