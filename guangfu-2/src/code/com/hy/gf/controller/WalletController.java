package com.hy.gf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.BankCardBiz;
import com.hy.gf.biz.IncomeBiz;
import com.hy.gf.biz.OrderBiz;
import com.hy.gf.biz.UserBiz;
import com.hy.gf.biz.WalletBiz;
import com.hy.gf.biz.WithdrawBiz;
import com.hy.gf.model.BankCard;
import com.hy.gf.model.Income;
import com.hy.gf.model.Order;
import com.hy.gf.model.Page;
import com.hy.gf.model.Server;
import com.hy.gf.model.User;
import com.hy.gf.model.Wallet;
import com.hy.gf.model.Withdraw;
import com.hy.gf.util.Constant;
import com.hy.gf.util.MD5Util;
import com.hy.gf.util.RongLianSMS;
import com.hy.gf.vo.OrderVO;
import com.hy.gf.vo.ResultData;


@Controller
@RequestMapping(value = "/wallet")
public class WalletController {
@Resource
WalletBiz walletBiz;
@Resource
WithdrawBiz withdrawBiz;
@Resource
UserBiz userBiz;
@Resource
BankCardBiz bankCardBiz;
@Resource
IncomeBiz incomeBiz;
@Resource
OrderBiz orderBiz;

//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	walletBiz.delete(id);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insert")
	public ResultData insert(Wallet wallet, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		walletBiz.insert(wallet);
		resultData.setData(wallet);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Wallet wallet = walletBiz.select(id);
		resultData.setData(wallet);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/update")
	public ResultData update(Wallet wallet, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		walletBiz.update(wallet);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Wallet wallet, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Wallet selectByExample =walletBiz.selectOneByExample(wallet);
		resultData.setData(selectByExample);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Wallet wallet, Page<Wallet> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(wallet);
			}
		Page<Wallet> selectByExample = walletBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<Wallet> list = JSON.parseArray(listJson.getData(), Wallet.class);
		walletBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		walletBiz.deleteBatch(ids);
		return resultData;
	}
	
	/**
	 * 提现、充值、查询余额
	 */
	@ResponseBody
	@RequestMapping(value = "/dixian")
	public ResultData dixian(Integer type, String dixianPwd, String code4dixian, Withdraw withdraw, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		User user = (User)httpSession.getAttribute("user");
		if(user == null){
			return Constant.noLogin(resultData);
		}
		
		Wallet wallet = new Wallet();
		wallet.setUserId(user.getId());
		Wallet sobe = walletBiz.selectOneByExample(wallet);
		// 用户没有钱包就新建一个
		if(sobe == null){
			Wallet nw = new Wallet();
			nw.setUserId(user.getId());
			walletBiz.insert(nw);
			sobe = walletBiz.selectOneByExample(nw);
		}
		
		//查询可提现金余额
		if(1 == type){
			resultData.setData(sobe.getMoney());
			return resultData;
		}
		
		//提现
		if(2 == type){
			// 检查钱包是否已经被冻结，冻结无法提现
			if(sobe.getStatus() != 0){
				resultData.setCode(606);
				resultData.setMsg("钱包已经被冻结");
				resultData.setSuccess(false);
				return resultData;
			}
			// 核对验证码
			String code = (String)httpSession.getAttribute("code4dixian");
			if (code == null) {
				resultData.setCode(403);
				resultData.setSuccess(false);
				resultData.setMsg("请先发送验证码");
				return resultData;
			}
			if (!code4dixian.equals(code)) {
				resultData.setCode(403);
				resultData.setSuccess(false);
				resultData.setMsg("验证码不正确");
				return resultData;
			}
			// 检测短信验证码是否过期
			ResultData<Object> resultData2 = RongLianSMS.checkMsgTimeOut("code4dixianTime",httpSession);
			if(!resultData2.getSuccess()){
				return resultData2;
			}
			
			// 核对登陆密码
			User sobeU = userBiz.select(user.getId());
			if(!sobeU.getPassword().equals(MD5Util.GetMD5Code(dixianPwd))){
				resultData.setCode(501);
				resultData.setMsg("登陆密码错误");
				return resultData;
			}
			// 提现金额是否大于钱包金额
			if(withdraw.getMoney() > sobe.getMoney()){
				resultData.setCode(501);
				resultData.setMsg("提现金额大于钱包余额");
				return resultData;
			}
			// 钱包减去提现金额， 并更新
			double nowMoney = sobe.getMoney() - withdraw.getMoney();
			sobe.setMoney(nowMoney);
			walletBiz.update(sobe);
			// 插入提现记录
			Withdraw nw = new Withdraw();
			nw.setUserId(user.getId().intValue());
			nw.setType(0);
			nw.setMoney(withdraw.getMoney());
			BankCard bc = new BankCard();
			bc.setBankNum(withdraw.getCardNum());
			bc = bankCardBiz.selectOneByExample(bc);
			nw.setCardId(bc.getId().intValue());
			nw.setCardNum(withdraw.getCardNum());
			nw.setCardUserName(withdraw.getCardUserName());
			nw.setStatus(0);
			withdrawBiz.insert(nw);
			// 插入消费记录
			Income income = new Income();
			income.setUserId(user.getId().intValue());
			income.setWalletId(sobe.getId().intValue());
			income.setWithdrawId(nw.getId().intValue());
			income.setCount(nowMoney);
			income.setMoney(withdraw.getMoney());
			income.setType(1);
			incomeBiz.insert(income);
			
			resultData.setMsg("申请提现成功");
			return resultData;
		}
		
		// 充值
		/*if(3 == type){
			// 增加钱包金额
			double walletMoney = withdraw.getMoney()+sobe.getMoney();
			sobe.setMoney(walletMoney);
			sobe.setJifen(withdraw.getMoney().intValue());
			walletBiz.update(sobe);
			// 插入消费记录
			Income in = new Income();
			in.setUserId(user.getId().intValue());
			in.setWalletId(sobe.getId().intValue());
			in.setCount(walletMoney);
			in.setMoney(withdraw.getMoney());
			in.setType(0);
			int total = incomeBiz.insert(in);
			if(total > 0 ){
				return resultData;
			} else{
				resultData.setMsg("充值失败");
				resultData.setSuccess(false);
				return resultData;
			}
		}*/
		
		if(3 == type){
			resultData.setCode(500);
			resultData.setMsg("充值功能暂未开放");
			resultData.setSuccess(false);
			return resultData;
		}
		
		return resultData;
	}
	
	/**
	 * 提现时 获取短信验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/code4dixian")
	public ResultData<Object> code4dixian(String phone, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		User user = (User)httpSession.getAttribute("user");
		if(user == null){
			return Constant.noLogin(resultData);
		}
		
//		String code = "123456";
		String code = RongLianSMS.sendCode(user.getPhone());
		httpSession.setAttribute("code4dixian", code);
		httpSession.setAttribute("code4dixianTime",System.currentTimeMillis());
		return resultData;
	}
	
	/**
	 * 根据session获取服务商钱包信息和营业总额，营业利润
	 */
	@ResponseBody
	@RequestMapping(value = "/gainServerW")
	public ResultData gainServerW(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Object attribute = httpSession.getAttribute("server");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		
		if(!(attribute instanceof Server)){
			return resultData;
		}
		
		Server server = (Server)attribute;
		Wallet wallet = new Wallet();
		wallet.setServerId(server.getId());
		Wallet sobe = walletBiz.selectOneByExample(wallet);
		
		Page<OrderVO> page = new Page<>();
		OrderVO ov = new OrderVO();
		ov.setServerId(server.getId());
		page.setExample(ov);
		page.setLimit(Integer.MAX_VALUE);
		Page<OrderVO> sbe = orderBiz.selectByExample(page);
		List<Order> list = new ArrayList<>();
		list.addAll(sbe.getList());
		
		Double totalMoney = 0d;
		Double totalIncome = 0d;
		Double factorage = server.getFactorage();
		
		if(list.size() > 0){
			for(Order o:list){
				totalMoney += o.getTotalMoney();
				totalIncome += (o.getTotalMoney() * factorage);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("totalMoney", totalMoney);
		map.put("totalIncome", totalIncome);
		
		Map<String, Object> rm = new HashMap<>();
		rm.put("wallet", sobe);
		rm.put("finance", map);
		
		resultData.setData(rm);
		return resultData;
	}
	
}