package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Push;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface PushMapper {

	int delete(Long id);

	Push select(Long id);

	int insert(Push record);

	Push selectByPK(Long id);

	int update(Push record);

	Push selectOneByExample(Push record);

	List<Push> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Push push);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}