package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.District;


public class DistrictVO extends District {

	private List<District> districts;

	public void setDistricts(List<District> districts){
		this.districts=districts;
	}

	public List<District>  getDistricts(){
		return districts;
	}


}
