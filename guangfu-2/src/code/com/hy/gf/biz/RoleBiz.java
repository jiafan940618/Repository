package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Role;
import com.hy.gf.util.*;
import com.hy.gf.mapper.RoleMapper;
import com.hy.gf.model.Page;

@Service
public class RoleBiz {

@Resource
RoleMapper roleMapper;

	public int delete(Long id) {
		return roleMapper.delete(id);
	}
	public int insert(Role record) {
		return roleMapper.insert(record);
	}
	public Role select(Long id) {
		return roleMapper.select(id);
	}
	public int update(Role record) {
		return roleMapper.update(record);
	}
	public Role selectOneByExample(Role record) {
		return roleMapper.selectOneByExample(record);
	}
	public Page<Role> selectByExample(Page<Role> page) {
Object example = page.getExample();
Role role = null;
if (example != null) {
role = (Role) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(role);
objectMap.putAll(objectMap2);List<Role> selectByExample = roleMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = roleMapper.selectByExampleCount(role);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		roleMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return roleMapper.deleteBatch(ids);
}}