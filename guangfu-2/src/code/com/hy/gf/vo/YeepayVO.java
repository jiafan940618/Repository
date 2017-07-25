package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Yeepay;


public class YeepayVO extends Yeepay {

	private List<Yeepay> yeepays;

	public void setYeepays(List<Yeepay> yeepays){
		this.yeepays=yeepays;
	}

	public List<Yeepay>  getYeepays(){
		return yeepays;
	}


}
