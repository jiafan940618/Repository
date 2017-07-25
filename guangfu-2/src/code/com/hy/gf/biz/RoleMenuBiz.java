package com.hy.gf.biz;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.RoleMenu;
import com.hy.gf.util.*;
import com.hy.gf.mapper.MenuMapper;
import com.hy.gf.mapper.RoleMenuMapper;
import com.hy.gf.model.Menu;
import com.hy.gf.model.Page;

@Service
public class RoleMenuBiz {

@Resource
RoleMenuMapper roleMenuMapper;
@Resource
MenuMapper menuMapper;

	public int delete(Long id) {
		return roleMenuMapper.delete(id);
	}
	public int insert(RoleMenu record) {
		return roleMenuMapper.insert(record);
	}
	public RoleMenu select(Long id) {
		return roleMenuMapper.select(id);
	}
	public int update(RoleMenu record) {
		return roleMenuMapper.update(record);
	}
	public RoleMenu selectOneByExample(RoleMenu record) {
		return roleMenuMapper.selectOneByExample(record);
	}
	public Page<RoleMenu> selectByExample(Page<RoleMenu> page) {
Object example = page.getExample();
RoleMenu roleMenu = null;
if (example != null) {
roleMenu = (RoleMenu) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(roleMenu);
objectMap.putAll(objectMap2);List<RoleMenu> selectByExample = roleMenuMapper.selectByExample(objectMap);



page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = roleMenuMapper.selectByExampleCount(roleMenu);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List<RoleMenu> list) {
		/*roleMenuMapper.delMenuByRoleId(list.get(0).getRoleId());
		List<RoleMenu> list2 = new ArrayList<>();
		List<String> parentIds = new ArrayList<>();
		for(int i=0; i<list.size(); i++){
			list2.add(list.get(i));
			Menu menu = MenuMapper.select(list.get(i).getMenuId().longValue());
			RoleMenu newRm = new RoleMenu();
			parentIds.add(menu.getParentId().toString());
			
			newRm.setMenuId(menu.getParentId().intValue());
			newRm.setRoleId(list.get(0).getRoleId());
			list2.add(newRm);
		}
		roleMenuMapper.insertBatch(list2);*/
		
		roleMenuMapper.delMenuByRoleId(list.get(0).getRoleId());
		List<RoleMenu> list2 = new ArrayList<>();
		List<String> parentIds = new ArrayList<>();
		for(int i=0; i<list.size(); i++){
			roleMenuMapper.insert(list.get(i));
			Menu menu = menuMapper.select(list.get(i).getMenuId().longValue());
			
			RoleMenu newRm2 = new RoleMenu();
			newRm2.setRoleId(list.get(0).getRoleId());
			newRm2.setMenuId(menu.getParentId().intValue());
			int total = roleMenuMapper.selectByExampleCount(newRm2);
			if(total == 0){
				RoleMenu newRm = new RoleMenu();
				newRm.setMenuId(newRm2.getMenuId());
				newRm.setRoleId(list.get(0).getRoleId());
				roleMenuMapper.insert(newRm);
			}
			
			
			/*list2.add(list.get(i));
			Menu menu = MenuMapper.select(list.get(i).getMenuId().longValue());
			RoleMenu newRm = new RoleMenu();
			newRm.setMenuId(menu.getParentId().intValue());
			newRm.setRoleId(list.get(0).getRoleId());
			list2.add(newRm);*/
		}
		
		
		return list;
}	public int deleteBatch(List ids) {
		return roleMenuMapper.deleteBatch(ids);
}}