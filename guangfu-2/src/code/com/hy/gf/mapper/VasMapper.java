package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Vas;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface VasMapper {

	int delete(Long id);

	Vas select(Long id);

	int insert(Vas record);

	Vas selectByPK(Long id);

	int update(Vas record);

	Vas selectOneByExample(Vas record);

	List<Vas> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Vas vas);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}