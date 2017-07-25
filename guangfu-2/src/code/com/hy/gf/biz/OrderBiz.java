package com.hy.gf.biz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.mapper.OrderMapper;
import com.hy.gf.model.Order;
import com.hy.gf.model.Page;
import com.hy.gf.util.ObjToMap;
import com.hy.gf.vo.OrderVO;

@Service
public class OrderBiz {

	@Resource
	OrderMapper orderMapper;

	public int delete(Long id) {
		return orderMapper.delete(id);
	}

	public int insert(Order record) {
		return orderMapper.insert(record);
	}

	public Order select(Long id) {
		return orderMapper.select(id);
	}

	public int update(Order record) {
		return orderMapper.update(record);
	}

	public Order selectOneByExample(Order record) {
		return orderMapper.selectOneByExample(record);
	}

	public Page<OrderVO> selectByExample(Page<OrderVO> page) {
		Object example = page.getExample();
		Order order = null;
		if (example != null) {
			order = (Order) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(order);
		objectMap.putAll(objectMap2);
		List<OrderVO> selectByExample = orderMapper.selectByExample(objectMap);
		page.setList(selectByExample);
		page.setExample(null);
		Integer selectByExampleCount = orderMapper.selectByExampleCount(order);
		page.setTotal(selectByExampleCount);
		return page;
	}

	public List insertBatch(List list) {
		orderMapper.insertBatch(list);
		return list;
	}

	public int deleteBatch(List ids) {
		return orderMapper.deleteBatch(ids);
	}

	public String findMonthCapacity(Map<String, Object> map) {
		return orderMapper.findMonthCapacity(map);
	}

	public List<OrderVO> find(Order order) {
		return orderMapper.find(order);
	}

	public Order selectByPayOrderId(String payOrderId) {
		return orderMapper.selectByPayOrderId(payOrderId);
	}

	public List<Order> businessFind(Map<String, Object> map) {
		return orderMapper.businessFind(map);
	}

	public int businessFindCount(Map<String, Object> map) {
		return orderMapper.businessFindCount(map);
	}

	public Order selectByEbankPayOrderId(String ebankPayOrderId) {
		return orderMapper.selectByEbankPayOrderId(ebankPayOrderId);
	}

}