package com.hy.gf.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.hy.gf.biz.ProvinceBiz;
import com.hy.gf.biz.PushBiz;
import com.hy.gf.biz.ServerBiz;
import com.hy.gf.biz.StationBiz;
import com.hy.gf.biz.UserBiz;
import com.hy.gf.biz.VasBiz;
import com.hy.gf.biz.WalletBiz;
import com.hy.gf.model.City;
import com.hy.gf.model.Order;
import com.hy.gf.model.Page;
import com.hy.gf.model.Province;
import com.hy.gf.model.Push;
import com.hy.gf.model.Server;
import com.hy.gf.model.Station;
import com.hy.gf.model.User;
import com.hy.gf.model.Vas;
import com.hy.gf.model.Wallet;
import com.hy.gf.util.Constant;
import com.hy.gf.util.DateUtil;
import com.hy.gf.util.RongLianSMS;
import com.hy.gf.util.StringUtil;
import com.hy.gf.vo.OrderVO;
import com.hy.gf.vo.ResultData;

@Controller
@RequestMapping(value = "/order")
public class OrderController {
	@Resource
	OrderBiz orderBiz;
	@Resource
	StationBiz stationBiz;
	@Resource
	ProvinceBiz provinceBiz;
	@Resource
	CityBiz cityBiz;
	@Resource
	ServerBiz serverBiz;
	@Resource
	VasBiz vasBiz;
	@Resource
	DeviceBiz deviceBiz;
	@Resource
	UserBiz userBiz;
	@Resource
	PushBiz pushBiz;
	@Resource
	WalletBiz walletBiz;

	@ResponseBody
//	@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		orderBiz.delete(id);
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/insert")
	public ResultData insert(Order order, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();

		orderBiz.insert(order);
		resultData.setData(order);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Order order = orderBiz.select(id);
		resultData.setData(order);
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/update")
	public ResultData update(Order order, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		orderBiz.update(order);
		resultData.setData(order);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Order order, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Order selectByExample = orderBiz.selectOneByExample(order);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(OrderVO orderVO, Page<OrderVO> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(orderVO);
		}
		Page<OrderVO> selectByExample = orderBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<Order> list = JSON.parseArray(listJson.getData(), Order.class);
		orderBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		orderBiz.deleteBatch(ids);
		return resultData;
	}

	/**
	 * web 查找订单列表
	 */
	@ResponseBody
	@RequestMapping(value = "/find")
	public ResultData<Map<String, Object>> find(Order order, HttpSession httpSession) {
		ResultData<Map<String, Object>> resultData = new ResultData<Map<String, Object>>();

		Object attribute = httpSession.getAttribute("user");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}
		User user = (User) attribute;
		order.setUserId(user.getId());
		List<OrderVO> find = orderBiz.find(order);
		Map<String, Object> map = new HashMap<>();
		map.put("list", find);
		resultData.setData(map);

		return resultData;
	}

	SimpleDateFormat format = new SimpleDateFormat("yyMMddHH");
	/** 自定义进制(0,1没有加入,容易与o,l混淆) */
	private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p',
			'5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h' };
	/** (不能与自定义进制有重复) */
	private static final char b = 'o';
	/** 进制长度 */
	private static final int binLen = r.length;

	/**
	 * 根据ID生成六位随机码
	 * 
	 * @param id
	 *            ID
	 * @return 随机码
	 */
	public static String toSerialCode(long id, int s) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			// System.out.println(num + "-->" + ind);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		// System.out.println(num + "-->" + num % binLen);
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if (str.length() < s) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			Random rnd = new Random();
			for (int i = 1; i < s - str.length(); i++) {
				sb.append(r[rnd.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return str;
	}


	static DecimalFormat df = new DecimalFormat("0.00");
	static DecimalFormat df1 = new DecimalFormat("0000");
	static Random rd = new Random();
	
	/**
	 * 下单页面，装机容量输入框计算价格
	 * @param orderVO
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/calPrice")
	public ResultData calPrice(OrderVO orderVO, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Server server = serverBiz.select(Long.valueOf(orderVO.getServerId()));
		Double price = 0d;
		Double vasPrice = 0d;
		if(orderVO.getCapacity() <= 20){
			price = Double.valueOf(server.getPriceaRing());
		} else{
			price = Double.valueOf(server.getPricebRing());
		}
		
		if(!StringUtils.isEmpty(orderVO.getVasId())){
			String[] vasIds = orderVO.getVasId().split(",");
			for(String vid:vasIds){
				Vas vas = vasBiz.select(Long.valueOf(vid));
				vasPrice += vas.getPrice();
			}
		}
		
		// 工程造价=装机容量（kw）*每瓦多少钱*1000 + 增值服务
		Double totalPrice = orderVO.getCapacity()*price*1000 + vasPrice;
		
		Map<String, Object> map = new HashMap<>();
		map.put("price", df.format(price)); // 单价
		map.put("vasPrice", vasPrice); // 增值服务价格
		map.put("totalPrice", df.format(totalPrice)); // 工程总价
		
		httpSession.setAttribute("totalPrice", df.format(totalPrice));
		httpSession.setAttribute("capacity", df.format(orderVO.getCapacity()));
		resultData.setData(map);
		return resultData;
	}
	
	/**
	 * web 新建订单 且 新建电站
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public ResultData<Order> add(Order order, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Order> resultData = new ResultData<Order>();

		User sessionUser = (User) httpSession.getAttribute("user");
		if (sessionUser == null) {
			return Constant.noLogin(resultData);
		}
		
		// 初始化步骤
		order.setPlan(0);
		order.setState(1);
		order.setStepA(0);
		order.setStepB(2);
		order.setStepC(2);
		order.setStepD(2);
		order.setStepE(2);
		order.setStatus(0);
		
		// 判断用户所选的建站地址是否在 服务商服务地址范围内
		Server server = serverBiz.select(order.getServerId());
		String[] cityIds = server.getServerCityIds().split(",");
		boolean isContains = false;
		for(String cId:cityIds) {
			if(cId.equals(String.valueOf(order.getAddressId()))) {
				isContains = true;
			}
		}
		if(!isContains) {
			resultData.setCode(777);
			resultData.setMsg("建站地址不在该服务商的服务范围内");
			resultData.setSuccess(false);
			return resultData;
		}
		
		String totalPrice = (String)httpSession.getAttribute("totalPrice");
		if(!String.valueOf(df.format(order.getTotalMoney())).equals(totalPrice)){
			resultData.setCode(600);
			resultData.setMsg("价格与后台不一致");
			resultData.setSuccess(false);
			return resultData;
		}
		String capacity = (String)httpSession.getAttribute("capacity");
		if(!String.valueOf(df.format(order.getCapacity())).equals(capacity)){
			resultData.setCode(600);
			resultData.setMsg("装机容量与后台不一致");
			resultData.setSuccess(false);
			return resultData;
		}

		// 判断订单的优惠码是否存在
		if (!StringUtils.isBlank(order.getPrivilegeCode())) {
			User cu = new User();
			cu.setPrivilegeCodeInit(order.getPrivilegeCode());
			User sobe = userBiz.selectOneByExample(cu);
			if (sobe == null) {
				resultData.setCode(600);
				resultData.setMsg("该优惠码不存在");
				resultData.setSuccess(false);
				return resultData;
			}
			if(order.getPrivilegeCode().equals(sessionUser.getPrivilegeCodeInit())) {
				resultData.setCode(600);
				resultData.setMsg("不能使用自己的代码");
				resultData.setSuccess(false);
				return resultData;
			}
		} else {
			order.setPrivilegeCode(null);
		}

		String orderCode = toSerialCode(sessionUser.getId(), 4) + format.format(System.currentTimeMillis()) + df1.format(rd.nextInt(9999));
		order.setOrderCode(orderCode);
		// 如果增值服务为空
		if(StringUtil.isEmpty(order.getVasId())){
			order.setVasId(null);
			order.setVasText("无");
		} else{
			String[] vasIds = order.getVasId().split(",");
			List<String> vasTextList = new ArrayList<>();
			for(String id:vasIds){
				Vas vas = vasBiz.select(Long.valueOf(id));
				String vasText = vas.getName()+":￥"+vas.getPrice();
				vasTextList.add(vasText);
			}
			order.setVasText(StringUtils.join(vasTextList.toArray(),"，"));
		}
		if(!StringUtils.isEmpty(order.getPrivilegeCode())){
			// 如果用户没有 被推荐码，此时使用下单时用户填写的订单推荐码
			if(StringUtils.isEmpty(sessionUser.getPrivilegeCode())){
				sessionUser.setPrivilegeCode(order.getPrivilegeCode());
				userBiz.update(sessionUser);
			}
		}
		
		order.setPrivilegeCode(sessionUser.getPrivilegeCode());
		order.setUserId(sessionUser.getId());
		orderBiz.insert(order);
		Order sobe = orderBiz.select(order.getId());
		
		// 新建电站
		City c = cityBiz.select(sobe.getAddressId());
		Province pro = provinceBiz.select(c.getProvinceId());
		
		Station s = new Station();
		s.setStationName("新建电站"); // 建立电站时名称默认为 ”新建电站“
		s.setStationCode(String.valueOf(System.currentTimeMillis()));
		s.setOrderId(sobe.getId());
		s.setUserId(order.getUserId());
		s.setCapacity(sobe.getCapacity());
		s.setProvinceId(c.getProvinceId());
		s.setProvinceText(pro.getProvinceName());
		s.setCityId(sobe.getAddressId());
		s.setCityText(c.getCityName());
		s.setAddressText(sobe.getAddressText());
		s.setPrivilegeCode(order.getPrivilegeCode()); // 电站优惠码
		s.setServerId(sobe.getServerId());
		s.setType(sobe.getType());
		stationBiz.insert(s);

		resultData.setData(order);
		return resultData;
	}

	/**
	 * 查找当月已完成订单的并网装机容量
	 */
	@ResponseBody
	@RequestMapping(value = "/findThisMonthCapacity")
	public ResultData findThisMonthCapacity(String type, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		
		List<Map<String, Object>> list = new ArrayList<>();
		String [] times = {year+"-01",year+"-02",year+"-03",year+"-04",year+"-05",year+"-06",year+"-07",year+"-08",year+"-09",year+"-10",year+"-11",year+"-12"};
		
		if(!StringUtils.isEmpty(type)){
			Server server = (Server)httpSession.getAttribute("server");
			for(String time:times){
				Map<String, Object> map = new HashMap<>();
				map.put("serverId", server.getId());
				map.put("timeStr", time);
				map.put("state", 8);
				String capacity = orderBiz.findMonthCapacity(map);
				if(capacity == null) capacity = "0.00";
				Map<String, Object> map2 = new HashMap<>();
				map2.put("createDtm", time);
				map2.put("capacity", capacity);
				list.add(map2);
			}
		} else{
			for(String time:times){
				Map<String, Object> map = new HashMap<>();
				map.put("state", 8);
				map.put("timeStr", time);
				String capacity = orderBiz.findMonthCapacity(map);
				if(capacity == null) {
					capacity = "0.00";
				}
				Map<String, Object> map2 = new HashMap<>();
				map2.put("createDtm", time);
				map2.put("capacity", capacity);
				list.add(map2);
			}
		}

		resultData.setData(list);

		return resultData;
	}

	/**
	 * 根据orderId修改电站名称
	 */
	@ResponseBody
	@RequestMapping(value = "/editStationName")
	public ResultData editStation(Long orderId, String newStationName, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("user");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		
		if(StringUtils.isBlank(newStationName)){
			resultData.setMsg("电站名称不能为空！");
			resultData.setSuccess(false);
			resultData.setCode(505);
			return resultData;
		}

		Station station = new Station();
		station.setOrderId(orderId);
		Station sobe = stationBiz.selectOneByExample(station);
		sobe.setStationName(newStationName);
		stationBiz.update(sobe);

		return resultData;
	}

	/**
	 * 根据订单号查找订单详情
	 */
	@ResponseBody
	@RequestMapping(value = "/findOrderDetail")
	public ResultData findOrderDetail(Long orderId,HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		User user = (User)httpSession.getAttribute("user");
		if(user == null){
			return Constant.noLogin(resultData);
		}
		
		OrderVO order = (OrderVO) orderBiz.select(orderId);
		if (order == null || order.getUserId() != user.getId().intValue()) {
			resultData.setMsg("无权访问该订单");
			resultData.setSuccess(false);
			return resultData;
		}
		
		List<String> vasList = new ArrayList<>();
		if (order.getVasId() != null) {
			String[] vasIds = order.getVasId().split(",");
			for (String id : vasIds) {
				Vas sbp = vasBiz.select(Long.valueOf(id));
				vasList.add(sbp.getName());
			}
		}

		Map<String, Object> map = new HashMap<>();
		map.put("linkMan", order.getLinkMan());
		map.put("phone", order.getPhone());
		map.put("addressText", order.getAddressText());
		map.put("serverName", order.getServer().getServerName());
		map.put("orderCode", order.getOrderCode());
		map.put("capacity", order.getCapacity());
		map.put("vasName", order.getVasText());
		map.put("vasMoney", order.getVasMoney());
		map.put("capacityMoney", order.getTotalMoney() - order.getVasMoney());
		map.put("totalMoney", order.getTotalMoney());
		map.put("deviceA", order.getDeviceA());
		map.put("deviceB", order.getDeviceB());
		map.put("deviceC", order.getDeviceC());
		map.put("deviceD", order.getDeviceD());
		map.put("deviceE", order.getDeviceE());
		// 1申请中，2施工中，3并网发电，4待评价，5待退款，6过期，7取消，8完成，9退款完成
		switch (order.getState()) {
		case 1:
			map.put("order_state", "申请中");
			break;
		case 2:
			map.put("order_state", "施工中");
			break;
		case 3:
			map.put("order_state", "并网中");
			break;
		case 4:
			map.put("order_state", "待评价");
			break;
		case 5:
			map.put("order_state", "待退款");
			break;
		case 6:
			map.put("order_state", "过期");
			break;
		case 7:
			map.put("order_state", "取消");
			break;
		case 8:
			map.put("order_state", "完成");
			break;
		case 9:
			map.put("order_state", "退款完成");
			break;
		default:
			break;
		}

		resultData.setData(map);
		return resultData;
	}

	// 根据订单id查找订单（不属于当前登录用户的订单无法查看）
	@ResponseBody
	@RequestMapping(value = "/select2")
	public ResultData select2(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Order order = orderBiz.select(id);

		User user = (User) httpSession.getAttribute("user");
		if (order == null || order.getUserId() != user.getId().intValue()) {
			resultData.setMsg("无权访问该订单");
			resultData.setSuccess(false);
			return resultData;
		}

		resultData.setData(order);
		return resultData;
	}

	/**
	 * 针对后台管理 更新订单的状态和短信通知客户
	 */
	@ResponseBody
	@RequestMapping(value = "/update2")
	public ResultData update2(Order order, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
//		orderBiz.update(order);
		OrderVO s = (OrderVO)orderBiz.select(order.getId());

		if(order.getStepA() != null){
			orderBiz.update(order);
		}else if(order.getStepB() !=null && s.getState() == 1){
			orderBiz.update(order);
			if(order.getStepB() == 1){
				// 短信提醒客户    已经完成屋顶勘查
				String title = "关于订单："+s.getOrderCode()+"的消息";
				String content = "您的订单："+s.getOrderCode()+"已经完成     屋顶勘查!";
				pushMsg(s.getUserId().intValue(), title, content);
				String msgContent = "已经完成场地勘查。";
				RongLianSMS.sendMsg4User(s.getUser().getPhone(), s.getUser().getUsername(), s.getOrderCode(), msgContent);
			}
		}else if(order.getStepC() != null && s.getState() == 1){
			orderBiz.update(order);
			if(order.getStepC() == 1){
				// 短信提醒客户   申请并网已受理
				String title = "关于订单："+s.getOrderCode()+"的消息";
				String content = "您的订单："+s.getOrderCode()+"已经受理     申请并网!";
				pushMsg(s.getUserId().intValue(), title, content);
				String msgContent = "申请并网已受理。";
				RongLianSMS.sendMsg4User(s.getUser().getPhone(), s.getUser().getUsername(), s.getOrderCode(), msgContent);
			}
		} else if(order.getStepD() != null){
			if(s.getStepA() != 1 || s.getStepB() != 1 || s.getStepC() != 1){
				resultData.setCode(666);
				resultData.setMsg("请完成前面的步骤");
				resultData.setSuccess(false);
				return resultData;
			}
			orderBiz.update(order);
			if(order.getStepD() == 1){
				s.setStepD(1);
				s.setState(3);
				orderBiz.update(s);
				
				String title = "关于订单："+s.getOrderCode()+"的消息";
				String content = "您的订单："+s.getOrderCode()+"已经施工完成，请前去申请并网发电吧";
				pushMsg(s.getUserId().intValue(), title, content);
				// 短信提醒客户    施工已经完成
				String msgContent = "已完成施工。";
				RongLianSMS.sendMsg4User(s.getUser().getPhone(), s.getUser().getUsername(), s.getOrderCode(), msgContent);
			}
		}else if(order.getStepE() != null){
			if(s.getStepA() != 1 || s.getStepB() != 1 || s.getStepC() != 1 || s.getStepD() != 1) {
				resultData.setCode(666);
				resultData.setMsg("请完成前面的步骤");
				resultData.setSuccess(false);
				return resultData;
			}
			orderBiz.update(order);
			if(order.getStepE() == 1) {
				Station station = new Station();
				station.setOrderId(order.getId());
				Station sobe = stationBiz.selectOneByExample(station);
				if (StringUtils.isEmpty(sobe.getAmmeterCode())) {
					resultData.setCode(777);
					resultData.setMsg("电站未绑定电表");
					resultData.setSuccess(false);
					return resultData;
				}
				
				s.setStepE(1);
				s.setState(8);
				s.setPlan(1);
				orderBiz.update(s);
				// 修改电站状态
				sobe.setCreateDtm(new Date());
				sobe.setUpDtm(new Date());
				sobe.setState(2);
				stationBiz.update(sobe);
				// 推送消息
				String title = "关于订单："+s.getOrderCode()+"的消息";
				String content = "您的订单："+s.getOrderCode()+"已经开始发电，请前去查看电站的发电情况吧";
				pushMsg(s.getUserId().intValue(), title, content);
			}
		}
		
		s = (OrderVO)orderBiz.select(order.getId());
		if(s.getState() == 1 && s.getStepA() == 1 && s.getStepB() == 1 && s.getStepC() == 1) {
			s.setState(2);
			orderBiz.update(s);
			
			String title = "关于订单："+s.getOrderCode()+"的消息";
			String content = "您的订单："+s.getOrderCode()+"已经完成支付、屋顶勘查、申请并网，请前去预约施工吧";
			pushMsg(s.getUserId().intValue(), title, content);
			// 短信提醒客户  前去预约施工
			String msgContent = "已经完成支付、场地勘查、申请并网，请用电脑登陆网页或手机登录APP，预约施工。";
			RongLianSMS.sendMsg4User(s.getUser().getPhone(), s.getUser().getUsername(), s.getOrderCode(), msgContent);
		}

		resultData.setData(order);
		return resultData;
	}
	
	/**
	 * 建设中电站  用户预约步骤的更新和短信通知服务商
	 */
	@ResponseBody
	@RequestMapping(value = "/update3")
	public ResultData update3(Order order, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		User sessionUser = (User)httpSession.getAttribute("user");
		if(sessionUser == null){
			return Constant.noLogin(resultData);
		}
		OrderVO s = (OrderVO)orderBiz.select(order.getId());
		
		if(order.getStepB() != null && order.getStepB() == 0){
			// 预约屋顶勘查
			s.setStepB(0);
			orderBiz.update(s);
			// 短信通知    服务商    来勘查屋顶
			String time = DateUtil.formatDate(new Date(), "，在yyyy年MM月dd日 HH时mm分ss秒");
			String msgContent = s.getLinkMan()+time+"预约了场地勘察，订单号码："+s.getOrderCode()+"，客户联系电话："+s.getPhone()+"。请您在短时间内与客户联系，上门勘察场地。";
			RongLianSMS.sendMsg4Server(s.getServer().getSalemanPhone(), msgContent);
		}else if(order.getStepC() != null && order.getStepC() == 0){
			// 预约申请并网
			s.setStepC(0);
			orderBiz.update(s);
			// 短信通知    服务商    去申请并网
			String time = DateUtil.formatDate(new Date(), "，在yyyy年MM月dd日 HH时mm分ss秒");
			String msgContent = s.getLinkMan()+time+"预约了申请并网，订单号码："+s.getOrderCode()+"，客户联系电话："+s.getPhone()+"。请您在短时间内前去受理申请并网。";
			RongLianSMS.sendMsg4Server(s.getServer().getSalemanPhone(), msgContent);
		}else if(order.getStepD() != null && order.getStepD() == 0){
			// 预约施工
			s.setStepD(0);
			orderBiz.update(s);
			// 短信通知    服务商    去施工
			String time = DateUtil.formatDate(new Date(), "，在yyyy年MM月dd日 HH时mm分ss秒");
			String msgContent = s.getLinkMan()+time+"预约了进场施工，订单号码："+s.getOrderCode()+"，客户联系电话："+s.getPhone()+"。请您在短时间内与客户联系，前去场地施工。";
			RongLianSMS.sendMsg4Server(s.getServer().getSalemanPhone(), msgContent);
		}else if(order.getStepE() != null && order.getStepE() ==1){
			if(s.getState() == 3 && s.getStepA() == 1 && s.getStepB() == 1 && s.getStepC() == 1 && s.getStepD() == 1) {
				Station station = new Station();
				station.setOrderId(order.getId());
				Station sobe = stationBiz.selectOneByExample(station);
				if (StringUtils.isEmpty(sobe.getAmmeterCode())) {
					resultData.setCode(777);
					resultData.setMsg("电站未绑定电表");
					resultData.setSuccess(false);
					return resultData;
				}
				
				s.setStepE(1);
				s.setState(8);
				s.setPlan(1);
				orderBiz.update(s);
				// 修改电站状态
				sobe.setCreateDtm(new Date());
				sobe.setUpDtm(new Date());
				sobe.setState(2);
				stationBiz.update(sobe);
				// 推送消息
				String title = "关于订单："+s.getOrderCode()+"的消息";
				String content = "您的订单："+s.getOrderCode()+"已经开始发电，请前去查看电站的发电情况吧";
				pushMsg(s.getUserId().intValue(), title, content);
			} else {
				resultData.setCode(666);
				resultData.setMsg("请完成前面的步骤");
				resultData.setSuccess(false);
				return resultData;
			}
		}
		
		resultData.setData(order);
		return resultData;
	}

	/**
	 * 针对后台管理 根据当前登录服务商ID
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByExample2")
	public ResultData selectByExample2(OrderVO orderVO, Page<OrderVO> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		if (page.getExample() == null) {
			page.setExample(orderVO);
		}
		
		Server server = (Server) httpSession.getAttribute("server");
		
		orderVO.setServerId(server.getId());
		page.setExample(orderVO);
		Page<OrderVO> sbe = orderBiz.selectByExample(page);
		List<OrderVO> orderList = sbe.getList();
		
		resultData.setData(sbe);
		return resultData;
	}
	
	/**
	 * 推送消息
	 */
	private int pushMsg(int userId, String title, String content){
		Push np = new Push();
		np.setUserId(userId);
		np.setTitle(title);
		np.setContent(content);
		np.setIsPush(1);
		np.setIsRead(0);
		np.setPushDtm(new Date());
		int i = pushBiz.insert(np);
		return i;
	}
	
	
	public static void main(String[] args) {
		String time = DateUtil.formatDate(new Date(), "，在yyyy年MM月dd日 HH时mm分ss秒");
		System.out.println(time);
	}
	
	/**
	 * 针对后台，超级管理员修改订单信息
	 */
	@ResponseBody
	@RequestMapping(value = "/manageModifyOrder")
	public ResultData manageModifyOrder(Order order, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("admin");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		if(((User)attribute).getRoleId() != 1){
			resultData.setCode(600);
			resultData.setMsg("不是管理员，无权操作");
			resultData.setSuccess(false);
			return resultData;
		}
		
		orderBiz.update(order);
		
		Station station = new Station();
		station.setOrderId(order.getId());
		station = stationBiz.selectOneByExample(station);
		station.setCapacity(order.getCapacity());
		station.setServerId(order.getServerId());
		
		stationBiz.update(station);
		
		return resultData;
	}
	
	/**
	 * 针对后台，服务商对订单进行退款
	 */
	/*@ResponseBody
	@RequestMapping(value = "/serverRefundOrder")
	public ResultData serverRefundOrder(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("server");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		
		Order order = orderBiz.select(id);
		order.setState(5);
		orderBiz.update(order);
		
		if(order.getPayWay() == 0){
			resultData.setCode(600);
			resultData.setMsg("该订单是线下付款，无法使用易宝退款");
			resultData.setSuccess(false);
			return resultData;
		}
		
		return resultData;
	}*/

}