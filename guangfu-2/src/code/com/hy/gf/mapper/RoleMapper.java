package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Role;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface RoleMapper {

	int delete(Long id);

	Role select(Long id);

	int insert(Role record);

	Role selectByPK(Long id);

	int update(Role record);

	Role selectOneByExample(Role record);

	List<Role> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Role role);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}