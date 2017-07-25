package com.hy.gf.model;

import java.util.*;


public class RoleMenu {

	private Long id;

	private Integer roleId;

	private Integer menuId;

	private Date createDtm;


	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setRoleId(Integer roleId){
		this.roleId=roleId;
	}

	public Integer getRoleId(){
		return roleId;
	}

	public void setMenuId(Integer menuId){
		this.menuId=menuId;
	}

	public Integer getMenuId(){
		return menuId;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
