package com.hy.gf.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.SystemConfigBiz;
import com.hy.gf.biz.TemStationYearBiz;
import com.hy.gf.model.Page;
import com.hy.gf.model.TemStationYear;
import com.hy.gf.model.User;
import com.hy.gf.util.Constant;
import com.hy.gf.vo.ResultData;


@Controller
@RequestMapping(value = "/temStationYear")
public class TemStationYearController {
@Resource
TemStationYearBiz temStationYearBiz;
@Resource
SystemConfigBiz systemConfigBiz;


//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	temStationYearBiz.delete(id);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insert")
	public ResultData insert(TemStationYear temStationYear, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		temStationYearBiz.insert(temStationYear);
		resultData.setData(temStationYear);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		TemStationYear temStationYear = temStationYearBiz.select(id);
		resultData.setData(temStationYear);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/update")
	public ResultData update(TemStationYear temStationYear, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		temStationYearBiz.update(temStationYear);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(TemStationYear temStationYear, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		TemStationYear selectByExample =temStationYearBiz.selectOneByExample(temStationYear);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(TemStationYear temStationYear, Page<TemStationYear> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(temStationYear);
			}
		Page<TemStationYear> selectByExample = temStationYearBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<TemStationYear> list = JSON.parseArray(listJson.getData(), TemStationYear.class);
		temStationYearBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		temStationYearBiz.deleteBatch(ids);
		return resultData;
	}
	
	
	/**
	 * web 根据stationCode查找今年的发电量
	 * @param temStationYear
	 */
	@ResponseBody
	@RequestMapping(value = "/getYearPower")
	public Object getYearPower(TemStationYear temStationYear, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(date);

		List<Map<String, Object>> rl = new ArrayList<>();
		List<Map<String, Object>> usedRl = new ArrayList<>();
		String [] time = {year+"-01",year+"-02",year+"-03",year+"-04",year+"-05",year+"-06",year+"-07",year+"-08",year+"-09",year+"-10",year+"-11",year+"-12"};
		for(String timeStr:time){
			Map<String, Object> map = new HashMap<>();
			map.put("stationCode", temStationYear.getStationCode());
			map.put("createDtm", timeStr);
			
			map.put("d_addr", 1); // 发电
			TemStationYear tsy1 = temStationYearBiz.findEachMonth(map);
			Map<String, Object> map2 = new HashMap<>();
			map2.put("createDtm", timeStr);
			map2.put("kwh", tsy1 != null ? tsy1.getKwh() : 0);
			map2.put("kw",tsy1 != null ?  tsy1.getKw() : 0);
			rl.add(map2);

			map.put("d_addr", 2); // 用电
			TemStationYear tsy2 = temStationYearBiz.findEachMonth(map);
			Map<String, Object> map3 = new HashMap<>();
			map3.put("createDtm", timeStr);
			map3.put("kwh", tsy2 != null ? tsy2.getKwh() : 0);
			map3.put("kw",tsy2 != null ?  tsy2.getKw() : 0);
			usedRl.add(map3);
		}
		
		Map<String, List<Map<String, Object>>> data = new HashMap<>();
		data.put("list", rl);
		data.put("usedList", usedRl);
		resultData.setData(data);
		
		/*List<TemStationYear> fem = temStationYearBiz.findEveryMonth(temStationYear.getStationCode());
		if(fem.size() > 0) {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM"));
			JSONArray jsonArray = JSONArray.fromObject(fem, jsonConfig);
			
			resultData.setData(jsonArray);
		}*/
		
		return resultData;
	}
	
	/**
	 * 根据userId查询环保贡献，按月份排
	 * 环保植树，环保减排
	 */
	@ResponseBody
	@RequestMapping(value = "/huanbao")
	public Object huanbao(TemStationYear temStationYear, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		User user = (User)httpSession.getAttribute("user");
		if(user == null){
			return Constant.noLogin(resultData);
		}
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		// 植树参数
		Double plant_trees_prm = Double.valueOf(systemConfigBiz.findValueByKey("plant_trees_prm"));
		// co2减排参数
		Double CO2_prm = Double.valueOf(systemConfigBiz.findValueByKey("CO2_prm"));
		// SO减排参数
		Double SO_prm = Double.valueOf(systemConfigBiz.findValueByKey("SO_prm"));
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(date);

		List<Map<String, Object>> rl = new ArrayList<>();
		List<Map<String, Object>> rl2 = new ArrayList<>();
		List<Map<String, Object>> rl3 = new ArrayList<>();
		String [] time = {year+"-01",year+"-02",year+"-03",year+"-04",year+"-05",year+"-06",year+"-07",year+"-08",year+"-09",year+"-10",year+"-11",year+"-12"};
		for(String timeStr:time){
			Map<String, Object> map = new HashMap<>();
			map.put("userId", user.getId());
			map.put("createDtm", timeStr);
			TemStationYear tsy = temStationYearBiz.findHuanbao(map);
			Map<String, Object> map2 = new HashMap<>();
			Map<String, Object> map3 = new HashMap<>();
			Map<String, Object> map4 = new HashMap<>();
			if(tsy != null){
				map2.put("co2", df.format(Double.valueOf(tsy.getKwh()) * CO2_prm));
				map2.put("createDtm", timeStr);
				map3.put("treeNum", df.format(Double.valueOf(tsy.getKwh()) * plant_trees_prm));
				map3.put("createDtm", timeStr);
				map4.put("SONum", df.format(Double.valueOf(tsy.getKwh()) * SO_prm));
				map4.put("createDtm", timeStr);
				
				rl.add(map2);
				rl2.add(map3);
				rl3.add(map4);
			} else{
				map2.put("co2", 0);
				map2.put("createDtm", timeStr);
				map3.put("treeNum", 0);
				map3.put("createDtm", timeStr);
				map4.put("SONum", 0);
				map4.put("createDtm", timeStr);
				
				rl.add(map2);
				rl2.add(map3);
				rl3.add(map4);
			}
		}
		
		Map<String, Object> rm = new HashMap<>();
		rm.put("co2data", rl);
		rm.put("treeData", rl2);
		rm.put("SOData", rl3);
		
		resultData.setData(rm);
		return resultData;
	}
	
	/**
	 * 查询每天的发电量
	 */
	@ResponseBody
	@RequestMapping(value = "/eachdayKWH")
	public Object eachdayKWH(String station_code, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("station_code", station_code);
		paramMap.put("d_addr", 1); // 发电
		List<TemStationYear> list = temStationYearBiz.eachdayKWH(paramMap);
		
		resultData.setData(list);
		return resultData;
	}
	
}