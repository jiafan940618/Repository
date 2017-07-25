package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.BankCard;
import com.hy.gf.util.*;
import com.hy.gf.mapper.BankCardMapper;
import com.hy.gf.model.Page;

@Service
public class BankCardBiz {

@Resource
BankCardMapper bankCardMapper;

	public int delete(Long id) {
		return bankCardMapper.delete(id);
	}
	public int insert(BankCard record) {
		return bankCardMapper.insert(record);
	}
	public BankCard select(Long id) {
		return bankCardMapper.select(id);
	}
	public int update(BankCard record) {
		return bankCardMapper.update(record);
	}
	public BankCard selectOneByExample(BankCard record) {
		return bankCardMapper.selectOneByExample(record);
	}
	public Page<BankCard> selectByExample(Page<BankCard> page) {
Object example = page.getExample();
BankCard bankCard = null;
if (example != null) {
bankCard = (BankCard) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(bankCard);
objectMap.putAll(objectMap2);List<BankCard> selectByExample = bankCardMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = bankCardMapper.selectByExampleCount(bankCard);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		bankCardMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return bankCardMapper.deleteBatch(ids);
}}