package com.hy.gf.vo;

import java.util.*;
import java.math.*;
import com.hy.gf.model.Wallet;


public class WalletVO extends Wallet {

	private List<Wallet> wallets;

	public void setWallets(List<Wallet> wallets){
		this.wallets=wallets;
	}

	public List<Wallet>  getWallets(){
		return wallets;
	}


}
