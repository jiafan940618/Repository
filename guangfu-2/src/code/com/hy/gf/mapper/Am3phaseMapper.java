package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Am3phase;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface Am3phaseMapper {

	int delete(Long id);

	Am3phase select(Long id);

	int insert(Am3phase record);

	Am3phase selectByPK(Long id);

	int update(Am3phase record);

	Am3phase selectOneByExample(Am3phase record);

	List<Am3phase> selectByExample(Map<String, Object> objectMap);

	List<Am3phase> selectByExampleKWH(Map<String, Object> objectMap);
	
	List<Am3phase> selectKw(Map<String, Object> objectMap);
	
	Integer selectByExampleCount(Am3phase am3phase);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}