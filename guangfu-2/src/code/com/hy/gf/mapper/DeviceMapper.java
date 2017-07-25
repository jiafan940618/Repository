package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Device;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface DeviceMapper {

	int delete(Long id);

	Device select(Long id);

	int insert(Device record);

	Device selectByPK(Long id);

	int update(Device record);

	Device selectOneByExample(Device record);

	List<Device> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Device device);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}