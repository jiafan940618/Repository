package com.hy.gf.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hy.gf.model.User;

@Component
public interface UserMapper {

	int delete(Long id);

	User select(Long id);

	int insert(User record);

	User selectByPK(Long id);

	int update(User record);

	User selectOneByExample(User record);

	List<User> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(User user);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	User selectByPhone(String phone);

	int updatePassword(User record);
}