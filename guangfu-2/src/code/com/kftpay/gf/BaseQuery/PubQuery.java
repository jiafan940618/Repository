package com.kftpay.gf.BaseQuery;

public class PubQuery {
	/** 当前页码*/
	private Integer pageNo=1;   
	/** 页面尺寸大小*/
	private Integer pageSize=10;   
	/** 总记录数*/ 
	private Integer totalCount;
	/** 总页数*/ 
	private Integer pageCount;     
	/** 开始时间*/
	private String startTime;      
	/** 结尾时间*/
	private String endTime;
	/** 商户id*/
	private String mchId;
	/** 订单的状态 0、支付成功  1、未支付成功*/
	private String status = "0" ; 
	    
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getMchId() {
			return mchId;
		}
		public void setMchId(String mchId) {
			this.mchId = mchId;
		}
		public Integer getPageNo() {
			return pageNo;
		}
		public void setPageNo(Integer pageNo) {
			this.pageNo = pageNo;
		}
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
		public Integer getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(Integer totalCount) {
			if (this.pageSize !=0 ) {
				this.pageCount =totalCount%(this.pageSize) ==0 ? 0 : totalCount/this.pageSize+1 ;
			}
			
				this.totalCount = totalCount;
		}
		
		
		public Integer getPageCount() {
			return pageCount;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}  
	
		public static void main(String[] args) {
			PubQuery pub = new PubQuery();
			 pub.setTotalCount(51);
			 System.out.println(pub.getPageCount());
			 
		}
	
	

}
