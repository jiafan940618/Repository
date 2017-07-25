package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.BankCard;


public class BankCardVO extends BankCard {

	private List<BankCard> bankCards;

	public void setBankCards(List<BankCard> bankCards){
		this.bankCards=bankCards;
	}

	public List<BankCard>  getBankCards(){
		return bankCards;
	}


}
