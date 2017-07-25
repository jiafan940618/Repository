package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.MeterRecord;
import com.hy.gf.util.*;
import com.hy.gf.mapper.MeterRecordMapper;
import com.hy.gf.model.Page;

@Service
public class MeterRecordBiz {

	@Resource
	MeterRecordMapper meterRecordMapper;

	public int delete(Long id) {
		return meterRecordMapper.delete(id);
	}

	public int insert(MeterRecord record) {
		return meterRecordMapper.insert(record);
	}

	public MeterRecord select(Long id) {
		return meterRecordMapper.select(id);
	}

	public int update(MeterRecord record) {
		return meterRecordMapper.update(record);
	}

	public MeterRecord selectOneByExample(MeterRecord record) {
		return meterRecordMapper.selectOneByExample(record);
	}

	public Page<MeterRecord> selectByExample(Page<MeterRecord> page) {
		Object example = page.getExample();
		MeterRecord meterRecord = null;
		if (example != null) {
			meterRecord = (MeterRecord) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(meterRecord);
		objectMap.putAll(objectMap2);
		List<MeterRecord> selectByExample = meterRecordMapper.selectByExample(objectMap);
		page.setList(selectByExample);
		page.setExample(null);
		Integer selectByExampleCount = meterRecordMapper.selectByExampleCount(objectMap);
		page.setTotal(selectByExampleCount);
		return page;
	}

	public List insertBatch(List list) {
		meterRecordMapper.insertBatch(list);
		return list;
	}

	public int deleteBatch(List ids) {
		return meterRecordMapper.deleteBatch(ids);
	}
}