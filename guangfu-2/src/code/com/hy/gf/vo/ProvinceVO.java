package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Province;


public class ProvinceVO extends Province {

	private List<Province> provinces;

	public void setProvinces(List<Province> provinces){
		this.provinces=provinces;
	}

	public List<Province>  getProvinces(){
		return provinces;
	}


}
