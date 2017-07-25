package com.hy.gf.model;

import java.util.*;


public class TemStation {

	private Long id;

	private String stationCode;

	private String ammeterCode;
	
	private Long d_addr;

	private Integer d_type;

	private Integer w_addr;
	
	private Double kw;

	private Double kwh;

	private Date createDtm;


	public Long getD_addr() {
		return d_addr;
	}

	public void setD_addr(Long d_addr) {
		this.d_addr = d_addr;
	}

	public Integer getD_type() {
		return d_type;
	}

	public void setD_type(Integer d_type) {
		this.d_type = d_type;
	}

	public Integer getW_addr() {
		return w_addr;
	}

	public void setW_addr(Integer w_addr) {
		this.w_addr = w_addr;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setStationCode(String stationCode){
		this.stationCode=stationCode;
	}

	public String getStationCode(){
		return stationCode;
	}

	public void setAmmeterCode(String ammeterCode){
		this.ammeterCode=ammeterCode;
	}

	public String getAmmeterCode(){
		return ammeterCode;
	}

	public Double getKwh() {
		return kwh;
	}

	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}

	public Double getKw() {
		return kw;
	}

	public void setKw(Double kw) {
		this.kw = kw;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
