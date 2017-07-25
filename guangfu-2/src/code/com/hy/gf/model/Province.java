package com.hy.gf.model;

import java.util.*;


public class Province {

	private Long id;

	private String provinceName;

	private Date createDtm;


	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setProvinceName(String provinceName){
		this.provinceName=provinceName;
	}

	public String getProvinceName(){
		return provinceName;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
