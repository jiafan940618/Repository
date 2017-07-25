package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Menu;


public class MenuVO extends Menu {

	private List<Menu> menus;
	
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setMenus(List<Menu> menus){
		this.menus=menus;
	}

	public List<Menu>  getMenus(){
		return menus;
	}


}
