package com.hy.gf.controller;

import java.util.ArrayList;
import java.util.Collections;
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
import com.hy.gf.biz.ProvinceBiz;
import com.hy.gf.biz.StationBiz;
import com.hy.gf.mapper.DevConfMapper;
import com.hy.gf.model.Ammeter;
import com.hy.gf.model.City;
import com.hy.gf.model.DevConf;
import com.hy.gf.model.Page;
import com.hy.gf.model.Province;
import com.hy.gf.model.Station;
import com.hy.gf.model.User;
import com.hy.gf.util.Constant;
import com.hy.gf.util.ObjToMap;
import com.hy.gf.util.StringUtil;
import com.hy.gf.vo.ResultData;

@Controller
@RequestMapping(value = "/ammeter")
public class AmmeterController {
	@Resource
	AmmeterBiz ammeterBiz;
	@Resource
	ProvinceBiz provinceBiz;
	@Resource
	CityBiz cityBiz;
	@Resource
	DevConfMapper devConfMapper;
	@Resource
	StationBiz stationBiz;

	@ResponseBody
	// @RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		ammeterBiz.delete(id);
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/insert")
	public ResultData insert(Ammeter ammeter, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();

		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}

		if (StringUtil.isNumeric(ammeter.getCode()) == false) {
			resultData.setCode(777);
			resultData.setMsg("电表码只能是数字");
			resultData.setSuccess(false);
			return resultData;
		}

		DevConf devConf = new DevConf();
		devConf.setCAddr(Integer.parseInt(ammeter.getCode()));
		Map<String, Object> objectMap = ObjToMap.getObjectMap(devConf);
		objectMap.put("start", 0);
		objectMap.put("limit", Integer.MAX_VALUE);
		List<DevConf> devs = devConfMapper.selectByExample(objectMap);
		if (devs.size() <= 0) {
			resultData.setCode(777);
			resultData.setMsg("电表库中不存在该电表");
			resultData.setSuccess(false);
			return resultData;
		}

		Ammeter na = new Ammeter();
		na.setCode(ammeter.getCode());
		Ammeter sobe = ammeterBiz.selectOneByExample(na);
		if (sobe != null) {
			resultData.setCode(400);
			resultData.setMsg("该电表已存在，不能重复添加");
			resultData.setSuccess(false);
			return resultData;
		}

		ammeter.setUserId(((User) attribute).getId());
		ammeter.setStationId(null);

		ammeterBiz.insert(ammeter);
		resultData.setData(ammeter);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Ammeter ammeter = ammeterBiz.select(id);
		resultData.setData(ammeter);
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/update")
	public ResultData update(Ammeter ammeter, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		ammeterBiz.update(ammeter);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Ammeter ammeter, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Ammeter selectByExample = ammeterBiz.selectOneByExample(ammeter);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Ammeter ammeter, Page<Ammeter> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(ammeter);
		}
		Page<Ammeter> selectByExample = ammeterBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	// @RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<Ammeter> list = JSON.parseArray(listJson.getData(), Ammeter.class);
		ammeterBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	@ResponseBody
	// @RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		ammeterBiz.deleteBatch(ids);
		return resultData;
	}

	/**
	 * 针对后台，电表管理，地址二级联动
	 */
	@ResponseBody
	@RequestMapping(value = "/ammeterAddress")
	public ResultData servertkglFilter(Long provinceId, Page<Ammeter> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();

		if (provinceId == null) {
			page.setLimit(Integer.MAX_VALUE);
			Page<Ammeter> sbe = ammeterBiz.selectByExample(page);

			Set<Long> proIds = new HashSet<>();
			for (Ammeter o : sbe.getList()) {
				if (o.getProvinceId() != null) {
					proIds.add(o.getProvinceId());
				}
			}

			List<Province> proList = new ArrayList<>();
			for (Long id : proIds) {
				proList.add(provinceBiz.select(id));
			}

			resultData.setData(proList);
			return resultData;
		} else {
			Page<City> cityPage = new Page<>();
			City city = new City();
			city.setProvinceId(provinceId);
			cityPage.setExample(city);
			cityPage.setLimit(Integer.MAX_VALUE);
			Page<City> sbe = cityBiz.selectByExample(cityPage);

			page.setLimit(Integer.MAX_VALUE);
			Page<Ammeter> ammetersbe = ammeterBiz.selectByExample(page);

			Set<City> citySet = new HashSet<>();
			for (City c : sbe.getList()) {
				for (Ammeter a : ammetersbe.getList()) {
					if (a.getCityId() != null) {
						if (a.getCityId() == c.getId().intValue()) {
							citySet.add(c);
						}
					}
				}
			}
			resultData.setData(citySet);
			return resultData;
		}
	}
	
	/**
	 * 新增电表
	 */
	@ResponseBody
	@RequestMapping(value = "/insertByAdmin")
	public ResultData insertByAdmin(Ammeter ammeter, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();

		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}

		if (StringUtil.isNumeric(ammeter.getCode()) == false) {
			resultData.setCode(777);
			resultData.setMsg("电表码只能是数字");
			resultData.setSuccess(false);
			return resultData;
		}

		DevConf devConf = new DevConf();
		devConf.setCAddr(Integer.parseInt(ammeter.getCode()));
		Map<String, Object> objectMap = ObjToMap.getObjectMap(devConf);
		objectMap.put("start", 0);
		objectMap.put("limit", Integer.MAX_VALUE);
		List<DevConf> devs = devConfMapper.selectByExample(objectMap);
		if (devs.size() <= 0) {
			resultData.setCode(777);
			resultData.setMsg("电表库中不存在该电表");
			resultData.setSuccess(false);
			return resultData;
		}

		Ammeter na = new Ammeter();
		na.setCode(ammeter.getCode());
		Ammeter sobe = ammeterBiz.selectOneByExample(na);
		if (sobe != null) {
			resultData.setCode(400);
			resultData.setMsg("该电表已存在，不能重复添加");
			resultData.setSuccess(false);
			return resultData;
		}

		ammeter.setUserId(((User) attribute).getId());
		
		if (!StringUtils.isEmpty(ammeter.getStationId())) {
			Station s1 = stationBiz.select(Long.valueOf(ammeter.getStationId()));
			if (!StringUtils.isEmpty(s1.getAmmeterCode())) {
				String[] s1_ammeters = s1.getAmmeterCode().split(",");
				List<String> s1_codeList = new ArrayList<>();
				Collections.addAll(s1_codeList, s1_ammeters);
				s1_codeList.add(ammeter.getCode());
				String s1_ammeter_codes = StringUtils.join(s1_codeList, ",");
				s1.setAmmeterCode(s1_ammeter_codes);
			} else {
				s1.setAmmeterCode(ammeter.getCode());
				s1.setStatus(1);
			}
			stationBiz.update(s1);
		} else {
			ammeter.setStationId("");
		}

		ammeterBiz.insert(ammeter);
		
		resultData.setData(ammeter);
		return resultData;
	}

	/**
	 * 删除电表
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteByAdmin")
	public ResultData deleteByAdmin(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();

		Object object = httpSession.getAttribute("admin");
		if (object == null) {
			return Constant.noLogin(resultData);
		}

		Ammeter ammeter = ammeterBiz.select(id);

		// 先解绑电站的电表，再删除该电表
		if (!StringUtils.isEmpty(ammeter.getStationId())) {
			Station station = stationBiz.select(Long.valueOf(ammeter.getStationId()));
			if (station != null) {
				String[] strs = station.getAmmeterCode().split(",");
				List<String> codes = new ArrayList<>();
				for (String str : strs) {
					codes.add(str);
				}

				for (int i = 0; i < codes.size(); i++) {
					if (codes.get(i).equals(ammeter.getCode())) {
						codes.remove(i);
					}
				}

				Station new_station = new Station();
				new_station.setId(station.getId());
				new_station.setAmmeterCode(StringUtils.join(codes, ","));
				if (codes.size() == 0) {
					new_station.setStatus(0);
				}
				stationBiz.update(new_station);
			}
		}

		ammeterBiz.delete(id);
		return resultData;
	}

	/**
	 * 更新电表信息
	 */
	@ResponseBody
	@RequestMapping(value = "/updateByAdmin")
	public ResultData updateByAdmin(Ammeter ammeter, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();

		Object object = httpSession.getAttribute("admin");
		if (object == null) {
			return Constant.noLogin(resultData);
		}

		Ammeter record = ammeterBiz.select(ammeter.getId());
		ammeterBiz.update(ammeter);

		if (!record.getStationId().equals(ammeter.getStationId())) {
			// 电表新绑定的电站添加电表码
			Station s1 = stationBiz.select(Long.valueOf(ammeter.getStationId()));
			if (!StringUtils.isEmpty(s1.getAmmeterCode())) {
				String[] s1_ammeters = s1.getAmmeterCode().split(",");
				List<String> s1_codeList = new ArrayList<>();
				Collections.addAll(s1_codeList, s1_ammeters);
				s1_codeList.add(ammeter.getCode());
				String s1_ammeter_codes = StringUtils.join(s1_codeList, ",");
				s1.setAmmeterCode(s1_ammeter_codes);
			} else {
				s1.setAmmeterCode(ammeter.getCode());
				s1.setStatus(1);
			}
			stationBiz.update(s1);

			// 电表原来绑定的电站去掉该电表的电表码
			if (!StringUtils.isEmpty(record.getStationId())) {
				Station s2 = stationBiz.select(Long.valueOf(record.getStationId()));
				if (!StringUtils.isEmpty(s2.getAmmeterCode())) {
					String[] s2_ammeters = s2.getAmmeterCode().split(",");
					List<String> s2_codeList = new ArrayList<>();
					Collections.addAll(s2_codeList, s2_ammeters);

					for (int i = 0; i < s2_codeList.size(); i++) {
						if (s2_codeList.get(i).equals(ammeter.getCode())) {
							s2_codeList.remove(i);
						}
					}

					String s2_ammeter_codes = StringUtils.join(s2_codeList, ",");
					s2.setAmmeterCode(s2_ammeter_codes);
				} else {
					s2.setAmmeterCode("");
					s2.setStatus(0);
				}
				stationBiz.update(s2);
			}
		}

		return resultData;
	}

	/**
	 * 电表解绑电站
	 */
	@ResponseBody
	@RequestMapping(value = "/reliefStation")
	public ResultData reliefStation(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();

		Object object = httpSession.getAttribute("admin");
		if (object == null) {
			return Constant.noLogin(resultData);
		}

		Ammeter record = ammeterBiz.select(id);

		// 电表原来绑定的电站去掉该电表的电表码
		Station s2 = stationBiz.select(Long.valueOf(record.getStationId()));
		String[] s2_ammeters = s2.getAmmeterCode().split(",");
		List<String> s2_codeList = new ArrayList<>();
		Collections.addAll(s2_codeList, s2_ammeters);

		for (int i = 0; i < s2_codeList.size(); i++) {
			if (s2_codeList.get(i).equals(record.getCode())) {
				s2_codeList.remove(i);
			}
		}

		String s2_ammeter_codes = StringUtils.join(s2_codeList, ",");
		s2.setAmmeterCode(s2_ammeter_codes);
		if (s2_codeList.size() == 0) {
			s2.setStatus(0);
		}
		stationBiz.update(s2);

		record.setStationId("");
		ammeterBiz.update(record);

		return resultData;
	}

}