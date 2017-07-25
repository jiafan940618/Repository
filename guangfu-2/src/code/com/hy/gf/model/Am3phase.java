package com.hy.gf.model;

import java.util.*;
import java.math.*;


public class Am3phase {

	private Integer rowId;

	private Integer cAddr;

	private Integer iAddr;

	private Long dAddr;

	private Integer dType;

	private Integer wAddr;

	private Long meterTime;

	private String meterState;

	private Integer voltChange;

	private Integer currentChange;

	private Double frequency;

	private Double aVolt;

	private Double bVolt;

	private Double cVolt;

	private Double abVolt;

	private Double bcVolt;

	private Double caVolt;

	private Double aCurrent;

	private Double bCurrent;

	private Double cCurrent;

	private Double kw;

	private Double aKw;

	private Double bKw;

	private Double cKw;

	private Double kvar;

	private Double aKvar;

	private Double bKvar;

	private Double cKvar;

	private Double kva;

	private Double aKva;

	private Double bKva;

	private Double cKva;

	private Double powerFactor;

	private Double aPowerFactor;

	private Double bPowerFactor;

	private Double cPowerFactor;

	private Double currentZero;

	private Double kwhTotal;

	private Double kwh;

	private Double kwhRev;

	private Double kvarh1;

	private Double kvarh2;

	private Double aKwhTotal;

	private Double bKwhTotal;

	private Double cKwhTotal;


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

	public void setWAddr(Integer wAddr){
		this.wAddr=wAddr;
	}

	public Integer getWAddr(){
		return wAddr;
	}

	public void setMeterTime(Long meterTime){
		this.meterTime=meterTime;
	}

	public Long getMeterTime(){
		return meterTime;
	}

	public void setMeterState(String meterState){
		this.meterState=meterState;
	}

	public String getMeterState(){
		return meterState;
	}

	public void setVoltChange(Integer voltChange){
		this.voltChange=voltChange;
	}

	public Integer getVoltChange(){
		return voltChange;
	}

	public void setCurrentChange(Integer currentChange){
		this.currentChange=currentChange;
	}

	public Integer getCurrentChange(){
		return currentChange;
	}

	public void setFrequency(Double frequency){
		this.frequency=frequency;
	}

	public Double getFrequency(){
		return frequency;
	}

	public void setAVolt(Double aVolt){
		this.aVolt=aVolt;
	}

	public Double getAVolt(){
		return aVolt;
	}

	public void setBVolt(Double bVolt){
		this.bVolt=bVolt;
	}

	public Double getBVolt(){
		return bVolt;
	}

	public void setCVolt(Double cVolt){
		this.cVolt=cVolt;
	}

	public Double getCVolt(){
		return cVolt;
	}

	public void setAbVolt(Double abVolt){
		this.abVolt=abVolt;
	}

	public Double getAbVolt(){
		return abVolt;
	}

	public void setBcVolt(Double bcVolt){
		this.bcVolt=bcVolt;
	}

	public Double getBcVolt(){
		return bcVolt;
	}

	public void setCaVolt(Double caVolt){
		this.caVolt=caVolt;
	}

	public Double getCaVolt(){
		return caVolt;
	}

	public void setACurrent(Double aCurrent){
		this.aCurrent=aCurrent;
	}

	public Double getACurrent(){
		return aCurrent;
	}

	public void setBCurrent(Double bCurrent){
		this.bCurrent=bCurrent;
	}

	public Double getBCurrent(){
		return bCurrent;
	}

	public void setCCurrent(Double cCurrent){
		this.cCurrent=cCurrent;
	}

	public Double getCCurrent(){
		return cCurrent;
	}

	public void setKw(Double kw){
		this.kw=kw;
	}

	public Double getKw(){
		return kw;
	}

	public void setAKw(Double aKw){
		this.aKw=aKw;
	}

	public Double getAKw(){
		return aKw;
	}

	public void setBKw(Double bKw){
		this.bKw=bKw;
	}

	public Double getBKw(){
		return bKw;
	}

	public void setCKw(Double cKw){
		this.cKw=cKw;
	}

	public Double getCKw(){
		return cKw;
	}

	public void setKvar(Double kvar){
		this.kvar=kvar;
	}

	public Double getKvar(){
		return kvar;
	}

	public void setAKvar(Double aKvar){
		this.aKvar=aKvar;
	}

	public Double getAKvar(){
		return aKvar;
	}

	public void setBKvar(Double bKvar){
		this.bKvar=bKvar;
	}

	public Double getBKvar(){
		return bKvar;
	}

	public void setCKvar(Double cKvar){
		this.cKvar=cKvar;
	}

	public Double getCKvar(){
		return cKvar;
	}

	public void setKva(Double kva){
		this.kva=kva;
	}

	public Double getKva(){
		return kva;
	}

	public void setAKva(Double aKva){
		this.aKva=aKva;
	}

	public Double getAKva(){
		return aKva;
	}

	public void setBKva(Double bKva){
		this.bKva=bKva;
	}

	public Double getBKva(){
		return bKva;
	}

	public void setCKva(Double cKva){
		this.cKva=cKva;
	}

	public Double getCKva(){
		return cKva;
	}

	public void setPowerFactor(Double powerFactor){
		this.powerFactor=powerFactor;
	}

	public Double getPowerFactor(){
		return powerFactor;
	}

	public void setAPowerFactor(Double aPowerFactor){
		this.aPowerFactor=aPowerFactor;
	}

	public Double getAPowerFactor(){
		return aPowerFactor;
	}

	public void setBPowerFactor(Double bPowerFactor){
		this.bPowerFactor=bPowerFactor;
	}

	public Double getBPowerFactor(){
		return bPowerFactor;
	}

	public void setCPowerFactor(Double cPowerFactor){
		this.cPowerFactor=cPowerFactor;
	}

	public Double getCPowerFactor(){
		return cPowerFactor;
	}

	public void setCurrentZero(Double currentZero){
		this.currentZero=currentZero;
	}

	public Double getCurrentZero(){
		return currentZero;
	}

	public void setKwhTotal(Double kwhTotal){
		this.kwhTotal=kwhTotal;
	}

	public Double getKwhTotal(){
		return kwhTotal;
	}

	public void setKwh(Double kwh){
		this.kwh=kwh;
	}

	public Double getKwh(){
		return kwh;
	}

	public void setKwhRev(Double kwhRev){
		this.kwhRev=kwhRev;
	}

	public Double getKwhRev(){
		return kwhRev;
	}

	public void setKvarh1(Double kvarh1){
		this.kvarh1=kvarh1;
	}

	public Double getKvarh1(){
		return kvarh1;
	}

	public void setKvarh2(Double kvarh2){
		this.kvarh2=kvarh2;
	}

	public Double getKvarh2(){
		return kvarh2;
	}

	public void setAKwhTotal(Double aKwhTotal){
		this.aKwhTotal=aKwhTotal;
	}

	public Double getAKwhTotal(){
		return aKwhTotal;
	}

	public void setBKwhTotal(Double bKwhTotal){
		this.bKwhTotal=bKwhTotal;
	}

	public Double getBKwhTotal(){
		return bKwhTotal;
	}

	public void setCKwhTotal(Double cKwhTotal){
		this.cKwhTotal=cKwhTotal;
	}

	public Double getCKwhTotal(){
		return cKwhTotal;
	}

}
