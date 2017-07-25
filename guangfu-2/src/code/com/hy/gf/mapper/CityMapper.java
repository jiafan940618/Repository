package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.City;
import com.hy.gf.model.Page;
import com.hy.gf.vo.CityVO;

import java.util.List;

import java.util.Map;

@Component
public interface CityMapper {
     
   /** ²âÊÔµÚ¶þ¸ö*/
	int delete(Long id);

	City select(Long id);

	int insert(City record);

	City selectByPK(Long id);

	int update(City record);

	City selectOneByExample(City record);

	List<City> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(City city);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	List<City> findCityListByProvinceId(Long provinceId);

	CityVO findProAndCity(Long id);

	String selectName(Integer city_id);

	List<City> findByProvinceId(Long provinceId);
}