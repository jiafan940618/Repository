package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.SystemConfig;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface SystemConfigMapper {

	int delete(Long id);

	SystemConfig select(Long id);

	int insert(SystemConfig record);

	SystemConfig selectByPK(Long id);

	int update(SystemConfig record);

	SystemConfig selectOneByExample(SystemConfig record);

	List<SystemConfig> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(SystemConfig systemConfig);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
	
	String findValueByKey(String propertyKey);
}