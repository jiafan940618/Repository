package com.hy.gf.model;

import java.util.*;
import java.math.*;


public class Device {

	private Long id;

	private Long serveId;

	private String serveName;

	private Integer deviveTypeId;

	private String deviceTypeText;

	private String name;

	private String brand;

	private String model;

	private Double price;

	private String unit;

	private String quality;

	private String remark;

	private String createDtm;
	
	private Integer brandId;


	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setServeId(Long serveId){
		this.serveId=serveId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Long getServeId(){
		return serveId;
	}

	public void setServeName(String serveName){
		this.serveName=serveName;
	}

	public String getServeName(){
		return serveName;
	}

	public void setDeviveTypeId(Integer deviveTypeId){
		this.deviveTypeId=deviveTypeId;
	}

	public Integer getDeviveTypeId(){
		return deviveTypeId;
	}

	public void setDeviceTypeText(String deviceTypeText){
		this.deviceTypeText=deviceTypeText;
	}

	public String getDeviceTypeText(){
		return deviceTypeText;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setBrand(String brand){
		this.brand=brand;
	}

	public String getBrand(){
		return brand;
	}

	public void setModel(String model){
		this.model=model;
	}

	public String getModel(){
		return model;
	}

	public void setPrice(Double price){
		this.price=price;
	}

	public Double getPrice(){
		return price;
	}

	public void setUnit(String unit){
		this.unit=unit;
	}

	public String getUnit(){
		return unit;
	}

	public void setQuality(String quality){
		this.quality=quality;
	}

	public String getQuality(){
		return quality;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setCreateDtm(String createDtm){
		this.createDtm=createDtm;
	}

	public String getCreateDtm(){
		return createDtm;
	}

}
