package com.hy.gf.vo;

import java.util.List;

import com.hy.gf.model.City;
import com.hy.gf.model.Province;


public class CityVO extends City {

	private List<City> citys;
	
	private Province province;

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public void setCitys(List<City> citys){
		this.citys=citys;
	}

	public List<City>  getCitys(){
		return citys;
	}


}
