package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Evaluate;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface EvaluateMapper {

	int delete(Long id);

	Evaluate select(Long id);

	int insert(Evaluate record);

	Evaluate selectByPK(Long id);

	int update(Evaluate record);

	Evaluate selectOneByExample(Evaluate record);

	List<Evaluate> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Evaluate evaluate);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}