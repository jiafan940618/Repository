package com.hy.gf.vo;

import java.util.*;
import java.math.*;
import com.hy.gf.model.Device;


public class DeviceVO extends Device {

	private List<Device> devices;

	public void setDevices(List<Device> devices){
		this.devices=devices;
	}

	public List<Device>  getDevices(){
		return devices;
	}


}
