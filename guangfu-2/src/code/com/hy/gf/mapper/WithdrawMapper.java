package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Withdraw;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface WithdrawMapper {

	int delete(Long id);

	Withdraw select(Long id);

	int insert(Withdraw record);

	Withdraw selectByPK(Long id);

	int update(Withdraw record);

	Withdraw selectOneByExample(Withdraw record);

	List<Withdraw> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Withdraw withdraw);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	List<Withdraw> dixianFilter(Map<String, Object> map);

	int dixianFilterCount(Map<String, Object> map);
}