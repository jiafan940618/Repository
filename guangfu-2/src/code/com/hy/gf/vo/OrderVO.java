package com.hy.gf.vo;

import com.hy.gf.model.Order;
import com.hy.gf.model.Server;
import com.hy.gf.model.Station;
import com.hy.gf.model.User;


public class OrderVO extends Order {

	private User user;
	
	private Server server;
	
	private Station station;

	private String query;
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public User getUser() {
		return user;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

}
