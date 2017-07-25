package com.hy.gf.model;

import java.util.*;
import java.math.*;


public class DeviceType {

	private Long id;

	private String typetext;

	private String parentId;

	private String remark;

	private Date createDtm;
	
	private List<DeviceType> devicesList;


	public List<DeviceType> getDevicesList() {
		return devicesList;
	}

	public void setDevicesList(List<DeviceType> devicesList) {
		this.devicesList = devicesList;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setTypetext(String typetext){
		this.typetext=typetext;
	}

	public String getTypetext(){
		return typetext;
	}

	public void setParentId(String parentId){
		this.parentId=parentId;
	}

	public String getParentId(){
		return parentId;
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
