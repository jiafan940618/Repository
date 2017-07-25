package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Role;


public class RoleVO extends Role {

	private List<Role> roles;

	public void setRoles(List<Role> roles){
		this.roles=roles;
	}

	public List<Role>  getRoles(){
		return roles;
	}


}
