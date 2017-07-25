package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.User;


public class UserVO extends User {

	String code4register;
	
	private String query;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getCode4register() {
		return code4register;
	}

	public void setCode4register(String code4register) {
		this.code4register = code4register;
	}
	
}
