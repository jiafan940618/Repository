package com.yeepay.paymobile.servlet;

import java.util.TreeMap;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.paymobile.utils.PaymobileUtils;

/**
 * 订单查询接口 
 * @author: yingjie.wang    
 * @since : 2015-10-10 16:20
 */

public class CheckBankcardApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CheckBankcardApiServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//UTF-8编码
		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out	= response.getWriter();

		String cardno       = formatStr(request.getParameter("cardno"));

		TreeMap<String, Object> treeMap	= new TreeMap<String, Object>();
		treeMap.put("cardno", cardno);

		//第一步 生成AESkey及encryptkey
		String AESKey		= PaymobileUtils.buildAESKey();
		String encryptkey	= PaymobileUtils.buildEncyptkey(AESKey);

		//第二步 生成data
		String data			= PaymobileUtils.buildData(treeMap, AESKey);

		//第三步 http请求，银行卡信息查询接口的请求方式为POST
		String merchantaccount				= PaymobileUtils.getMerchantaccount();
		String url							= PaymobileUtils.getRequestUrl(PaymobileUtils.CHECKBANKCARDAPI_NAME);
		TreeMap<String, String> responseMap	= PaymobileUtils.httpPost(url, merchantaccount, data, encryptkey);

		System.out.println("url=" + url);
		System.out.println("merchantaccount=" + merchantaccount);
		System.out.println("data=" + data);
		System.out.println("encryptkey=" + encryptkey);

		//第四步 判断请求是否成功
		if(responseMap.containsKey("error_code")) {
			out.println(responseMap);
			return;
		}

		//第五步 请求成功，则获取data、encryptkey，并将其解密
		String data_response						= responseMap.get("data");
		String encryptkey_response					= responseMap.get("encryptkey");
		TreeMap<String, String> responseDataMap	= PaymobileUtils.decrypt(data_response, encryptkey_response);

		//第六步 sign验签
		if(!PaymobileUtils.checkSign(responseDataMap)) {
			out.println("sign 验签失败！");
			out.println("<br><br>responseMap:" + responseDataMap);
			return;
		}

		//第七步 判断请求是否成功
		if(responseMap.containsKey("error_code")) {
			out.println(responseDataMap);
			return;
		}

		//第八步 进行业务处理
		request.setAttribute("responseDataMap", responseDataMap);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/47checkBankcardApiResponse.jsp");
		view.forward(request, response);
	}

	public String formatStr(String text) {
		return (text == null) ? "" : text.trim();
	}
}
