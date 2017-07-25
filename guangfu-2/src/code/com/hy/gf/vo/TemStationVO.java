package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.TemStation;


public class TemStationVO extends TemStation {

	private List<TemStation> temStations;

	public void setTemStations(List<TemStation> temStations){
		this.temStations=temStations;
	}

	public List<TemStation>  getTemStations(){
		return temStations;
	}


}
