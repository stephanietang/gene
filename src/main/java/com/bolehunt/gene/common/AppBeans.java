package com.bolehunt.gene.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolehunt.gene.service.PropertyService;

@Component
public class AppBeans implements InitializingBean {
	
	public static List<Label> countryList = new ArrayList<Label>();
	public static List<Label> sexList = new ArrayList<Label>();
	public static List<Label> experienceList = new ArrayList<Label>();
	public static List<Label> degreeList = new ArrayList<Label>();
	
	PropertyService propertyService;
	
	@Autowired
	public AppBeans(PropertyService propertyService) {
		this.propertyService = propertyService;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		countryList = propertyService.getCountryList();
		sexList = propertyService.getSexList();
		experienceList = propertyService.getExperienceList();
		degreeList = propertyService.getDegreeList();
	}

	public static List<Label> getCountryList() {
		return countryList;
	}
	
	public static List<Label> getSexList() {
		return sexList;
	}
	
	public static List<Label> getExperienceList() {
		return experienceList;
	}
	
	public static List<Label> getDegreeList() {
		return degreeList;
	}
	
}
