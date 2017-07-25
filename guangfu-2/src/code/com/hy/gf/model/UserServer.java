package com.hy.gf.model;

import java.util.*;


public class UserServer {

	private Long id;

	private Integer userId;

	private Integer serverId;

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

	public void setServerId(Integer serverId){
		this.serverId=serverId;
	}

	public Integer getServerId(){
		return serverId;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
