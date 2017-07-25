package com.hy.gf.model;

import java.util.*;


public class City {
	
/** ≈¨¡¶≤‚ ‘£°£°£°*/
	private Long id;

	private String cityName;

	private Long provinceId;

	private Date createDtm;


	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setCityName(String cityName){
		this.cityName=cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
