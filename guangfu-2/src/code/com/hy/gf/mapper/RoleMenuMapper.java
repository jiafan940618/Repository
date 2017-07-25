package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.RoleMenu;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface RoleMenuMapper {

	int delete(Long id);

	RoleMenu select(Long id);

	int insert(RoleMenu record);

	RoleMenu selectByPK(Long id);

	int update(RoleMenu record);

	RoleMenu selectOneByExample(RoleMenu record);

	List<RoleMenu> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(RoleMenu roleMenu);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	void delMenuByRoleId(Integer roleId);
}