package com.hy.gf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import com.hy.gf.biz.CityBiz;
import com.hy.gf.biz.ProvinceBiz;
import com.hy.gf.biz.ServerBiz;
import com.hy.gf.model.City;
import com.hy.gf.model.Page;
import com.hy.gf.model.Province;
import com.hy.gf.model.Server;
import com.hy.gf.vo.CityVO;
import com.hy.gf.vo.ResultData;
import com.hy.gf.vo.ServerVO;

@Controller
@RequestMapping(value = "/province")
public class ProvinceController {
	@Resource
	ProvinceBiz provinceBiz;
	@Resource
	CityBiz cityBiz;
	@Resource
	ServerBiz serverBiz;

	// @ResponseBody
	// @RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		provinceBiz.delete(id);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insert")
	public ResultData insert(Province province, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		provinceBiz.insert(province);
		resultData.setData(province);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Province province = provinceBiz.select(id);
		resultData.setData(province);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/update")
	public ResultData update(Province province, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		provinceBiz.update(province);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Province province, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Province selectByExample = provinceBiz.selectOneByExample(province);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Province province, Page<Province> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(province);
		}
		Page<Province> selectByExample = provinceBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<Province> list = JSON.parseArray(listJson.getData(), Province.class);
		provinceBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		provinceBiz.deleteBatch(ids);
		return resultData;
	}

	/**
	 * web 查找省列表
	 * 
	 * @param provinceId
	 */
	@ResponseBody
	@RequestMapping(value = "/findProvince")
	public List<Province> findProvince(Page<Province> page) {
		page.setSort("id");
		page.setSortUp("ASC");
		Page<Province> sbe = provinceBiz.selectByExample(page);
		return sbe.getList();
	}

	/**
	 * web 首页获取省列表
	 */
	@ResponseBody
	@RequestMapping(value = "/findProvince2")
	public List<Province> findProvince2() {

		List<Province> fpl = provinceBiz.findProvinceList();
		return fpl;
	}

	/**
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findPC")
	public ResultData<Map<String, Object>> findPC() {
		ResultData<Map<String, Object>> resultData = new ResultData<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Province> page1 = new Page<Province>();
		page1.setSort("id");
		page1.setSortUp("ASC");
		Page<City> page2 = new Page<City>();
		page2.setSort("id");
		page2.setSortUp("ASC");
		map.put("provinces", provinceBiz.selectByExample(page1));
		map.put("citys", cityBiz.selectByExample(page2));
		resultData.setData(map);
		return resultData;
	}
	
	/**
	 * 根据服务商id，查询出可服务的省和城市
	 */
	@ResponseBody
	@RequestMapping(value = "/findByServerId")
	public ResultData findByServerId(Long serverId, Integer type, Long provinceId, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		if(type == 1){
			Server server = serverBiz.select(serverId);
			
			String[] cityArr = server.getServerCityIds().split(",");
			HashSet<Integer> set = new HashSet<>();
			for(String i:cityArr){
				set.add(Integer.parseInt(i));
			}
			
			List<Province> proList = new ArrayList<>();
			List<CityVO> citys = new ArrayList<>();
			for(Integer p:set){
				CityVO cityvo = cityBiz.findProAndCity(Long.valueOf(p));
				citys.add(cityvo);
				proList.add(cityvo.getProvince());
			}
			
			List<Province> proList2 = new ArrayList<>();
			List<Long> pidsList = new ArrayList<>();
			for(Province p:proList){
				if(!pidsList.contains(p.getId())){
					proList2.add(p);
					pidsList.add(p.getId());
				}
			}
			resultData.setData(proList2);
			return resultData;
		} else if(2 == type){
			List<City> citys = cityBiz.findByProvinceId(provinceId);
			Server server = serverBiz.select(serverId);
			String[] cityArr = server.getServerCityIds().split(",");
			
			HashSet<Long> set = new HashSet<>();
			for(String i:cityArr){
				set.add(Long.valueOf(i));
			}
			
			List<City> cl = new ArrayList<>();
			for(Long cid:set){
				for(City city:citys) {
					if(city.getId().intValue() == cid.intValue()) {
						cl.add(city);
					}
				}
			}
			
			resultData.setData(cl);
			return resultData;
		}
		
		return resultData;
	}

}