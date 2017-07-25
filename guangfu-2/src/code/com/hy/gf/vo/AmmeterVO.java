package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Ammeter;
import com.hy.gf.model.Station;


public class AmmeterVO extends Ammeter {

	private List<Ammeter> ammeters;
	
	private Station station;

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public void setAmmeters(List<Ammeter> ammeters){
		this.ammeters=ammeters;
	}

	public List<Ammeter>  getAmmeters(){
		return ammeters;
	}


}
