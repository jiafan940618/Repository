package com.hy.gf.model;

import java.util.*;


public class DevConf {

	private Integer rowId;

	private Integer cAddr;

	private Integer iAddr;

	private Long dAddr;

	private Integer dType;

	private String dName;

	private String dOpt;

	private String wMask;

	private String wConf;


	public void setRowId(Integer rowId){
		this.rowId=rowId;
	}

	public Integer getRowId(){
		return rowId;
	}

	public void setCAddr(Integer cAddr){
		this.cAddr=cAddr;
	}

	public Integer getCAddr(){
		return cAddr;
	}

	public void setIAddr(Integer iAddr){
		this.iAddr=iAddr;
	}

	public Integer getIAddr(){
		return iAddr;
	}

	public void setDAddr(Long dAddr){
		this.dAddr=dAddr;
	}

	public Long getDAddr(){
		return dAddr;
	}

	public void setDType(Integer dType){
		this.dType=dType;
	}

	public Integer getDType(){
		return dType;
	}

	public void setDName(String dName){
		this.dName=dName;
	}

	public String getDName(){
		return dName;
	}

	public void setDOpt(String dOpt){
		this.dOpt=dOpt;
	}

	public String getDOpt(){
		return dOpt;
	}

	public void setWMask(String wMask){
		this.wMask=wMask;
	}

	public String getWMask(){
		return wMask;
	}

	public void setWConf(String wConf){
		this.wConf=wConf;
	}

	public String getWConf(){
		return wConf;
	}

}
