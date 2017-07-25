package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.UserRole;


public class UserRoleVO extends UserRole {

	private List<UserRole> userRoles;

	public void setUserRoles(List<UserRole> userRoles){
		this.userRoles=userRoles;
	}

	public List<UserRole>  getUserRoles(){
		return userRoles;
	}


}
