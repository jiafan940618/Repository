package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.ColConf;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface ColConfMapper {

	int delete(Long id);

	ColConf select(Long id);

	int insert(ColConf record);

	ColConf selectByPK(Long id);

	int update(ColConf record);

	ColConf selectOneByExample(ColConf record);

	List<ColConf> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(ColConf colConf);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}