package com.hy.gf.model;

import java.util.*;


public class Address {

	private Long id;

	private String province;

	private String city;

	private String district;

	private String detailed;

	private String full;

	private Date createDtm;


	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setProvince(String province){
		this.province=province;
	}

	public String getProvince(){
		return province;
	}

	public void setCity(String city){
		this.city=city;
	}

	public String getCity(){
		return city;
	}

	public void setDistrict(String district){
		this.district=district;
	}

	public String getDistrict(){
		return district;
	}

	public void setDetailed(String detailed){
		this.detailed=detailed;
	}

	public String getDetailed(){
		return detailed;
	}

	public void setFull(String full){
		this.full=full;
	}

	public String getFull(){
		return full;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
