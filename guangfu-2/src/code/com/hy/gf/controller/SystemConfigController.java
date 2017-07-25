package com.hy.gf.controller;

import java.io.IOException;
import java.text.DecimalFormat;
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.OrderBiz;
import com.hy.gf.biz.ServerBiz;
import com.hy.gf.biz.StationBiz;
import com.hy.gf.biz.SystemConfigBiz;
import com.hy.gf.biz.TemStationBiz;
import com.hy.gf.model.Order;
import com.hy.gf.model.Page;
import com.hy.gf.model.Server;
import com.hy.gf.model.Station;
import com.hy.gf.model.SystemConfig;
import com.hy.gf.model.TemStation;
import com.hy.gf.model.User;
import com.hy.gf.util.Constant;
import com.hy.gf.util.DateUtil;
import com.hy.gf.vo.OrderVO;
import com.hy.gf.vo.ResultData;
import com.hy.gf.vo.ServerVO;

@Controller
@RequestMapping(value = "/systemConfig")
public class SystemConfigController {
	@Resource
	SystemConfigBiz systemConfigBiz;
	@Resource
	OrderBiz orderBiz;
	@Resource
	ServerBiz serverBiz;
	@Resource
	TemStationBiz temStationBiz;
	@Resource
	StationBiz stationBiz;

	@ResponseBody
//	@RequestMapping(value = "/delete")
	public Object delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		systemConfigBiz.delete(id);
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/insert")
	public Object insert(SystemConfig systemConfig, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		systemConfigBiz.insert(systemConfig);
		resultData.setData(systemConfig);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		SystemConfig systemConfig = systemConfigBiz.select(id);
		resultData.setData(systemConfig);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public Object update(SystemConfig systemConfig, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();

		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}
		systemConfigBiz.update(systemConfig);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(SystemConfig systemConfig, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		SystemConfig selectByExample = systemConfigBiz.selectOneByExample(systemConfig);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(SystemConfig systemConfig, Page<SystemConfig> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(systemConfig);
		}
		Page<SystemConfig> selectByExample = systemConfigBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<SystemConfig> list = JSON.parseArray(listJson.getData(), SystemConfig.class);
		systemConfigBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		systemConfigBiz.deleteBatch(ids);
		return resultData;
	}

	/*
	 * 根据keys来查找值
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByKeys")
	public ResultData selectByKeys(String keys) {
		ResultData resultData = new ResultData();

		String[] keysArr = keys.split(",");

		Map<String, Object> map = new HashMap<>();

		for (String key : keysArr) {
			String value = systemConfigBiz.findValueByKey(key);
			map.put(key, value);
		}

		resultData.setData(map);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/updateAndroid")
	public ResultData<Map<String, String>> deleteBatch(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Map<String, String>> resultData = new ResultData<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("android_version_code", systemConfigBiz.findValueByKey("android_version_code"));
		map.put("android_version_name", systemConfigBiz.findValueByKey("android_version_name"));
		map.put("android_update_url", systemConfigBiz.findValueByKey("android_update_url"));
		map.put("android_update_info", systemConfigBiz.findValueByKey("android_update_info"));
		resultData.setData(map);
		return resultData;
	}

	/**
	 * 后台管理，针对超级管理员，首页需要的数据
	 */
	@ResponseBody
	@RequestMapping(value = "/manageIndexData")
	public Object manageIndexData(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
			throws IOException {
		ResultData<Object> resultData = new ResultData<Object>();
		Map<String, Object> map = new HashMap<String, Object>();

		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		} else {
			User user = (User) attribute;
			if (user.getRoleId() == 6) {
				String path = request.getRequestURL().toString().replace(request.getRequestURI(), "");
				response.sendRedirect(path + "/guangfu/younen/html/login/login.html");
			}
		}

		// 最新的订单8条
		Page<OrderVO> oPage = new Page<>();
		oPage.setLimit(8);
		Page<OrderVO> osbe = orderBiz.selectByExample(oPage);
		map.put("order", osbe.getList());

		// 最新的未审核服务商8个
		ServerVO server = new ServerVO();
		server.setState(0);
		Page<Server> sPage = new Page<>();
		sPage.setLimit(8);
		sPage.setExample(server);
		Page<Server> ssbe = serverBiz.selectByExample(sPage);
		map.put("server", ssbe.getList());

		DecimalFormat df = new DecimalFormat("0.00");
		// 装机容量
		Double totalCapacity = 0d;
		// 实时功率
		TemStation ts = new TemStation();
		Map<String, Object> par_map = new HashMap<>();
		Station selectCapacityKwTotal = stationBiz.selectCapacityKwTotal(par_map);
		if (selectCapacityKwTotal != null) {
			ts.setKw(selectCapacityKwTotal.getNowKw());
			ts.setKwh(selectCapacityKwTotal.getNowKw());
			totalCapacity = selectCapacityKwTotal.getCapacity();
		}
		map.put("MomentKW", ts);
		map.put("totalCapacity", df.format(totalCapacity));

		// 当月的并网装机容量
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		List<Map<String, Object>> list = new ArrayList<>();
		String[] times = { year + "-01", year + "-02", year + "-03", year + "-04", year + "-05", year + "-06",
				year + "-07", year + "-08", year + "-09", year + "-10", year + "-11", year + "-12" };
		for (String time : times) {
			Map<String, Object> map1 = new HashMap<>();
			map1.put("timeStr", time);
			String capacity = orderBiz.findMonthCapacity(map1);
			if (capacity == null)
				capacity = "0.00";
			Map<String, Object> map2 = new HashMap<>();
			map2.put("createDtm", time);
			map2.put("capacity", capacity);
			list.add(map2);
		}
		map.put("monthCapacity", list);

		// 所有电站总发电量
		Page<Station> staPage = new Page<>();
		staPage.setLimit(Integer.MAX_VALUE);
		Page<Station> stationSbe = stationBiz.selectByExample(staPage);
		Double stkwh = 0d;
		for (int i = 0; i < stationSbe.getList().size(); i++) {
			stkwh += stationSbe.getList().get(i).getWorkTotaKwh();
		}
		map.put("totalStationKwh", df.format(stkwh));

		resultData.setData(map);
		return resultData;
	}

	/**
	 * 后台管理，针对服务商，首页需要的数据
	 */
	@ResponseBody
	@RequestMapping(value = "/serverIndexData")
	public ResultData<Map<String, Object>> serverIndexData(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Map<String, Object>> resultData = new ResultData<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		Object attribute = httpSession.getAttribute("server");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}

		Server server = (Server) attribute;
		// Server server = serverBiz.select(Long.valueOf(23));

		// 最新的订单8条
		OrderVO order = new OrderVO();
		order.setServerId(server.getId());
		Page<OrderVO> oPage = new Page<>();
		oPage.setExample(order);
		oPage.setLimit(8);
		Page<OrderVO> osbe = orderBiz.selectByExample(oPage);
		map.put("neworder", osbe.getList());

		// 自己的用户的电站发电情况
		Station station = new Station();
		station.setServerId(server.getId());
		Page<Station> staPage = new Page<>();
		staPage.setExample(station);
		staPage.setLimit(Integer.MAX_VALUE);
		Page<Station> stationSbe = stationBiz.selectByExample(staPage);
		map.put("stationInfo", stationSbe.getList());

		Double orderTotalMoney = 0d;
		Double totalFactorage = 0d;
		OrderVO finishOrder = new OrderVO();
		finishOrder.setServerId(server.getId());
		// finishOrder.setState(8);
		Page<OrderVO> fPage = new Page<>();
		fPage.setExample(finishOrder);
		fPage.setLimit(Integer.MAX_VALUE);
		Page<OrderVO> fsbe = orderBiz.selectByExample(fPage);
		List<Order> finishorders = new ArrayList<>();
		List<Order> notfinishorders = new ArrayList<>();
		Set<Long> set = new HashSet<>();
		for (Order o : fsbe.getList()) {
			if (o.getState() == 8) {
				finishorders.add(o);
			} else {
				notfinishorders.add(o);
			}
			set.add(o.getUserId());
		}

		for (Order o : fsbe.getList()) {
			orderTotalMoney += o.getTotalMoney();
			totalFactorage += o.getTotalMoney() * server.getFactorage();
		}

		// 总装机容量
		Double totalCapacity = 0d;
		// 实时功率
		Double MomentKW = 0d;
		Map<String, Object> par_map = new HashMap<>();
		par_map.put("serverId", server.getId());
		Station selectCapacityKwTotal = stationBiz.selectCapacityKwTotal(par_map);
		if (selectCapacityKwTotal != null) {
			MomentKW = selectCapacityKwTotal.getNowKw();
			totalCapacity = selectCapacityKwTotal.getCapacity();
		}

		DecimalFormat df = new DecimalFormat("0.00");
		TemStation ts = new TemStation();
		ts.setKw(Double.valueOf(df.format(MomentKW)));
		ts.setKwh(Double.valueOf(df.format(MomentKW)));

		map.put("MomentKW", ts);
		map.put("totalCapacity", totalCapacity);

		// 服务商收入=已完成订单的总金额-总的优能手续费
		map.put("income", orderTotalMoney);
		// 用户总数
		map.put("userNum", set.size());
		// 已完成订单总数
		map.put("finishOrderNum", finishorders.size());
		// 未完成订单总数
		map.put("notfinishOrderNum", notfinishorders.size());

		Double stkwh = 0d;
		for (int i = 0; i < stationSbe.getList().size(); i++) {
			stkwh += stationSbe.getList().get(i).getWorkTotaKwh();
		}
		map.put("totalStationKwh", df.format(stkwh));

		resultData.setData(map);
		return resultData;
	}

}