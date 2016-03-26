package com.bolehunt.gene.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolehunt.gene.common.Status;
import com.bolehunt.gene.domain.BasicInfo;
import com.bolehunt.gene.domain.Education;
import com.bolehunt.gene.domain.EducationExample;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.exception.ApplicationException;
import com.bolehunt.gene.form.EducationForm;
import com.bolehunt.gene.form.ResumeForm;
import com.bolehunt.gene.persistence.BasicInfoMapper;
import com.bolehunt.gene.persistence.EducationMapper;
import com.bolehunt.gene.service.ResumeService;

@Service
public class ResumeServiceImpl implements ResumeService {
	
	@Autowired
	private BasicInfoMapper basicInfoMapper;
	
	@Autowired
	private EducationMapper educationMapper;
	
	@Override
	public ResumeForm retrieveResume(User user){
		ResumeForm form = new ResumeForm();
		BasicInfo basicInfo = basicInfoMapper.selectByPrimaryKey(user.getId());
		form.setBasicInfo(basicInfo);
		
		List<Education> educationList = retrieveEducationList(user.getId());
		form.setEducationList(educationList);
		
		return form;
	}
	
	@Override
	public List<Education> retrieveEducationList(int userId) {
		EducationExample eduEx = new EducationExample();
		eduEx.createCriteria().andUserIdEqualTo(userId);
		eduEx.setOrderByClause("start_year desc, end_year desc");
		return educationMapper.selectByExample(eduEx);
	}
	
	@Override
	public void saveResume(ResumeForm resumeForm){
		BasicInfo basicInfo = resumeForm.getBasicInfo();
		basicInfoMapper.updateByPrimaryKey(basicInfo);
		
	}
	
	@Override
	public void validateEducationForm(EducationForm educationForm){
		if(educationForm == null){
			throw new ApplicationException(Status.UNKNOWN_EXCEPTION);
		}
	}
	
	@Override
	public void proceedEducationForm(EducationForm educationForm, User user){
		if("add".equals(educationForm.getAction())){
			
			addEducation(educationForm, user);
		
		} else if("save".equals(educationForm.getAction())){
			
			updateEducation(educationForm, user);
			
		}else if("delete".equals(educationForm.getAction())){
			
			deleteEducation(educationForm, user);
		
		}
	}
	
	private void addEducation(EducationForm educationForm, User user){
		
		Education education = new Education();
		education.setUserId(user.getId());
		education.setSchoolName(educationForm.getSchoolName());
		education.setDegree(educationForm.getDegree());
		education.setStartYear(educationForm.getStartYear());
		education.setEndYear(educationForm.getEndYear());
		education.setDepartment(educationForm.getDepartment());
		educationMapper.insert(education);
		
	}
	
	private void updateEducation(EducationForm educationForm, User user){
		Education education = educationMapper.selectByPrimaryKey(educationForm.getEducationId());
		
		if(education == null){
			throw new ApplicationException(Status.UNKNOWN_EXCEPTION);
		}else if(! education.getUserId().equals(user.getId())){
			throw new ApplicationException(Status.USER_FORBIDDEN_ACCESS);
		}
		
		education.setSchoolName(educationForm.getSchoolName());
		education.setDegree(educationForm.getDegree());
		education.setStartYear(educationForm.getStartYear());
		education.setEndYear(educationForm.getEndYear());
		education.setDepartment(educationForm.getDepartment());
		educationMapper.updateByPrimaryKeySelective(education);
		
	}
	
	private void deleteEducation(EducationForm educationForm, User user){
		Education education = educationMapper.selectByPrimaryKey(educationForm.getEducationId());
		
		if(education == null){
			throw new ApplicationException(Status.UNKNOWN_EXCEPTION);
		}else if(! education.getUserId().equals(user.getId())){
			throw new ApplicationException(Status.USER_FORBIDDEN_ACCESS);
		}
		
		educationMapper.deleteByPrimaryKey(educationForm.getEducationId());
		
	}

}
