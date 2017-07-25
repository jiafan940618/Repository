package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Evaluate;


public class EvaluateVO extends Evaluate {

	private List<Evaluate> evaluates;

	public void setEvaluates(List<Evaluate> evaluates){
		this.evaluates=evaluates;
	}

	public List<Evaluate>  getEvaluates(){
		return evaluates;
	}


}
