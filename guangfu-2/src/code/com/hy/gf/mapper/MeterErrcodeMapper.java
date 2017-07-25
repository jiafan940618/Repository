package com.hy.gf.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hy.gf.model.MeterErrcode;

@Component
public interface MeterErrcodeMapper {

	int delete(Long id);

	MeterErrcode select(Long id);

	int insert(MeterErrcode record);

	MeterErrcode selectByPK(Long id);

	int update(MeterErrcode record);

	MeterErrcode selectOneByExample(MeterErrcode record);

	List<MeterErrcode> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(MeterErrcode meterErrcode);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
	
	MeterErrcode selectByErrcode(String errcode);
}