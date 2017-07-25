package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Subsidy;


public class SubsidyVO extends Subsidy {

	private List<Subsidy> subsidys;

	public void setSubsidys(List<Subsidy> subsidys){
		this.subsidys=subsidys;
	}

	public List<Subsidy>  getSubsidys(){
		return subsidys;
	}


}
