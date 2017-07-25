package com.hy.gf.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hy.gf.model.MeterRecord;

@Component
public interface MeterRecordMapper {

	int delete(Long id);

	MeterRecord select(Long id);

	int insert(MeterRecord record);

	MeterRecord selectByPK(Long id);

	int update(MeterRecord record);

	MeterRecord selectOneByExample(MeterRecord record);

	List<MeterRecord> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Map<String, Object> objectMap);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
	 
	
}