package com.hy.gf.mapper;

import org.springframework.stereotype.Component;
import com.hy.gf.model.Address;
import com.hy.gf.model.Page;
import java.util.List;

import java.util.Map;

@Component
public interface AddressMapper {

	int delete(Long id);

	Address select(Long id);

	int insert(Address record);

	Address selectByPK(Long id);

	int update(Address record);

	Address selectOneByExample(Address record);

	List<Address> selectByExample(Map<String, Object> objectMap);

	Integer selectByExampleCount(Address address);
	Integer insertBatch(List list);
	Integer deleteBatch(List ids);
}