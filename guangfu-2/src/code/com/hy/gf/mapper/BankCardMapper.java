package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.BankCard;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface BankCardMapper {

	int delete(Long id);

	BankCard select(Long id);

	int insert(BankCard record);

	BankCard selectByPK(Long id);

	int update(BankCard record);

	BankCard selectOneByExample(BankCard record);

	List<BankCard> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(BankCard bankCard);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}