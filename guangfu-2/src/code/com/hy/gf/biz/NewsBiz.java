package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.News;
import com.hy.gf.util.*;
import com.hy.gf.mapper.NewsMapper;
import com.hy.gf.model.Page;

@Service
public class NewsBiz {

@Resource
NewsMapper newsMapper;

	public int delete(Long id) {
		return newsMapper.delete(id);
	}
	public int insert(News record) {
		return newsMapper.insert(record);
	}
	public News select(Long id) {
		return newsMapper.select(id);
	}
	public int update(News record) {
		return newsMapper.update(record);
	}
	public News selectOneByExample(News record) {
		return newsMapper.selectOneByExample(record);
	}
	public Page<News> selectByExample(Page<News> page) {
Object example = page.getExample();
News news = null;
if (example != null) {
news = (News) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(news);
objectMap.putAll(objectMap2);List<News> selectByExample = newsMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = newsMapper.selectByExampleCount(news);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		newsMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return newsMapper.deleteBatch(ids);
}}