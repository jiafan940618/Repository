package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Ammeter;
import com.hy.gf.util.*;
import com.hy.gf.mapper.AmmeterMapper;
import com.hy.gf.model.Page;

@Service
public class AmmeterBiz {

	@Resource
	AmmeterMapper ammeterMapper;

	public int delete(Long id) {
		return ammeterMapper.delete(id);
	}

	public int insert(Ammeter record) {
		return ammeterMapper.insert(record);
	}

	public Ammeter select(Long id) {
		return ammeterMapper.select(id);
	}

	public int update(Ammeter record) {
		return ammeterMapper.update(record);
	}

	public Ammeter selectOneByExample(Ammeter record) {
		return ammeterMapper.selectOneByExample(record);
	}

	public Page<Ammeter> selectByExample(Page<Ammeter> page) {
		Object example = page.getExample();
		Ammeter ammeter = null;
		if (example != null) {
			ammeter = (Ammeter) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(ammeter);
		objectMap.putAll(objectMap2);
		List<Ammeter> selectByExample = ammeterMapper.selectByExample(objectMap);
		page.setList(selectByExample);
		page.setExample(null);
		Integer selectByExampleCount = ammeterMapper.selectByExampleCount(ammeter);
		page.setTotal(selectByExampleCount);
		return page;
	}

	public List insertBatch(List list) {
		ammeterMapper.insertBatch(list);
		return list;
	}

	public int deleteBatch(List ids) {
		return ammeterMapper.deleteBatch(ids);
	}

	public Ammeter selectByCode(String code) {
		return ammeterMapper.selectByCode(code);
	}
	
	/**
	 * 根据电站id查找电表
	 * @param stationId
	 * @return
	 */
	public List<Ammeter> findByStationId(Long stationId) {
		Page<Ammeter> page = new Page<>();
		page.setLimit(Integer.MAX_VALUE);
		Ammeter ammeter = new Ammeter();
		ammeter.setStationId(stationId.toString());
		page.setExample(ammeter);
		
		Page<Ammeter> selectByExample = selectByExample(page);
		List<Ammeter> list = selectByExample.getList();
		
		return list;
	}

}