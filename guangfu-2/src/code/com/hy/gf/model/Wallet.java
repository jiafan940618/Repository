package com.hy.gf.model;

import java.util.*;
import java.math.*;


public class Wallet {

	private Long id;

	private Long userId;
	
	private Long serverId;
	
	private Integer type; // 1为用户，2为服务商

	private Double money;

	private String alipayAccount;

	private String yibaoAccount;

	private Integer jifen;
	
	private Integer status; // 0未冻结，1已冻结

	private Date createDtm;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setMoney(Double money){
		this.money=money;
	}

	public Double getMoney(){
		return money;
	}

	public void setAlipayAccount(String alipayAccount){
		this.alipayAccount=alipayAccount;
	}

	public String getAlipayAccount(){
		return alipayAccount;
	}

	public void setYibaoAccount(String yibaoAccount){
		this.yibaoAccount=yibaoAccount;
	}

	public String getYibaoAccount(){
		return yibaoAccount;
	}

	public void setJifen(Integer jifen){
		this.jifen=jifen;
	}

	public Integer getJifen(){
		return jifen;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
