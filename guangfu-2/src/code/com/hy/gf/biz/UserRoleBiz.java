package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.UserRole;
import com.hy.gf.util.*;
import com.hy.gf.mapper.UserRoleMapper;
import com.hy.gf.model.Page;

@Service
public class UserRoleBiz {

@Resource
UserRoleMapper userRoleMapper;

	public int delete(Long id) {
		return userRoleMapper.delete(id);
	}
	public int insert(UserRole record) {
		return userRoleMapper.insert(record);
	}
	public UserRole select(Long id) {
		return userRoleMapper.select(id);
	}
	public int update(UserRole record) {
		return userRoleMapper.update(record);
	}
	public UserRole selectOneByExample(UserRole record) {
		return userRoleMapper.selectOneByExample(record);
	}
	public Page<UserRole> selectByExample(Page<UserRole> page) {
Object example = page.getExample();
UserRole userRole = null;
if (example != null) {
userRole = (UserRole) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(userRole);
objectMap.putAll(objectMap2);List<UserRole> selectByExample = userRoleMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = userRoleMapper.selectByExampleCount(userRole);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		userRoleMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return userRoleMapper.deleteBatch(ids);
}}