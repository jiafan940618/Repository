package com.yeepay.paymobile.servlet;

import java.util.TreeMap;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.paymobile.utils.PaymobileUtils;

/**
 * 订单查询接口 
 * @author: yingjie.wang    
 * @since : 2015-10-09 13:49
 */

public class QueryOrderApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public QueryOrderApiServlet() {
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

		String orderid      = formatStr(request.getParameter("orderid"));

		TreeMap<String, Object> treeMap	= new TreeMap<String, Object>();
		treeMap.put("orderid", 	orderid);

		//第一步 生成AESkey及encryptkey
		String AESKey		= PaymobileUtils.buildAESKey();
		String encryptkey	= PaymobileUtils.buildEncyptkey(AESKey);

		//第二步 生成data
		String data			= PaymobileUtils.buildData(treeMap, AESKey);

		//第三步 http请求，订单查询接口的请求方式为GET
		String merchantaccount				= PaymobileUtils.getMerchantaccount();
		String url							= PaymobileUtils.getRequestUrl(PaymobileUtils.QUERYORDERAPI_NAME);
		TreeMap<String, String> responseMap	= PaymobileUtils.httpGet(url, merchantaccount, data, encryptkey);

		System.out.println("请求串：" + url + "?merchantaccount=" + merchantaccount 
							   				+ "&data=" + URLEncoder.encode(data, "utf-8") 
							 				+ "&encryptkey=" + URLEncoder.encode(encryptkey, "utf-8"));
		
		//第四步 判断请求是否成功
		if(responseMap.containsKey("error_code")) {
			out.println(responseMap);
			return;
		}

		//第五步 请求成功，则获取data、encryptkey，并将其解密
		String data_response					= responseMap.get("data");
		String encryptkey_response				= responseMap.get("encryptkey");
		TreeMap<String, String> responseDataMap	= PaymobileUtils.decrypt(data_response, encryptkey_response);

		System.out.println("请求返回的明文参数：" + responseDataMap);

		//第六步 sign验签
		if(!PaymobileUtils.checkSign(responseDataMap)) {
			out.println("sign 验签失败！");
			out.println("<br><br>responseDataMap:" + responseDataMap);
			return;
		}

		//第七步 判断请求是否成功
		if(responseDataMap.containsKey("error_code")) {
			out.println(responseDataMap);
			return;
		}

		//第八步 进行业务处理
		request.setAttribute("responseDataMap", responseDataMap);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/42queryOrderApiResponse.jsp");
		view.forward(request, response);
	}

	public String formatStr(String text) {
		return (text == null) ? "" : text.trim();
	}

}
