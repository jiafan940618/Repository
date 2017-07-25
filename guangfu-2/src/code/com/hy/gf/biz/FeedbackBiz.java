package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Feedback;
import com.hy.gf.util.*;
import com.hy.gf.mapper.FeedbackMapper;
import com.hy.gf.model.Page;

@Service
public class FeedbackBiz {

@Resource
FeedbackMapper feedbackMapper;

	public int delete(Long id) {
		return feedbackMapper.delete(id);
	}
	public int insert(Feedback record) {
		return feedbackMapper.insert(record);
	}
	public Feedback select(Long id) {
		return feedbackMapper.select(id);
	}
	public int update(Feedback record) {
		return feedbackMapper.update(record);
	}
	public Feedback selectOneByExample(Feedback record) {
		return feedbackMapper.selectOneByExample(record);
	}
	public Page<Feedback> selectByExample(Page<Feedback> page) {
Object example = page.getExample();
Feedback feedback = null;
if (example != null) {
feedback = (Feedback) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(feedback);
objectMap.putAll(objectMap2);List<Feedback> selectByExample = feedbackMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = feedbackMapper.selectByExampleCount(feedback);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		feedbackMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return feedbackMapper.deleteBatch(ids);
}}