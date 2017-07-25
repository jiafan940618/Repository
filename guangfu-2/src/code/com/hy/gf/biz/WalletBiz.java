package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Wallet;
import com.hy.gf.util.*;
import com.hy.gf.mapper.WalletMapper;
import com.hy.gf.model.Page;

@Service
public class WalletBiz {

@Resource
WalletMapper walletMapper;

	public int delete(Long id) {
		return walletMapper.delete(id);
	}
	public int insert(Wallet record) {
		return walletMapper.insert(record);
	}
	public Wallet select(Long id) {
		return walletMapper.select(id);
	}
	public int update(Wallet record) {
		return walletMapper.update(record);
	}
	public Wallet selectOneByExample(Wallet record) {
		return walletMapper.selectOneByExample(record);
	}
	public Page<Wallet> selectByExample(Page<Wallet> page) {
Object example = page.getExample();
Wallet wallet = null;
if (example != null) {
wallet = (Wallet) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(wallet);
objectMap.putAll(objectMap2);List<Wallet> selectByExample = walletMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = walletMapper.selectByExampleCount(wallet);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		walletMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return walletMapper.deleteBatch(ids);
}}