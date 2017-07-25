package com.hy.gf.model;

import java.util.*;
import java.math.*;


public class Subsidy {

	private Long id;

	private Double selfuse;

	private Double sell;
	
	private Double sellPrice; // 出售电价（元/度）

	private Double subsidy;

	private Integer bsidyYear;

	private Double unlsubsidy;

	private Integer unlsubsidyYear;

	private Long provinceId;

	private String provinceName;

	private Integer isProvince;

	private Long cityId;

	private String cityName;

	private Double initialsubsidy;
	
	private String sunCount;

	private Date createDtm;
	
	private Integer type;

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSunCount() {
		return sunCount;
	}

	public void setSunCount(String sunCount) {
		this.sunCount = sunCount;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setSelfuse(Double selfuse){
		this.selfuse=selfuse;
	}

	public Double getSelfuse(){
		return selfuse;
	}

	public void setSell(Double sell){
		this.sell=sell;
	}

	public Double getSell(){
		return sell;
	}

	public void setSubsidy(Double subsidy){
		this.subsidy=subsidy;
	}

	public Double getSubsidy(){
		return subsidy;
	}

	public void setBsidyYear(Integer bsidyYear){
		this.bsidyYear=bsidyYear;
	}

	public Integer getBsidyYear(){
		return bsidyYear;
	}

	public void setUnlsubsidy(Double unlsubsidy){
		this.unlsubsidy=unlsubsidy;
	}

	public Double getUnlsubsidy(){
		return unlsubsidy;
	}

	public void setUnlsubsidyYear(Integer unlsubsidyYear){
		this.unlsubsidyYear=unlsubsidyYear;
	}

	public Integer getUnlsubsidyYear(){
		return unlsubsidyYear;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public void setIsProvince(Integer isProvince){
		this.isProvince=isProvince;
	}

	public Integer getIsProvince(){
		return isProvince;
	}
	
	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public void setCityName(String cityName){
		this.cityName=cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setInitialsubsidy(Double initialsubsidy){
		this.initialsubsidy=initialsubsidy;
	}

	public Double getInitialsubsidy(){
		return initialsubsidy;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
