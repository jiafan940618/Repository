package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Income;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface IncomeMapper {

	int delete(Long id);

	Income select(Long id);

	int insert(Income record);

	Income selectByPK(Long id);

	int update(Income record);

	Income selectOneByExample(Income record);

	List<Income> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Income income);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);

	List<Income> find(Map<String, Object> map);
	int findCount(Map<String, Object> map);

	List<Income> forIncomeEcharts(Long id);
}