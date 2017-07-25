package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Yeepay;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface YeepayMapper {

	int delete(Long id);

	Yeepay select(Long id);

	int insert(Yeepay record);

	Yeepay selectByPK(Long id);

	int update(Yeepay record);

	Yeepay selectOneByExample(Yeepay record);

	List<Yeepay> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Yeepay yeepay);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}