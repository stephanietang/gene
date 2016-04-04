package com.bolehunt.gene.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolehunt.gene.common.ErrorStatus;
import com.bolehunt.gene.domain.Avatar;
import com.bolehunt.gene.domain.AvatarExample;
import com.bolehunt.gene.domain.BasicInfo;
import com.bolehunt.gene.domain.BasicInfoExample;
import com.bolehunt.gene.domain.Education;
import com.bolehunt.gene.domain.EducationExample;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.exception.ApplicationException;
import com.bolehunt.gene.form.EducationForm;
import com.bolehunt.gene.form.ResumeForm;
import com.bolehunt.gene.persistence.AvatarMapper;
import com.bolehunt.gene.persistence.BasicInfoMapper;
import com.bolehunt.gene.persistence.EducationMapper;
import com.bolehunt.gene.service.ResumeService;

@Service
public class ResumeServiceImpl implements ResumeService {
	
	@Autowired
	private AvatarMapper avatarMapper;
	
	@Autowired
	private BasicInfoMapper basicInfoMapper;
	
	@Autowired
	private EducationMapper educationMapper;
	
	@Override
	public ResumeForm retrieveResume(User user){
		ResumeForm form = new ResumeForm();
		form.setUserId(user.getId());
		
		Avatar avatar = retrieveAvatar(user);
		form.setAvatar(avatar);
		
		BasicInfo basicInfo = retrieveBasicInfo(user);
		
		if(basicInfo != null){
			form.setBasicInfo(basicInfo);
			List<Education> educationList = retrieveEducationList(basicInfo.getId());
			form.setEducationList(educationList);
		}
		
		return form;
	}
	
	@Override
	public BasicInfo retrieveBasicInfo(User user) {
		
		BasicInfo basicInfo = null;
		BasicInfoExample ex = new BasicInfoExample();
		ex.createCriteria().andUserIdEqualTo(user.getId());
		List<BasicInfo> basicInfoList = basicInfoMapper.selectByExample(ex);
		
		if(basicInfoList != null && basicInfoList.size() > 0 ){
			basicInfo = basicInfoList.get(0);
		}
		
		return basicInfo;
	}
	
	@Override
	public Avatar retrieveAvatar(User user) {
		Avatar avatar = null;
		AvatarExample ex = new AvatarExample();
		ex.createCriteria().andUserIdEqualTo(user.getId());
		List<Avatar> list = avatarMapper.selectByExample(ex);
		
		if(list != null && list.size() > 0) {
			avatar = list.get(0);
		}
		
		return avatar;
	}
	
	@Override
	public List<Education> retrieveEducationList(int basicInfoId) {
		
		EducationExample eduEx = new EducationExample();
		eduEx.createCriteria().andBasicInfoIdEqualTo(basicInfoId);
		eduEx.setOrderByClause("start_year desc, end_year desc");
		return educationMapper.selectByExample(eduEx);
	}
	
	@Override
	public void saveResume(ResumeForm resumeForm){
		BasicInfo basicInfo = resumeForm.getBasicInfo();
		if(basicInfo.getId() != null){
			basicInfoMapper.updateByPrimaryKeySelective(basicInfo);
		} else {
			basicInfo.setUserId(resumeForm.getUserId());
			basicInfoMapper.insertSelective(basicInfo);
		}
	}
	
	@Override
	public void validateEducationForm(EducationForm educationForm){
		if(educationForm == null){
			throw new ApplicationException(ErrorStatus.UNKNOWN_EXCEPTION);
		}
	}
	
	@Override
	public void proceedEducationForm(EducationForm educationForm, User user){
		BasicInfo basicInfo = retrieveBasicInfo(user);
		if("add".equals(educationForm.getAction())){
			
			addEducation(educationForm, basicInfo);
		
		} else if("save".equals(educationForm.getAction())){
			
			updateEducation(educationForm, basicInfo);
			
		}else if("delete".equals(educationForm.getAction())){
			
			deleteEducation(educationForm, basicInfo);
		
		}
	}
	
	private void addEducation(EducationForm educationForm, BasicInfo basicInfo){
		
		Education education = new Education();
		education.setBasicInfoId(basicInfo.getId());
		education.setSchoolName(educationForm.getSchoolName());
		education.setDegree(educationForm.getDegree());
		education.setStartYear(educationForm.getStartYear());
		education.setEndYear(educationForm.getEndYear());
		education.setDepartment(educationForm.getDepartment());
		educationMapper.insert(education);
		
	}
	
	private void updateEducation(EducationForm educationForm, BasicInfo basicInfo){
		Education education = educationMapper.selectByPrimaryKey(educationForm.getEducationId());
		
		if(education == null){
			throw new ApplicationException(ErrorStatus.UNKNOWN_EXCEPTION);
		}else if(! education.getBasicInfoId().equals(basicInfo.getId())){
			throw new ApplicationException(ErrorStatus.USER_FORBIDDEN_ACCESS);
		}
		
		education.setSchoolName(educationForm.getSchoolName());
		education.setDegree(educationForm.getDegree());
		education.setStartYear(educationForm.getStartYear());
		education.setEndYear(educationForm.getEndYear());
		education.setDepartment(educationForm.getDepartment());
		educationMapper.updateByPrimaryKeySelective(education);
		
	}
	
	private void deleteEducation(EducationForm educationForm, BasicInfo basicInfo){
		Education education = educationMapper.selectByPrimaryKey(educationForm.getEducationId());
		
		if(education == null){
			throw new ApplicationException(ErrorStatus.UNKNOWN_EXCEPTION);
		}else if(! education.getBasicInfoId().equals(basicInfo.getId())){
			throw new ApplicationException(ErrorStatus.USER_FORBIDDEN_ACCESS);
		}
		
		educationMapper.deleteByPrimaryKey(educationForm.getEducationId());
		
	}

}
