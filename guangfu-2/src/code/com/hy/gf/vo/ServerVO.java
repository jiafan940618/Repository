package com.hy.gf.vo;

import java.util.*;
import java.math.*;

import com.hy.gf.model.Device;
import com.hy.gf.model.Server;


public class ServerVO extends Server {

	private List<Device> device;
	
	private String query;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Device> getDevice() {
		return device;
	}

	public void setDevice(List<Device> device) {
		this.device = device;
	}

	public List<Device> getDevices() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
