package com.hy.gf.vo;

import java.util.*;
import java.math.*;

import com.hy.gf.model.User;
import com.hy.gf.model.Withdraw;


public class WithdrawVO extends Withdraw {

	private String query;
	
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private List<Withdraw> withdraws;

	public void setWithdraws(List<Withdraw> withdraws){
		this.withdraws=withdraws;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Withdraw>  getWithdraws(){
		return withdraws;
	}


}
