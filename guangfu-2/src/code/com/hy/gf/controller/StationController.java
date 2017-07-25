package com.hy.gf.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.AmmeterBiz;
import com.hy.gf.biz.CityBiz;
import com.hy.gf.biz.OrderBiz;
import com.hy.gf.biz.ServerBiz;
import com.hy.gf.biz.StationBiz;
import com.hy.gf.biz.SubsidyBiz;
import com.hy.gf.biz.SystemConfigBiz;
import com.hy.gf.biz.TemStationBiz;
import com.hy.gf.biz.TemStationYearBiz;
import com.hy.gf.model.Ammeter;
import com.hy.gf.model.Order;
import com.hy.gf.model.Page;
import com.hy.gf.model.Server;
import com.hy.gf.model.Station;
import com.hy.gf.model.Subsidy;
import com.hy.gf.model.TemStationYear;
import com.hy.gf.model.User;
import com.hy.gf.util.Constant;
import com.hy.gf.util.DateUtil;
import com.hy.gf.vo.ResultData;
import com.hy.gf.vo.StationVO;

@Controller
@RequestMapping(value = "/station")
public class StationController {
	@Resource
	StationBiz stationBiz;
	@Resource
	OrderBiz orderBiz;
	@Resource
	CityBiz cityBiz;
	@Resource
	SubsidyBiz subsidyBiz;
	@Resource
	SystemConfigBiz systemConfigBiz;
	@Resource
	ServerBiz serverBiz;
	@Resource
	TemStationBiz temStationBiz;
	@Resource
	AmmeterBiz ammeterBiz;
	@Resource
	TemStationYearBiz temStationYearBiz;

	@ResponseBody
	@RequestMapping(value = "/delete")
	public Object delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		// stationBiz.delete(id);
		
		User user = (User)httpSession.getAttribute("admin");
		if(user == null){
			return Constant.noLogin(resultData);
		}
		
		Station station = stationBiz.select(id);
		Station ns = new Station();
		ns.setId(station.getId());
		ns.setDel(1);
		ns.setDel_dtm(new Date());
		int flag = stationBiz.update(ns);
		
		if (flag == 1) {
			// 删除订单
			Order order = orderBiz.select(station.getOrderId());
			Order no = new Order();
			no.setId(order.getId());
			no.setDel(1);
			no.setDel_dtm(new Date());
			orderBiz.update(no);
			
			// 清空该电站电表绑定的电站id
			String[] ammeters = station.getAmmeterCode().split(",");
			if (ammeters.length > 0 && !StringUtils.isEmpty(ammeters[0])) {
				for (String am_code : ammeters) {
					Ammeter ammeter = ammeterBiz.selectByCode(am_code);
					Ammeter na = new Ammeter();
					na.setId(ammeter.getId());
					na.setStationId("");
					ammeterBiz.update(na);
				}
			}
		}
		
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/insert")
	public Object insert(Station station, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		stationBiz.insert(station);
		resultData.setData(station);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		/*User user = (User)httpSession.getAttribute("user");
		if(user == null) {
			return Constant.noLogin(resultData);
		} */
		
		Station station = stationBiz.select(id);
		resultData.setData(station);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public ResultData update(Station station, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		stationBiz.update(station);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Station station, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Station selectByExample = stationBiz.selectOneByExample(station);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData<Page<Station>> selectByExample(StationVO stationVO, Page<Station> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData<Page<Station>> resultData = new ResultData<Page<Station>>();
		if (page.getExample() == null) {
			page.setExample(stationVO);
		}
		Page<Station> selectByExample = stationBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<Station> list = JSON.parseArray(listJson.getData(), Station.class);
		stationBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		stationBiz.deleteBatch(ids);
		return resultData;
	}
	
	/*
	 * session用户根据stationId查找自己的电站
	 */
	@ResponseBody
	@RequestMapping(value = "/select2")
	public ResultData select2(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Station station = stationBiz.select(id);
		
		User user = (User)httpSession.getAttribute("user");
		if(station == null || station.getUserId() != user.getId().intValue()){
			resultData.setMsg("无权访问");
			resultData.setSuccess(false);
			return resultData;
		}
		
		if(!StringUtils.isBlank(station.getAmmeterCode())) {
			String[] ammeterCodeArr = station.getAmmeterCode().split(",");
			List<Double> list = new ArrayList<>();
			for(String code:ammeterCodeArr) {
				Ammeter ammeter = ammeterBiz.selectByCode(code);
				list.add(ammeter.getInitKwh());
			}
			if(list.size() > 0) {
				station.setAmmeter_initKwh_total(StringUtils.join(list.toArray(), ", "));
			}
		}
		
		resultData.setData(station);
		return resultData;
	}
	
	/**
	 * web 查询运行中电站
	 */
	@ResponseBody
	@RequestMapping(value = "/runningStation")
	public ResultData runningStation(Station station, Page<Station> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		User user = (User)httpSession.getAttribute("user");
		if(user == null) {
			return Constant.noLogin(resultData);
		}
		
		station.setUserId(user.getId());
		if (page.getExample() == null) {
			page.setExample(station);
		}
		page.setStart(0);
		page.setLimit(Integer.MAX_VALUE);
		Page<Station> sbe = stationBiz.selectByExample(page);
		
		List<Map<String, Object>> list = new ArrayList<>();
		if(sbe.getList().size() > 0) {
			
			Double plant_trees_prm = Double.valueOf(systemConfigBiz.findValueByKey("plant_trees_prm")); // 植树参数
			Double CO2_prm = Double.valueOf(systemConfigBiz.findValueByKey("CO2_prm")); // 减排co2参数
			DecimalFormat df = new DecimalFormat("0.00"); 
			
			for(Station st:sbe.getList()) {
				Map<String, Object> map = new HashMap<>();
				
				map.put("id", st.getId());
				map.put("stationCode", st.getStationCode());
				map.put("stationName", st.getStationName());
				map.put("nowKw", st.getNowKw());
				map.put("workTotaTm", st.getWorkTotaTm());
				map.put("workTotaKwh", st.getWorkTotaKwh());
				map.put("status", st.getStatus()==2?1:st.getStatus());
				map.put("treeNum", df.format(st.getWorkTotaKwh() * plant_trees_prm));
				map.put("CO2", df.format(st.getWorkTotaKwh() * CO2_prm));
				list.add(map);
			}
		}
		
		Map<String, Object> rm = new HashMap<>();
		rm.put("list", list);
		resultData.setData(rm);
		
		
		return resultData;
	}
	
	/**
	 * 根据stationCode查找25年收益
	 */
	@ResponseBody
	@RequestMapping(value = "/find25YearIncome")
	public ResultData find25YearIncome(Station station, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Map<String,Object> map = get25YearIncome(station);
		resultData = (ResultData)map.get("resultData");
		
		resultData.setData(resultData.getData());
		
		return resultData;
	}
	
	
	/**
	 * web 优能电站增长图表
	 */
	@ResponseBody
	@RequestMapping(value = "/findIncreaseStation")
	public ResultData findIncreaseStation(String countType, Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Map<String, Object> map = new HashMap<>();
		
		if(StringUtils.isEmpty(countType)){
			map.put("state", 2);
			List<Map> list = stationBiz.findIncreaseStation(map);
			resultData.setData(list);
			return resultData;
		} else{
			Server server = (Server)httpSession.getAttribute("server");
			map.put("state", 2);
			map.put("serverId", server.getId());
			List<Map> list = stationBiz.findIncreaseStation(map);
			resultData.setData(list);
			return resultData;
		}
	}
	
	/**
	 * 后台添加电表
	 */
	@ResponseBody
//	@RequestMapping(value = "/addAmmeter")
	public ResultData addAmmeter(Long id, String newCode, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Ammeter ammeter = new Ammeter();
		ammeter.setCode(newCode);
		Ammeter amsobe = ammeterBiz.selectOneByExample(ammeter);
		if(amsobe == null){
			resultData.setCode(777);
			resultData.setMsg("电表不存在，无法绑定");
			resultData.setSuccess(false);
			return resultData;
		} else if (amsobe.getStationId().equals(String.valueOf(id))) {
			resultData.setCode(777);
			resultData.setMsg("电表已经绑定该电站，无须重复绑定");
			resultData.setSuccess(false);
			return resultData;
		}
		
		Station station = stationBiz.select(id);
		if(StringUtils.isEmpty(station.getAmmeterCode())){
			station.setAmmeterCode(newCode);
			stationBiz.update(station);
			
			amsobe.setStationId(String.valueOf(station.getId()));
			ammeterBiz.update(amsobe);
			
			return resultData;
		}
		String [] ammeters = station.getAmmeterCode().split(",");
		List<String> ammeterList = new ArrayList<>();
		Collections.addAll(ammeterList, ammeters);
		if(ammeterList.contains(newCode)){
			resultData.setCode(400);
			resultData.setMsg("该电站已经绑定了这个电表，无法重复绑定");
			return resultData;
		}
		ammeterList.add(newCode);
		String ammeterStr = StringUtils.join(ammeterList,",");
		station.setAmmeterCode(ammeterStr);
		stationBiz.update(station);
		
		amsobe.setStationId(String.valueOf(station.getId()));
		ammeterBiz.update(amsobe);
		
		return resultData;
	}
	
	/**
	 * 后台删除电表
	 */
	@ResponseBody
//	@RequestMapping(value = "/delAmmeter")
	public ResultData delAmmeter(Long id, String oldCode, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Station sob = stationBiz.select(id);
		if (sob.getAmmeterCode() != null) {
			String [] ammeters = sob.getAmmeterCode().split(",");
			List<String> ammetersList = new ArrayList<String>();
			Collections.addAll(ammetersList, ammeters);
			
			for(int i=0; i<ammetersList.size(); i++){
				if(ammetersList.get(i).equals(oldCode)){
					ammetersList.remove(i);
				} 
			}
			
			sob.setAmmeterCode(StringUtils.join(ammetersList,","));
			stationBiz.update(sob);
			
			Ammeter ammeter = new Ammeter();
			ammeter.setCode(oldCode);
			ammeter = ammeterBiz.selectOneByExample(ammeter);
			ammeter.setStationId("");
			ammeterBiz.update(ammeter);
			
			resultData.setMsg("解绑电表成功");
			return resultData;
		}
		
		return resultData;
	}
	
	/**
	 * 后台修改电站电表
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAmmeterCode")
	public ResultData updateAmmeterCode(Long id, String codesStr, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object object = httpSession.getAttribute("admin");
		if (object == null) {
			return Constant.noLogin(resultData);
		}
		
		String[] codes = codesStr.split(",");
		List<String> codeList = new ArrayList<>();
		Collections.addAll(codeList, codes);
		
		Station station = stationBiz.select(id);
		String[] stationCodes = station.getAmmeterCode().split(",");
		List<String> stationCodeList = new ArrayList<>();
		Collections.addAll(stationCodeList, stationCodes);
		
		// 把电表的stationId先变为空
		Ammeter am = new Ammeter();
		for(String code:stationCodeList){
			am.setCode(code);
			Ammeter sobe = ammeterBiz.selectOneByExample(am);
			if(sobe != null){
				sobe.setStationId("");
				ammeterBiz.update(sobe);
			}
		}
		for (String code:codeList){
			am.setCode(code);
			Ammeter sobe = ammeterBiz.selectOneByExample(am);
			if(sobe != null){
				sobe.setStationId(String.valueOf(id));
				ammeterBiz.update(sobe);
			}
		}
		
		station.setAmmeterCode(codesStr);
		stationBiz.update(station);
		
		return resultData;
	}
	
	/**
	 * 根据stationCode查找发电量、实时功率、25年总收益
	 */
	@ResponseBody
	@RequestMapping(value = "/kwKwh25Total")
	public ResultData kwKwh25Total(Station station, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Map<String, Object> rm = get25YearIncome(station);
		
		Station sobe = stationBiz.selectOneByExample(station);
		Map<String,Object> map = new HashMap<>();
		map.put("workTotaKwh", sobe.getWorkTotaKwh());
		map.put("nowKw", sobe.getNowKw());
		map.put("totalIncomeOf25Year", rm.get("totalMoneyOf25Year"));
		
		resultData.setData(map);
		return resultData;
	}
	
	private Map<String, Object> get25YearIncome(Station station){
		ResultData resultData = new ResultData();
		Map<String, Object> rm = new HashMap<>();
		
		station = stationBiz.selectOneByExample(station);
		
		// 25年总收益
		Double totalMoneyOf25Year = 0d;
		
		Subsidy s = new Subsidy();
		s.setCityId(station.getCityId());
		s.setType(station.getType());
		Subsidy sob = subsidyBiz.selectOneByExample(s);
		
		// 如果电站所在城市没有模拟数据，就根据服务商的服务城市查找模拟数据，再没有就返回：“该地区没有模拟数据，请到数据库中添加地区模拟数据“
		if(sob == null) {
			Server server = serverBiz.select(Long.valueOf(station.getServerId()));
			String[] ids = server.getServerCityIds().split(",");
			
			if(ids!=null&&ids.length>0) {
				for(String id:ids) {
					s.setCityId(Long.valueOf(id));
					s.setType(station.getType());
					sob = subsidyBiz.selectOneByExample(s);
					if(sob != null) break;
				}
			}
			
			if(sob == null) {
				resultData.setMsg("该地区没有模拟数据，请到数据库中添加地区模拟数据");
				rm.put("resultData", resultData);
				rm.put("totalMoneyOf25Year", totalMoneyOf25Year);
				return rm;
			}
		}
		
		// 电站使用年限: 25年
		int year = Integer.parseInt(systemConfigBiz.findValueByKey("station_durable_years"));
		
		// 衰减率，每年0.8%（0.008），25年共20%
		Double dampingRate = Double.valueOf(systemConfigBiz.findValueByKey("damping_rate"))/100;
		
		// 年发电量=装机容量*年日招数
		Double yearTotalKWH = station.getCapacity()*Double.valueOf(sob.getSunCount());
		
		// 一年国家补贴=全年发电量*国家补贴（元/度）*国家补贴年限
		Double countrySub = Double.valueOf(systemConfigBiz.findValueByKey("country_subsidy"));
		Double countrySubYear = Double.valueOf(systemConfigBiz.findValueByKey("country_subsidy_year"));
		Double countrySubTotal = countrySub*countrySubYear*yearTotalKWH;
		Double countrySubOneYear = countrySub*yearTotalKWH;
		
		// 一年地方补贴=全年发电量*地方补贴（元/度）*地方补贴年限
		Double difangSub = sob.getSubsidy();
		int difangSubYear = sob.getBsidyYear();
		Double difangSubTotal = difangSub*difangSubYear*yearTotalKWH;
		Double difangSubOneYear = difangSub*yearTotalKWH;
		
		// 一年：节省电费+国家补贴+地方补贴+卖出电费
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		List<Map<String,Object>> list = new ArrayList<>();
		for (int i=1; i<=year; i++) {
			Map<String,Object> map = new HashMap<>();
			// 第几年
			map.put("year", i+"年");
			// 国家补贴
			if(countrySubYear>=i){
				map.put("countrySub", df.format(countrySubOneYear));
				totalMoneyOf25Year += countrySubOneYear;
			}else{
				map.put("countrySub", 0);
				totalMoneyOf25Year += 0;
			}
			// 地方补贴
			if(difangSubYear>=i){
				map.put("difangSub", df.format(difangSubOneYear));
				totalMoneyOf25Year += difangSubOneYear;
			}else{
				map.put("difangSub", 0);
				totalMoneyOf25Year += 0;
			}
			
			if((i-1) == 0){
				// 节省电费=年发电量*本地用电价格*自用率
				Double economicTotal = yearTotalKWH*sob.getSelfuse()*(1-sob.getSell());
				// 出售电费=年发电量*售电价格*出售率
				Double sellTotal = yearTotalKWH*sob.getSellPrice()*sob.getSell();
				// 年发电量*衰减率
				yearTotalKWH = yearTotalKWH*(1-dampingRate);
				
				// 第一年不用减去衰减率
				map.put("economicTotal", df.format(economicTotal));
				map.put("sellTotal", df.format(sellTotal));
				totalMoneyOf25Year += economicTotal;
				totalMoneyOf25Year += sellTotal;
			}else {
				Double economicTotal = yearTotalKWH*sob.getSelfuse()*(1-sob.getSell());
				Double sellTotal = yearTotalKWH*sob.getSellPrice()*sob.getSell();
				yearTotalKWH = yearTotalKWH*(1-dampingRate);
				
				map.put("economicTotal", df.format(economicTotal));
				map.put("sellTotal", df.format(sellTotal));
				totalMoneyOf25Year += economicTotal;
				totalMoneyOf25Year += sellTotal;
			}
			
			list.add(map);
		}
		String totalMoneyOf25YearStr = df.format(totalMoneyOf25Year);
		resultData.setData(list);
		
		rm.put("resultData", resultData);
		rm.put("totalMoneyOf25Year", totalMoneyOf25YearStr);
		return rm;
	}
	
	/**
	 * 针对后台，根据电站的地址查出省份
	 */
	@ResponseBody
	@RequestMapping(value = "/findPro")
	public ResultData findPro(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Station station = new Station();
		Page<Station> page = new Page<>();
		page.setExample(station);
		page.setLimit(Integer.MAX_VALUE);
		
		Page<Station> sbe = stationBiz.selectByExample(page);
		List<Station> sl = sbe.getList();
		
		Set<Long> proSet = new HashSet<>();
		for(Station s:sl){
			proSet.add(s.getProvinceId());
		}
		List<String> proTextList = new ArrayList<>();
		List<Station> StationList = new ArrayList<>();
		for(Station s:sl){
			if(!proTextList.contains(s.getProvinceText())){
				StationList.add(s);
				proTextList.add(s.getProvinceText());
			}
		}
		
		List<Map<String, Object>> rl= new ArrayList<>();
		for(Station s:StationList){
			Map<String, Object> map = new HashMap<>();
			map.put("id", s.getProvinceId());
			map.put("name", s.getProvinceText());
			rl.add(map);
		}
		
		resultData.setData(rl);
		return resultData;
	}
	
	/**
	 * 针对后台，根据省份查出城市
	 */
	@ResponseBody
	@RequestMapping(value = "/findCity")
	public ResultData findCity(Long provinceId, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		if(provinceId == null){
			resultData.setCode(500);
			resultData.setSuccess(false);
			resultData.setMsg("省份id不能为空");
			return resultData;
		}
		
		Station station = new Station();
		station.setProvinceId(provinceId);
		Page<Station> page = new Page<>();
		page.setExample(station);
		page.setLimit(Integer.MAX_VALUE);
		
		Page<Station> sbe = stationBiz.selectByExample(page);
		List<Station> sl = sbe.getList();
		
		Set<String> proSet = new HashSet<>();
		for(Station s:sl){
			proSet.add(s.getCityText());
		}
		
		List<String> cityTextList = new ArrayList<>();
		List<Station> StationList = new ArrayList<>();
		for(Station s:sl){
			if(!cityTextList.contains(s.getCityText())){
				StationList.add(s);
				cityTextList.add(s.getCityText());
			}
		}
		
		List<Map<String, Object>> rl= new ArrayList<>();
		for(Station s:StationList){
			Map<String, Object> map = new HashMap<>();
			map.put("id", s.getCityId());
			map.put("name", s.getCityText());
			rl.add(map);
		}
		
		resultData.setData(rl);
		return resultData;
	}
	
	/**
	 * 查找电站分布
	 */
	@ResponseBody
	@RequestMapping(value = "/stationFenbu")
	public ResultData stationFenbu(String countType, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Map<String, Object> map = new HashMap<>();
		
		if(!StringUtils.isEmpty(countType)){
			Server server = (Server)httpSession.getAttribute("server");
			
			map.put("serverId", server.getId());
			map.put("state", 2);
			List<Map> rm = stationBiz.stationFenbu(map);
			List<Map> rm2 = new ArrayList<>();
			for(int i=0; i<rm.size(); i++){
				Map<String, Object> map2 = new HashMap<>();
				if(rm.get(i).get("provinceName") != null){
					map2.put("name", provinceName((String)rm.get(i).get("provinceName")));
					map2.put("value", rm.get(i).get("stationNum"));
					rm2.add(map2);
				}
			}
			resultData.setData(rm2);
			return resultData;
		} else{
			map.put("state", 2);
			List<Map> rm = stationBiz.stationFenbu(map);
			List<Map> rm2 = new ArrayList<>();
			for(int i=0; i<rm.size(); i++){
				Map<String, Object> map2 = new HashMap<>();
				if(rm.get(i).get("provinceName") != null){
					map2.put("name", provinceName((String)rm.get(i).get("provinceName")));
					map2.put("value", rm.get(i).get("stationNum"));
					rm2.add(map2);
				}
			}
			resultData.setData(rm2);
			return resultData;
		}
	}
	
	private String provinceName(String str){
		// 判断是否是直辖市
		if(str.contains("市")){
			int i = str.indexOf("市");
			String rs = str.substring(0, i);
			return rs;
		} else if(str.contains("省")){
			int i = str.indexOf("省");
			String rs = str.substring(0, i);
			return rs;
		} else if(str.equals("内蒙古自治区")){
			return "内蒙古";
		} else if(str.equals("广西壮族自治区")){
			return "广西";
		} else if(str.equals("宁夏回族自治区")){
			return "宁夏";
		} else if(str.equals("新疆维吾尔自治区")){
			return "新疆";
		} else if(str.equals("香港特别行政区")){
			return "香港";
		} else if(str.equals("澳门特别行政区")){
			return "澳门";
		}
		return null;
	}
	
	/**
	 * 针对后台，对超级管理员和服务商展示电站列表
	 * countType， 1管理员，2服务商
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByExample2")
	public ResultData<Page<Station>> selectByExample2(Integer countType, StationVO stationVO, Page<Station> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Page<Station>> resultData = new ResultData<Page<Station>>();
		
		if(countType == 1){
			User user = (User)httpSession.getAttribute("admin");
			if(user == null){
				return Constant.noLogin(resultData);
			}
			if (page.getExample() == null) {
				page.setExample(stationVO);
			}
			Page<Station> selectByExample = stationBiz.selectByExample(page);
			resultData.setData(selectByExample);
			return resultData;
		} else if(countType == 2){
			Server server = (Server)httpSession.getAttribute("server");
			if(server == null){
				return Constant.noLogin(resultData);
			}
			stationVO.setServerId(server.getId());
			if (page.getExample() == null) {
				page.setExample(stationVO);
			}
			Page<Station> selectByExample = stationBiz.selectByExample(page);
			resultData.setData(selectByExample);
			return resultData;
		}
		
		return resultData;
	}
	
	/**
	 * 电站的能源信息
	 */
	@ResponseBody
	@RequestMapping(value = "/energyInfo")
	public ResultData energyInfo(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData<>();
		
		Double plant_trees_prm = Double.valueOf(systemConfigBiz.findValueByKey("plant_trees_prm"));
		Double CO2_prm = Double.valueOf(systemConfigBiz.findValueByKey("CO2_prm"));
		Double SO_prm = Double.valueOf(systemConfigBiz.findValueByKey("SO_prm"));
		Double save_sqm_prm = Double.valueOf(systemConfigBiz.findValueByKey("save_sqm_prm"));
		
		Map<String, Object> map = new HashMap<>();
		
		Station station = stationBiz.select(id);
		
		map.put("work_tota_kwh", station.getWorkTotaKwh()); // 发电总量
		map.put("now_kw", station.getNowKw()); // 实时功率
		map.put("tree_num", station.getWorkTotaKwh() * plant_trees_prm); // 植树量
		map.put("co2_num", station.getWorkTotaKwh() * CO2_prm); // CO2减排量
		map.put("so_num", station.getWorkTotaKwh() * SO_prm); // SO减排量
		map.put("sqm_num", station.getCapacity() * save_sqm_prm); // 节省面积
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = DateUtil.formatDate(now.getTime(), "yyyy-MM-dd");
		Map<String, Object> map_param = new HashMap<>();
		map_param.put("stationCode", station.getStationCode());
		map_param.put("date", yesterday);
		List<Map> yesterday_list = temStationBiz.findPowerByDate(map_param);
		Double yesterday_total_kwh = 0d;
		for (Map map2 : yesterday_list) {
			if (yesterday_list.get(0) != null) {
				yesterday_total_kwh += (Double)map2.get("kwh");
			}
		}
		now.add(Calendar.DAY_OF_MONTH, 1);
		String today = DateUtil.formatDate(now.getTime(), "yyyy-MM-dd");
		map_param.put("date", today);
		List<Map> today_list = temStationBiz.findPowerByDate(map_param);
		Double today_total_kwh = 0d;
		for (Map map2 : today_list) {
			if (today_list.get(0) != null) {
				today_total_kwh += (Double)map2.get("kwh");
			}
		}
		map.put("yesterday_total_kwh", yesterday_total_kwh); // 昨日发电量
		map.put("today_total_kwh", today_total_kwh); // 今日发电量
		
		now = Calendar.getInstance();
		String thisMonth = DateUtil.formatDate(now.getTime(), "yyyy-MM");
		now.add(Calendar.MONTH, -1);
		String lastMonth = DateUtil.formatDate(now.getTime(), "yyyy-MM");
		map_param.put("date", thisMonth);
		List<Map> thisMonth_list = temStationBiz.findPowerByDate(map_param);
		Double thisMonth_total_kwh = 0d;
		for (Map map2 : thisMonth_list) {
			if (thisMonth_list.get(0) != null) {
				thisMonth_total_kwh += (Double)map2.get("kwh");
			}
		}
		map_param.put("date", lastMonth);
		List<Map> lastMonth_list = temStationBiz.findPowerByDate(map_param);
		Double lastMonth_total_kwh = 0d;
		for (Map map2 : lastMonth_list) {
			if (lastMonth_list.get(0) != null) {
				lastMonth_total_kwh += (Double)map2.get("kwh");
			}
		}
		map.put("thisMonth_total_kwh", thisMonth_total_kwh); // 今月发电量
		map.put("lastMonth_total_kwh", lastMonth_total_kwh); // 上月发电量
		
		now = Calendar.getInstance();
		String thisyear = DateUtil.formatDate(now.getTime(), "yyyy");
		Map<String, Object> year_map = new HashMap<>();
		year_map.put("station_code", station.getStationCode());
		year_map.put("year", thisyear);
		List<TemStationYear> thisYear_list = temStationYearBiz.kwhByTime(year_map);
		Double thisYear_total_kwh = 0d;
		for (TemStationYear temStationYear : thisYear_list) {
			thisYear_total_kwh += Double.valueOf(temStationYear.getKwh());
		}
		now.add(Calendar.YEAR, -1);
		String lastyear = DateUtil.formatDate(now.getTime(), "yyyy");
		year_map.put("year", lastyear);
		List<TemStationYear> lastYear_list = temStationYearBiz.kwhByTime(year_map);
		Double lastYear_total_kwh = 0d;
		for (TemStationYear temStationYear : lastYear_list) {
			lastYear_total_kwh += Double.valueOf(temStationYear.getKwh());
		}
		map.put("thisYear_total_kwh", thisYear_total_kwh); // 今年发电量
		map.put("lastYear_total_kwh", lastYear_total_kwh); // 上年发电量
		
		resultData.setData(map);
		return resultData;
	}
	
	public static void main(String[] args) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -1);
		String thisyear = DateUtil.formatDate(now.getTime(), "yyyy");
		System.out.println(thisyear);
	}
}