package com.hy.gf.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hy.gf.model.DevConf;

@Component
public interface DevConfMapper {

	int delete(Long id);

	DevConf select(Long id);

	int insert(DevConf record);

	DevConf selectByPK(Long id);

	int update(DevConf record);

	DevConf selectOneByExample(DevConf record);

	List<DevConf> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(DevConf devConf);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}