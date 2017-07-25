package com.hy.gf.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hy.gf.model.Menu;
import com.hy.gf.vo.MenuVO;

@Component
public interface MenuMapper {

	int delete(Long id);

	Menu select(Long id);

	int insert(Menu record);

	Menu selectByPK(Long id);

	int update(Menu record);

	Menu selectOneByExample(Menu record);

	List<Menu> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Menu menu);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	List<MenuVO> selectByUserRole(Map<String, Object> map);
}