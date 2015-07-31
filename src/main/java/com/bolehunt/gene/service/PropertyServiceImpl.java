package com.bolehunt.gene.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.bolehunt.gene.common.Label;
import com.bolehunt.gene.domain.Country;
import com.bolehunt.gene.domain.CountryExample;
import com.bolehunt.gene.domain.Degree;
import com.bolehunt.gene.domain.DegreeExample;
import com.bolehunt.gene.domain.Experience;
import com.bolehunt.gene.domain.ExperienceExample;
import com.bolehunt.gene.domain.Sex;
import com.bolehunt.gene.domain.SexExample;
import com.bolehunt.gene.persistence.CountryMapper;
import com.bolehunt.gene.persistence.DegreeMapper;
import com.bolehunt.gene.persistence.ExperienceMapper;
import com.bolehunt.gene.persistence.SexMapper;

@Service
public class PropertyServiceImpl implements PropertyService {
	
	@Autowired
	private CountryMapper countryMapper;
	
	@Autowired
	private SexMapper sexMapper;
	
	@Autowired
	private ExperienceMapper experienceMapper;
	
	@Autowired
	private DegreeMapper degreeMapper;
	
	@Autowired
	private MessageSource messageSource;
	
	public List<Label> getCountryList(){
		Locale locale = LocaleContextHolder.getLocale();
		List<Label> labelList = new ArrayList<Label>();
		labelList.add(new Label(-1,messageSource.getMessage("label.select.country", null, Locale.CHINA)));

		List<Country> countryList = countryMapper.selectByExample(new CountryExample());
		for(Country c : countryList){
			labelList.add(new Label(c.getId(), c.getCountryName()));
		}
		return labelList;
	}
	
	public List<Label> getSexList(){
		List<Label> labelList = new ArrayList<Label>();
		labelList.add(new Label(-1,messageSource.getMessage("label.select.sex", null, Locale.CHINA)));
		
		List<Sex> sexList = sexMapper.selectByExample(new SexExample());
		for(Sex s: sexList){
			labelList.add(new Label(s.getId(), s.getSexDesc()));
		}
		return labelList;
	}
	
	public List<Label> getExperienceList(){
		List<Label> labelList = new ArrayList<Label>();
		labelList.add(new Label(-1,messageSource.getMessage("label.select.experience", null, Locale.CHINA)));
		List<Experience> list = experienceMapper.selectByExample(new ExperienceExample());
		for(Experience l: list){
			labelList.add(new Label(l.getId(), l.getExpDesc()));
		}
		return labelList;
	}
	
	public List<Label> getDegreeList(){
		List<Label> labelList = new ArrayList<Label>();
		labelList.add(new Label(-1,messageSource.getMessage("label.select.degree", null, Locale.CHINA)));
		List<Degree> list = degreeMapper.selectByExample(new DegreeExample());
		for(Degree l: list){
			labelList.add(new Label(l.getId(), l.getDegreeDesc()));
		}
		return labelList;
	}
	
	

}
