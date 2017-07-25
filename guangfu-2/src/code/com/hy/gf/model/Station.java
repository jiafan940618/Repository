package com.hy.gf.model;

import java.util.*;


public class Station {

	private Long id;

	private String stationName;

	private String stationCode;
	
	private Long orderId;

	private Long userId;

	private String userName;

	private Double capacity;

	private Long provinceId;

	private String provinceText;

	private Long cityId;

	private String cityText;

	private String addressText;

	private Integer workTotaTm;

	private Double workTotaKwh;

	private Double nowKw;

	private Date workDtm;

	private Date upDtm;

	private Date downDtm;

	private String ammeterCode;

	private Integer state;

	private String privilegeCode;

	private Long serverId;
	
	private Integer type; //1.居民，2工业，3商业，4农业

	private Date createDtm;
	
	private List<Ammeter> ammeter;
	
	private String query;
	
	private Order order;
	
	private User user;
	
	private Server server;
	
	private String ammeter_initKwh_total; // 电站的所有电表的初始电量，以逗号分割
	
	private Integer status;
	
	private String meter_state_desc; // 电表状态描述
	
	private Integer del;
	
	private Date del_dtm;
	
	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public Date getDel_dtm() {
		return del_dtm;
	}

	public void setDel_dtm(Date del_dtm) {
		this.del_dtm = del_dtm;
	}

	public String getAmmeter_initKwh_total() {
		return ammeter_initKwh_total;
	}

	public void setAmmeter_initKwh_total(String ammeter_initKwh_total) {
		this.ammeter_initKwh_total = ammeter_initKwh_total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Ammeter> getAmmeter() {
		return ammeter;
	}

	public void setAmmeter(List<Ammeter> ammeter) {
		this.ammeter = ammeter;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setStationName(String stationName){
		this.stationName=stationName;
	}

	public String getStationName(){
		return stationName;
	}

	public void setStationCode(String stationCode){
		this.stationCode=stationCode;
	}

	public String getStationCode(){
		return stationCode;
	}

	public void setUserId(Long userId){
		this.userId=userId;
	}

	public Long getUserId(){
		return userId;
	}

	public void setUserName(String userName){
		this.userName=userName;
	}

	public String getUserName(){
		return userName;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}
	
	public void setProvinceText(String provinceText){
		this.provinceText=provinceText;
	}

	public String getProvinceText(){
		return provinceText;
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

	public void setCityText(String cityText){
		this.cityText=cityText;
	}

	public String getCityText(){
		return cityText;
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

	public void setAmmeterCode(String ammeterCode){
		this.ammeterCode=ammeterCode;
	}

	public String getAmmeterCode(){
		return ammeterCode;
	}

	public void setState(Integer state){
		this.state=state;
	}

	public Integer getState(){
		return state;
	}

	public void setPrivilegeCode(String privilegeCode){
		this.privilegeCode=privilegeCode;
	}

	public String getPrivilegeCode(){
		return privilegeCode;
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMeter_state_desc() {
		return meter_state_desc;
	}

	public void setMeter_state_desc(String meter_state_desc) {
		this.meter_state_desc = meter_state_desc;
	}

}
