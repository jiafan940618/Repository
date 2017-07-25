package com.hy.gf.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.CityBiz;
import com.hy.gf.biz.IncomeBiz;
import com.hy.gf.biz.OrderBiz;
import com.hy.gf.biz.ProvinceBiz;
import com.hy.gf.biz.ServerBiz;
import com.hy.gf.model.City;
import com.hy.gf.model.Income;
import com.hy.gf.model.Order;
import com.hy.gf.model.Page;
import com.hy.gf.model.Province;
import com.hy.gf.model.Server;
import com.hy.gf.model.User;
import com.hy.gf.util.Constant;
import com.hy.gf.util.DateJsonValueProcessor;
import com.hy.gf.vo.OrderVO;
import com.hy.gf.vo.ResultData;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;


@Controller
@RequestMapping(value = "/income")
public class IncomeController {
@Resource
IncomeBiz incomeBiz;
@Resource
OrderBiz orderBiz;
@Resource
ServerBiz serverBiz;
@Resource
ProvinceBiz provinceBiz;
@Resource
CityBiz cityBiz;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}

//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	incomeBiz.delete(id);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/insert")
	public ResultData insert(Income income, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		incomeBiz.insert(income);
		resultData.setData(income);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Income income = incomeBiz.select(id);
		resultData.setData(income);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/update")
	public ResultData update(Income income, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		incomeBiz.update(income);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Income income, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Income selectByExample =incomeBiz.selectOneByExample(income);
		resultData.setData(selectByExample);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Income income, Page<Income> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(income);
			}
		Page<Income> selectByExample = incomeBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<Income> list = JSON.parseArray(listJson.getData(), Income.class);
		incomeBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		incomeBiz.deleteBatch(ids);
		return resultData;
	}
	
	/**
	 * 条件查询交易记录
	 */
	@ResponseBody
	@RequestMapping(value = "/find")
	public ResultData find(Income income, Date timeFrom, Date timeTo, Page<Income> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		User user = (User)httpSession.getAttribute("user");
		if(user == null){
			return Constant.noLogin(resultData);
		}
		
		if(-1 == income.getType()){
			income.setType(null);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", user.getId());
		map.put("type", income.getType());
		map.put("timeFrom", timeFrom);
		map.put("timeTo", timeTo);
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		List<Income> list = incomeBiz.find(map);
		int num = incomeBiz.findCount(map);
		
		Map<String, Object> rm = new HashMap<>();
		rm.put("list", list);
		rm.put("totalCounts", num);
		rm.put("pageSize", page.getLimit());
		
		resultData.setData(rm);
		return resultData;
	}
	
	/**
	 * 我的钱包---收益记录，优能补贴
	 * 到income表里找数据
	 */
	@ResponseBody
	@RequestMapping(value = "/incomeEchart")
	public ResultData incomeEchart(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		User user = (User)httpSession.getAttribute("user");
		if(user == null){
			return Constant.noLogin(resultData);
		}
		
		List<Income> fie = incomeBiz.forIncomeEcharts(user.getId());
		
		
		Double tic = 0d;
		if(fie.size() > 0){
			for(Income ic:fie){
				tic += ic.getMoney();
			}
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy/MM/dd"));
		JSONArray jsonArray = JSONArray.fromObject(fie, jsonConfig);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", jsonArray);
		map.put("totalIncome", tic);
		
		resultData.setData(map);
		return resultData;
	}
	
	// 我的钱包收益补贴，根据tem_station_year表，以天为单位
	@ResponseBody
	@RequestMapping(value = "/unlsubsidyIncome")
	public ResultData unlsubsidyIncome(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		User user = (User)httpSession.getAttribute("user");
		if(user == null){
			return Constant.noLogin(resultData);
		}
		
		Income income = new Income();
		income.setUserId(user.getId().intValue());
		income.setType(2);
		
		Page<Income> page = new Page<>();
		page.setStart(0);
		page.setLimit(1000000);
		page.setExample(income);
		page.setSortUp("ASC");
		Page<Income> sbe = incomeBiz.selectByExample(page);
		
		Double tic = 0d;
		if(sbe.getList().size() > 0){
			for(Income ic:sbe.getList()){
				tic += ic.getMoney();
			}
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy/MM/dd HH:mm"));
		JSONArray jsonArray = JSONArray.fromObject(sbe.getList(), jsonConfig);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", jsonArray);
		map.put("totalIncome", tic);
		
		resultData.setData(map);
		return resultData;
	}
	
	/**
	 * 服务商--营业明细--财务统计
	 * countType，1管理员，2服务商
	 */
	@ResponseBody
	@RequestMapping(value = "/serveryymxFinance")
	public ResultData serveryymxFinance(Integer countType, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Map<String, Object> map = new HashMap<>();
			
		OrderVO ov = new OrderVO();
		if(countType == 2){
			Server server = (Server)httpSession.getAttribute("server");
			ov.setServerId(server.getId());
		}
		Page<OrderVO> page = new Page<>();
		page.setExample(ov);
		page.setLimit(Integer.MAX_VALUE);
		Page<OrderVO> sbe = orderBiz.selectByExample(page);
		List<Order> orders = new ArrayList<>();
		for(int i=0; i<sbe.getList().size(); i++){
			orders.add(sbe.getList().get(i));
		}
		
		// 营业额 = 建设中营业额 + 已完成营业额
		Double totalYye = 0d;
		for(int i=0; i<orders.size(); i++){
			totalYye += orders.get(i).getTotalMoney();
		}
		map.put("totalYye", totalYye);
		
		// 建设中营业额 = 待支付 + 已支付
		List<Order> buildingOrders = new ArrayList<>();
		for(Order o:orders){
			if(o.getState() != 8){
				buildingOrders.add(o);
			}
		}
		Double buildingYye = 0d;
		for(Order o:buildingOrders){
			buildingYye += o.getTotalMoney();
		}
		map.put("buildingYye", buildingYye);
		
		// 已完成项目营业额
		List<Order> completeOrders = new ArrayList<>();
		for(Order o:orders){
			if(o.getState() == 8){
				completeOrders.add(o);
			}
		}
		Double completeYye = 0d;
		for(Order o:completeOrders){
			completeYye += o.getTotalMoney();
		}
		map.put("completeYye", completeYye);
		
		// 待支付的建设中项目营业额
		List<Order> notPayOrders = new ArrayList<>();
		for(Order o:orders){
			if(o.getState() != 8 && o.getStepA() == 0){
				notPayOrders.add(o);
			}
		}
		Double notPayYye = 0d;
		for(Order o:notPayOrders){
			notPayYye += o.getTotalMoney();
		}
		map.put("notPayYye", notPayYye);
		
		// 已支付的建设中项目营业额
		List<Order> hadPayOrders = new ArrayList<>();
		for(Order o:orders){
			if(o.getState() != 8 && o.getStepA() == 1){
				hadPayOrders.add(o);
			}
		}
		Double hadPayYye = 0d;
		for(Order o:hadPayOrders){
			hadPayYye += o.getTotalMoney();
		}
		map.put("hadPayYye", hadPayYye);
		
		// 订单数
		map.put("orderNumber", orders.size());
		
		// 订单总金额、优能服务费、营业利润
		Double ordersTotalMoney = 0d;
		Double totalFactorage = 0d;
		Double yylr = 0d;
		if(countType == 2){
			Server server = (Server)httpSession.getAttribute("server");
			for(Order o:orders){
				ordersTotalMoney += o.getTotalMoney();
				totalFactorage += o.getTotalMoney()*server.getFactorage();
			}
		} else if (countType == 1){
			for(Order o:orders){
				ordersTotalMoney += o.getTotalMoney();
				Server server = serverBiz.select(Long.valueOf(o.getServerId()));
				if(server != null){
					totalFactorage += o.getTotalMoney()*server.getFactorage();
				}
			}
		}
		yylr = ordersTotalMoney - totalFactorage;
		map.put("ordersTotalMoney", ordersTotalMoney);
		map.put("totalFactorage", totalFactorage);
		map.put("totalFactorage", yylr);
		
		resultData.setData(map);
		return resultData;
	}
	
	/**
	 * 服务商--营业明细--筛选功能和订单列表
	 * countType，1管理员，2服务商
	 */
	@ResponseBody
	@RequestMapping(value = "/serveryymxFilter")
	public ResultData serveryymxFilter(Integer countType, Order order, String cityName, Date timeFrom, Date timeTo, String query, Page<Order> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> rm = new HashMap<>();
		
		if(countType == 2){
			Server server = (Server)httpSession.getAttribute("server");
			map.put("serverId", server.getId());
			rm.put("factorage", server.getFactorage());
		}
		map.put("cityName", cityName);
		map.put("addressId", order.getAddressId());
		map.put("timeFrom", timeFrom);
		map.put("timeTo", timeTo);
		map.put("orderCode", order.getOrderCode());
		map.put("linkMan", order.getLinkMan());
		map.put("state", order.getState());
		map.put("status", order.getStatus());
		map.put("query", query);
		map.put("stepA", order.getStepA());
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		List<Order> list = orderBiz.businessFind(map);
		int num = orderBiz.businessFindCount(map);
		
		page.setTotal(num);
		page.setList(list);
		rm.put("page", page);
		
		Map<String, Object> financeMap = new HashMap<>();
		// 总营业额
		Double totalYye = 0d;
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getState() != 5 && list.get(i).getState() != 6 && list.get(i).getState() != 7 && list.get(i).getState() != 9)
			totalYye += list.get(i).getTotalMoney();
		}
		financeMap.put("totalYye", totalYye);
		
		// 建设中营业额  = 待支付 + 已支付
		Double buildingYye = 0d;
		for(Order o:list){
			if(o.getState() != 5 && o.getState() != 6 && o.getState() != 7  && o.getState() != 8 && o.getState() != 9)
			buildingYye += o.getTotalMoney();
		}
		financeMap.put("buildingYye", buildingYye);
		
		// 已完成营业额
		Double completeYye = 0d;
		for(Order o:list){
			if(o.getState() == 8)
			completeYye += o.getTotalMoney();
		}
		financeMap.put("completeYye", completeYye);
		
		// 待支付营业额
		Double notPayYye = 0d;
		for(Order o:list){
			if(o.getState() != 5 && o.getState() != 6 && o.getState() != 7 && o.getState() != 9 && o.getStepA() == 0 )
			notPayYye += o.getTotalMoney();
		}
		financeMap.put("notPayYye", notPayYye);
		
		// 已支付营业额
		Double hadPayYye = 0d;
		for(Order o:list){
			if(o.getState() != 5 && o.getState() != 6 && o.getState() != 7 && o.getState() != 9 && o.getStepA() == 1 )
			hadPayYye += o.getTotalMoney();
		}
		financeMap.put("hadPayYye", hadPayYye);
		
		// 订单数
		financeMap.put("orderNumber", list.size());
		
		// 订单总金额、优能服务费、营业利润
		Double ordersTotalMoney = 0d;
		Double totalFactorage = 0d;
		Double yylr = 0d;
		
		if(countType == 2){
			Server server = (Server)httpSession.getAttribute("server");
			for(Order o:list){
				if(o.getState() != 5 && o.getState() != 6 && o.getState() != 7 && o.getState() != 9 ){
					ordersTotalMoney += o.getTotalMoney();
					totalFactorage += o.getTotalMoney()*server.getFactorage();
				}
			}
		} else if(countType == 1){
			for(Order o:list){
				if(o.getState() != 5 && o.getState() != 6 && o.getState() != 7 && o.getState() != 9 ){
					ordersTotalMoney += o.getTotalMoney();
					Server server = serverBiz.select(Long.valueOf(o.getServerId()));
					if(server != null){
						totalFactorage += o.getTotalMoney()*server.getFactorage();
					}
				}
			}
		}
			
		yylr = ordersTotalMoney - totalFactorage;
		financeMap.put("ordersTotalMoney", ordersTotalMoney);
		financeMap.put("totalFactorage", totalFactorage);
		financeMap.put("yylr", yylr);
		rm.put("financeMap", financeMap);
		
		resultData.setData(rm);
		return resultData;
	}
	
	/**
	 * 服务商--退款管理--财务统计
	 */
	@ResponseBody
	@RequestMapping(value = "/servertkglFinance")
	public ResultData servertkglFinance(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("server");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		
		Server server = (Server)attribute;
//		Server server = serverBiz.select(Long.valueOf(45));
		Map<String, Object> map = new HashMap<>();
			
		OrderVO ov = new OrderVO();
		ov.setServerId(server.getId());
		ov.setState(5); // 待退款状态
		Page<OrderVO> page = new Page<>();
		page.setExample(ov);
		page.setLimit(Integer.MAX_VALUE);
		Page<OrderVO> sbe = orderBiz.selectByExample(page);
		List<Order> orders = new ArrayList<>(); // 待退款订单
		for(int i=0; i<sbe.getList().size(); i++){
			orders.add(sbe.getList().get(i));
		}
		
		Double refundTotalMoney = 0d;
		for(Order o:orders){
			refundTotalMoney += o.getTotalMoney();
		}
		map.put("notFinishRefundTotalMoney", refundTotalMoney); // 待退总金额
		
		ov.setState(9); // 退款完成状态
		page.setExample(ov);
		sbe = orderBiz.selectByExample(page);
		List<Order> finishRefundOrders = new ArrayList<>(); // 退款完成订单
		for(int i=0; i<sbe.getList().size(); i++){
			finishRefundOrders.add(sbe.getList().get(i));
		}
		map.put("refundOrderNum", orders.size() + finishRefundOrders.size()); // 退款订单数
		Double finishRefundTotalMoney = 0d;
		for(Order o:finishRefundOrders){
			finishRefundTotalMoney += o.getTotalMoney();
		}
		map.put("finishRefundTotalMoney", finishRefundTotalMoney); // 已退款总金额
		map.put("refundTotalMoney", refundTotalMoney + finishRefundTotalMoney); // 退款总金额
		
		resultData.setData(map);
		return resultData;
	}
	
	/**
	 * 服务商--退款管理--筛选功能和显示订单列表
	 */
	@ResponseBody
	@RequestMapping(value = "/servertkglFilter")
	public ResultData servertkglFilter(Order order, String cityName, Date timeFrom, Date timeTo, String query, Page<Order> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("server");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		
		Server server = (Server)attribute;
//		Server server = serverBiz.select(Long.valueOf(23));
		
		Map<String, Object> map = new HashMap<>();
		map.put("serverId", server.getId());
		map.put("timeFrom", timeFrom);
		map.put("timeTo", timeTo);
		map.put("cityName", cityName);
		map.put("addressId", order.getAddressId());
		map.put("orderCode", order.getOrderCode());
		map.put("linkMan", order.getLinkMan());
		map.put("state", order.getState()); // 5待退款，9已退款
		map.put("query", query);
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		List<Order> list = orderBiz.businessFind(map);
		int num = orderBiz.businessFindCount(map);
		
		Map<String, Object> rm = new HashMap<>();
		rm.put("factorage", server.getFactorage());
		page.setTotal(num);
		page.setList(list);
		rm.put("page", page);
		
		resultData.setData(rm);
		return resultData;
	}
	
	/**
	 * 针对后台，根据服务商的订单，地址二级联动
	 */
	@ResponseBody
	@RequestMapping(value = "/findOrderAddress")
	public ResultData servertkglFilter(Long provinceId, Page<OrderVO> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("server");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		
		Server server = (Server)attribute;
//		Server server = serverBiz.select(Long.valueOf(41));
		
		if(provinceId == null){
			OrderVO order = new OrderVO();
			order.setServerId(server.getId());
			page.setExample(order);
			page.setLimit(Integer.MAX_VALUE);
			Page<OrderVO> sbe = orderBiz.selectByExample(page);
			List<Order> orderList = new ArrayList<>();
			orderList.addAll(sbe.getList());
			
			Set<Long> cityIds = new HashSet<>();
			for(Order o:orderList){
				cityIds.add(o.getAddressId());
			}
			Set<Long> proIds = new HashSet<>();
			for(Long cityId:cityIds){
				City city = cityBiz.select(cityId);
				proIds.add(city.getProvinceId());
			}
			
			List<Province> proList = new ArrayList<>();
			for(Long id:proIds){
				proList.add(provinceBiz.select(id));
			}
			
			resultData.setData(proList);
			return resultData;
		} else{
			Page<City> cityPage = new Page<>();
			City city = new City();
			city.setProvinceId(provinceId);
			cityPage.setExample(city);
			cityPage.setLimit(1000000);
			Page<City> sbe = cityBiz.selectByExample(cityPage);
			
			OrderVO order = new OrderVO();
			order.setServerId(server.getId());
			page.setExample(order);
			page.setLimit(1000000);
			Page<OrderVO> ordersbe = orderBiz.selectByExample(page);
			List<Order> orderList = new ArrayList<>();
			orderList.addAll(ordersbe.getList());
			
			Set<City> citySet = new HashSet<>();
			for(City c:sbe.getList()){
				for(Order o:orderList){
					if(o.getAddressId().equals(c.getId().toString())){
						citySet.add(c);
					}
				}
			}
			resultData.setData(citySet);
			return resultData;
		}
	}
	
}