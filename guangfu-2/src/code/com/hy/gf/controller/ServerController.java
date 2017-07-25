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
import com.hy.gf.biz.DeviceBiz;
import com.hy.gf.biz.OrderBiz;
import com.hy.gf.biz.ServerBiz;
import com.hy.gf.biz.SystemConfigBiz;
import com.hy.gf.biz.UserBiz;
import com.hy.gf.biz.WalletBiz;
import com.hy.gf.model.City;
import com.hy.gf.model.Device;
import com.hy.gf.model.Page;
import com.hy.gf.model.Province;
import com.hy.gf.model.Server;
import com.hy.gf.model.User;
import com.hy.gf.util.Constant;
import com.hy.gf.util.MD5Util;
import com.hy.gf.util.RongLianSMS;
import com.hy.gf.vo.CityVO;
import com.hy.gf.vo.ResultData;
import com.hy.gf.vo.ServerVO;


@Controller
@RequestMapping(value = "/server")
public class ServerController {
@Resource
ServerBiz serverBiz;
@Resource
UserBiz userBiz;
@Resource
SystemConfigBiz systemConfigBiz;
@Resource
DeviceBiz deviceBiz;
@Resource
CityBiz cityBiz;
@Resource
OrderBiz orderBiz;
@Resource
WalletBiz walletBiz;


//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	serverBiz.delete(id);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/insert")
	public ResultData insert(ServerVO server, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		serverBiz.insert(server);
		resultData.setData(server);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Server server = serverBiz.select(id);
		resultData.setData(server);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/update")
	public ResultData update(Server server, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		if(server.getState() != null){
			if(server.getState() == 1){
				server.setRoleId(4);
			} else if(server.getState() == 0){
				server.setRoleId(5);
			}
		}
		
		serverBiz.update(server);
		return resultData;
	}
	@ResponseBody
	@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Server server, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Server selectByExample =serverBiz.selectOneByExample(server);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(ServerVO serverVO, Page<Server> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(serverVO);
			}
		Page<Server> selectByExample = serverBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<Server> list = JSON.parseArray(listJson.getData(), Server.class);
		serverBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		serverBiz.deleteBatch(ids);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/login")
	public ResultData login(Server server, String graphicCode, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData<>();
		 
		if(graphicCode != null){
			if(StringUtils.isBlank(graphicCode)){
				resultData.setCode(405);
	    		resultData.setMsg("请输入图形验证码");
	    		resultData.setSuccess(false);
	    		return resultData;
	    	}
			String sessionGraphicCode = (String)httpSession.getAttribute("captchaToken");
	    	if(!sessionGraphicCode.equalsIgnoreCase(graphicCode)){
	    		resultData.setCode(405);
	    		resultData.setMsg("输入的图形验证码错误");
	    		resultData.setSuccess(false);
	    		return resultData;
	    	}
		}
		
		if (StringUtils.isEmpty(server.getPhone())) {
			resultData.setCode(401);
			resultData.setMsg("负责人手机号不能为空");
			return resultData;
		}
		if (StringUtils.isEmpty(server.getPassword())) {
			resultData.setCode(401);
			resultData.setMsg("密码不能为空");
			return resultData;
		}
		
		
		Server s = new Server();
		s.setPhone(server.getPhone());
		Server serverLocal = serverBiz.selectOneByExample(s);
		// String header = request.getHeader("token");
		if (serverLocal == null) {
			resultData.setCode(404);
			resultData.setMsg("服务商不存在");
			return resultData;
		} else if (!serverLocal.getPassword().equals(MD5Util.GetMD5Code(server.getPassword()))) {
			resultData.setCode(405);
			resultData.setMsg(Constant.PASSWORD_ERROR);
			return resultData;
		}
		
		httpSession.setAttribute("server", serverLocal);
		serverLocal.setPassword(null);
		
		resultData.setData(serverLocal);
	
		return resultData;
	}
	
	/**
	 * 找回密码时请求验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/code4serverFindpsw")
	public ResultData<Object> code4findpsw(Server server, String graphicCode, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		String sessionGraphicCode = (String)httpSession.getAttribute("captchaToken");
		if(StringUtils.isBlank(graphicCode)){
			return Constant.error(resultData, 405, "请输入图形验证码");
    	} else if (StringUtils.isBlank(sessionGraphicCode)) {
    		return Constant.error(resultData, 405, "后台验证码为空");
		} else if (!sessionGraphicCode.equalsIgnoreCase(graphicCode)){
			return Constant.error(resultData, 405, "图形验证码错误");
    	} else if (StringUtils.isBlank(server.getPhone())) {
			return Constant.error(resultData, 403, "手机号不能为空");
		}
		
		Server s = new Server();
		s.setPhone(server.getPhone());
		Server sobe = serverBiz.selectOneByExample(s);
		if (sobe == null) {
			return Constant.error(resultData, 401, "该服务商不存在");
		}
		
//		String code = "123456";
		String code = RongLianSMS.sendCode(sobe.getPhone());
		if(code == null){
			return Constant.error(resultData, 500, "发送手机验证码失败");
		}
		httpSession.setAttribute("code4serverFindpsw", code);
		httpSession.setAttribute("serverFindpswPhone", sobe.getPhone());
		return resultData;
	}

	/**
	 * 找回密码
	 */
	@ResponseBody
	@RequestMapping(value = "/serverUpdatePassword")
	public ResultData<Object> serverUpdatePassword(Server server, String code4serverFindpsw, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		if (StringUtils.isEmpty(server.getPhone())) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("电话不能为空");
			return resultData;
		}
		
		Server s = new Server();
		s.setPhone(server.getPhone());
		Server sobe = serverBiz.selectOneByExample(s);
		if (sobe == null) {
			resultData.setCode(401);
			resultData.setSuccess(false);
			resultData.setMsg("该服务商不存在");
			return resultData;
		}
		
		Object attribute2 = httpSession.getAttribute("code4serverFindpsw");
		if (attribute2 == null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("请先发送验证码");
			return resultData;
		}
		if (!code4serverFindpsw.equals(attribute2) || !server.getPhone().equals(httpSession.getAttribute("serverFindpswPhone"))) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("验证码不正确");
			return resultData;
		}
		
		Server s2 = new Server();
		s2.setId(sobe.getId());
		s2.setPassword(MD5Util.GetMD5Code(server.getPassword()));
		serverBiz.update(s2);
		
		sobe.setPassword(null);
		
		resultData.setData(sobe);
		return resultData;
	}
	
	/**
	 * 服务商注册第一步
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "/register1")
	public ResultData<Object> register1(UserVO user,ServerVO server,String code4register, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();

		if (user.getPhone() == null) {
			resultData.setCode(405);
			resultData.setSuccess(false);
			resultData.setMsg("电话号码不能为空");
			return resultData;
		}
		// 如果短信验证码超过了5分钟
		Long code4serverRegTime = (Long)httpSession.getAttribute("code4serverRegTime");
		if (code4serverRegTime==null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("请先发送验证码");
			return resultData;
		}
		Long spaceTime = System.currentTimeMillis() - code4serverRegTime;
		if(spaceTime > 300000){
			httpSession.setAttribute("code4serverReg",null);
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("该验证码已失效，请重新获取");
			return resultData;
		}
		
		
		Object attribute2 = httpSession.getAttribute("code4serverReg");
		if (attribute2==null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("请先发送验证码");
			return resultData;
		}
		if (!code4register.equals(attribute2)) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("验证码不正确");
			return resultData; 
		}
		User user2 =new User();
		user2.setPhone(user.getPhone());
		User selectByPhone = userBiz.selectOneByExample(user2);
		if (selectByPhone!=null) {
//			if(selectByPhone.getRoleId() == 6){
//				// 普通用户注册服务商
//				selectByPhone.setRoleId(5);
//				selectByPhone.setPassword(user.getPassword());
//				httpSession.setAttribute("server", server);
//				httpSession.setAttribute("user", selectByPhone);
//				return resultData;
//			} else{
				resultData.setCode(403);
				resultData.setSuccess(false);
				resultData.setMsg("负责人手机号已经被注册");
				return resultData;
//			}
			
		}
		httpSession.setAttribute("server", server);
		httpSession.setAttribute("user", user);
		return resultData;
	}*/
	
	
	/**
	 * 服务商注册第二步
	 */
	@ResponseBody
	@RequestMapping(value = "/register2")
	public ResultData<Object> register2(String server,HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		ServerVO serverVo = JSON.parseObject(server, ServerVO.class);
		serverVo.setPassword(MD5Util.GetMD5Code(serverVo.getPassword()));
		serverVo.setContactPhone(serverVo.getPhone());
		serverVo.setContactName(serverVo.getRealName());
		// 判断该手机号是否已经被注册
		Server cs = new Server();
		cs.setPhone(serverVo.getPhone());
		Server selectByPhone = serverBiz.selectOneByExample(cs);
		if (selectByPhone != null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("该负责人手机号已经被注册");
			return resultData;
		}
		if(serverVo.getServerCity().length() > 250){
			resultData.setCode(606);
			resultData.setSuccess(false);
			resultData.setMsg("服务城市过长");
			return resultData;
		}
		
		serverBiz.insert(serverVo);
		
		httpSession.setAttribute("server", serverVo);
		serverVo.setPassword(null);
		
		resultData.setData(serverVo);
		return resultData;
	}
	
	/**
	 * 服务商注册第一步点击 填写详细信息时 校验验证码是否正确
	 */
	@ResponseBody
	@RequestMapping(value = "/checkCode")
	public ResultData<Object> checkCode(String code, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		String code4serverReg = (String)httpSession.getAttribute("code4serverReg");
		// 检测短信验证码是否过期
		ResultData<Object> resultData2 = RongLianSMS.checkMsgTimeOut("code4serverRegTime", httpSession);
		if(!resultData2.getSuccess()){
			return resultData2;
		}
		if(StringUtils.isEmpty(code4serverReg)){
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("请先发送验证码");
			return resultData;
		}
		if(!code4serverReg.equals(code)){
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("验证码错误");
			return resultData;
		}
		
		return resultData;
	}
	
	/**
	 * 服务商注册时请求验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/code4serverReg")
	public Object code4serverReg(User user, String graphicCode, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		String sessionGraphicCode = (String)httpSession.getAttribute("captchaToken");
		if (StringUtils.isBlank(sessionGraphicCode)) {
			return Constant.error(resultData, 405, "后台验证码为空");
		} else if(StringUtils.isBlank(graphicCode)){
			return Constant.error(resultData, 405, "请输入图形验证码");
    	} else if(!sessionGraphicCode.equalsIgnoreCase(graphicCode)){
    		return Constant.error(resultData, 405, "图形验证码错误");
    	} else if (StringUtils.isEmpty(user.getPhone())) {
			return Constant.error(resultData, 403, "负责人手机号不能为空");
		}
		
		Server record = new Server();
		record.setPhone(user.getPhone());
		Server selectByPhone = serverBiz.selectOneByExample(record);
		if (selectByPhone != null) {
			return Constant.error(resultData, 403, "负责人手机号已经被注册");
		}
		
//		String code = "123456";
		String code = RongLianSMS.sendCode(user.getPhone());
		if(code == null){
			return Constant.error(resultData, 500, "发送手机验证码失败");
		}
		
		httpSession.setAttribute("code4serverRegTime",System.currentTimeMillis());
		httpSession.setAttribute("code4serverReg", code);
		httpSession.setAttribute("phone", user.getPhone());
		return resultData;
	}
	
	private static List<CityVO> finalCitys = new ArrayList<>();
	
	/**
	 * 根据服务商可服务的城市来，显示省和城市
	 */
	@ResponseBody
	@RequestMapping(value = "/findProAndCity")
	public Object findProAndCity(Integer type, Long provinceId, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<>();
		
		
		if(1 == type){
			Page<Server> page = new Page<>();
			ServerVO server = new ServerVO();
			server.setState(1);
			page.setExample(server);
			page.setLimit(Integer.MAX_VALUE);
			Page<Server> sbe = serverBiz.selectByExample(page);
			
			String cityStr = "";
			for(Server s:sbe.getList()){
				if(!StringUtils.isEmpty(s.getServerCityIds())){
					cityStr += s.getServerCityIds()+",";
				}
			}
			
			String[] cityArr = cityStr.split(",");
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
			finalCitys = citys;
			
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
			List<City> cl = new ArrayList<>();
			for(CityVO c:finalCitys){
				if(provinceId.longValue() == c.getProvinceId().longValue()){
					cl.add(c);
				}
			}
			resultData.setData(cl);
			return resultData;
		}
		
		return resultData;
	}
	
	/**
	 * 分页显示服务商
	 */
	@ResponseBody
	@RequestMapping(value = "/find")
	public Map<String, Object> find(String cityName, Integer isProvince, Page<Server> page) {
		
		Map<String, Object> sm = new HashMap<>();
		sm.put("start", page.getStart());
		sm.put("limit", page.getLimit());
		sm.put("state", 1);
		if(!StringUtils.isEmpty(cityName)){
			sm.put("serverCity", cityName);
		}
		sm.put("sort", "top");
		sm.put("sortUp", "desc");
		List<Server> list = serverBiz.find(sm);
		Long total = serverBiz.getTotal(sm);
		List<Map<String, Object>> rl = new ArrayList<>();
		for(int i=0; i<list.size(); i++) {
			Server s = list.get(i);
			Map<String, Object> map = new HashMap<>();
			// 质保年限，根据设备表查找
			Device d = new Device();
			d.setServeId(s.getId());
			Page<Device> dp = new Page<>();
			dp.setExample(d);
			Page<Device> sbe = deviceBiz.selectByExample(dp);
			String zujianQuantity = "0";
			String fujianQuantity = "0";
			if(sbe.getList().size() >= 2){
				zujianQuantity = sbe.getList().get(0).getQuality();
				fujianQuantity = sbe.getList().get(1).getQuality();
			}
			
			map.put("sqmElectric", s.getSqmElectric()); // 一平米可以转换成多少瓦
//			map.put("wattPrice", wattPrice); //每瓦多少钱 
			map.put("priceaRing", s.getPriceaRing()); // 20千瓦以下多少钱/瓦
			map.put("pricebRing", s.getPricebRing()); // 20千瓦以上多少钱/瓦
			map.put("serverId", s.getId());
			map.put("banner_url", s.getBanner_url());
			map.put("serverName", s.getServerName()); //服务商名称
			map.put("serverCity", s.getServerCity()); // 服务城市
			map.put("texture", s.getTexture()); //组件材质
			map.put("zhxl", s.getCvr()+"%"); // 转化效率
			map.put("zbnxcz", zujianQuantity+"年");// 质保年限（材质）
			map.put("zbnxfj", fujianQuantity+"年");// 质保年限（附件）
			map.put("businessUrl", s.getBusinessUrl());
			map.put("aptitude", s.getAptitude());
			map.put("contactPhone", s.getContactPhone());
			rl.add(map);
		}
		Map<String, Object> rm = new HashMap<>();
		rm.put("rows", rl);
		rm.put("totalCounts", total);
		rm.put("pageSize", page.getLimit());
		
		return rm;
	}
	
	/*
	 * 修改服务商信息和设备信息
	 */
	@ResponseBody
	@RequestMapping(value = "/update2")
	public ResultData update2(String server, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		ServerVO serverVo = JSON.parseObject(server, ServerVO.class);
		
		serverBiz.update(serverVo);
		// 更新服务商设备
		List<Device> devices = serverVo.getDevice();
		if (devices!=null&&devices.size()>0) {
			for (Device device : devices) {
				deviceBiz.update(device);
			}
		}
		
		return resultData;
	}
	
}