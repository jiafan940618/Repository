package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.ColConf;


public class ColConfVO extends ColConf {

	private List<ColConf> colConfs;

	public void setColConfs(List<ColConf> colConfs){
		this.colConfs=colConfs;
	}

	public List<ColConf>  getColConfs(){
		return colConfs;
	}


}
