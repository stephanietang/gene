package com.bolehunt.gene.service;

import java.util.List;

import com.bolehunt.gene.common.Label;

public interface PropertyService {
	
	public List<Label> getCountryList();
	public List<Label> getSexList();
	public List<Label> getExperienceList();
	public List<Label> getDegreeList();
 
}
