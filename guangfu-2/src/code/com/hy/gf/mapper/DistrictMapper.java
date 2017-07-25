package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.District;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface DistrictMapper {

	int delete(Long id);

	District select(Long id);

	int insert(District record);

	District selectByPK(Long id);

	int update(District record);

	District selectOneByExample(District record);

	List<District> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(District district);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}