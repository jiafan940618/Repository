package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Server;
import com.hy.gf.model.Wallet;
import com.hy.gf.util.*;
import com.hy.gf.vo.ServerVO;
import com.hy.gf.mapper.ServerMapper;
import com.hy.gf.mapper.WalletMapper;
import com.hy.gf.model.Device;
import com.hy.gf.model.Page;

@Service
public class ServerBiz {

@Resource
ServerMapper serverMapper;
@Resource
DeviceBiz deviceBiz;
@Resource
WalletMapper walletMapper;

	public int delete(Long id) {
		return serverMapper.delete(id);
	}
	public int insert(ServerVO record) {
		int flag = serverMapper.insert(record);
		List<Device> device = record.getDevice();
		if (device!=null&&device.size()>0) {
			for (Device devices : device) {
				
				devices.setServeId(record.getId());
				devices.setServeName(record.getServerName());
			}
			deviceBiz.insertBatch(device);
		}
		
		// 生成钱包
		Wallet nw = new Wallet();
		nw.setServerId(record.getId());
		nw.setType(2);
		walletMapper.insert(nw);
		
		return flag;
	}
	public Server select(Long id) {
		return serverMapper.select(id);
	}
	public int update(Server record) {
		return serverMapper.update(record);
	}
	public Server selectOneByExample(Server record) {
		return serverMapper.selectOneByExample(record);
	}
	public Page<Server> selectByExample(Page<Server> page) {
Object example = page.getExample();
Server server = null;
if (example != null) {
server = (Server) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(server);
objectMap.putAll(objectMap2);List<Server> selectByExample = serverMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = serverMapper.selectByExampleCount(server);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		serverMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return serverMapper.deleteBatch(ids);
}

	public List<Server> find(Map<String, Object> sm) {
		return serverMapper.find(sm);
	}
	
	public Long getTotal(Map<String, Object> sm) {
		return serverMapper.getTotal(sm);
	}

}