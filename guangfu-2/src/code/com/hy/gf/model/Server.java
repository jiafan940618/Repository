package com.hy.gf.model;

import java.util.*;


public class Server {

	private Long id;

	private String type;

	private String serverName;

	private String addressText;

	private Double registeredCapital;

	private Date registeredDt;

	private String contactName;

	private String contactPhone;
	
	private String salemanPhone; // 业务员电话，用于接收短信通知

	private String businessUrl;

	private String legalPerson;

	private String legalPersonPhone;

	private Double companyAssets; //公司资产

	private String businessText;

	private Integer state;

	private Double capacity; // 每月装配能力

	private String useCouponcode;

	private String couponcode;

	private String texture;

	private Double pricea;

	private Double priceb;

	private Double priceaRing; // 市场单价 20kw以下

	private Double pricebRing; // 市场单价 20kw以上

	private Double sqmElectric; // 平米数转换成瓦
	
	private Double wattPrice; // 每瓦多少钱

	private String aptitude;

	private Double threeMonths;

	private Integer bankDraft;

	private Integer projectSizeA;

	private Integer projectSizeB;

	private Integer epc;

	private Long userId;

	private String serverCityIds;
	
	private String serverCity;

	private Double maxPersonSize;

	private Double maxProjectSize;

	private Double designPrice;
	
	private Double falloff; // 衰减值
	
	private Double cvr; // 转化率
	
	private Double moduleQuality; // 质保年限（组件）
	
	private Double adjunctQuality; // 质保年限（附件）

	private Date createDtm;
	
	private String phone;

	private String password;
	
	private String email;
	
	private String realName;
	
	private Integer roleId;

	private String imgUrl; // 服务商头像地址
	
	private String banner_url; // 服务商banner地址
	
	private Double factorage; // 优能收取服务商的手续费率
	
	private Integer top; // 置顶参数
	
	public String getBanner_url() {
		return banner_url;
	}

	public void setBanner_url(String banner_url) {
		this.banner_url = banner_url;
	}

	public String getSalemanPhone() {
		return salemanPhone;
	}

	public void setSalemanPhone(String salemanPhone) {
		this.salemanPhone = salemanPhone;
	}

	public Double getFactorage() {
		return factorage;
	}

	public void setFactorage(Double factorage) {
		this.factorage = factorage;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerCityIds() {
		return serverCityIds;
	}

	public void setServerCityIds(String serverCityIds) {
		this.serverCityIds = serverCityIds;
	}

	public Double getCvr() {
		return cvr;
	}

	public void setCvr(Double cvr) {
		this.cvr = cvr;
	}

	public Double getModuleQuality() {
		return moduleQuality;
	}

	public void setModuleQuality(Double moduleQuality) {
		this.moduleQuality = moduleQuality;
	}

	public Double getAdjunctQuality() {
		return adjunctQuality;
	}

	public void setAdjunctQuality(Double adjunctQuality) {
		this.adjunctQuality = adjunctQuality;
	}

	public Double getFalloff() {
		return falloff;
	}

	public void setFalloff(Double falloff) {
		this.falloff = falloff;
	}

	public Double getSqmElectric() {
		return sqmElectric;
	}

	public void setSqmElectric(Double sqmElectric) {
		this.sqmElectric = sqmElectric;
	}

	public Double getWattPrice() {
		return wattPrice;
	}

	public void setWattPrice(Double wattPrice) {
		this.wattPrice = wattPrice;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setType(String type){
		this.type=type;
	}

	public String getType(){
		return type;
	}

	public void setServerName(String serverName){
		this.serverName=serverName;
	}

	public String getServerName(){
		return serverName;
	}

	public void setAddressText(String addressText){
		this.addressText=addressText;
	}

	public String getAddressText(){
		return addressText;
	}

	public void setRegisteredCapital(Double registeredCapital){
		this.registeredCapital=registeredCapital;
	}

	public Double getRegisteredCapital(){
		return registeredCapital;
	}

	public void setRegisteredDt(Date registeredDt){
		this.registeredDt=registeredDt;
	}

	public Date getRegisteredDt(){
		return registeredDt;
	}

	public void setContactName(String contactName){
		this.contactName=contactName;
	}

	public String getContactName(){
		return contactName;
	}

	public void setContactPhone(String contactPhone){
		this.contactPhone=contactPhone;
	}

	public String getContactPhone(){
		return contactPhone;
	}

	public void setBusinessUrl(String businessUrl){
		this.businessUrl=businessUrl;
	}

	public String getBusinessUrl(){
		return businessUrl;
	}

	public void setLegalPerson(String legalPerson){
		this.legalPerson=legalPerson;
	}

	public String getLegalPerson(){
		return legalPerson;
	}

	public void setLegalPersonPhone(String legalPersonPhone){
		this.legalPersonPhone=legalPersonPhone;
	}

	public String getLegalPersonPhone(){
		return legalPersonPhone;
	}

	public Double getCompanyAssets() {
		return companyAssets;
	}

	public void setCompanyAssets(Double companyAssets) {
		this.companyAssets = companyAssets;
	}

	public void setBusinessText(String businessText){
		this.businessText=businessText;
	}

	public String getBusinessText(){
		return businessText;
	}

	public void setState(Integer state){
		this.state=state;
	}

	public Integer getState(){
		return state;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public void setUseCouponcode(String useCouponcode){
		this.useCouponcode=useCouponcode;
	}

	public String getUseCouponcode(){
		return useCouponcode;
	}

	public void setCouponcode(String couponcode){
		this.couponcode=couponcode;
	}

	public String getCouponcode(){
		return couponcode;
	}

	public void setTexture(String texture){
		this.texture=texture;
	}

	public String getTexture(){
		return texture;
	}

	public void setPricea(Double pricea){
		this.pricea=pricea;
	}

	public Double getPricea(){
		return pricea;
	}

	public void setPriceb(Double priceb){
		this.priceb=priceb;
	}

	public Double getPriceb(){
		return priceb;
	}

	public Double getPriceaRing() {
		return priceaRing;
	}

	public void setPriceaRing(Double priceaRing) {
		this.priceaRing = priceaRing;
	}

	public Double getPricebRing() {
		return pricebRing;
	}

	public void setPricebRing(Double pricebRing) {
		this.pricebRing = pricebRing;
	}

	public void setAptitude(String aptitude){
		this.aptitude=aptitude;
	}

	public String getAptitude(){
		return aptitude;
	}

	public Double getThreeMonths() {
		return threeMonths;
	}

	public void setThreeMonths(Double threeMonths) {
		this.threeMonths = threeMonths;
	}

	public void setBankDraft(Integer bankDraft){
		this.bankDraft=bankDraft;
	}

	public Integer getBankDraft(){
		return bankDraft;
	}

	public void setProjectSizeA(Integer projectSizeA){
		this.projectSizeA=projectSizeA;
	}

	public Integer getProjectSizeA(){
		return projectSizeA;
	}

	public void setProjectSizeB(Integer projectSizeB){
		this.projectSizeB=projectSizeB;
	}

	public Integer getProjectSizeB(){
		return projectSizeB;
	}

	public void setEpc(Integer epc){
		this.epc=epc;
	}

	public Integer getEpc(){
		return epc;
	}

	public void setUserId(Long userId){
		this.userId=userId;
	}

	public Long getUserId(){
		return userId;
	}

	public void setServerCity(String serverCity){
		this.serverCity=serverCity;
	}

	public String getServerCity(){
		return serverCity;
	}

	public void setMaxPersonSize(Double maxPersonSize){
		this.maxPersonSize=maxPersonSize;
	}

	public Double getMaxPersonSize(){
		return maxPersonSize;
	}

	public void setMaxProjectSize(Double maxProjectSize){
		this.maxProjectSize=maxProjectSize;
	}

	public Double getMaxProjectSize(){
		return maxProjectSize;
	}

	public void setDesignPrice(Double designPrice){
		this.designPrice=designPrice;
	}

	public Double getDesignPrice(){
		return designPrice;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

}
