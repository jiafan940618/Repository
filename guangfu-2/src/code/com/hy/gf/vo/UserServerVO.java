package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.UserServer;


public class UserServerVO extends UserServer {

	private List<UserServer> userServers;

	public void setUserServers(List<UserServer> userServers){
		this.userServers=userServers;
	}

	public List<UserServer>  getUserServers(){
		return userServers;
	}


}
