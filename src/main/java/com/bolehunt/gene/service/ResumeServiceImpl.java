package com.bolehunt.gene.service;

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
		
		EducationExample eduEx = new EducationExample();
		eduEx.createCriteria().andUserIdEqualTo(user.getId());
		eduEx.setOrderByClause("start_year desc");
		List<Education> educationList = educationMapper.selectByExample(eduEx);
		form.setEducationList(educationList);
		
		return form;
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
		
		// TODO verify token
		
		return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
	}
	
	@Override
	public void proceedEducationForm(EducationForm educationForm, User user, JsonResponse jsonResponse){
		if("add".equals(educationForm.getAction())){
			Education education = new Education();
			education.setUserId(user.getId());
			education.setSchoolName(educationForm.getSchoolName());
			education.setDegree(educationForm.getDegree());
			education.setStartYear(educationForm.getStartYear());
			education.setEndYear(educationForm.getEndYear());
			education.setDepartment(educationForm.getDepartment());
			educationMapper.insert(education);
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("education", education);
			jsonResponse.setData(data);
			
		}else if("save".equals(educationForm.getAction())){
			Education education = new Education();
			education.setId(educationForm.getEducationId());
			education.setSchoolName(educationForm.getSchoolName());
			education.setDegree(educationForm.getDegree());
			education.setStartYear(educationForm.getStartYear());
			education.setEndYear(educationForm.getEndYear());
			education.setDepartment(educationForm.getDepartment());
			educationMapper.updateByPrimaryKeySelective(education);
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("education", education);
			jsonResponse.setData(data);
			
		}else if("delete".equals(educationForm.getAction())){
			educationMapper.deleteByPrimaryKey(educationForm.getEducationId());
		}
	}

}
