package com.hy.gf.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.OrderBiz;
import com.hy.gf.biz.StationBiz;
import com.hy.gf.biz.TemStationBiz;
import com.hy.gf.biz.TemStationYearBiz;
import com.hy.gf.model.Page;
import com.hy.gf.model.Server;
import com.hy.gf.model.Station;
import com.hy.gf.model.TemStation;
import com.hy.gf.util.DateJsonValueProcessor;
import com.hy.gf.util.DateUtil;
import com.hy.gf.util.StringUtil;
import com.hy.gf.vo.ResultData;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;


@Controller
@RequestMapping(value = "/temStation")
public class TemStationController {
@Resource
TemStationBiz temStationBiz;
@Resource
TemStationYearBiz temStationYearBiz;
@Resource
StationBiz stationBiz;
@Resource
OrderBiz orderBiz;

//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	temStationBiz.delete(id);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insert")
	public ResultData insert(TemStation temStation, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		temStationBiz.insert(temStation);
		resultData.setData(temStation);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		TemStation temStation = temStationBiz.select(id);
		resultData.setData(temStation);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/update")
	public ResultData update(TemStation temStation, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		temStationBiz.update(temStation);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(TemStation temStation, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		TemStation selectByExample =temStationBiz.selectOneByExample(temStation);
		resultData.setData(selectByExample);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(TemStation temStation, Page<TemStation> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(temStation);
			}
		Page<TemStation> selectByExample = temStationBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<TemStation> list = JSON.parseArray(listJson.getData(), TemStation.class);
		temStationBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		temStationBiz.deleteBatch(ids);
		return resultData;
	}
	
	
	/**
	 * web 获取实时功率
	 */
	@ResponseBody
	@RequestMapping(value = "/getMomentPower")
	public Map<String, Object> getMomentPower(){
		
		// 获取当前时间
		Calendar now = DateUtil.getToday();
		String nowStr = StringUtil.formatLike(DateUtil.formatDate(now.getTime(), "yyyy-MM-dd"));
		
		Map<String, Object> map = new HashMap<>();
		map.put("createDtm", nowStr);
		List<TemStation> gmpl = temStationBiz.getTotalPower(map);
		
		Double totalKw = 0d;
		if(gmpl.size()>0){
			for(TemStation ts:gmpl) {
				totalKw += Double.valueOf(ts.getKw());
			}
			Double momentKw = totalKw/24;
			DecimalFormat df = new DecimalFormat("0.0000"); 
			map.put("momentKw", df.format(momentKw));
		}
		
		return map;
	}
	
	/**
	 * 全网累计发电量
	 * 有type就根据当前登录的服务商来查询
	 */
	@ResponseBody
	@RequestMapping(value = "/getTotalPowerKWH")
	public List<Map<String, Object>> getTotalPower(String type, HttpSession httpSession){
		DecimalFormat df = new DecimalFormat("0.00"); 
		List<Map<String, Object>> list = new ArrayList<>();
		if(StringUtils.isEmpty(type)){
			Map<String, Object> map = new HashMap<>();
			map.put("d_addr", 1); // 1代表发电记录
			List<TemStation> gmpl = temStationBiz.getTotalPower(map);
			
			if(gmpl.size() > 0 ){
				for(TemStation ts:gmpl){
					Map<String, Object> map2 = new HashMap<>();
					// 绝对值增加100倍
					map2.put("time", DateUtil.formatDate(ts.getCreateDtm(),"HH:mm"));
					map2.put("kwh", df.format(Math.abs(Double.valueOf(ts.getKwh()) * 1)));
					list.add(map2);
				}
			} else{
				for(int i=0; i<24; i++){
					Map<String, Object> map2 = new HashMap<>();
					if(i<10){
						map2.put("time", "0"+String.valueOf(i)+":00");
						map2.put("kwh", "0.00");
						list.add(map2);
					} else{
						map2.put("time", ""+String.valueOf(i)+":00");
						map2.put("kwh", "0.00");
						list.add(map2);
					}
				}
			}
		} else{
			Map<String, Object> map = new HashMap<>();
			
			Server server = (Server)httpSession.getAttribute("server");
			Page<Station> page = new Page<>();
			Station station = new Station();
			station.setServerId(server.getId());
			page.setExample(station);
			page.setLimit(Integer.MAX_VALUE);
			Page<Station> sbe = stationBiz.selectByExample(page);
			
			List<String> strList = new ArrayList<>();
			for(Station s:sbe.getList()){
				strList.add("'"+s.getStationCode()+"'");
			}
			String stationCode = StringUtils.join(strList.toArray(),",");
			
			map.put("stationCode", stationCode);
			map.put("d_addr", 1); // 1代表发电记录
			List<TemStation> gmpl = new ArrayList<>();
			if(!StringUtils.isEmpty(stationCode)){
				temStationBiz.getTotalPower(map);
			}
			
			if(gmpl.size() > 0 ){
				for(TemStation ts:gmpl){
					Map<String, Object> map2 = new HashMap<>();
					map2.put("time", DateUtil.formatDate(ts.getCreateDtm(),"HH:mm"));
					map2.put("kwh", df.format(Math.abs(Double.valueOf(ts.getKwh()) * 1)));
					list.add(map2);
				}
			} else{
				for(int i=0; i<24; i++){
					Map<String, Object> map2 = new HashMap<>();
					if(i<10){
						map2.put("time", "0"+String.valueOf(i)+":00");
						map2.put("kwh", "0.00");
						list.add(map2);
					} else{
						map2.put("time", ""+String.valueOf(i)+":00");
						map2.put("kwh", "0.00");
						list.add(map2);
					}
				}
			}
		}
		
		return list;
	}
	
	DecimalFormat df = new DecimalFormat("0.##");
	/**
	 * web 根据stationCode查询当天发电量
	 * @param stationCode
	 * @param type 1查询当天每小时的发电量，2查询当天每小时的功率
	 */
	@ResponseBody
	@RequestMapping(value = "/getTodayPowerByStationCode")
	public Object getTodayPowerByStationCode(String stationCode, Integer type, String time){
		ResultData<Object> resultData = new ResultData<Object>();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("station_code", stationCode);
		paramMap.put("time", time);
		paramMap.put("d_addr", 1); // 发电
		List<TemStation> list = temStationBiz.getOneDayPower(paramMap);
		paramMap.put("d_addr", 2); // 用电
		List<TemStation> usedList = temStationBiz.getOneDayPower(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("HH:00"));
		
		map.put("list", JSONArray.fromObject(list, jsonConfig));
		map.put("usedList",  JSONArray.fromObject(usedList, jsonConfig));
		map.put("total", list.size());
		
		if(1 == type){ // 总发电量
			Double totalKWH = 0d;
			Double usedKWH = 0d;	//用电量
			if(list.size()>0){
				for(TemStation t:list){
					totalKWH += t.getKwh();
				}
			}
			if(usedList.size()>0) {
				for(TemStation t:usedList){
					usedKWH += t.getKwh();
				}
			}
			map.put("totalKWH", df.format(totalKWH)+"瓦");
			map.put("usedKWH", df.format(usedKWH)+"瓦");
		} else if(2 == type){ // 总功率
			Double totalKW = 0d;
			if(list.size()>0){
				for(TemStation t:list){
					totalKW += Double.valueOf(t.getKw());
				}
			}
			map.put("totalKW", df.format(totalKW)+"瓦");
		}
		
		resultData.setData(map);
		
		return resultData;
	}
	
	
	/**
	 * 后台管理 根据stationCOde，日期或年，查询电站某天或某年的发电量和功率
	 * @param stationCode
	 * @param date
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findPowerByDateOrYear")
	public Object findPowerByDateOrYear(String stationCode, String year, String time_from, String time_to, Integer type, String d_addr) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		Map<String, Object> map = new HashMap<>();
		map.put("stationCode", stationCode);
		map.put("d_addr", d_addr);
		if(type == 1) {
			map.put("time_from", time_from);
			map.put("time_to", time_to);
			List<Map> fpbdoy = temStationBiz.findPowerByDate(map);
			resultData.setData(fpbdoy);
		} else if(type == 2) {
			map.put("date", year);
			List<Map> fpbdoy = temStationBiz.findPowerByDate(map);
			resultData.setData(fpbdoy);
		}
		
		return resultData;
	}
	
	/**
	 * 首页以百分比显示实时功率， 实时功率/装机容量*100
	 */
	@ResponseBody
	@RequestMapping(value = "/findThisMomentTotalPower2")
	public Object findThisMomentTotalPower2(String type, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		if (StringUtils.isEmpty(type)){
			Map<String, Object> par_map = new HashMap<>();
			Station selectCapacityKwTotal = stationBiz.selectCapacityKwTotal(par_map);
			TemStation tts = new TemStation();
			if (selectCapacityKwTotal != null) {
				tts.setKw(Double.valueOf(df.format((selectCapacityKwTotal.getNowKw() / selectCapacityKwTotal.getCapacity()) * 100)));
				tts.setKwh(Double.valueOf(String.valueOf(selectCapacityKwTotal.getNowKw())));
			} else {
				tts.setKw(0d);
				tts.setKwh(0d);
			}
			resultData.setData(tts);
			return resultData;
		} else{
			Server server = (Server)httpSession.getAttribute("server");
			Map<String, Object> par_map = new HashMap<>();
			par_map.put("serverId", server.getId());
			Station selectCapacityKwTotal = stationBiz.selectCapacityKwTotal(par_map);
			TemStation tts = new TemStation();
			if (selectCapacityKwTotal != null) {
				tts.setKw(Double.valueOf(df.format((selectCapacityKwTotal.getNowKw() / selectCapacityKwTotal.getCapacity()) * 100)));
				tts.setKwh(Double.valueOf(String.valueOf(selectCapacityKwTotal.getNowKw())));
			} else {
				tts.setKw(0d);
				tts.setKwh(0d);
			}
			resultData.setData(tts);
			return resultData;
		}
	}
	
	/**
	 * 已完成电站里面的第一个图表--实时功率
	 */
	@ResponseBody
	@RequestMapping(value = "/findMomentPowerByStationCode")
	public Object findMomentPowerByStationCode(String stationCode, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		Map<String, Object> par_map = new HashMap<>();
		par_map.put("stationCode", stationCode);
		Station station = stationBiz.selectCapacityKwTotal(par_map);
		
		Map<String, Object> rm = new HashMap<>();
		
		if (station != null) {
			rm.put("kw", station.getNowKw());
			rm.put("percent", (station.getNowKw() / station.getCapacity()) * 100);
		} else {
			rm.put("kw", 0);
			rm.put("percent", 0);
		}
		
		resultData.setData(rm);
		return resultData;
	}
	
	/**
	 * 全网累计发电量
	 */
	@ResponseBody
	@RequestMapping(value = "/totalKwh")
	public Object totalKwh(String stationCode, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		Map<String, Object> map = new HashMap<>();
		if(!StringUtils.isEmpty(stationCode)) {
			map.put("station_code", stationCode);
		}
		
		Double total_kwh = temStationBiz.findTotalKwh(map);
		
		resultData.setData(total_kwh);
		return resultData;
	}
	
	/**
	 * App首页:全网累积发电  和  全网并网装机容量
	 */
	@ResponseBody
	@RequestMapping(value = "/kwhAndCapacity")
	public Object kwhAndCapacity(HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		Map<String, Object> map = new HashMap<>();
		Double total_kwh = temStationBiz.findTotalKwh(map);
		
		Double totalCapacity = 0d;
		Page<Station> sp = new Page<>();
		Station station = new Station();
		station.setState(2);
		sp.setExample(station);
		sp.setLimit(Integer.MAX_VALUE);
		Page<Station> station_sbe = stationBiz.selectByExample(sp);
		for( int i=0; i<station_sbe.getList().size(); i++ ) {
			if( !StringUtils.isEmpty(station_sbe.getList().get(i).getAmmeterCode()) ) {
				totalCapacity += station_sbe.getList().get(i).getCapacity();
			}
		}
		
		Map<String, Object> rm = new HashMap<>();
		rm.put("total_kwh", df.format(total_kwh));
		rm.put("totalCapacity", df.format(totalCapacity));
		
		resultData.setData(rm);
		return resultData;
	}
	
	/**
	 * 根据电站id查找该电站当日，当月，当年的发电量
	 */
	@ResponseBody
	@RequestMapping(value = "/dayMonthYearKwh")
	public Object dayMonthYearKwh(Long id) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		Station station = stationBiz.select(id);
		
		Map<String, Object> rm = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		
		Double thisDay = 0d;
		Double thisMonth = 0d;
		Double thisYear = 0d;
		
		paramMap.put("stationCode", station.getStationCode());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		paramMap.put("date", sdf.format(new Date()));
		List<Map> thisDaymap = temStationBiz.findPowerByDate(paramMap);
		if (thisDaymap.size() > 0) {
			if (thisDaymap.get(0) != null) {
				thisDay = (Double)thisDaymap.get(0).get("kwh");
			}
		}
		rm.put("thisDay", thisDay);
		
		sdf = new SimpleDateFormat("yyyy-MM");
		paramMap.put("date", sdf.format(new Date()));
		List<Map> thisMonthmap = temStationBiz.findPowerByDate(paramMap);
		if (thisMonthmap.size() > 0) {
			if (thisDaymap.get(0) != null) {
				thisMonth = (Double)thisMonthmap.get(0).get("kwh");
			}
		}
		rm.put("thisMonth", thisMonth);
		
		sdf = new SimpleDateFormat("yyyy");
		paramMap.put("date", sdf.format(new Date()));
		List<Map> thisYearmap = temStationBiz.findPowerByDate(paramMap);
		if (thisYearmap.size() > 0) {
			if (thisDaymap.get(0) != null) {
				thisYear = (Double)thisYearmap.get(0).get("kwh");
			}
		}
		rm.put("thisYear", thisYear);
		
		resultData.setData(rm);
		return resultData;
	}
	
	/**
	 * 根据时间段，返回这段时间内每天的发电量，以天为单位
	 */
	@ResponseBody
	@RequestMapping(value = "/eachDayByTime")
	public Object eachDayByTime(String stationCode, Page<TemStation> page) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("time_from", page.getTime_from());
		map.put("time_to", page.getTime_to());
		map.put("stationCode", stationCode);
		
		List<TemStation> eachDayByTime = temStationBiz.eachDayByTime(map);
		
		/*List<Map> rl = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (TemStation temStation : eachDayByTime) {
			Map<String, Object> rm = new HashMap<>();
			rm.put("time", sdf.format(temStation.getCreateDtm()));
			rm.put("kwh", temStation.getKwh());
			rl.add(rm);
		}*/
		
		resultData.setData(eachDayByTime);
		return resultData;
	}
	
	/**
	 * 发电量统计图，
	 * 统计当前电站发电量，
	 * 按日/周/月/年为X轴时间单位来展示近期发电量趋势图
	 */
	@ResponseBody
	@RequestMapping(value = "/energyInfoEcharts")
	public Object energyInfoEcharts(String station_code, Integer type, String date_str, String d_addr, Page<TemStation> page) {
		ResultData<Object> resultData = new ResultData<Object>();
		Map<String, Object> map = new HashMap<>();
		
		List<Map> eachDayByTime = new ArrayList<>();
		map.put("type", type);
		map.put("station_code", station_code);
		map.put("date_str", date_str);
		map.put("d_addr", d_addr);
		if (type == 0) {
			map.put("date_format", "%Y");
			map.put("date_format2", "%Y");
		} else if (type == 1) {
			map.put("date_format", "%Y-%m");
			map.put("date_format2", "%Y-%m");
		} else if (type == 2) {
			map.put("date_format", "%Y-%m-%d");
			map.put("date_format2", "%Y-%m-%d");
		} else if (type == 3) {
			map.put("date_format", "%Y-%m-%d %H");
			map.put("date_format2", "%Y-%m-%d %H");
		}
		eachDayByTime = temStationBiz.energyInfoEcharts(map);
		
		resultData.setData(eachDayByTime);
		return resultData;
	}
	
}