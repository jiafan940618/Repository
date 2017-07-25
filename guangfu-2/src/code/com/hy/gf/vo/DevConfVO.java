package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.DevConf;


public class DevConfVO extends DevConf {

	private List<DevConf> devConfs;

	public void setDevConfs(List<DevConf> devConfs){
		this.devConfs=devConfs;
	}

	public List<DevConf>  getDevConfs(){
		return devConfs;
	}


}
