package com.hy.gf.model;

public class Yeepay {

	private Long id;

	private String merchantaccount;

	private String yborderid;

	private String orderid;

	private String bankcode;

	private String bank;

	private String lastno;

	private Integer cardtype;

	private Long amount;

	private Integer status;

	private String sign;


	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setMerchantaccount(String merchantaccount){
		this.merchantaccount=merchantaccount;
	}

	public String getMerchantaccount(){
		return merchantaccount;
	}

	public void setYborderid(String yborderid){
		this.yborderid=yborderid;
	}

	public String getYborderid(){
		return yborderid;
	}

	public void setOrderid(String orderid){
		this.orderid=orderid;
	}

	public String getOrderid(){
		return orderid;
	}

	public void setBankcode(String bankcode){
		this.bankcode=bankcode;
	}

	public String getBankcode(){
		return bankcode;
	}

	public void setBank(String bank){
		this.bank=bank;
	}

	public String getBank(){
		return bank;
	}

	public void setLastno(String lastno){
		this.lastno=lastno;
	}

	public String getLastno(){
		return lastno;
	}

	public void setCardtype(Integer cardtype){
		this.cardtype=cardtype;
	}

	public Integer getCardtype(){
		return cardtype;
	}

	public void setAmount(Long amount){
		this.amount=amount;
	}

	public Long getAmount(){
		return amount;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setSign(String sign){
		this.sign=sign;
	}

	public String getSign(){
		return sign;
	}

}
