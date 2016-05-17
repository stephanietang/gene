package com.bolehunt.gene.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolehunt.gene.domain.SystemParam;
import com.bolehunt.gene.domain.SystemParamExample;
import com.bolehunt.gene.persistence.SystemParamMapper;
import com.bolehunt.gene.service.PropertyService;

@Service
public final class PropertyServiceImpl implements PropertyService {
	
	@Autowired
	private SystemParamMapper systemParamMapper;
	
	public int getLowerBirthYear(){

		SystemParamExample ex = new SystemParamExample();
		ex.createCriteria().andEnabledEqualTo(1).andParamKeyEqualTo("LOWER_BIRTH_YEAR");
		List<SystemParam> systemParamList = systemParamMapper.selectByExample(ex);
		if(systemParamList != null && systemParamList.size() > 0) {
			return systemParamList.get(0).getParamValue();
		}
		
		return 0;
	}
	

}
