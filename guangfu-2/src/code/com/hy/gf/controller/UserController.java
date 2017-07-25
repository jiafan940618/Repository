package com.hy.gf.controller;

import java.text.DecimalFormat;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.CityBiz;
import com.hy.gf.biz.OSSBiz;
import com.hy.gf.biz.OrderBiz;
import com.hy.gf.biz.ProvinceBiz;
import com.hy.gf.biz.PushBiz;
import com.hy.gf.biz.StationBiz;
import com.hy.gf.biz.UserBiz;
import com.hy.gf.model.Order;
import com.hy.gf.model.Page;
import com.hy.gf.model.Push;
import com.hy.gf.model.Station;
import com.hy.gf.model.User;
import com.hy.gf.util.Constant;
import com.hy.gf.util.MD5Util;
import com.hy.gf.util.RongLianSMS;
import com.hy.gf.util.ShareCodeUtil;
import com.hy.gf.vo.ResultData;
import com.hy.gf.vo.UserVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Resource
	UserBiz userBiz;
	@Resource
	StationBiz stationBiz;
	@Resource
	PushBiz pushBiz;
	@Resource
	ProvinceBiz provinceBiz;
	@Resource
	CityBiz cityBiz;
	@Resource
	OrderBiz orderBiz;

	@ResponseBody
	@RequestMapping(value = "/login")
	public ResultData login(User user, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData<>();
		if (user.getPhone() == null || user.getPhone().equals("")) {
			resultData.setCode(401);
			resultData.setMsg("手机号不能为空");
			return resultData;
		}
		if (user.getPassword() == null || user.getPassword().equals("")) {
			resultData.setCode(401);
			resultData.setMsg("用户密码不能为空");
			return resultData;
		}
		User u = new User();
		u.setPhone(user.getPhone());
		User userLocal = userBiz.selectOneByExample(u);
		// String header = request.getHeader("token");
		if (userLocal == null) {
			resultData.setCode(404);
			resultData.setMsg(Constant.NO_THIS_USER);
			return resultData;
		} else if (!userLocal.getPassword().equals(MD5Util.GetMD5Code(user.getPassword()))) {
			resultData.setCode(405);
			resultData.setMsg(Constant.PASSWORD_ERROR);
			return resultData;
		}else if(userLocal.getRoleId() == 1) {
			resultData.setCode(405);
			resultData.setMsg("管理员请在后台管理 ");
			return resultData;
		}
		httpSession.setAttribute("user", userLocal);
		userLocal.setPassword(null);
		Map<String, Object> userMap = userInfo(1, httpSession);
		resultData.setData(userMap);

		return resultData;
	}

	/**
	 * 安卓端微信登录
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wxLogin")
	public ResultData<User> wxLogin(User user, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<User> resultData = new ResultData<User>();
		if (user.getOpenIda() == null || user.getOpenIda().length() < 1) {
			resultData.setCode(400);
			resultData.setSuccess(false);
			resultData.setMsg(Constant.PARAMS_ERROR);
			return resultData;
		}
		User selectByAppOpenId = userBiz.selectByOpenIda(user.getOpenIda());
		if (selectByAppOpenId == null) {
			resultData.setCode(404);
			resultData.setSuccess(false);
			resultData.setMsg(Constant.NO_THIS_USER);
			return resultData;
		}
		httpSession.setAttribute("user", selectByAppOpenId);
		selectByAppOpenId.setPassword(null);
		resultData.setData(selectByAppOpenId);
		return resultData;
	}

	/**
	 * 注册，步骤一
	 */
	@ResponseBody
	@RequestMapping(value = "/register1")
	public ResultData<Object> register1(UserVO user, String code4register, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		if (user.getPhone() == null) {
			resultData.setCode(405);
			resultData.setSuccess(false);
			resultData.setMsg("电话号码不能为空");
			return resultData;
		}
		
		// 如果短信验证码超过了5分钟
		Long code4registerTime = (Long)httpSession.getAttribute("code4registerTime");
		if (code4registerTime==null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("请先发送验证码");
			return resultData;
		}
		Long spaceTime = System.currentTimeMillis() - code4registerTime;
		if(spaceTime > 300000){
			httpSession.setAttribute("code4register",null);
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("该验证码已失效，请重新获取");
			return resultData;
		}
		
		String attribute2 = (String)httpSession.getAttribute("code4register");
		if (attribute2 == null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("请先发送验证码");
			return resultData;
		}
		if (!code4register.equals(attribute2) || !user.getPhone().equals(httpSession.getAttribute("phone"))) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("验证码不正确");
			return resultData;
		}
		User user2 = new User();
		user2.setPhone(user.getPhone());
		User selectByPhone = userBiz.selectOneByExample(user2);
		if (selectByPhone != null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("该手机号已经被注册");
			return resultData;
		}
		httpSession.setAttribute("register1", user);
		return resultData;
	}

	/**
	 * 注册，步骤二
	 */
	@ResponseBody
	@RequestMapping(value = "/register2")
	public ResultData<Object> register2(User user, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		// User selectByPhone = userBiz.selectOneByExample(user);
		Object attribute = httpSession.getAttribute("register1");
		if (attribute == null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("请先完成第一步注册");
			return resultData;
		}
		if (StringUtils.isEmpty(user.getPrivilegeCode()))
			user.setPrivilegeCode(null);
		// 判断优惠码是否存在
		if (user.getPrivilegeCode() != null) {
			User newUser = new User();
			newUser.setPrivilegeCodeInit(user.getPrivilegeCode());
			if (userBiz.selectOneByExample(newUser) == null) {
				resultData.setCode(505);
				resultData.setSuccess(false);
				resultData.setMsg("该优惠码不存在");
				return resultData;
			}
		}
		if (attribute instanceof User) {
			User new_name = (User) attribute;
			user.setPassword(MD5Util.GetMD5Code(new_name.getPassword()));
			user.setPhone(new_name.getPhone());
			if(user.getEmail() == null || user.getEmail().equals(""))
				user.setEmail(new_name.getEmail());
		}

		userBiz.insert(user);
		
		// 根据userId生成自己的优惠码
		User selectByPhone = userBiz.selectByPhone(user.getPhone());
		selectByPhone.setPrivilegeCodeInit(ShareCodeUtil.toSerialCode(selectByPhone.getId()));
		userBiz.update(selectByPhone);

		user.setPassword("");
		httpSession.setAttribute("user", user);
		resultData.setData(user);
		return resultData;
	}

	/**
	 * 输入优惠码
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePrivilegeCode")
	public ResultData<String> updatePrivilegeCode(String privilegeCode, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData<String> resultData = new ResultData<String>();
		
		Object attribute = httpSession.getAttribute("user");
		if(attribute == null) {
			return Constant.noLogin(resultData);
		}
		User sessionUser = (User)attribute;
		
		if(StringUtils.isEmpty(privilegeCode)) {
			resultData.setCode(504);
			resultData.setSuccess(false);
			resultData.setMsg("优惠码为空");
			return resultData;
		}

		// 判断优惠码是否存在
		User newUser = new User();
		newUser.setPrivilegeCodeInit(privilegeCode);
		if (userBiz.selectOneByExample(newUser) == null) {
			resultData.setCode(505);
			resultData.setSuccess(false);
			resultData.setMsg("该优惠码不存在");
			return resultData;
		}
		
		User user = new User();
		user.setId(sessionUser.getId());
		user.setPrivilegeCode(privilegeCode);
		userBiz.update(user);

		resultData.setData(privilegeCode);
		return resultData;
	}

	/**
	 * APP端-注册时请求验证码
	 * 不需要图形验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/code4register")
	public ResultData<Object> code4register(User user, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		if (StringUtils.isBlank(user.getPhone())) {
			return Constant.error(resultData, 403, "电话不能为空");
		}
		
		User record = new User();
		record.setPhone(user.getPhone());
		User selectByPhone = userBiz.selectOneByExample(record);
		if (selectByPhone != null) {
			return Constant.error(resultData, 403, "该手机号已经被注册");
		}
		
//		String code = "123456";
		String code = RongLianSMS.sendCode(user.getPhone());
		if(code == null){
			return Constant.error(resultData, 500, "发送手机验证码失败");
		}
		httpSession.setAttribute("code4registerTime",System.currentTimeMillis());
		httpSession.setAttribute("code4register", code);
		httpSession.setAttribute("phone", user.getPhone());
		return resultData;
	}
	
	/**
	 * 网页端-注册时请求验证码
	 * 需要图形验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/webCode4register")
	public ResultData<Object> webCode4register(User user, String graphicCode, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		String sessionGraphicCode = (String)httpSession.getAttribute("captchaToken");
		if(StringUtils.isBlank(graphicCode)){
			return Constant.error(resultData, 405, "请输入图形验证码");
    	} else if (StringUtils.isBlank(sessionGraphicCode)) {
    		return Constant.error(resultData, 405, "后台验证码为空");
		} else if (!sessionGraphicCode.equalsIgnoreCase(graphicCode)){
			return Constant.error(resultData, 405, "图形验证码错误");
    	} else if (StringUtils.isBlank(user.getPhone())) {
			return Constant.error(resultData, 403, "电话不能为空");
		}
		
		User record = new User();
		record.setPhone(user.getPhone());
		User selectByPhone = userBiz.selectOneByExample(record);
		if (selectByPhone != null) {
			return Constant.error(resultData, 403, "该手机号已经被注册");
		}
		
//		String code = "123456";
		String code = RongLianSMS.sendCode(user.getPhone());
		if(code == null){
			return Constant.error(resultData, 500, "发送手机验证码失败");
		}
		httpSession.setAttribute("code4registerTime",System.currentTimeMillis());
		httpSession.setAttribute("code4register", code);
		httpSession.setAttribute("phone", user.getPhone());
		return resultData;
	}

	/**
	 * APP端-找回密码时请求验证码
	 * 不需要图形验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/code4findpsw")
	public ResultData<Object> code4findpsw(User user, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		if (StringUtils.isBlank(user.getPhone())) {
			return Constant.error(resultData, 403, "手机号不能为空");
		}
		
		User selectByPhone = userBiz.selectByPhone(user.getPhone());
		if (selectByPhone == null) {
			return Constant.error(resultData, 401, Constant.NO_THIS_USER);
		}
		
		// String code = "123456";
		String code = RongLianSMS.sendCode(user.getPhone());
		if(code == null){
			return Constant.error(resultData, 500, "发送手机验证码失败");
		}
		httpSession.setAttribute("code4findpsw", code);
		httpSession.setAttribute("findpswPhone", user.getPhone());
		return resultData;
	}
	
	/**
	 * 网页端-找回密码时请求验证码
	 * 需要图形验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/webCode4findpsw")
	public ResultData<Object> webCode4findpsw(User user, String graphicCode, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		String sessionGraphicCode = (String)httpSession.getAttribute("captchaToken");
		if(StringUtils.isBlank(graphicCode)){
			return Constant.error(resultData, 405, "请输入图形验证码");
    	} else if (StringUtils.isBlank(sessionGraphicCode)) {
    		return Constant.error(resultData, 405, "后台验证码为空");
		} else if (!sessionGraphicCode.equalsIgnoreCase(graphicCode)){
			return Constant.error(resultData, 405, "图形验证码错误");
    	} else if (StringUtils.isBlank(user.getPhone())) {
			return Constant.error(resultData, 403, "手机号不能为空");
		}
		
		User selectByPhone = userBiz.selectByPhone(user.getPhone());
		if (selectByPhone == null) {
			return Constant.error(resultData, 401, Constant.NO_THIS_USER);
		}
		
		// String code = "123456";
		String code = RongLianSMS.sendCode(user.getPhone());
		if(code == null){
			return Constant.error(resultData, 500, "发送手机验证码失败");
		}
		httpSession.setAttribute("code4findpsw", code);
		httpSession.setAttribute("findpswPhone", user.getPhone());
		return resultData;
	}
	

	/**
	 * 修改密码
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePassword")
	public ResultData<Object> updatePassword(User user, String code4findpsw, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		if (user.getPhone() == null || user.getPhone().equals("")) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("电话不能为空");
			return resultData;
		}
		User selectByPhone = userBiz.selectByPhone(user.getPhone());
		if (selectByPhone == null) {
			resultData.setCode(401);
			resultData.setSuccess(false);
			resultData.setMsg(Constant.NO_THIS_USER);
			return resultData;
		}
		Object attribute2 = httpSession.getAttribute("code4findpsw");
		if (attribute2 == null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("请先发送验证码");
			return resultData;
		}
		if (!code4findpsw.equals(attribute2) || !user.getPhone().equals(httpSession.getAttribute("findpswPhone"))) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("验证码不正确");
			return resultData;
		}
		user.setId(selectByPhone.getId());
		user.setPassword(MD5Util.GetMD5Code(user.getPassword()));
		userBiz.update(user);
		selectByPhone.setPassword(null);
		resultData.setData(selectByPhone);
		return resultData;
	}

	/**
	 * 注册
	 * 
	 * @param user
	 * @param code
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register")
	public ResultData<User> register(User user, String code, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<User> resultData = new ResultData<User>();
		if (!code.equals(httpSession.getAttribute("code4register"))
				&& !user.getPhone().equals(httpSession.getAttribute("phone"))) {
			resultData.setCode(404);
			resultData.setSuccess(false);
			resultData.setMsg(Constant.CODE_ERROR);
			return resultData;
		} else if (userBiz.selectOneByExample(user) != null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg(Constant.EXIST_THIS_USER);
			return resultData;
		}
		userBiz.insert(user);
		user.setPassword(null);
		httpSession.setAttribute("user", user);
		resultData.setData(user);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		userBiz.delete(id);
		return resultData;
	}

	/**
	 * 注册
	 * 
	 * @param user
	 * @param code
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert")
	public ResultData<User> insert(User user, String code, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<User> resultData = new ResultData<User>();
		if (!code.equals(httpSession.getAttribute("code4register"))
				&& !user.getPhone().equals(httpSession.getAttribute("phone"))) {
			resultData.setCode(404);
			resultData.setSuccess(false);
			resultData.setMsg(Constant.CODE_ERROR);
			return resultData;
		} else if (userBiz.selectByPhone(user.getPhone()) != null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg(Constant.EXIST_THIS_USER);
			return resultData;
		}
		user.setPassword(MD5Util.GetMD5Code(user.getPassword()));
		userBiz.insert(user);
		httpSession.setAttribute("user", user);
		user.setPassword(null);
		resultData.setData(user);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		User user = userBiz.select(id);
		resultData.setData(user);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public ResultData<User> update(User user, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<User> resultData = new ResultData<User>();
		Object obj = httpSession.getAttribute("user");
		if (obj == null) {
			return Constant.noLogin(resultData);
		}
		User sessionUser = (User) obj;
		user.setId(sessionUser.getId());
		userBiz.update(user);
		resultData.setData(userBiz.select(user.getId()));
		return resultData;
	}

	/*
	 * 针对后台修改用户资料接口
	 */
	@ResponseBody
	@RequestMapping(value = "/update2")
	public ResultData<User> update2(User user, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<User> resultData = new ResultData<User>();
		
		User su = (User)httpSession.getAttribute("admin");
		if(su == null){
			return Constant.noLogin(resultData);
		} else if(su.getRoleId() == 6){
			resultData.setCode(666);
			resultData.setMsg("无权操作");
			resultData.setSuccess(false);
			return resultData;
		}
		
		user.setId(user.getId());
		userBiz.update(user);
		resultData.setData(userBiz.select(user.getId()));
		return resultData;
	}
	
	@Resource
	OSSBiz oosBiz;

	@ResponseBody
	@RequestMapping(value = "/updateHeadImg")
	public ResultData<User> updateHeadImg(MultipartHttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<User> resultData = new ResultData<User>();
		Object obj = httpSession.getAttribute("user");
		if (obj == null) {
			return Constant.noLogin(resultData);
		}
		User sessionUser = (User) obj;
		String[] saveToOSSs = oosBiz.uploadFilesData(request);
		if (saveToOSSs == null || saveToOSSs.length < 1 || saveToOSSs[0].equals("上传失败")) {
			resultData.setCode(401);
			resultData.setMsg("上传失败");
			resultData.setSuccess(false);
			return resultData;
		}
		User u = new User();
		u.setId(sessionUser.getId());
		u.setImgUrl(saveToOSSs[0]);
		userBiz.update(u);
		sessionUser.setImgUrl(u.getImgUrl());
		resultData.setData(sessionUser);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(User user, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		User selectByExample = userBiz.selectOneByExample(user);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(UserVO userVO, Page<User> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(userVO);
		}
		Page<User> selectByExample = userBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<User> list = JSON.parseArray(listJson.getData(), User.class);
		userBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		userBiz.deleteBatch(ids);
		return resultData;
	}

	/**
	 * 查找session用户的信息，电站数量，积分，我的信息
	 */
	public Map<String, Object> userInfo(Integer countType, HttpSession httpSession) {
		User user = null;
		if(countType == 1){
			user = (User) httpSession.getAttribute("user");
		}else if(countType == 2){
			user = (User) httpSession.getAttribute("admin");
		}
		user = userBiz.select(user.getId());

		Page<Station> page = new Page<>();
		Station station = new Station();
		station.setUserId(user.getId());
		page.setExample(station);
		page.setStart(0);
		page.setLimit(Integer.MAX_VALUE);
		Page<Station> sbe = stationBiz.selectByExample(page);
		List<Station> sl = sbe.getList();
		// 电站数量
		int stationNum = 0;
		// 积分：根据发电量总量计算
		Double jifen = 0d;
		// 我的信息
		int myMsg = 0;

		if (sl.size() > 0) {
			stationNum = sl.size();
			for (int i = 0; i < sl.size(); i++) {
				if (sl.get(i).getWorkTotaKwh() != null) {
					jifen += sl.get(i).getWorkTotaKwh();
				}
			}
		}

		DecimalFormat df = new DecimalFormat("#.00");
		String jifenStr = "";
		if(jifen > 0){
			// 最终积分 == 总积分*积分参数
			Double param = Double.valueOf(50);  // 50度电代表1积分
			jifenStr = df.format(jifen / param);
		} else {
			jifenStr = "0";
		}

		Page<Push> pp = new Page<>();
		Push push = new Push();
		push.setUserId(user.getId().intValue());
		push.setIsPush(1);
		push.setIsRead(0);
		pp.setExample(push);
		pp.setStart(0);
		pp.setLimit(Integer.MAX_VALUE);
		Page<Push> pushSbe = pushBiz.selectByExample(pp);
		myMsg = pushSbe.getList().size();
		
		int station_status = 1;
		Order order = new Order();
		order.setUserId(user.getId());
		order = orderBiz.selectOneByExample(order);
		if(order != null){
			if(order.getState() == 2){
				station_status = 2;
			} else if(order.getState() == 3){
				station_status = 3;
			} else if(order.getState() == 8){
				station_status = 8;
			}
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("station_status", station_status);
		map.put("stationNum", stationNum);
		map.put("jifen", jifenStr);
		map.put("myMsg", myMsg);
		map.put("id", user.getId());
		map.put("roleId", user.getRoleId());
		map.put("imgUrl", user.getImgUrl());
		map.put("username", user.getUsername());
		map.put("phone", user.getPhone());
		map.put("province_id", user.getProvince_id());
		map.put("city_id", user.getCity_id());
		map.put("addressText", user.getAddressText());
		map.put("full_address", user.getFull_address());
		map.put("sex", user.getSex());
		map.put("email", user.getEmail());
		map.put("realName", user.getRealName());
		map.put("privilegeCode", user.getPrivilegeCode());
		map.put("privilegeCodeInit", user.getPrivilegeCodeInit());
		map.put("openIda", user.getOpenIda());
		map.put("openIdb", user.getOpenIdb());
		map.put("openIdc", user.getOpenIdc());
		map.put("createDtm", user.getCreateDtm());

		return map;
	}

	/**
	 * 用户退出登录
	 */
	@ResponseBody
	@RequestMapping(value = "/logout")
	public ResultData logout(Integer countType, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		if(countType == 1){
			httpSession.removeAttribute("user");
		} else if(countType == 2) {
			httpSession.removeAttribute("server");
		} else if(countType == 3) {
			httpSession.removeAttribute("admin");
		}
		
		return resultData;
	}
	
	/**
	 * 用户个人信息里，修改密码时获取验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/code4updatePwd")
	public ResultData<Object> code4updatePwd(User user, String graphicCode, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		String sessionGraphicCode = (String)httpSession.getAttribute("captchaToken");
		if(StringUtils.isBlank(graphicCode)){
			return Constant.error(resultData, 405, "请输入图形验证码");
    	} else if (StringUtils.isBlank(sessionGraphicCode)) {
    		return Constant.error(resultData, 405, "后台验证码为空");
		} else if (!sessionGraphicCode.equalsIgnoreCase(graphicCode)){
			return Constant.error(resultData, 405, "图形验证码错误");
    	} else if (StringUtils.isBlank(user.getPhone())) {
			return Constant.error(resultData, 403, "新手机号不能为空");
		} else if(StringUtils.isEmpty(user.getPassword())){
			return Constant.error(resultData, 500, "请输入登录密码");
		}
		
		User sessionUer = (User) httpSession.getAttribute("user");
		if (sessionUer == null) {
			return Constant.noLogin(resultData);
		}

		User su = userBiz.select(sessionUer.getId());
		if (!MD5Util.GetMD5Code(user.getPassword()).equals(su.getPassword())) {
			return Constant.error(resultData, 500, "原密码不正确");
		}
		if(!su.getPhone().equals(user.getPhone())){
			return Constant.error(resultData, 500, "手机号不属于当前登录用户");
		}
		
//		String code = "123456";
		String code = RongLianSMS.sendCode(user.getPhone());
		if(code == null){
			return Constant.error(resultData, 500, "发送手机验证码失败");
		}
		httpSession.setAttribute("code4updatePwdTime",System.currentTimeMillis());
		httpSession.setAttribute("code4updatePwd", code);
		return resultData;
	}
	
	/**
	 * web 个人信息里的修改密码
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePwd")
	public ResultData<Object> updatePwd(String oldPwd, String newPwd, String updatePwdCode, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();

		User sessionUer = (User) httpSession.getAttribute("user");
		if (sessionUer == null) {
			return Constant.noLogin(resultData);
		}
		
		String code = (String)httpSession.getAttribute("code4updatePwd");
		if(code == null){
			return Constant.error(resultData, 500, "请先发送验证码");
		} else if(!code.equals(updatePwdCode)){
			return Constant.error(resultData, 500, "验证码错误");
		}
		// 检测短信验证码是否过期
		ResultData<Object> resultData2 = RongLianSMS.checkMsgTimeOut("code4updatePwdTime",httpSession);
		if(!resultData2.getSuccess()){
			return resultData2;
		}

		User su = userBiz.select(sessionUer.getId());
		if (!MD5Util.GetMD5Code(oldPwd).equals(su.getPassword())) {
			return Constant.error(resultData, 500, "旧密码不正确");
		}

		su.setPassword(MD5Util.GetMD5Code(newPwd));
		userBiz.update(su);
		
		return resultData;
	}
	
	/**
	 * 用户个人信息里，修改手机号码时获取验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/code4updatePhone")
	public ResultData<Object> code4updatePhone(User user, String graphicCode, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		String sessionGraphicCode = (String)httpSession.getAttribute("captchaToken");
		if(StringUtils.isBlank(graphicCode)){
			return Constant.error(resultData, 405, "请输入图形验证码");
    	} else if (StringUtils.isBlank(sessionGraphicCode)) {
    		return Constant.error(resultData, 405, "后台验证码为空");
		} else if (!sessionGraphicCode.equalsIgnoreCase(graphicCode)){
			return Constant.error(resultData, 405, "图形验证码错误");
    	} else if (StringUtils.isBlank(user.getPhone())) {
			return Constant.error(resultData, 403, "新手机号不能为空");
		} else if(StringUtils.isEmpty(user.getPassword())){
			return Constant.error(resultData, 500, "请输入登录密码");
		}

		User sessionUer = (User) httpSession.getAttribute("user");
		if (sessionUer == null) {
			return Constant.noLogin(resultData);
		}
		
		User su = userBiz.select(sessionUer.getId());
		if(!su.getPassword().equals(MD5Util.GetMD5Code(user.getPassword()))){
			return Constant.error(resultData, 500, "登陆密码错误");
		}
		
		User ou = new User();
		ou.setPhone(user.getPhone());
		User sobe = userBiz.selectOneByExample(ou);
		if(sobe != null){
			return Constant.error(resultData, 500, "新手机号已经被注册");
		}
		
//		String code = "123456";
		String code = RongLianSMS.sendCode(user.getPhone());
		if(code == null){
			return Constant.error(resultData, 500, "发送手机验证码失败");
		}
		httpSession.setAttribute("code4updatePhoneTime",System.currentTimeMillis());
		httpSession.setAttribute("code4updatePhone", code);
		
		return resultData;
	}
	
	/**
	 * web 个人信息里的修改手机号码
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePhone")
	public ResultData<Object> updatePhone(String oldPhone, String newPhone, String updatePhoneCode, String password, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();

		User sessionUer = (User) httpSession.getAttribute("user");
		if (sessionUer == null) {
			return Constant.noLogin(resultData);
		}
		
		String code = (String)httpSession.getAttribute("code4updatePhone");
		if(code == null){
			resultData.setCode(500);
			resultData.setSuccess(false);
			resultData.setMsg("请先发送验证码");
			return resultData;
		}
		if(!code.equals(updatePhoneCode)){
			resultData.setCode(500);
			resultData.setSuccess(false);
			resultData.setMsg("验证码错误");
			return resultData;
		}
		// 检测短信验证码是否过期
		ResultData<Object> resultData2 = RongLianSMS.checkMsgTimeOut("code4updatePhoneTime",httpSession);
		if(!resultData2.getSuccess()){
			return resultData2;
		}

		User su = userBiz.select(sessionUer.getId());
		if (!oldPhone.equals(su.getPhone())) {
			resultData.setCode(500);
			resultData.setSuccess(false);
			resultData.setMsg("输入的已绑定手机号不正确");
			return resultData;
		}
		if(!su.getPassword().equals(MD5Util.GetMD5Code(password))){
			resultData.setCode(500);
			resultData.setSuccess(false);
			resultData.setMsg("登陆密码错误");
			return resultData;
		}
		
		User ou = new User();
		ou.setPhone(newPhone);
		User sobe = userBiz.selectOneByExample(ou);
		if(sobe != null){
			resultData.setCode(500);
			resultData.setSuccess(false);
			resultData.setMsg("该手机号已经被绑定");
			return resultData;
		}

		su.setPhone(newPhone);
		userBiz.update(su);
		
		/*su.setPassword(null);
		httpSession.setAttribute("user", su);*/
		return resultData;
	}

	/**
	 * 检测用户是否已经登陆
	 */
	@ResponseBody
	@RequestMapping(value = "/checkLogin")
	public ResultData<Object> checkLogin(HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();

		User sessionUer = (User) httpSession.getAttribute("user");
		if (sessionUer == null) {
			return Constant.noLogin(resultData);
		}

		return resultData;
	}

	/**
	 * 获取用户电站数量、积分、未读信息
	 * 如果type==1，是查找 谁使用过我的优惠码
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserinfo")
	public ResultData<Object> getUserinfo(Integer type, String privilegeCode, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();

		User sessionUser = (User) httpSession.getAttribute("user");
		if (sessionUser == null) {
			return Constant.noLogin(resultData);
		}

		if(type != null && type == 1 && !StringUtils.isEmpty(privilegeCode)){
			UserVO userVO = new UserVO();
			userVO.setPrivilegeCode(privilegeCode);
			Page<User> page = new Page<>();
			page.setExample(userVO);
			Page<User> sbe = userBiz.selectByExample(page);
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"password"});
			JSONArray jsonArray = JSONArray.fromObject(sbe.getList(), jsonConfig);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("list", jsonArray);
			
			resultData.setData(jsonObject);
			return resultData;
		}
		
		Map<String, Object> userMap = userInfo(1, httpSession);
		resultData.setData(userMap);
		
		return resultData;
	}
	
	/**
	 * 查找谁使用过我的优惠码  和 录入优惠码
	 */
	@ResponseBody
	@RequestMapping(value = "/privileCodeUsed")
	public ResultData<Object> privileCodeUsed(UserVO userVO, Page<User> page, String type, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();

		User sessionUser = (User) httpSession.getAttribute("user");
		if (sessionUser == null) {
			return Constant.noLogin(resultData);
		}
		
		// 录入优惠码
		if(!StringUtils.isEmpty(type) && type.equals("1")){
			if(userVO.getPrivilegeCode().equals(sessionUser.getPrivilegeCodeInit())) {
				resultData.setCode(600);
				resultData.setMsg("不能使用自己的代码");
				resultData.setSuccess(false);
				return resultData;
			}
			
			User user = new User();
			user.setPrivilegeCodeInit(userVO.getPrivilegeCode());
			// 判断优惠码是否存在
			User sobe = userBiz.selectOneByExample(user);
			if(sobe == null){
				resultData.setCode(500);
				resultData.setSuccess(false);
				resultData.setMsg("该优惠码不存在");
				return resultData;
			}
			
			User user2 = new User();
			user2.setId(sessionUser.getId());
			user2.setPrivilegeCode(userVO.getPrivilegeCode());
			userBiz.update(user2);
			return resultData;
		}
		
		if(page.getExample() == null){
			page.setExample(userVO);
		}
		Page<User> sbe = userBiz.selectByExample(page);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"password"});
		JSONArray jsonArray = JSONArray.fromObject(sbe.getList(), jsonConfig);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", jsonArray);
		
		resultData.setData(jsonObject);
		return resultData;
	}

	/*
	 * 后台管理登陆接口
	 */
	@ResponseBody
	@RequestMapping(value = "/login2")
	public Object login2(User user, String graphicCode, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		if(graphicCode != null){
			String sessionGraphicCode = (String)httpSession.getAttribute("captchaToken");
			if(StringUtils.isBlank(graphicCode)){
				resultData.setCode(405);
	    		resultData.setMsg("请输入图形验证码");
	    		resultData.setSuccess(false);
	    		return resultData;
	    	} else if (StringUtils.isBlank(sessionGraphicCode)) {
	    		resultData.setCode(405);
	    		resultData.setMsg("后台验证码为空");
	    		resultData.setSuccess(false);
	    		return resultData;
			} else if (!sessionGraphicCode.equalsIgnoreCase(graphicCode)){
	    		resultData.setCode(405);
	    		resultData.setMsg("输入的图形验证码错误");
	    		resultData.setSuccess(false);
	    		return resultData;
	    	}
		}
		
		if ( StringUtils.isEmpty(user.getPhone()) ) {
			resultData.setCode(401);
			resultData.setMsg("手机号不能为空");
			return resultData;
		} else if ( StringUtils.isEmpty(user.getPassword()) ) {
			resultData.setCode(401);
			resultData.setMsg("用户密码不能为空");
			return resultData;
		}
		
		User u = new User();
		u.setPhone(user.getPhone());
		User userLocal = userBiz.selectOneByExample(u);
		// String header = request.getHeader("token");
		if (userLocal == null) {
			resultData.setCode(404);
			resultData.setMsg(Constant.NO_THIS_USER);
			return resultData;
		} 
		if (!userLocal.getPassword().equals(MD5Util.GetMD5Code(user.getPassword()))) {
			resultData.setCode(405);
			resultData.setMsg(Constant.PASSWORD_ERROR);
			return resultData;
		}
		if(userLocal.getRoleId() == 6) {
			resultData.setCode(404);
			resultData.setMsg("普通用户请在其他页面登录");
			return resultData;
		}
		
		httpSession.setAttribute("admin", userLocal);
		userLocal.setPassword(null);
		Map<String, Object> userMap = userInfo(2, httpSession);
		resultData.setData(userMap);

		return resultData;
	}
	
	/**
	 * 手机web注册
	 */
	@ResponseBody
	@RequestMapping(value = "/mobilewebregister")
	public ResultData<Object> mobilewebregister(User user, String code4register, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		User sbp = userBiz.selectByPhone(user.getPhone());
		if(sbp != null){
			resultData.setCode(400);
			resultData.setMsg("该手机号已经被注册");
			resultData.setSuccess(false);
			return resultData;
		}
		
		// 验证短信时效性
		ResultData rd = RongLianSMS.checkMsgTimeOut("code4registerTime", httpSession);
		if(rd.getSuccess() == false){
			return rd;
		}
		
		if(!code4register.equals(httpSession.getAttribute("code4register"))){
			resultData.setCode(400);
			resultData.setMsg("验证码错误");
			resultData.setSuccess(false);
			return resultData;
		}
		
		// 判断优惠码是否存在
		if (!StringUtils.isEmpty(user.getPrivilegeCode())) {
			User newUser = new User();
			newUser.setPrivilegeCodeInit(user.getPrivilegeCode());
			if (userBiz.selectOneByExample(newUser) == null) {
				resultData.setCode(400);
				resultData.setSuccess(false);
				resultData.setMsg("该优惠码不存在");
				return resultData;
			}
		} else{
			user.setPrivilegeCode(null);
		}
		
		user.setPassword(MD5Util.GetMD5Code(user.getPassword()));
		user.setRoleId(6);

		userBiz.insert(user);
		
		// 根据userId生成自己的优惠码
		User selectByPhone = userBiz.selectByPhone(user.getPhone());
		selectByPhone.setPrivilegeCodeInit(ShareCodeUtil.toSerialCode(selectByPhone.getId()));
		userBiz.update(selectByPhone);

		selectByPhone.setPassword("");
		httpSession.setAttribute("user", selectByPhone);
		resultData.setData(user);
		return resultData;
	}

	
	
}