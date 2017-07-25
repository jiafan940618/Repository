package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.UserServer;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface UserServerMapper {

	int delete(Long id);

	UserServer select(Long id);

	int insert(UserServer record);

	UserServer selectByPK(Long id);

	int update(UserServer record);

	UserServer selectOneByExample(UserServer record);

	List<UserServer> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(UserServer userServer);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}