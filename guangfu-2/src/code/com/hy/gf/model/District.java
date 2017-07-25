package com.hy.gf.model;

import java.util.*;


public class District {

	private Long id;

	private String districtName;

	private Integer cityId;

	private Date createDtm;


	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setDistrictName(String districtName){
		this.districtName=districtName;
	}

	public String getDistrictName(){
		return districtName;
	}

	public void setCityId(Integer cityId){
		this.cityId=cityId;
	}

	public Integer getCityId(){
		return cityId;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
