package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.DeviceType;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface DeviceTypeMapper {

	int delete(Long id);

	DeviceType select(Long id);

	int insert(DeviceType record);

	DeviceType selectByPK(Long id);

	int update(DeviceType record);

	DeviceType selectOneByExample(DeviceType record);

	List<DeviceType> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(DeviceType deviceType);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}