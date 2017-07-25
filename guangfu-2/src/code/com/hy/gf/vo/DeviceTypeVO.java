package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.DeviceType;


public class DeviceTypeVO extends DeviceType {

	private List<DeviceType> deviceTypes;

	public void setDeviceTypes(List<DeviceType> deviceTypes){
		this.deviceTypes=deviceTypes;
	}

	public List<DeviceType>  getDeviceTypes(){
		return deviceTypes;
	}


}
