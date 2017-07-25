package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Feedback;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface FeedbackMapper {

	int delete(Long id);

	Feedback select(Long id);

	int insert(Feedback record);

	Feedback selectByPK(Long id);

	int update(Feedback record);

	Feedback selectOneByExample(Feedback record);

	List<Feedback> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Feedback feedback);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}