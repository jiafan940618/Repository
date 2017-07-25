package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Information;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface InformationMapper {

	int delete(Long id);

	Information select(Long id);

	int insert(Information record);

	Information selectByPK(Long id);

	int update(Information record);

	Information selectOneByExample(Information record);

	List<Information> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Information information);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}