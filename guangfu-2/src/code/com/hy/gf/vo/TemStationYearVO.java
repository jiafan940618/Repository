package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.TemStationYear;


public class TemStationYearVO extends TemStationYear {

	private List<TemStationYear> temStationYears;

	public void setTemStationYears(List<TemStationYear> temStationYears){
		this.temStationYears=temStationYears;
	}

	public List<TemStationYear>  getTemStationYears(){
		return temStationYears;
	}


}
