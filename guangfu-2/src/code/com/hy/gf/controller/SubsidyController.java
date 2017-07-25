package com.hy.gf.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.CityBiz;
import com.hy.gf.biz.ProvinceBiz;
import com.hy.gf.biz.SubsidyBiz;
import com.hy.gf.biz.SystemConfigBiz;
import com.hy.gf.model.City;
import com.hy.gf.model.Page;
import com.hy.gf.model.Province;
import com.hy.gf.model.Subsidy;
import com.hy.gf.util.Constant;
import com.hy.gf.vo.ResultData;

@Controller
@RequestMapping(value = "/subsidy")
public class SubsidyController {
	@Resource
	SubsidyBiz subsidyBiz;
	@Resource
	SystemConfigBiz systemConfigBiz;
	@Resource
	ProvinceBiz provinceBiz;
	@Resource
	CityBiz cityBiz;

	@ResponseBody
	@RequestMapping(value = "/delete")
	public Object delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();

		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}
		subsidyBiz.delete(id);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/insert")
	public Object insert(Subsidy subsidy, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();

		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}
		subsidyBiz.insert(subsidy);
		resultData.setData(subsidy);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Subsidy subsidy = subsidyBiz.select(id);
		resultData.setData(subsidy);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public Object update(Subsidy subsidy, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();

		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}
		subsidyBiz.update(subsidy);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Subsidy subsidy, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Subsidy selectByExample = subsidyBiz.selectOneByExample(subsidy);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Subsidy subsidy, Page<Subsidy> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(subsidy);
		}
		Page<Subsidy> selectByExample = subsidyBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<Subsidy> list = JSON.parseArray(listJson.getData(), Subsidy.class);
		subsidyBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	@ResponseBody
	// @RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		subsidyBiz.deleteBatch(ids);
		return resultData;
	}

	/**
	 * 模拟收益
	 * 
	 * @param subsidy
	 * @param sqm
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/monishouyi")
	public Object findByType(Subsidy subsidy, String sqm, Page<Subsidy> page) {
		ResultData<Object> resultData = new ResultData<Object>();

		DecimalFormat df = new DecimalFormat("#0.00");

		Subsidy sobe = subsidyBiz.selectOneByExample(subsidy);

		if (sobe != null) {
			// 植树参数
			Double plant_trees_prm = Double.valueOf(systemConfigBiz.findValueByKey("plant_trees_prm"));
			// co2减排参数
			Double CO2_prm = Double.valueOf(systemConfigBiz.findValueByKey("CO2_prm"));
			// SO减排参数
			Double SO_prm = Double.valueOf(systemConfigBiz.findValueByKey("SO_prm"));

			// 出售价格（元/度）
			Double sellPrice = sobe.getSellPrice();
			// 衰减率，0.8代表0.8%
			Double dampingRate = Double.valueOf(systemConfigBiz.findValueByKey("damping_rate"));
			// 按安装面积的80%计算装机容量
			Double area_capacity_per = Double.valueOf(systemConfigBiz.findValueByKey("area_capacity_per"));
			// 每平米发电多少瓦
			Double sqm_electric = Double.valueOf(systemConfigBiz.findValueByKey("sqm_electric"));
			// 每瓦多少钱
			Double watt_price = Double.valueOf(systemConfigBiz.findValueByKey("watt_price"));
			// 装机容量(KW) = 【安装面积（㎡） * 0.8（按安装面积的80%计算装机容量） * 每平米发电多少瓦(155.49)】 / 1000
			Double totalInstallCapacity = (Double.valueOf(sqm) * Double.valueOf(area_capacity_per) * sqm_electric)
					/ 1000;
			// 总造价(元) = 装机容量(KW) * 每瓦多少钱(11000)
			Double totalprice = totalInstallCapacity * watt_price;
			// 年发电量(kw·h) = 装机容量(KW) * 地区的年日照数(小时)
			Double yearTotalWatt = totalInstallCapacity * Double.valueOf(sobe.getSunCount());

			// 总的国家补贴(元)=年发电量*国家补贴参数（元/度）*国家补贴年限
			Double countrySub = Double.valueOf(systemConfigBiz.findValueByKey("country_subsidy"));
			Double countrySubYear = Double.valueOf(systemConfigBiz.findValueByKey("country_subsidy_year"));
			Double countrySubTotal = countrySub * countrySubYear * yearTotalWatt;

			// 总的地方补贴(元)=年发电量*地方补贴参数（元/度）*地方补贴年限
			Double difangSub = sobe.getSubsidy();
			int difangSubYear = sobe.getBsidyYear();
			Double difangSubTotal = difangSub * difangSubYear * yearTotalWatt;

			// 总的优能补贴(元)=年发电量*优能补贴参数（元/度）*优能补贴年限 ，说明：该地区的售电价格低于10.5元/w时没有优能补贴
			Double unSubTotal = 0d;
			if (sobe.getUnlsubsidy() != null) {
				if (sellPrice > 10.5) {
					Double unSub = sobe.getUnlsubsidy();
					int unSubYear = sobe.getUnlsubsidyYear();
					unSubTotal = unSub * unSubYear * yearTotalWatt;
				}
			}
			// 一次性补贴(元)=装机容量*一次性补贴参数（元/kw）
			Double initialsubsidy = 0d;
			if (sobe.getInitialsubsidy() != null) {
				initialsubsidy = sobe.getInitialsubsidy() * totalInstallCapacity;
			}

			// 总补贴(元)=总的国家补贴+总的地方补贴+总的优能补贴+一次性补贴
			Double totalSub = countrySubTotal + difangSubTotal + unSubTotal + initialsubsidy;

			// 20年总节省(元)=年发电量*自用率*用电单价（元/度）*20(年)
			Double economicTotal = yearTotalWatt * sobe.getSelfuse() * (1 - sobe.getSell()) * 20;
			// 20年总卖出(元)=年发电量*出售率*售电单价（元/度）*20(年)
			Double sellTotal = yearTotalWatt * sobe.getSell() * sobe.getSellPrice() * 20;
			// 20年总收益 = (20年总节省电费+20年总卖出电费+总补贴) * (1-衰减率*20)
			Double twentyYearTotalIncome = (economicTotal + sellTotal + totalSub) * (1 - (dampingRate / 100) * 20);
			// 每年发电收益=20年总收益 / 20(年)
			Double everyYearTotalIncome = twentyYearTotalIncome / 20;

			// 地方补贴结束前的总收益（假设地方补贴年限是5年） = 五年省下的电费+五年卖出的电费+五年国家补贴+五年地方补贴+总的优能补贴
			Double beforeEndSubsidy = yearTotalWatt * sobe.getSelfuse() * (1 - sobe.getSell()) * sobe.getBsidyYear()
					+ yearTotalWatt * sobe.getSell() * sobe.getSellPrice() * sobe.getBsidyYear() + difangSubTotal
					+ unSubTotal + countrySub * yearTotalWatt * sobe.getBsidyYear();

			Double incomePerMax = 0d;
			if (beforeEndSubsidy == 0) { // 如果没有地方补贴
				
			} else { // 最大年收益率=(地方补贴结束前的总收益/地方补贴年限/总造价)*(1-衰减率*地方补贴年限）
				incomePerMax = (beforeEndSubsidy / sobe.getBsidyYear() / totalprice) * 100 * (1 - (dampingRate / 100) * sobe.getBsidyYear());
			}

			// 最小年收益率 = (20年总收益/20(年)) / 总造价
			Double incomePerMin = ((twentyYearTotalIncome / 20) / totalprice) * 100;

			Map<String, Object> rm = new HashMap<>();
			// 成本
			rm.put("totalprice", df.format(totalprice)); // 工程总造价
			rm.put("totalInstallCapacity", df.format(totalInstallCapacity)); // 机容量
			rm.put("sunCount", df.format(Double.valueOf(sobe.getSunCount()))); // 年日照数
			rm.put("yearTotalWatt", df.format(yearTotalWatt)); // 年发电量
			rm.put("treeNum", df.format(yearTotalWatt * plant_trees_prm)); // 植树棵树
																			// =
																			// 发电量
																			// *
																			// 植树参数
			rm.put("CO2Num", df.format(yearTotalWatt * CO2_prm)); // 减排二氧化碳多少吨
			rm.put("SONum", df.format(yearTotalWatt * SO_prm)); // 减排so
			// 收益
			rm.put("twentyYearTotalIncome", df.format(twentyYearTotalIncome));
			rm.put("yearIncome", df.format(everyYearTotalIncome)); // 发电收益(元/年)
			rm.put("selfuse", sobe.getSelfuse()); // 当地电价
			rm.put("guojiabutie", countrySub); // 国家补贴(元/度)
			rm.put("subsidy", sobe.getSubsidy()); // 地方补贴(元/度)
			rm.put("unlsubsidy", sobe.getUnlsubsidy()); // 优能补贴(元/度)
			rm.put("incomePerMax", String.valueOf(df.format(incomePerMax)) + "%");
			rm.put("incomePerMin", String.valueOf(df.format(incomePerMin)) + "%");

			resultData.setData(rm);
		} else {
			Map<String, Object> rm = new HashMap<>();
			// 成本
			rm.put("totalprice", 0); // 工程总造价
			rm.put("totalInstallCapacity", 0);
			rm.put("sunCount", 0); // 年日照数
			rm.put("yearTotalWatt", 0); // 年发电量
			rm.put("treeNum", 0); // 植树棵树
			rm.put("CO2Num", 0); // 减排二氧化碳多少吨

			// 收益
			rm.put("twentyYearTotalIncome", 0);
			rm.put("yearIncome", 0); // 发电收益(元/年)
			rm.put("selfuse", 0); // 当地电价
			rm.put("guojiabutie", 0); // 国家补贴(元/度)
			rm.put("subsidy", 0); // 地方补贴(元/度)
			rm.put("unlsubsidy", 0); // 优能补贴(元/度)
			rm.put("incomePerMax", 0 + "%");
			rm.put("incomePerMin", 0 + "%");
			resultData.setData(rm);
		}

		return resultData;
	}

	/**
	 * 模拟收益地区二级联动
	 */
	@ResponseBody
	@RequestMapping(value = "/area")
	public Object area(String province_id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		if (StringUtils.isBlank(province_id)) { // 查找所有的省份
			List<Province> list = new ArrayList<>();
			List<Long> pro_list = subsidyBiz.selectProvince();
			for (Long pid : pro_list) {
				Province province = provinceBiz.select(pid);
				list.add(province);
			}
			resultData.setData(list);
		} else {
			List<City> list = new ArrayList<>();
			List<Long> city_list = subsidyBiz.selectCityByProvinceId(Long.valueOf(province_id));
			for (Long cid : city_list) {
				City city = cityBiz.select(cid);
				list.add(city);
			}
			resultData.setData(list);
		}
		
		return resultData;
	}
}