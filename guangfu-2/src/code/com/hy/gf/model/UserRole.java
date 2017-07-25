package com.hy.gf.model;

import java.util.*;


public class UserRole {

	private Long id;

	private Integer userId;

	private Integer roleId;

	private Integer employeeId;

	private String remark;

	private Date createDtm;


	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setRoleId(Integer roleId){
		this.roleId=roleId;
	}

	public Integer getRoleId(){
		return roleId;
	}

	public void setEmployeeId(Integer employeeId){
		this.employeeId=employeeId;
	}

	public Integer getEmployeeId(){
		return employeeId;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
