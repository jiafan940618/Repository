package com.hy.gf.vo;

import java.util.List;

import com.hy.gf.model.Station;


public class StationVO extends Station {

	private List<Station> stations;
	
	private String query;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setStations(List<Station> stations){
		this.stations=stations;
	}

	public List<Station>  getStations(){
		return stations;
	}


}
