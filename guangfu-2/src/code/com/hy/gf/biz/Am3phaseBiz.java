package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Am3phase;
import com.hy.gf.util.*;
import com.hy.gf.mapper.Am3phaseMapper;
import com.hy.gf.model.Page;

@Service
public class Am3phaseBiz {

	@Resource
	Am3phaseMapper am3phaseMapper;

	public int delete(Long id) {
		return am3phaseMapper.delete(id);
	}

	public int insert(Am3phase record) {
		return am3phaseMapper.insert(record);
	}

	public Am3phase select(Long id) {
		return am3phaseMapper.select(id);
	}

	public int update(Am3phase record) {
		return am3phaseMapper.update(record);
	}

	public Am3phase selectOneByExample(Am3phase record) {
		return am3phaseMapper.selectOneByExample(record);
	}
	
	

	public Page<Am3phase> selectByExample(Page<Am3phase> page) {
		Object example = page.getExample();
		Am3phase am3phase = null;
		if (example != null) {
			am3phase = (Am3phase) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(am3phase);
		objectMap.putAll(objectMap2);
		List<Am3phase> selectByExample = am3phaseMapper.selectByExample(objectMap);
		page.setList(selectByExample);
		page.setExample(null);
		Integer selectByExampleCount = am3phaseMapper.selectByExampleCount(am3phase);
		page.setTotal(selectByExampleCount);
		return page;
	}

	public List insertBatch(List list) {
		am3phaseMapper.insertBatch(list);
		return list;
	}

	public int deleteBatch(List ids) {
		return am3phaseMapper.deleteBatch(ids);
	}
}