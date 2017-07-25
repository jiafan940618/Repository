package com.yeepay.paymobile.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yeepay.paymobile.utils.PaymobileUtils;

/**
 * 支付清算文件下载接口
 * @author: yingjie.wang    
 * @since : 2015-10-10 20:00
 */

public class PayClearDataApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PayClearDataApiServlet() {
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

		String startdate    = formatStr(request.getParameter("startdate"));
		String enddate      = formatStr(request.getParameter("enddate"));

		//使用TreeMap
		TreeMap<String, Object> treeMap	= new TreeMap<String, Object>();
		treeMap.put("startdate",startdate);
		treeMap.put("enddate", 	enddate);

		//生成AESkey及encryptkey
		String AESKey			= PaymobileUtils.buildAESKey();
		String encryptkey		= PaymobileUtils.buildEncyptkey(AESKey);

		//生成data
		String data				= PaymobileUtils.buildData(treeMap, AESKey);

		String merchantaccount	= PaymobileUtils.getMerchantaccount();
		String url				= PaymobileUtils.getRequestUrl(PaymobileUtils.PAYCLEARDATAAPI_NAME);
		
		//发起请求
//		InputStream	responseStream	= PaymobileUtils.clearDataHttpGet(url, merchantaccount, data, encryptkey);
		
		
		InputStream result = null;
        
        CloseableHttpClient httpClient  = HttpClients.createDefault();
        HttpGet httpGet                 = null;
        CloseableHttpResponse responsec  = null;

        //超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60*60*1000).setSocketTimeout(60*60*1000).build();

        String filePath = "test";
        try {
            
            List<NameValuePair> pairs   = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("merchantaccount", merchantaccount));
            pairs.add(new BasicNameValuePair("data", data));
            pairs.add(new BasicNameValuePair("encryptkey", encryptkey));
            url = url + "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, "UTF-8"));
            
            httpGet     = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            responsec    = httpClient.execute(httpGet);
            
            int statusCode = responsec.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }

            HttpEntity entity   = responsec.getEntity();
            result              = entity.getContent(); 
            
            String realPath         = this.getServletConfig().getServletContext().getRealPath("/"); 
            System.out.println("realPath:" + realPath);
              //对账文件的存储路径
              String path             = realPath + File.separator + "YeepayClearData";
              String time             = String.valueOf(System.currentTimeMillis());
              String fileName         = "PayClearData" + "_" + time + ".txt";
              filePath         = path + File.separator + fileName;
                  
              File file   = new File(filePath);
              file.getParentFile().mkdirs();
              file.createNewFile();
      
              FileOutputStream orderFile  = new FileOutputStream(filePath);
              BufferedInputStream bis     = new BufferedInputStream(result);
              
              System.out.println(bis);
              
              byte[] by = new byte[1024];
              while (true) {
                  int i = bis.read(by);
                  if (i == -1) {
                      break;
                  }
                  orderFile.write(by, 0, i);
              }
              orderFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源  
                if (responsec != null) {
                    responsec.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
//		String line					= reader.readLine();
//		
//		//当请求失败时，会返回一个json串。
//		if(line.startsWith("{")) {
//			Map<String, String> jsonMap	= JSON.parseObject(line,new TypeReference<TreeMap<String, String>>() {});
//			if(jsonMap.containsKey("error_code")) {
//				out.println(jsonMap);
//			} else {
//				String dataFromYeepay		= formatStr((String)jsonMap.get("data"));
//				String encryptkeyFromYeepay	= formatStr((String)jsonMap.get("encryptkey")); 
//				Map<String, String> respMap	= PaymobileUtils.decrypt(dataFromYeepay, encryptkeyFromYeepay);
//				out.println(respMap);
//			}
//			return;
//		}
//
//		//获得当前绝对路径	
//		String realPath 		= this.getServletConfig().getServletContext().getRealPath("/"); 
//		//对账文件的存储路径
//		String path				= realPath + File.separator + "YeepayClearData";
//		String time				= String.valueOf(System.currentTimeMillis());
//		String fileName			= "PayClearData" + "_" + time + ".txt";
//		String filePath			= path + File.separator + fileName;
//		File file				= new File(filePath);
//		file.getParentFile().mkdirs();
//		file.createNewFile();
//
//		File outputFile			= new File(filePath);
//		FileWriter fileWriter	= new FileWriter(outputFile);
//		BufferedWriter writer	= new BufferedWriter(fileWriter);
//
//		writer.write(line);
//		writer.write(System.getProperty("line.separator"));
//		while((line = reader.readLine()) != null) {
//			writer.write(line);
//			writer.write(System.getProperty("line.separator"));
//		}
//		writer.close();

		request.setAttribute("filePath", filePath);
		RequestDispatcher view	= request.getRequestDispatcher("jsp/43payClearDataApiResponse.jsp");
		view.forward(request, response);
	}

	public String formatStr(String text) {
		return (text == null) ? "" : text.trim();
	}

}
