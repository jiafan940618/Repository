package com.hy.gf.biz;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Station;
import com.hy.gf.util.*;
import com.hy.gf.mapper.StationMapper;
import com.hy.gf.model.Ammeter;
import com.hy.gf.model.Page;

@Service
public class StationBiz {

	@Resource
	StationMapper stationMapper;
	@Resource
	AmmeterBiz ammeterBiz;

	public int delete(Long id) {
		return stationMapper.delete(id);
	}

	public int insert(Station record) {
		return stationMapper.insert(record);
	}

	public Station select(Long id) {
		Station select = stationMapper.select(id);
		List<Station> list = new ArrayList<>();
		list.add(select);
		List<Station> workTotalKwhAddAmmeterInitKwh = workTotalKwhAddAmmeterInitKwh(list);
		Station station = workTotalKwhAddAmmeterInitKwh.get(0);
		return station;
	}

	public int update(Station record) {
		return stationMapper.update(record);
	}

	public Station selectOneByExample(Station record) {
		Station selectOneByExample = stationMapper.selectOneByExample(record);
		List<Station> list = new ArrayList<>();
		list.add(selectOneByExample);
		List<Station> workTotalKwhAddAmmeterInitKwh = workTotalKwhAddAmmeterInitKwh(list);
		Station station = workTotalKwhAddAmmeterInitKwh.get(0);
		return station;
	}

	public Page<Station> selectByExample(Page<Station> page) {
		Object example = page.getExample();
		Station station = null;
		if (example != null) {
			station = (Station) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(station);
		objectMap.putAll(objectMap2);
		List<Station> selectByExample = stationMapper.selectByExample(objectMap);
		List<Station> workTotalKwhAddAmmeterInitKwh = workTotalKwhAddAmmeterInitKwh(selectByExample); // 发电总量加上电表的初始电量
		page.setList(workTotalKwhAddAmmeterInitKwh);
		page.setExample(null);
		Integer selectByExampleCount = stationMapper.selectByExampleCount(station);
		page.setTotal(selectByExampleCount);
		return page;
	}

	public List insertBatch(List list) {
		stationMapper.insertBatch(list);
		return list;
	}

	public int deleteBatch(List ids) {
		return stationMapper.deleteBatch(ids);
	}

	public List<Map> findIncreaseStation(Map<String, Object> map) {
		return stationMapper.findIncreaseStation(map);
	}

	public List<Map> stationFenbu(Map<String, Object> map) {
		return stationMapper.stationFenbu(map);
	}

	/**
	 * 所有已绑定电表的电站的实时功率之和，装机容量之和
	 */
	public Station selectCapacityKwTotal(Map<String, Object> map) {
		return stationMapper.selectCapacityKwTotal(map);
	}
	
	/**
	 * 发电总量加上电表的初始电量
	 * @param list
	 * @return
	 */
	public List<Station> workTotalKwhAddAmmeterInitKwh(List<Station> list) {
		for (Station station : list) {
			Double totalInitKwh = 0D;
			List<Ammeter> findByStationId = ammeterBiz.findByStationId(station.getId());
			for (Ammeter ammeter : findByStationId) {
				totalInitKwh += ammeter.getInitKwh();
			}
			station.setWorkTotaKwh(station.getWorkTotaKwh() + totalInitKwh);
		}
		return list;
	}

}