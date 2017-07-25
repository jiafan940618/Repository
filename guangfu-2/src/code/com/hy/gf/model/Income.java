package com.hy.gf.model;

import java.util.*;
import java.math.*;


public class Income {

	private Long id;

	private Integer userId;

	private Integer walletId;

	private Integer withdrawId;

	private Double count;

	private Double money;

	private Integer type;

	private Date createDtm;


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

	public void setWalletId(Integer walletId){
		this.walletId=walletId;
	}

	public Integer getWalletId(){
		return walletId;
	}

	public void setWithdrawId(Integer withdrawId){
		this.withdrawId=withdrawId;
	}

	public Integer getWithdrawId(){
		return withdrawId;
	}

	public void setCount(Double count){
		this.count=count;
	}

	public Double getCount(){
		return count;
	}

	public void setMoney(Double money){
		this.money=money;
	}

	public Double getMoney(){
		return money;
	}

	public void setType(Integer type){
		this.type=type;
	}

	public Integer getType(){
		return type;
	}

	public Date getCreateDtm() {
		return createDtm;
	}

	public void setCreateDtm(Date createDtm) {
		this.createDtm = createDtm;
	}

}
