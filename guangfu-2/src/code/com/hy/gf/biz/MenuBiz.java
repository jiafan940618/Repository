package com.hy.gf.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.mapper.MenuMapper;
import com.hy.gf.model.Menu;
import com.hy.gf.model.Page;
import com.hy.gf.util.ObjToMap;
import com.hy.gf.vo.MenuVO;

@Service
public class MenuBiz {

	@Resource
	MenuMapper menuMapper;

	public int delete(Long id) {
		return menuMapper.delete(id);
	}

	public int insert(Menu record) {
		return menuMapper.insert(record);
	}

	public Menu select(Long id) {
		return menuMapper.select(id);
	}

	public int update(Menu record) {
		return menuMapper.update(record);
	}

	public Menu selectOneByExample(Menu record) {
		return menuMapper.selectOneByExample(record);
	}

	public Page<Menu> selectByExample(Page<Menu> page) {
		Object example = page.getExample();
		Menu menu = null;
		if (example != null) {
			menu = (Menu) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(menu);
		objectMap.putAll(objectMap2);
		List<Menu> selectByExample = menuMapper.selectByExample(objectMap);
		page.setList(selectByExample);
		page.setExample(null);
		Integer selectByExampleCount = menuMapper.selectByExampleCount(menu);
		page.setTotal(selectByExampleCount);
		return page;
	}

	public List insertBatch(List list) {
		menuMapper.insertBatch(list);
		return list;
	}

	public int deleteBatch(List ids) {
		return menuMapper.deleteBatch(ids);
	}

	public List<MenuVO> selectByUserRole(Map<String, Object> map) {
		List<MenuVO> selectByExample = menuMapper.selectByUserRole(map);
		// 取level==1（第一级目录）的menu
		List<MenuVO> firstMenus = new ArrayList<MenuVO>();
		for (int i = 0; i < selectByExample.size(); i++) {
			MenuVO menuVO1 = selectByExample.get(i);
			if (menuVO1.getLevel() == 1) {
				firstMenus.add(menuVO1);
			}
		}
		List<Object> menus = new ArrayList<Object>();
		for (int i = 0; i < firstMenus.size(); i++) {
			List<Menu> secondMenus = new ArrayList<>();
			Long id = firstMenus.get(i).getId();// id为t_menu id
			for (int j = 0; j < selectByExample.size(); j++) {
				Menu menu = selectByExample.get(j);
				if (menu.getParentId().intValue() == id && menu.getLevel() == 2) {
					secondMenus.add(menu);
				}
			}
			firstMenus.get(i).setMenus(secondMenus);
			menus.add(firstMenus.get(i));
		}
		return firstMenus;
	}

}