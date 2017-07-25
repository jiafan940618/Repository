package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Station;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface StationMapper {

	int delete(Long id);

	Station select(Long id);

	int insert(Station record);

	Station selectByPK(Long id);

	int update(Station record);

	Station selectOneByExample(Station record);

	List<Station> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Station station);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	List<Map> findIncreaseStation(Map<String, Object> map);

	List<Map> stationFenbu(Map<String, Object> map);

	Station selectCapacityKwTotal(Map<String, Object> map);
}