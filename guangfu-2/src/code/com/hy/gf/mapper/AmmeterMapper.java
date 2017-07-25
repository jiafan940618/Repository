package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Ammeter;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface AmmeterMapper {

	int delete(Long id);

	Ammeter select(Long id);

	int insert(Ammeter record);

	Ammeter selectByPK(Long id);

	int update(Ammeter record);

	Ammeter selectOneByExample(Ammeter record);

	List<Ammeter> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Ammeter ammeter);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
	
	Ammeter selectByCode(String code);
}