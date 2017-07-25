package com.hy.gf.biz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.gf.mapper.MeterErrcodeMapper;
import com.hy.gf.model.MeterErrcode;
import com.hy.gf.model.Page;
import com.hy.gf.util.ObjToMap;

@Service
public class MeterErrcodeBiz {

	@Resource
	MeterErrcodeMapper meterErrcodeMapper;

	public int delete(Long id) {
		return meterErrcodeMapper.delete(id);
	}

	public int insert(MeterErrcode record) {
		return meterErrcodeMapper.insert(record);
	}

	public MeterErrcode select(Long id) {
		return meterErrcodeMapper.select(id);
	}

	public int update(MeterErrcode record) {
		return meterErrcodeMapper.update(record);
	}

	public MeterErrcode selectOneByExample(MeterErrcode record) {
		return meterErrcodeMapper.selectOneByExample(record);
	}

	public Page<MeterErrcode> selectByExample(Page<MeterErrcode> page) {
		Object example = page.getExample();
		MeterErrcode meterErrcode = null;
		if (example != null) {
			meterErrcode = (MeterErrcode) example;
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(page);
		Map<String, Object> objectMap2 = ObjToMap.getObjectMap(meterErrcode);
		objectMap.putAll(objectMap2);
		List<MeterErrcode> selectByExample = meterErrcodeMapper.selectByExample(objectMap);
		page.setList(selectByExample);
		page.setExample(null);
		Integer selectByExampleCount = meterErrcodeMapper.selectByExampleCount(meterErrcode);
		page.setTotal(selectByExampleCount);
		return page;
	}

	public List insertBatch(List list) {
		meterErrcodeMapper.insertBatch(list);
		return list;
	}

	public int deleteBatch(List ids) {
		return meterErrcodeMapper.deleteBatch(ids);
	}
	
	public MeterErrcode selectByErrcode(String errcode) {
		return meterErrcodeMapper.selectByErrcode(errcode);
	}
}