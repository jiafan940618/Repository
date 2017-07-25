package com.hy.gf.model;

import java.util.Date;


public class Order {

	private Long id;

	private String orderCode;

	private Long userId;

	private Double capacity; 

	private Double gross; // 发电总量
	
	private Integer plan; //进度，0指（申请中，施工中，并网发电，待评价），1指（完成），2指（待退款，过期，取消）

	private Integer state;

	private Long addressId;

	private String addressText;

	private Integer payWay;

	private Integer stepA;

	private Integer stepB;

	private Integer stepC;

	private Integer stepD;

	private Integer stepE;

	private Double entrustMoney;

	private Double paidMoney;

	private String privilegeCode;

	private Long serverId;
	
	private Server server;
	
	private String linkMan; //联系人
	
	private String phone; //联系电话
	
	private Integer type; // 1.居民，2工业，3商业，4农业
	
	private String vasId; // 增值服务
	
	private String vasText; // 增值服务文字说明vas_text
	
	private Double vasMoney; // 增值服务总价格
	
	private Double totalMoney; // 订单总价格
	
	private Double refundMoney; // 退款金额refund_money
	
	private String payOrderId;	//易宝支付订单号
	
	private String payUrl;	//易宝支付url
	
	private Date createPayUrlDtm;	//易宝支付url生成时间
	
	private String ebankPayOrderId;	//易宝支付订单号
	
	private String ebankPayUrl;	//易宝支付url
	
	private Date createEbankPayUrlDtm;	//易宝支付url生成时间
	
	private Integer status;

	private Date createDtm;
	
	private String deviceA;
	
	private String deviceB;
	
	private String deviceC;
	
	private String deviceD;
	
	private String deviceE;
	
	private Integer del;
	
	private Date del_dtm;
	
	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public Date getDel_dtm() {
		return del_dtm;
	}

	public void setDel_dtm(Date del_dtm) {
		this.del_dtm = del_dtm;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getVasText() {
		return vasText;
	}

	public void setVasText(String vasText) {
		this.vasText = vasText;
	}

	public String getDeviceA() {
		return deviceA;
	}

	public void setDeviceA(String deviceA) {
		this.deviceA = deviceA;
	}

	public String getDeviceB() {
		return deviceB;
	}

	public void setDeviceB(String deviceB) {
		this.deviceB = deviceB;
	}

	public String getDeviceC() {
		return deviceC;
	}

	public void setDeviceC(String deviceC) {
		this.deviceC = deviceC;
	}

	public String getDeviceD() {
		return deviceD;
	}

	public void setDeviceD(String deviceD) {
		this.deviceD = deviceD;
	}

	public String getDeviceE() {
		return deviceE;
	}

	public void setDeviceE(String deviceE) {
		this.deviceE = deviceE;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(Double refundMoney) {
		this.refundMoney = refundMoney;
	}

	public String getVasId() {
		return vasId;
	}

	public void setVasId(String vasId) {
		this.vasId = vasId;
	}

	public Double getVasMoney() {
		return vasMoney;
	}

	public void setVasMoney(Double vasMoney) {
		this.vasMoney = vasMoney;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPlan() {
		return plan;
	}

	public void setPlan(Integer plan) {
		this.plan = plan;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setOrderCode(String orderCode){
		this.orderCode=orderCode;
	}

	public String getOrderCode(){
		return orderCode;
	}

	public void setUserId(Long userId){
		this.userId=userId;
	}

	public Long getUserId(){
		return userId;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public Double getGross() {
		return gross;
	}

	public void setGross(Double gross) {
		this.gross = gross;
	}

	public void setState(Integer state){
		this.state=state;
	}

	public Integer getState(){
		return state;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public void setAddressText(String addressText){
		this.addressText=addressText;
	}

	public String getAddressText(){
		return addressText;
	}

	public void setPayWay(Integer payWay){
		this.payWay=payWay;
	}

	public Integer getPayWay(){
		return payWay;
	}

	public void setStepA(Integer stepA){
		this.stepA=stepA;
	}

	public Integer getStepA(){
		return stepA;
	}

	public void setStepB(Integer stepB){
		this.stepB=stepB;
	}

	public Integer getStepB(){
		return stepB;
	}

	public void setStepC(Integer stepC){
		this.stepC=stepC;
	}

	public Integer getStepC(){
		return stepC;
	}

	public void setStepD(Integer stepD){
		this.stepD=stepD;
	}

	public Integer getStepD(){
		return stepD;
	}

	public void setStepE(Integer stepE){
		this.stepE=stepE;
	}

	public Integer getStepE(){
		return stepE;
	}

	public void setEntrustMoney(Double entrustMoney){
		this.entrustMoney=entrustMoney;
	}

	public Double getEntrustMoney(){
		return entrustMoney;
	}

	public void setPaidMoney(Double paidMoney){
		this.paidMoney=paidMoney;
	}

	public Double getPaidMoney(){
		return paidMoney;
	}

	public void setPrivilegeCode(String privilegeCode){
		this.privilegeCode=privilegeCode;
	}

	public String getPrivilegeCode(){
		return privilegeCode;
	}
	
	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public Date getCreatePayUrlDtm() {
		return createPayUrlDtm;
	}

	public void setCreatePayUrlDtm(Date createPayUrlDtm) {
		this.createPayUrlDtm = createPayUrlDtm;
	}

	public String getEbankPayOrderId() {
		return ebankPayOrderId;
	}

	public void setEbankPayOrderId(String ebankPayOrderId) {
		this.ebankPayOrderId = ebankPayOrderId;
	}

	public String getEbankPayUrl() {
		return ebankPayUrl;
	}

	public void setEbankPayUrl(String ebankPayUrl) {
		this.ebankPayUrl = ebankPayUrl;
	}

	public Date getCreateEbankPayUrlDtm() {
		return createEbankPayUrlDtm;
	}

	public void setCreateEbankPayUrlDtm(Date createEbankPayUrlDtm) {
		this.createEbankPayUrlDtm = createEbankPayUrlDtm;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
