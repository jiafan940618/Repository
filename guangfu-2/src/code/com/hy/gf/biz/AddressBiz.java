package com.hy.gf.biz;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.model.Address;
import com.hy.gf.util.*;
import com.hy.gf.mapper.AddressMapper;
import com.hy.gf.model.Page;

@Service
public class AddressBiz {

@Resource
AddressMapper addressMapper;

	public int delete(Long id) {
		return addressMapper.delete(id);
	}
	public int insert(Address record) {
		return addressMapper.insert(record);
	}
	public Address select(Long id) {
		return addressMapper.select(id);
	}
	public int update(Address record) {
		return addressMapper.update(record);
	}
	public Address selectOneByExample(Address record) {
		return addressMapper.selectOneByExample(record);
	}
	public Page<Address> selectByExample(Page<Address> page) {
Object example = page.getExample();
Address address = null;
if (example != null) {
address = (Address) example;
}
Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
Map<String, Object> objectMap2 = ObjToMap.getObjectMap(address);
objectMap.putAll(objectMap2);List<Address> selectByExample = addressMapper.selectByExample(objectMap);
page.setList(selectByExample);
page.setExample(null);
Integer selectByExampleCount = addressMapper.selectByExampleCount(address);
page.setTotal(selectByExampleCount);
return page;
	}
	public List insertBatch(List list) {
		addressMapper.insertBatch(list);
		return list;
}	public int deleteBatch(List ids) {
		return addressMapper.deleteBatch(ids);
}}