package com.bolehunt.gene.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolehunt.gene.common.JsonResponse;
import com.bolehunt.gene.common.Status;
import com.bolehunt.gene.domain.BasicInfo;
import com.bolehunt.gene.domain.Education;
import com.bolehunt.gene.domain.EducationExample;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.form.EducationForm;
import com.bolehunt.gene.form.ResumeForm;
import com.bolehunt.gene.persistence.BasicInfoMapper;
import com.bolehunt.gene.persistence.EducationMapper;
import com.bolehunt.gene.service.ResumeService;
import com.bolehunt.gene.util.WebUtil;

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
	public JsonResponse validateEducationForm(EducationForm educationForm){
		if(educationForm == null){
			return WebUtil.formatJsonResponse(Status.UNKNOWN_EXCEPTION);
		}
		
		return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
	}
	
	@Override
	public JsonResponse proceedEducationForm(EducationForm educationForm, User user){
		JsonResponse jsonResponse = null;
		if("add".equals(educationForm.getAction())){
			
			jsonResponse = addEducation(educationForm, user);
		
		} else if("save".equals(educationForm.getAction())){
			
			jsonResponse = updateEducation(educationForm, user);
			
		}else if("delete".equals(educationForm.getAction())){
			
			jsonResponse = deleteEducation(educationForm, user);
		
		}
		return jsonResponse;
	}
	
	private JsonResponse addEducation(EducationForm educationForm, User user){
		
		Education education = new Education();
		education.setUserId(user.getId());
		education.setSchoolName(educationForm.getSchoolName());
		education.setDegree(educationForm.getDegree());
		education.setStartYear(educationForm.getStartYear());
		education.setEndYear(educationForm.getEndYear());
		education.setDepartment(educationForm.getDepartment());
		educationMapper.insert(education);
		
		Map<String, Object> data = new HashMap<String, Object>();
		List<Education> educationList = retrieveEducationList(user.getId());
		JsonResponse jsonResponse = WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
		data.put("educations", educationList);
		jsonResponse.setData(data);
		return jsonResponse;
	}
	
	private JsonResponse updateEducation(EducationForm educationForm, User user){
		Education education = educationMapper.selectByPrimaryKey(educationForm.getEducationId());
		
		if(education == null){
			return WebUtil.formatJsonResponse(Status.UNKNOWN_EXCEPTION);
		}else if(! education.getUserId().equals(user.getId())){
			return WebUtil.formatJsonResponse(Status.USER_FORBIDDEN_ACCESS);
		}
		
		education.setSchoolName(educationForm.getSchoolName());
		education.setDegree(educationForm.getDegree());
		education.setStartYear(educationForm.getStartYear());
		education.setEndYear(educationForm.getEndYear());
		education.setDepartment(educationForm.getDepartment());
		educationMapper.updateByPrimaryKeySelective(education);
		
		Map<String, Object> data = new HashMap<String, Object>();
		List<Education> educationList = retrieveEducationList(user.getId());
		JsonResponse jsonResponse = WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
		data.put("educations", educationList);
		jsonResponse.setData(data);
		
		return jsonResponse;
	}
	
	private JsonResponse deleteEducation(EducationForm educationForm, User user){
		Education education = educationMapper.selectByPrimaryKey(educationForm.getEducationId());
		
		if(education == null){
			return WebUtil.formatJsonResponse(Status.UNKNOWN_EXCEPTION);
		}else if(! education.getUserId().equals(user.getId())){
			return WebUtil.formatJsonResponse(Status.USER_FORBIDDEN_ACCESS);
		}
		
		educationMapper.deleteByPrimaryKey(educationForm.getEducationId());
		return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
		
	}

}
