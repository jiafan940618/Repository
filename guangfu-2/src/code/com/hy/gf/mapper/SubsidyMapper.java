package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Subsidy;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface SubsidyMapper {

	int delete(Long id);

	Subsidy select(Long id);

	int insert(Subsidy record);

	Subsidy selectByPK(Long id);

	int update(Subsidy record);

	Subsidy selectOneByExample(Subsidy record);

	List<Subsidy> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Subsidy subsidy);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	List<Long> selectProvince();

	List<Long> selectCityByProvinceId(Long province_id);
}