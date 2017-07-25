package com.hy.gf.model;

import java.util.Date;


public class User {

	private Long id;

	private Integer roleId;

	private String imgUrl;

	private String username;

	private String phone;

	private String password;
	
	private Integer province_id;
	
	private Integer city_id;

	private String addressText;
	
	private String full_address;

	private Integer sex;

	private String email;

	private String realName;

	private String privilegeCode;

	private String privilegeCodeInit;

	private String openIda;

	private String openIdb;

	private String openIdc;

	private Date createDtm;


	public String getFull_address() {
		return full_address;
	}

	public void setFull_address(String full_address) {
		this.full_address = full_address;
	}

	public Integer getProvince_id() {
		return province_id;
	}

	public void setProvince_id(Integer province_id) {
		this.province_id = province_id;
	}

	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setRoleId(Integer roleId){
		this.roleId=roleId;
	}

	public Integer getRoleId(){
		return roleId;
	}

	public void setImgUrl(String imgUrl){
		this.imgUrl=imgUrl;
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public void setUsername(String username){
		this.username=username;
	}

	public String getUsername(){
		return username;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public String getPassword(){
		return password;
	}

	public void setAddressText(String addressText){
		this.addressText=addressText;
	}

	public String getAddressText(){
		return addressText;
	}

	public void setSex(Integer sex){
		this.sex=sex;
	}

	public Integer getSex(){
		return sex;
	}

	public void setEmail(String email){
		this.email=email;
	}

	public String getEmail(){
		return email;
	}

	public void setRealName(String realName){
		this.realName=realName;
	}

	public String getRealName(){
		return realName;
	}

	public void setPrivilegeCode(String privilegeCode){
		this.privilegeCode=privilegeCode;
	}

	public String getPrivilegeCode(){
		return privilegeCode;
	}

	public void setPrivilegeCodeInit(String privilegeCodeInit){
		this.privilegeCodeInit=privilegeCodeInit;
	}

	public String getPrivilegeCodeInit(){
		return privilegeCodeInit;
	}

	public void setOpenIda(String openIda){
		this.openIda=openIda;
	}

	public String getOpenIda(){
		return openIda;
	}

	public void setOpenIdb(String openIdb){
		this.openIdb=openIdb;
	}

	public String getOpenIdb(){
		return openIdb;
	}

	public void setOpenIdc(String openIdc){
		this.openIdc=openIdc;
	}

	public String getOpenIdc(){
		return openIdc;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
