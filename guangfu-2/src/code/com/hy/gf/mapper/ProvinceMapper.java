package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Province;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface ProvinceMapper {

	int delete(Long id);

	Province select(Long id);

	int insert(Province record);

	Province selectByPK(Long id);

	int update(Province record);

	Province selectOneByExample(Province record);

	List<Province> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Province province);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	List<Province> findProvinceList();

	String selectName(Integer province_id);
	
}