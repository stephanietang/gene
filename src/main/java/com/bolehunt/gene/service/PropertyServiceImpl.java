package com.bolehunt.gene.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	CountryMapper countryMapper;
	
	@Autowired
	SexMapper sexMapper;
	
	@Autowired
	ExperienceMapper experienceMapper;
	
	@Autowired
	DegreeMapper degreeMapper;
	
	public List<Label> getCountryList(){
		List<Label> labelList = new ArrayList<Label>();
		List<Country> countryList = countryMapper.selectByExample(new CountryExample());
		for(Country c : countryList){
			labelList.add(new Label(c.getId(), c.getCountryName()));
		}
		return labelList;
	}
	
	public List<Label> getSexList(){
		List<Label> labelList = new ArrayList<Label>();
		List<Sex> sexList = sexMapper.selectByExample(new SexExample());
		for(Sex s: sexList){
			labelList.add(new Label(s.getId(), s.getSexDesc()));
		}
		return labelList;
	}
	
	public List<Label> getExperienceList(){
		List<Label> labelList = new ArrayList<Label>();
		List<Experience> list = experienceMapper.selectByExample(new ExperienceExample());
		for(Experience l: list){
			labelList.add(new Label(l.getId(), l.getExpDesc()));
		}
		return labelList;
	}
	
	public List<Label> getDegreeList(){
		List<Label> labelList = new ArrayList<Label>();
		List<Degree> list = degreeMapper.selectByExample(new DegreeExample());
		for(Degree l: list){
			labelList.add(new Label(l.getId(), l.getDegreeDesc()));
		}
		return labelList;
	}
	
	

}
