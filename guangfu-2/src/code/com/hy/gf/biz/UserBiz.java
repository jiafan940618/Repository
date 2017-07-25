package com.hy.gf.biz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.easemob.server.example.api.IMUserAPI;
import com.easemob.server.example.comm.ClientContext;
import com.easemob.server.example.comm.EasemobRestAPIFactory;
import com.easemob.server.example.comm.body.IMUserBody;
import com.easemob.server.example.comm.wrapper.BodyWrapper;
import com.hy.gf.mapper.CityMapper;
import com.hy.gf.mapper.ProvinceMapper;
import com.hy.gf.mapper.UserMapper;
import com.hy.gf.mapper.WalletMapper;
import com.hy.gf.model.Page;
import com.hy.gf.model.User;
import com.hy.gf.model.Wallet;
import com.hy.gf.util.ObjToMap;

@Service
public class UserBiz {

	@Resource
	UserMapper userMapper;
	@Resource
	WalletMapper walletMapper;
	@Resource
	ProvinceMapper provinceMapper;
	@Resource
	CityMapper cityMapper;
	
	static EasemobRestAPIFactory factory;
	static IMUserAPI userAPI;
	
	static {
		factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
	}

	public int delete(Long id) {
		return userMapper.delete(id);
	}

	public int insert(User user) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				BodyWrapper userBody = new IMUserBody(user.getPhone(), "ygyx" + user.getPhone(), user.getPhone());
				Object obj = userAPI.createNewIMUserSingle(userBody);
				System.out.println(obj);
			}
		}).start();
		
		// 生成全地址
		if(user.getProvince_id() != null && user.getCity_id() != null){
			String province_name = "";
			String city_name = "";
			province_name = provinceMapper.selectName(user.getProvince_id());
			city_name = cityMapper.selectName(user.getCity_id());
			String full_address = province_name+city_name+user.getAddressText();
			user.setFull_address(full_address);
		}
		
		int flag = userMapper.insert(user);
		
		// 生成钱包
		Wallet nw = new Wallet();
		nw.setUserId(user.getId());
		Wallet sobe = walletMapper.selectOneByExample(nw);
		if(sobe == null){
			nw.setType(1);
			walletMapper.insert(nw);
		}
		
		return flag;
	}

	public User select(Long id) {
		return userMapper.select(id);
	}

	public int update(User record) {
		return userMapper.update(record);
	}

	public User selectOneByExample(User record) {
		return userMapper.selectOneByExample(record);
	}

	public Page<User> selectByExample(Page<User> page) {
		Object example = page.getExample();
		User user = null;
		if (example != null) {
			user = (User) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(user);
		objectMap.putAll(objectMap2);
		List<User> selectByExample = userMapper.selectByExample(objectMap);
		page.setList(selectByExample);
		page.setExample(null);
		Integer selectByExampleCount = userMapper.selectByExampleCount(user);
		page.setTotal(selectByExampleCount);
		return page;
	}

	public List insertBatch(List list) {
		userMapper.insertBatch(list);
		return list;
	}

	public int deleteBatch(List ids) {
		return userMapper.deleteBatch(ids);
	}

	public User selectByPhone(String phone) {
		return userMapper.selectByPhone(phone);
	}

	public User selectByOpenIda(String openIda) {
		User user = new User();
		user.setOpenIda(openIda);
		return userMapper.selectOneByExample(user);
	}
	
	public int updatePassword(User record) {
		return userMapper.updatePassword(record);
	}
}