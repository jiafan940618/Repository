package com.hy.gf.model;

import java.util.*;


public class Ammeter {

	private Long id;

	private String code;

	private Long provinceId;

	private String provinceText;

	private Long cityId;

	private String cityText;

	private String addressText;

	private Long userId;

	private String stationId;

	private Integer workTotaTm;

	private Double workTotaKwh;

	private Double nowKw;

	private Date workDtm;

	private Date upDtm;

	private Date downDtm;

	private Date outfactoryDt;

	private Date updateDtm;

	private String state;
	
	private String sim;
	
	private Double initKwh; // 初始电量   init_kwh

	private Date createDtm;
	

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getInitKwh() {
		return initKwh;
	}

	public void setInitKwh(Double initKwh) {
		this.initKwh = initKwh;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setCode(String code){
		this.code=code;
	}

	public String getCode(){
		return code;
	}

	public void setProvinceText(String provinceText){
		this.provinceText=provinceText;
	}

	public String getProvinceText(){
		return provinceText;
	}

	public String getCityText() {
		return cityText;
	}

	public void setCityText(String cityText) {
		this.cityText = cityText;
	}

	public void setAddressText(String addressText){
		this.addressText=addressText;
	}

	public String getAddressText(){
		return addressText;
	}

	public void setWorkTotaTm(Integer workTotaTm){
		this.workTotaTm=workTotaTm;
	}

	public Integer getWorkTotaTm(){
		return workTotaTm;
	}

	public Double getWorkTotaKwh() {
		return workTotaKwh;
	}

	public void setWorkTotaKwh(Double workTotaKwh) {
		this.workTotaKwh = workTotaKwh;
	}

	public Double getNowKw() {
		return nowKw;
	}

	public void setNowKw(Double nowKw) {
		this.nowKw = nowKw;
	}

	public void setWorkDtm(Date workDtm){
		this.workDtm=workDtm;
	}

	public Date getWorkDtm(){
		return workDtm;
	}

	public void setUpDtm(Date upDtm){
		this.upDtm=upDtm;
	}

	public Date getUpDtm(){
		return upDtm;
	}

	public void setDownDtm(Date downDtm){
		this.downDtm=downDtm;
	}

	public Date getDownDtm(){
		return downDtm;
	}

	public void setOutfactoryDt(Date outfactoryDt){
		this.outfactoryDt=outfactoryDt;
	}

	public Date getOutfactoryDt(){
		return outfactoryDt;
	}

	public void setUpdateDtm(Date updateDtm){
		this.updateDtm=updateDtm;
	}

	public Date getUpdateDtm(){
		return updateDtm;
	}

	public void setState(String state){
		this.state=state;
	}

	public String getState(){
		return state;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

}
