package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.UserRole;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface UserRoleMapper {

	int delete(Long id);

	UserRole select(Long id);

	int insert(UserRole record);

	UserRole selectByPK(Long id);

	int update(UserRole record);

	UserRole selectOneByExample(UserRole record);

	List<UserRole> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(UserRole userRole);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}