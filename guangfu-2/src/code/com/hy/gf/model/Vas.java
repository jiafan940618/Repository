package com.hy.gf.model;

import java.util.*;


public class Vas {

	private Long id;

	private String name;

	private Double price;

	private Date createDtm;


	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setPrice(Double price){
		this.price=price;
	}

	public Double getPrice(){
		return price;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
