package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.News;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface NewsMapper {

	int delete(Long id);

	News select(Long id);

	int insert(News record);

	News selectByPK(Long id);

	int update(News record);

	News selectOneByExample(News record);

	List<News> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(News news);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}