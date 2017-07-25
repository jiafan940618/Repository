package com.hy.gf.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hy.gf.model.City;
import com.hy.gf.vo.CityVO;
import com.hy.gf.model.Page;
import com.hy.gf.vo.ResultData;
import com.hy.gf.biz.CityBiz;

@Controller
@RequestMapping(value = "/city")
public class CityController {
	@Resource
	CityBiz cityBiz;

	// @ResponseBody
	// @RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		cityBiz.delete(id);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insert")
	public ResultData insert(City city, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		cityBiz.insert(city);
		resultData.setData(city);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		City city = cityBiz.select(id);
		resultData.setData(city);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/update")
	public ResultData update(City city, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		cityBiz.update(city);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(City city, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		City selectByExample = cityBiz.selectOneByExample(city);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(City city, Page<City> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(city);
		}
		Page<City> selectByExample = cityBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<City> list = JSON.parseArray(listJson.getData(), City.class);
		cityBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		cityBiz.deleteBatch(ids);
		return resultData;
	}

	/**
	 * web 根据省id查找城市列表
	 */
	@ResponseBody
	@RequestMapping(value = "/findCityListByProvinceId")
	public List<City> findCityListByProvinceId(Page<City> page, City city) {

		page.setExample(city);
		page.setSort("id");
		page.setSortUp("ASC");
		Page<City> sbe = cityBiz.selectByExample(page);
		return sbe.getList();
	}

	/**
	 * web 根据省id查找城市列表2
	 */
	@ResponseBody
	@RequestMapping(value = "/findCityListByProvinceId2")
	public List<City> findCityListByProvinceId2(City city) {

		List<City> fclbp = cityBiz.findCityListByProvinceId(city.getProvinceId());
		return fclbp;
	}

}