package com.hy.gf.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hy.gf.model.Order;
import com.hy.gf.vo.OrderVO;

@Component
public interface OrderMapper {

	int delete(Long id);

	Order select(Long id);

	int insert(Order record);

	Order selectByPK(Long id);

	int update(Order record);

	Order selectOneByExample(Order record);

	List<OrderVO> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Order order);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	String findMonthCapacity(Map<String, Object> map);

	List<OrderVO> find(Order order);

	Order selectByPayOrderId(String payOrderId);

	List<Order> businessFind(Map<String, Object> map);

	int businessFindCount(Map<String, Object> map);

	Order selectByEbankPayOrderId(String ebankPayOrderId);
	
}