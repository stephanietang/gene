package com.bolehunt.gene.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.bolehunt.gene.common.Label;
import com.bolehunt.gene.domain.SystemParam;
import com.bolehunt.gene.domain.SystemParamExample;
import com.bolehunt.gene.persistence.SystemParamMapper;

@Service
public class PropertyServiceImpl implements PropertyService {
	
	@Autowired
	private SystemParamMapper systemParamMapper;
	
	@Autowired
	private MessageSource messageSource;
	
	public List<Label> getCountryList(){
		List<Label> labelList = new ArrayList<Label>();

		SystemParamExample ex = new SystemParamExample();
		ex.createCriteria().andEnabledEqualTo(1).andParamGroupEqualTo("COUNTRY");
		List<SystemParam> systemParamList = systemParamMapper.selectByExample(ex);
		for(SystemParam s : systemParamList){
			labelList.add(new Label(s.getParamValue(), s.getParamDescChi()));
		}
		return labelList;
	}
	
	public List<Label> getSexList(){
		List<Label> labelList = new ArrayList<Label>();
		
		SystemParamExample ex = new SystemParamExample();
		ex.createCriteria().andEnabledEqualTo(1).andParamGroupEqualTo("SEX");
		List<SystemParam> systemParamList = systemParamMapper.selectByExample(ex);
		for(SystemParam s : systemParamList){
			labelList.add(new Label(s.getParamValue(), s.getParamDescChi()));
		}
		return labelList;
	}
	
	public List<Label> getExperienceList(){
		List<Label> labelList = new ArrayList<Label>();
		SystemParamExample ex = new SystemParamExample();
		ex.createCriteria().andEnabledEqualTo(1).andParamGroupEqualTo("EXPERIENCE");
		List<SystemParam> systemParamList = systemParamMapper.selectByExample(ex);
		for(SystemParam s : systemParamList){
			labelList.add(new Label(s.getParamValue(), s.getParamDescChi()));
		}
		return labelList;
	}
	
	public List<Label> getDegreeList(){
		List<Label> labelList = new ArrayList<Label>();
		SystemParamExample ex = new SystemParamExample();
		ex.createCriteria().andEnabledEqualTo(1).andParamGroupEqualTo("DEGREE");
		List<SystemParam> systemParamList = systemParamMapper.selectByExample(ex);
		for(SystemParam s : systemParamList){
			labelList.add(new Label(s.getParamValue(), s.getParamDescChi()));
		}
		return labelList;
	}
	

}
