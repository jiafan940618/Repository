package com.hy.gf.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hy.gf.biz.OrderBiz;
import com.hy.gf.biz.WalletBiz;
import com.hy.gf.biz.YeepayBiz;
import com.hy.gf.model.EbankPay;
import com.hy.gf.model.Order;
import com.hy.gf.model.User;
import com.hy.gf.model.Wallet;
import com.hy.gf.model.Yeepay;
import com.hy.gf.util.Constant;
import com.hy.gf.vo.ResultData;
import com.yeepay.paymobile.utils.ConvertUtils;
import com.yeepay.paymobile.utils.DigestUtil;
import com.yeepay.paymobile.utils.HttpClient4Utils;
import com.yeepay.paymobile.utils.PaymobileUtils;

@Controller
@RequestMapping(value = "/yeepay")
public class YeepayController {

	private final Logger log = Logger.getLogger(YeepayController.class);

	@Resource
	YeepayBiz yeepayBiz;
	@Resource
	OrderBiz orderBiz;
	@Resource
	WalletBiz walletBiz;

	/**
	 * 订单支付接口
	 * 
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/pay")
	public ResultData<String> pay(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
			throws UnsupportedEncodingException {
		ResultData<String> resultData = new ResultData<>();

		Object attribute = httpSession.getAttribute("user");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}
		User user = (User) attribute;

		Long orderId = Long.valueOf(request.getParameter("orderid"));
		Order order = orderBiz.select(orderId);
		if(order.getStepA() != null && order.getStepA().intValue() == 1) {
			resultData.setCode(400);
			resultData.setMsg("已支付订单");
			resultData.setSuccess(false);
			return resultData;
		}
		if (!StringUtils.isEmpty(order.getPayUrl())) {
			if (order.getCreatePayUrlDtm().getTime() > System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000) {
				resultData.setData(order.getPayUrl());
				return resultData;
			}
		}

		String orderid = order.getOrderCode();
		if (!StringUtils.isEmpty(order.getPayOrderId())) {
			int count = Integer.valueOf(order.getPayOrderId().replace(order.getOrderCode(), ""));
			orderid += (count + 1);
		} else {
			orderid += "0";
		}
		String productcatalog = "7";
		String productname = "建站";
		String identityid = user.getId().toString();
		String userip = "127.0.0.1";
		String callbackurl = "http://120.76.98.74/guangfu/yeepay/callback";

		int transtime = (int) (System.currentTimeMillis() / 1000);
		int amount = (int) (order.getTotalMoney() * 100);
		int identitytype = 2;
		int orderexpdate = 7 * 24 * 60;

		// 使用TreeMap
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		treeMap.put("orderid", orderid);
		treeMap.put("productcatalog", productcatalog);
		treeMap.put("productname", productname);
		treeMap.put("identityid", identityid);
		treeMap.put("userip", userip);
		treeMap.put("transtime", transtime);
		treeMap.put("amount", amount);
		treeMap.put("identitytype", identitytype);
		treeMap.put("callbackurl", callbackurl);
		treeMap.put("orderexpdate", orderexpdate);
		treeMap.put("currency", 156);

		// 第一步 生成AESkey及encryptkey
		String AESKey = PaymobileUtils.buildAESKey();
		String encryptkey = PaymobileUtils.buildEncyptkey(AESKey);

		// 第二步 生成data
		String data = PaymobileUtils.buildData(treeMap, AESKey);

		// 第三步 获取商户编号及请求地址，并组装支付链接
		String merchantaccount = PaymobileUtils.getMerchantaccount();
		String url = PaymobileUtils.getRequestUrl(PaymobileUtils.PAYAPI_NAME);
		String payUrl = url + "?merchantaccount=" + URLEncoder.encode(merchantaccount, "UTF-8") + "&data="
				+ URLEncoder.encode(data, "UTF-8") + "&encryptkey=" + URLEncoder.encode(encryptkey, "UTF-8");

		System.out.println(payUrl);

		order.setPayOrderId(orderid);
		order.setPayUrl(payUrl);
		order.setCreatePayUrlDtm(new Date(System.currentTimeMillis()));
		orderBiz.update(order);

		// 第四步 跳转到支付链接payUrl
		resultData.setData(payUrl);
		return resultData;
	}
	
	@ResponseBody
	@RequestMapping(value = "/EBankPay")
	public ResultData<String> EBankPay(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<String> resultData = new ResultData<>();
		
		Object attribute = httpSession.getAttribute("user");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}

		Long orderId = Long.valueOf(request.getParameter("orderid"));
		Order order = orderBiz.select(orderId);
		if(order.getStepA() != null && order.getStepA().intValue() == 1) {
			resultData.setCode(400);
			resultData.setMsg("已经支付");
			resultData.setSuccess(false);
			return resultData;
		}
		if (!StringUtils.isEmpty(order.getEbankPayUrl())) {
			if (order.getCreateEbankPayUrlDtm().getTime() > System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000) {
				resultData.setData(order.getEbankPayUrl());
				return resultData;
			}
		}

		String orderid = order.getOrderCode();
		if (!StringUtils.isEmpty(order.getEbankPayOrderId())) {
			int count = Integer.valueOf(order.getEbankPayOrderId().replace(order.getOrderCode(), ""));
			orderid += (count + 1);
		} else {
			orderid += "0";
		}
		
		String p0_Cmd = "Buy";
		String p1_MerId = PaymobileUtils.getMerchantaccount();// 商户编号"10000457067"; //
		String p2_Order = orderid;
		String p3_Amt = order.getTotalMoney().toString();
		String p4_Cur = "CNY";
		String p5_Pid = "建站";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		String p8_Url = "http://120.76.98.74/guangfu/yeepay/ebank_callback";
		String p9_SAF = "0";
		String pa_MP = "";
		String pd_FrpId = "";
		String pm_Period = "7";
		String pn_Unit = "day";
		String pr_NeedResponse = "1";
		
		// keyValue : 商户密钥
		String keyValue = PaymobileUtils.getKeyvalue();//"U26po59182dV8d7654bo24o5z369408u4sQ3To9j6QuopAbo3gwj4h33mro4";//
		String[] strArr = new String[] {p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur,
		p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
		pd_FrpId, pm_Period, pn_Unit, pr_NeedResponse};
		String hmac = DigestUtil.getHmac(strArr, keyValue);
		
		try {
			p0_Cmd = URLEncoder.encode(p0_Cmd, "GBK");
			p1_MerId = URLEncoder.encode(p1_MerId, "GBK");
			p2_Order = URLEncoder.encode(p2_Order, "GBK");
			p3_Amt = URLEncoder.encode(p3_Amt, "GBK");
			p4_Cur = URLEncoder.encode(p4_Cur, "GBK");
			p5_Pid = URLEncoder.encode(p5_Pid, "GBK");
			p6_Pcat = URLEncoder.encode(p6_Pcat, "GBK");
			p7_Pdesc = URLEncoder.encode(p7_Pdesc, "GBK");
			p8_Url = URLEncoder.encode(p8_Url, "GBK");
			p9_SAF = URLEncoder.encode(p9_SAF, "GBK");
			pa_MP = URLEncoder.encode(pa_MP, "GBK");
			pd_FrpId = URLEncoder.encode(pd_FrpId, "GBK");
			pm_Period = URLEncoder.encode(pm_Period, "GBK");
			pn_Unit = URLEncoder.encode(pn_Unit, "GBK");
			pr_NeedResponse = URLEncoder.encode(pr_NeedResponse, "GBK");
			hmac = URLEncoder.encode(hmac, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String requestURL	= "https://www.yeepay.com/app-merchant-proxy/node";
		StringBuffer payURL = new StringBuffer();

		payURL.append(requestURL).append("?");
		payURL.append("p0_Cmd=").append(p0_Cmd);
        payURL.append("&p1_MerId=").append(p1_MerId);
        payURL.append("&p2_Order=").append(p2_Order);
        payURL.append("&p3_Amt=").append(p3_Amt);
        payURL.append("&p4_Cur=").append(p4_Cur);
        payURL.append("&p5_Pid=").append(p5_Pid);
        payURL.append("&p6_Pcat=").append(p6_Pcat);
        payURL.append("&p7_Pdesc=").append(p7_Pdesc);
        payURL.append("&p8_Url=").append(p8_Url);
        payURL.append("&p9_SAF=").append(p9_SAF);
        payURL.append("&pa_MP=").append(pa_MP);
        payURL.append("&pd_FrpId=").append(pd_FrpId);
        payURL.append("&pm_Period=").append(pm_Period);
        payURL.append("&pn_Unit=").append(pn_Unit);
        payURL.append("&pr_NeedResponse=").append(pr_NeedResponse);
        payURL.append("&hmac=").append(hmac);                  

		System.out.println("payURL : " + payURL);
		order.setEbankPayOrderId(orderid);
		order.setEbankPayUrl(payURL.toString());
		order.setCreateEbankPayUrlDtm(new Date(System.currentTimeMillis()));
		orderBiz.update(order);
		
		resultData.setData(payURL.toString());
		
		return resultData;
	}
	
	/**
	 * 支付成功回调接口
	 * 
	 * @param yeepay
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/callback")
	public String callback(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {

		String data = formatStr(request.getParameter("data"));
		String encryptkey = formatStr(request.getParameter("encryptkey"));
		// 解密data7
		TreeMap<String, String> dataMap = PaymobileUtils.decrypt(data, encryptkey);
		System.out.println("返回的明文参数：" + dataMap);
		// sign验签
		if (!PaymobileUtils.checkSign(dataMap)) {
			return "";
		}

		Yeepay yeepay = resultMapToModel(dataMap);
		yeepayBiz.insert(yeepay);
		log.error(dataMap.toString());

		Order order = orderBiz.selectByPayOrderId(yeepay.getOrderid());
		if (order == null)
			return "";
		// if ((int) (order.getTotalMoney() * 100) == yeepay.getAmount() &&
		// yeepay.getStatus() == 1) {
		
		if(order.getStepA() == 0){
			// 增加服务商钱包金额
			Wallet wallet = new Wallet();
			wallet.setServerId(order.getServerId());
			Wallet sobe = walletBiz.selectOneByExample(wallet);
			if(sobe != null){
				sobe.setMoney(sobe.getMoney() + order.getTotalMoney());
				walletBiz.update(sobe);
			}
		}
		
		order.setStepA(1);
		order.setPayWay(1);
		orderBiz.update(order);
		
		return "SUCCESS";
		// }

		// return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ebank_callback")
	public String ebank_callback(EbankPay ebankPay, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {

		Order order = orderBiz.selectByEbankPayOrderId(ebankPay.getR6_Order());
		if (order == null)
			return "";
		if(order.getTotalMoney().doubleValue() != Double.valueOf(ebankPay.getR3_Amt()).doubleValue())
			return "";
		
		if(order.getStepA() == 0){
			// 增加服务商钱包金额
			Wallet wallet = new Wallet();
			wallet.setServerId(order.getServerId());
			Wallet sobe = walletBiz.selectOneByExample(wallet);
			if(sobe != null){
				sobe.setMoney(sobe.getMoney() + order.getTotalMoney());
				walletBiz.update(sobe);
			}
		}
		
		order.setStepA(1);
		order.setPayWay(2);
		orderBiz.update(order);
		
		return "SUCCESS";
	}

	private Yeepay resultMapToModel(TreeMap<String, String> dataMap) {
		Yeepay yeepay = new Yeepay();

		yeepay.setMerchantaccount(dataMap.get("merchantaccount"));
		yeepay.setYborderid(dataMap.get("yborderid"));
		yeepay.setOrderid(dataMap.get("orderid"));
		yeepay.setBankcode(dataMap.get("bankcode"));
		yeepay.setBank(dataMap.get("bank"));
		yeepay.setLastno(dataMap.get("lastno"));
		try {
			yeepay.setCardtype(Integer.valueOf(dataMap.get("cardtype")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			yeepay.setAmount(Long.valueOf(dataMap.get("amount")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			yeepay.setStatus(Integer.getInteger(dataMap.get("status")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		yeepay.setSign(dataMap.get("sign"));

		return yeepay;
	}
	
	/**
	 * verifyCallbackHmac() : 验证回调参数是否有效
	 * @throws UnsupportedEncodingException
	 */
	public static boolean verifyCallbackHmac(String[] stringValue, String hmacFromYeepay)
			throws UnsupportedEncodingException {
		String keyValue = "U26po59182dV8d7654bo24o5z369408u4sQ3To9j6QuopAbo3gwj4h33mro4";
		StringBuffer sourceData = new StringBuffer();
		for (int i = 0; i < stringValue.length; i++) {
			stringValue[i] = URLDecoder.decode(stringValue[i], "GBK");
			stringValue[i] = new String(stringValue[i].getBytes("8859_1"), "GB2312");
			sourceData.append(stringValue[i]);
			System.out.println("stringValue ～～～～: " + stringValue[i]);
		}
		System.out.println("sourceData ～～～～: " + sourceData);
		String localHmac = DigestUtil.getHmac(stringValue, keyValue);
		StringBuffer hmacSource = new StringBuffer();
		for (int i = 0; i < stringValue.length; i++) {
			hmacSource.append(stringValue[i]);
		}
		return (localHmac.equals(hmacFromYeepay) ? true : false);
	}

	/**
	 * 订单查询接口
	 * 
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPay")
	public ResultData<Map<String, String>> query(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) throws UnsupportedEncodingException {
		ResultData<Map<String, String>> resultData = new ResultData<>();
		String orderid = formatStr(request.getParameter("orderid"));
		return query(resultData, orderid);
	}
	
	private ResultData<Map<String, String>> query(ResultData<Map<String, String>> resultData, String orderid) throws UnsupportedEncodingException {
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		treeMap.put("orderid", orderid);

		// 第一步 生成AESkey及encryptkey
		String AESKey = PaymobileUtils.buildAESKey();
		String encryptkey = PaymobileUtils.buildEncyptkey(AESKey);

		// 第二步 生成data
		String data = PaymobileUtils.buildData(treeMap, AESKey);

		// 第三步 http请求，订单查询接口的请求方式为GET
		String merchantaccount = PaymobileUtils.getMerchantaccount();
		String url = PaymobileUtils.getRequestUrl(PaymobileUtils.QUERYORDERAPI_NAME);
		TreeMap<String, String> responseMap = PaymobileUtils.httpGet(url, merchantaccount, data, encryptkey);

		System.out.println("请求串：" + url + "?merchantaccount=" + merchantaccount + "&data="
				+ URLEncoder.encode(data, "utf-8") + "&encryptkey=" + URLEncoder.encode(encryptkey, "utf-8"));

		// 第四步 判断请求是否成功
		if (responseMap.containsKey("error_code")) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setMsg("请求错误 :  " + responseMap.get("error_code"));
			resultData.setData(responseMap);
			return resultData;
		}

		// 第五步 请求成功，则获取data、encryptkey，并将其解密
		String data_response = responseMap.get("data");
		String encryptkey_response = responseMap.get("encryptkey");
		TreeMap<String, String> responseDataMap = PaymobileUtils.decrypt(data_response, encryptkey_response);

		System.out.println("请求返回的明文参数：" + responseDataMap);

		// 第六步 sign验签
		if (!PaymobileUtils.checkSign(responseDataMap)) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setMsg("sign 验签失败！");
			resultData.setData(responseDataMap);
			return resultData;
		}

		// 第七步 判断请求是否成功
		if (responseDataMap.containsKey("error_code")) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setMsg("请求错误 :  " + responseDataMap.get("error_code"));
			resultData.setData(responseDataMap);
			return resultData;
		}

		// 第八步 进行业务处理
		resultData.setData(responseDataMap);
		
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/queryEbankPay")
	public ResultData<Map<String, String>> queryEbankPay(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) throws UnsupportedEncodingException {
		ResultData<Map<String, String>> resultData = new ResultData<>();
		String p2_Order = formatStr(request.getParameter("orderid"));
		return queryEbankPay(resultData, p2_Order);
	}
	
	private ResultData<Map<String, String>> queryEbankPay(ResultData<Map<String, String>> resultData, String p2_Order) {
		String p0_Cmd = "QueryOrdDetail"; // 业务类型 固定值
		String p1_MerId =  PaymobileUtils.getMerchantaccount();//商户编号"10000457067"; //
		String pv_Ver = "3.0"; // 版本号 固定值
		String p3_ServiceType = "2";
		// keyValue : 商户密钥
		String keyValue = PaymobileUtils.getKeyvalue();//"U26po59182dV8d7654bo24o5z369408u4sQ3To9j6QuopAbo3gwj4h33mro4";// 
		String[] strArr = new String[] { p0_Cmd, p1_MerId, p2_Order, pv_Ver, p3_ServiceType };
		String hmac = DigestUtil.getHmac(strArr, keyValue);
		try {
			p0_Cmd = URLEncoder.encode(p0_Cmd, "GBK");
			p1_MerId = URLEncoder.encode(p1_MerId, "GBK");
			p2_Order = URLEncoder.encode(p2_Order, "GBK");
			pv_Ver = URLEncoder.encode(pv_Ver, "GBK");
			p3_ServiceType = URLEncoder.encode(p3_ServiceType, "GBK");
			hmac = URLEncoder.encode(hmac, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("p0_Cmd", p0_Cmd);
		paramMap.put("p1_MerId", p1_MerId);
		paramMap.put("p2_Order", p2_Order);
		paramMap.put("pv_Ver", pv_Ver);
		paramMap.put("p3_ServiceType", p3_ServiceType);
		paramMap.put("hmac", hmac);

		String url = "https://cha.yeepay.com/app-merchant-proxy/command";
		String responseBody = HttpClient4Utils.sendHttpRequest(url, paramMap, "GBK", false);
		String[] responseList = responseBody.split("\n");
		for(String res : responseList) {
			String[] s = res.split("=");
			result.put(s[0], s.length > 1 ? s[1] : "");
		}
		if(result.get("r2_TrxId") == null || result.get("r2_TrxId").toString().equals("")) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setMsg("未查询到该笔订单信息");
			return resultData;
		}

		resultData.setData(result);
		return resultData;
	}
	
	/**
	 * 消费对账文件下载接口
	 * 
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/payClearData")
	public ResultData<String> data(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
			throws ParseException, UnsupportedEncodingException, IOException {
		ResultData<String> resultData = new ResultData<>();

		String startdate = formatStr(request.getParameter("startdate"));
		String enddate = formatStr(request.getParameter("enddate"));

		// 使用TreeMap
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		treeMap.put("startdate", startdate);
		treeMap.put("enddate", enddate);

		// 生成AESkey及encryptkey
		String AESKey = PaymobileUtils.buildAESKey();
		String encryptkey = PaymobileUtils.buildEncyptkey(AESKey);

		// 生成data
		String data = PaymobileUtils.buildData(treeMap, AESKey);

		String merchantaccount = PaymobileUtils.getMerchantaccount();
		String url = PaymobileUtils.getRequestUrl(PaymobileUtils.PAYCLEARDATAAPI_NAME);

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("merchantaccount", merchantaccount));
		pairs.add(new BasicNameValuePair("data", data));
		pairs.add(new BasicNameValuePair("encryptkey", encryptkey));
		url = url + "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, "UTF-8"));

		resultData.setData(url);

		return resultData;
	}

	/**
	 * 单笔退款接口
	 * 
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/refund")
	public ResultData<Map<String, String>> refund(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) throws UnsupportedEncodingException {
		ResultData<Map<String, String>> resultData = new ResultData<>();

		Long orderId = Long.valueOf(request.getParameter("orderid"));
		Order order = orderBiz.select(orderId);
		if(order.getStepA() != null && order.getStepA().intValue() != 1) {
			resultData.setCode(400);
			resultData.setMsg("未支付订单");
			resultData.setSuccess(false);
			return resultData;
		}
		
		String cause = formatStr(request.getParameter("cause"));
		int currency = 156;
		int amount = (int) (ConvertUtils.objectToDouble(request.getParameter("amount")) * 100);
		
		query(resultData, order.getPayOrderId());
		if (!resultData.getSuccess())
			return resultData;

		// 使用TreeMap
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		treeMap.put("orderid", order.getPayOrderId());
		treeMap.put("origyborderid", resultData.getData().get("yborderid"));
		treeMap.put("amount", amount);
		treeMap.put("currency", currency);
		treeMap.put("cause", cause);

		// 第一步 生成AESkey及encryptkey
		String AESKey = PaymobileUtils.buildAESKey();
		String encryptkey = PaymobileUtils.buildEncyptkey(AESKey);

		// 第二步 生成data
		String data = PaymobileUtils.buildData(treeMap, AESKey);

		// 第三步 http请求，退款接口的请求方式为POST
		String merchantaccount = PaymobileUtils.getMerchantaccount();
		String url = PaymobileUtils.getRequestUrl(PaymobileUtils.REFUNDAPI_NAME);
		TreeMap<String, String> responseMap = PaymobileUtils.httpPost(url, merchantaccount, data, encryptkey);

		// 第四步 判断请求是否成功
		if (responseMap.containsKey("error_code")) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setData(responseMap);
			resultData.setMsg("请求错误 :  " + responseMap.get("error_code"));
			return resultData;
		}

		// 第五步 请求成功，则获取data、encryptkey，并将其解密
		String data_response = responseMap.get("data");
		String encryptkey_response = responseMap.get("encryptkey");
		TreeMap<String, String> responseDataMap = PaymobileUtils.decrypt(data_response, encryptkey_response);

		System.out.println("请求返回的明文参数：" + responseDataMap);

		// 第六步 sign验签
		if (!PaymobileUtils.checkSign(responseDataMap)) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setData(responseDataMap);
			resultData.setMsg("sign 验签失败！");
			return resultData;
		}

		// 第七步 判断请求是否成功
		if (responseDataMap.containsKey("error_code")) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setData(responseDataMap);
			resultData.setMsg("请求错误 :  " + responseDataMap.get("error_code"));
			return resultData;
		}

		// 第八步 进行业务处理
		resultData.setData(responseDataMap);
		
		// 修改订单的退款状态为完成
		order.setState(9);
		orderBiz.update(order);

		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/ebankRefund")
	public ResultData<Map<String, String>> ebankRefund(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) throws UnsupportedEncodingException {
		ResultData<Map<String, String>> resultData = new ResultData<>();
		
		Long orderId = Long.valueOf(request.getParameter("orderid"));
		Order order = orderBiz.select(orderId);
		if(order.getStepA() != null && order.getStepA().intValue() != 1) {
			resultData.setCode(400);
			resultData.setMsg("未支付订单");
			resultData.setSuccess(false);
			return resultData;
		}
		queryEbankPay(resultData, order.getEbankPayOrderId());
		if (!resultData.getSuccess())
			return resultData;
		double amount = ConvertUtils.objectToDouble(request.getParameter("amount"));
		
		String p0_Cmd = "RefundOrd";	//业务类型  固定值
		String p1_MerId = PaymobileUtils.getMerchantaccount();//商户编号"10000457067"; // 
		String p2_Order = order.getEbankPayOrderId();	//退款请求编号
		String pb_TrxId = resultData.getData().get("r2_TrxId");	//易宝交易流水号
		String p3_Amt = amount + "";	//退款金额
		String p4_Cur = "CNY";	//退款币种 固定值
		String p5_Desc = "";	//退款说明
		
		// keyValue : 商户密钥
		String keyValue = PaymobileUtils.getKeyvalue();//"U26po59182dV8d7654bo24o5z369408u4sQ3To9j6QuopAbo3gwj4h33mro4";// 
		String[] strArr = new String[] { p0_Cmd, p1_MerId, p2_Order, pb_TrxId, p3_Amt, p4_Cur, p5_Desc };
		String hmac = DigestUtil.getHmac(strArr, keyValue);
		try {
			p0_Cmd = URLEncoder.encode(p0_Cmd, "GBK");
			p1_MerId = URLEncoder.encode(p1_MerId, "GBK");
			p2_Order = URLEncoder.encode(p2_Order, "GBK");
			pb_TrxId = URLEncoder.encode(pb_TrxId, "GBK");
			p3_Amt = URLEncoder.encode(p3_Amt, "GBK");
			p4_Cur = URLEncoder.encode(p4_Cur, "GBK");
			p5_Desc = URLEncoder.encode(p5_Desc, "GBK");
			hmac = URLEncoder.encode(hmac, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("p0_Cmd", p0_Cmd);
		paramMap.put("p1_MerId", p1_MerId);
		paramMap.put("p2_Order", p2_Order);
		paramMap.put("pb_TrxId", pb_TrxId);
		paramMap.put("p3_Amt", p3_Amt);
		paramMap.put("p4_Cur", p4_Cur);
		paramMap.put("p5_Desc", p5_Desc);
		paramMap.put("hmac", hmac);

		String url = "https://cha.yeepay.com/app-merchant-proxy/command";
		String responseBody = HttpClient4Utils.sendHttpRequest(url, paramMap, "GBK", false);
		String[] responseList = responseBody.split("\n");
		for(String res : responseList) {
			String[] s = res.split("=");
			result.put(s[0], s.length > 1 ? s[1] : "");
		}
		if(result.get("r1_Code") != null) {
			int errCode = Integer.valueOf(result.get("r1_Code").toString());
			if(errCode != 1) {
				resultData.setCode(400);
				resultData.setSuccess(false);
				switch (errCode) {
				case 2:
					resultData.setMsg("账户状态无效");
					break;
				case 7:
					resultData.setMsg("该订单不支持退款");
					break;
				case 10:
					resultData.setMsg("退款金额超限");
					break;
				case 18:
					resultData.setMsg("余额不足");
					break;
				case 50:
					resultData.setMsg("订单不存在");
					break;
				case 55:
					resultData.setMsg("历史退款未开通");
					break;
				case 6801:
					resultData.setMsg("IP 限制");
					break;
				case 83:
					resultData.setMsg("未开通历史订单退款");
					break;
				default:
					break;
				}
				resultData.setData(null);
				return resultData;
			}
			
			// 修改订单的退款状态为完成
			order.setState(9);
			orderBiz.update(order);
			
		}
		resultData.setData(result);
		
		return resultData;
	}
	
	/**
	 * 退款查询接口
	 * 
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRefund")
	public ResultData<Map<String, String>> queryRefund(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Map<String, String>> resultData = new ResultData<>();

		String orderid = formatStr(request.getParameter("orderid"));

		// 使用TreeMap
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		treeMap.put("orderid", orderid);

		// 第一步 生成AESkey及encryptkey
		String AESKey = PaymobileUtils.buildAESKey();
		String encryptkey = PaymobileUtils.buildEncyptkey(AESKey);

		// 第二步 生成data
		String data = PaymobileUtils.buildData(treeMap, AESKey);

		// 第三步 http请求，退款查询接口的请求方式为POST
		String merchantaccount = PaymobileUtils.getMerchantaccount();
		String url = PaymobileUtils.getRequestUrl(PaymobileUtils.QUERYREFUNDAPI_NAME);
		TreeMap<String, String> responseMap = PaymobileUtils.httpGet(url, merchantaccount, data, encryptkey);

		// 第四步 判断请求是否成功
		if (responseMap.containsKey("error_code")) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setData(responseMap);
			resultData.setMsg("请求错误 :  " + responseMap.get("error_code"));
			return resultData;
		}

		// 第五步 请求成功，则获取data、encryptkey，并将其解密
		String data_response = responseMap.get("data");
		String encryptkey_response = responseMap.get("encryptkey");
		TreeMap<String, String> responseDataMap = PaymobileUtils.decrypt(data_response, encryptkey_response);

		System.out.println("请求返回的明文参数：" + responseDataMap);

		// 第六步 sign验签
		if (!PaymobileUtils.checkSign(responseDataMap)) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setData(responseDataMap);
			resultData.setMsg("sign 验签失败！");
			return resultData;
		}

		// 第七步 判断请求是否成功
		if (responseDataMap.containsKey("error_code")) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setData(responseDataMap);
			resultData.setMsg("请求错误 :  " + responseDataMap.get("error_code"));
			return resultData;
		}

		// 第八步 进行业务处理
		resultData.setData(responseDataMap);

		return resultData;
	}

	/**
	 * 退款对账文件下载接口
	 * 
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/refundClearData")
	public ResultData<String> dataRefund(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) throws ParseException, UnsupportedEncodingException, IOException {
		ResultData<String> resultData = new ResultData<String>();

		String startdate = formatStr(request.getParameter("startdate"));
		String enddate = formatStr(request.getParameter("enddate"));

		// 使用TreeMap
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		treeMap.put("startdate", startdate);
		treeMap.put("enddate", enddate);

		// 生成AESkey及encryptkey
		String AESKey = PaymobileUtils.buildAESKey();
		String encryptkey = PaymobileUtils.buildEncyptkey(AESKey);

		String data = PaymobileUtils.buildData(treeMap, AESKey);

		String merchantaccount = PaymobileUtils.getMerchantaccount();
		String url = PaymobileUtils.getRequestUrl(PaymobileUtils.REFUNDCLEARDATAAPI_NAME);

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("merchantaccount", merchantaccount));
		pairs.add(new BasicNameValuePair("data", data));
		pairs.add(new BasicNameValuePair("encryptkey", encryptkey));
		url = url + "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, "UTF-8"));

		resultData.setData(url);

		return resultData;
	}

	/**
	 * 银行卡信息查询接口
	 * 
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkBankcard")
	public ResultData<Map<String, String>> checkBankcard(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Map<String, String>> resultData = new ResultData<Map<String, String>>();

		String cardno = formatStr(request.getParameter("cardno"));

		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		treeMap.put("cardno", cardno);

		// 第一步 生成AESkey及encryptkey
		String AESKey = PaymobileUtils.buildAESKey();
		String encryptkey = PaymobileUtils.buildEncyptkey(AESKey);

		// 第二步 生成data
		String data = PaymobileUtils.buildData(treeMap, AESKey);

		// 第三步 http请求，银行卡信息查询接口的请求方式为POST
		String merchantaccount = PaymobileUtils.getMerchantaccount();
		String url = PaymobileUtils.getRequestUrl(PaymobileUtils.CHECKBANKCARDAPI_NAME);
		TreeMap<String, String> responseMap = PaymobileUtils.httpPost(url, merchantaccount, data, encryptkey);

		System.out.println("url=" + url);
		System.out.println("merchantaccount=" + merchantaccount);
		System.out.println("data=" + data);
		System.out.println("encryptkey=" + encryptkey);

		// 第四步 判断请求是否成功
		if (responseMap.containsKey("error_code")) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setData(responseMap);
			resultData.setMsg("请求错误 :  " + responseMap.get("error_code"));
			return resultData;
		}

		// 第五步 请求成功，则获取data、encryptkey，并将其解密
		String data_response = responseMap.get("data");
		String encryptkey_response = responseMap.get("encryptkey");
		TreeMap<String, String> responseDataMap = PaymobileUtils.decrypt(data_response, encryptkey_response);

		// 第六步 sign验签
		if (!PaymobileUtils.checkSign(responseDataMap)) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setData(responseDataMap);
			resultData.setMsg("sign 验签失败！");
			return resultData;
		}

		// 第七步 判断请求是否成功
		if (responseDataMap.containsKey("error_code")) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setData(responseDataMap);
			resultData.setMsg("请求错误 :  " + responseDataMap.get("error_code"));
			return resultData;
		}

		// 第八步 进行业务处理
		resultData.setData(responseDataMap);

		return resultData;
	}

	public String formatStr(String text) {
		return (text == null) ? "" : text.trim();
	}

}