package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Server;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface ServerMapper {

	int delete(Long id);

	Server select(Long id);

	int insert(Server record);

	Server selectByPK(Long id);

	int update(Server record);

	Server selectOneByExample(Server record);

	List<Server> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Server server);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	List<Server> find(Map<String, Object> map);
	Long getTotal(Map<String, Object> map);
}