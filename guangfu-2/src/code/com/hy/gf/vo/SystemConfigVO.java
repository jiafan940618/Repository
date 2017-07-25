package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.SystemConfig;


public class SystemConfigVO extends SystemConfig {

	private List<SystemConfig> systemConfigs;

	public void setSystemConfigs(List<SystemConfig> systemConfigs){
		this.systemConfigs=systemConfigs;
	}

	public List<SystemConfig>  getSystemConfigs(){
		return systemConfigs;
	}


}
