package com.bolehunt.gene.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public final static Map<String, String> domainMap = new HashMap<String, String>();
	
	PropertyService propertyService;
	
	@Autowired
	public AppBeans(PropertyService propertyService) {
		this.propertyService = propertyService;
		
		domainMap.put("qq.com", "http://mail.qq.com");
		domainMap.put("gmail.com", "http://mail.google.com");
		domainMap.put("sina.com", "http://mail.sina.com.cn");
		domainMap.put("163.com", "http://mail.163.com");
		domainMap.put("126.com", "http://mail.126.com");
		domainMap.put("sohu.com", "http://mail.sohu.com");
		domainMap.put("tom.com", "http://mail.tom.com");
		domainMap.put("sogou.com", "http://mail.sogou.com");
		domainMap.put("hotmail.com", "http://www.hotmail.com");
		domainMap.put("foxmail.com", "http://www.foxmail.com");
		domainMap.put("live.com", "http://login.live.com");
		domainMap.put("yahoo.com.cn", "http://mail.cn.yahoo.com");
		domainMap.put("yahoo.cn", "http://mail.cn.yahoo.com");
		
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

	public static Map<String, String> getDomainMap() {
		return domainMap;
	}
	
}
