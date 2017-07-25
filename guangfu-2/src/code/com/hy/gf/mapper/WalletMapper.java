package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Wallet;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface WalletMapper {

	int delete(Long id);

	Wallet select(Long id);

	int insert(Wallet record);

	Wallet selectByPK(Long id);

	int update(Wallet record);

	Wallet selectOneByExample(Wallet record);

	List<Wallet> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Wallet wallet);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}