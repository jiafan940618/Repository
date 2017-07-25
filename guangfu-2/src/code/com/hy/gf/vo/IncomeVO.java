package com.hy.gf.vo;

import java.util.*;
import java.math.*;
import com.hy.gf.model.Income;


public class IncomeVO extends Income {

	private List<Income> incomes;

	public void setIncomes(List<Income> incomes){
		this.incomes=incomes;
	}

	public List<Income>  getIncomes(){
		return incomes;
	}


}
