package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.UserServer;
import com.hy.gf.util.*;
import com.hy.gf.mapper.UserServerMapper;
import com.hy.gf.model.Page;

@Service
public class UserServerBiz {

@Resource
UserServerMapper userServerMapper;

	public int delete(Long id) {
		return userServerMapper.delete(id);
	}
	public int insert(UserServer record) {
		return userServerMapper.insert(record);
	}
	public UserServer select(Long id) {
		return userServerMapper.select(id);
	}
	public int update(UserServer record) {
		return userServerMapper.update(record);
	}
	public UserServer selectOneByExample(UserServer record) {
		return userServerMapper.selectOneByExample(record);
	}
	public Page<UserServer> selectByExample(Page<UserServer> page) {
Object example = page.getExample();
UserServer userServer = null;
if (example != null) {
userServer = (UserServer) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(userServer);
objectMap.putAll(objectMap2);List<UserServer> selectByExample = userServerMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = userServerMapper.selectByExampleCount(userServer);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		userServerMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return userServerMapper.deleteBatch(ids);
}}