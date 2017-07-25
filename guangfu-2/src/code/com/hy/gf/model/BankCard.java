package com.hy.gf.model;

import java.util.*;


public class BankCard {

	private Long id;

	private Integer userId;
	
	private Integer serverId;

	private String imgUrl;

	private String bankAddress;

	private String bankNum;

	private String username;

	private Integer bankId;

	private String type;
	
	private Integer state;

	private Date createDtm;

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setImgUrl(String imgUrl){
		this.imgUrl=imgUrl;
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public void setBankAddress(String bankAddress){
		this.bankAddress=bankAddress;
	}

	public String getBankAddress(){
		return bankAddress;
	}

	public void setBankNum(String bankNum){
		this.bankNum=bankNum;
	}

	public String getBankNum(){
		return bankNum;
	}

	public void setUsername(String username){
		this.username=username;
	}

	public String getUsername(){
		return username;
	}

	public void setBankId(Integer bankId){
		this.bankId=bankId;
	}

	public Integer getBankId(){
		return bankId;
	}

	public void setType(String type){
		this.type=type;
	}

	public String getType(){
		return type;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
