package com.hy.gf.model;

import java.util.*;
import java.math.*;


public class Withdraw {

	private Long id;

	private Integer userId;
	
	private Integer serverId;
	
	private Integer type; // 0为普通用户提现，1为服务商提现

	private Double money;

	private Integer cardId;

	private String cardNum;
	
	private String cardUserName;

	private String cardAddress;

	private Integer status;

	private String handleText;

	private Date handleDtm;

	private Date createDtm;
	
	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCardUserName() {
		return cardUserName;
	}

	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
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

	public void setMoney(Double money){
		this.money=money;
	}

	public Double getMoney(){
		return money;
	}

	public void setCardId(Integer cardId){
		this.cardId=cardId;
	}

	public Integer getCardId(){
		return cardId;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public void setCardAddress(String cardAddress){
		this.cardAddress=cardAddress;
	}

	public String getCardAddress(){
		return cardAddress;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setHandleText(String handleText){
		this.handleText=handleText;
	}

	public String getHandleText(){
		return handleText;
	}

	public void setHandleDtm(Date handleDtm){
		this.handleDtm=handleDtm;
	}

	public Date getHandleDtm(){
		return handleDtm;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
