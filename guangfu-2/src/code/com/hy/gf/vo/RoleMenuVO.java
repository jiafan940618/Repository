package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.RoleMenu;


public class RoleMenuVO extends RoleMenu {

	private List<RoleMenu> roleMenus;

	public void setRoleMenus(List<RoleMenu> roleMenus){
		this.roleMenus=roleMenus;
	}

	public List<RoleMenu>  getRoleMenus(){
		return roleMenus;
	}


}
