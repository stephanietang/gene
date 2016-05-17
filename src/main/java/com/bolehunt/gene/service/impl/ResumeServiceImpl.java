package com.bolehunt.gene.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.bolehunt.gene.common.ErrorStatus;
import com.bolehunt.gene.common.Label;
import com.bolehunt.gene.common.LabelEnum;
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
import com.bolehunt.gene.service.PropertyService;
import com.bolehunt.gene.service.ResumeService;

@Service
public final class ResumeServiceImpl implements ResumeService {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AvatarMapper avatarMapper;
	
	@Autowired
	private BasicInfoMapper basicInfoMapper;
	
	@Autowired
	private EducationMapper educationMapper;
	
	@Autowired
	private PropertyService propertyService;
	
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
			if(educationList != null) {
				form.setEducationList(educationList);
			}
			form.setAgeStr(this.getAgeStr(basicInfo.getBirthYear()));
		}
		
		return form;
	}
	
	private String getAgeStr(int birthYear) {
		DateTime dt = new DateTime();
		int age = dt.getYear() - birthYear;
		
		return age + getMessage("label.resume.yrOld");
	}
	
	@Override
	public BasicInfo saveBasicInfo(BasicInfo basicInfo){
		if(basicInfo.getId() != null){
			basicInfoMapper.updateByPrimaryKeySelective(basicInfo);
		}
		
		return basicInfoMapper.selectByPrimaryKey(basicInfo.getId());
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
	public void validateEducationForm(EducationForm educationForm){
		if(educationForm == null){
			throw new ApplicationException(ErrorStatus.UNKNOWN_EXCEPTION);
		}
	}
	
	@Override
	public void proceedEducationForm(EducationForm educationForm, User user){
		BasicInfo basicInfo = retrieveBasicInfo(user);
		if("save".equals(educationForm.getAction())){
			if(educationForm.getEducationId() == null) {
				addEducation(educationForm, basicInfo);
			} else {
				updateEducation(educationForm, basicInfo);
			}
			
		} else if("delete".equals(educationForm.getAction())){
			
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
	
	

	@Override
	public List<Label> getCityList(){
		List<Label> list = new ArrayList<Label>();
		list.add(getLabel(LabelEnum.CITY_SHENZHEN));
		list.add(getLabel(LabelEnum.CITY_BEIJING));
		list.add(getLabel(LabelEnum.CITY_GUANGZHOU));
		list.add(getLabel(LabelEnum.CITY_HANGZHOU));
		list.add(getLabel(LabelEnum.CITY_HONGKONG));
		list.add(getLabel(LabelEnum.CITY_TAIPEI));
		return list;
	}
	
	@Override
	public List<Label> getCountryList(){
		List<Label> list = new ArrayList<Label>();
		list.add(getLabel(LabelEnum.COUNTRY_CHINA));
		list.add(getLabel(LabelEnum.COUNTRY_US));
		return list;
	}
	
	@Override
	public List<Label> getSexList(){
		List<Label> list = new ArrayList<Label>();
		list.add(getLabel(LabelEnum.SEX_MALE));
		list.add(getLabel(LabelEnum.SEX_FEMALE));
		return list;
	}
	
	@Override
	public List<Label> getDegreeList(){
		List<Label> list = new ArrayList<Label>();
		list.add(getLabel(LabelEnum.DEGREE_BACHELOR));
		list.add(getLabel(LabelEnum.DEGREE_MASTER));
		list.add(getLabel(LabelEnum.DEGREE_PHD));
		list.add(getLabel(LabelEnum.DEGREE_ASSOCIATE));
		return list;
	}
	
	@Override
	public List<Label> getWorkExpList(){
		List<Label> list = new ArrayList<Label>();
		list.add(getLabel(LabelEnum.WORK_EXP_GRADUATE));
		list.add(getLabel(LabelEnum.WORK_EXP_ABOVE_ONE_YEAR));
		list.add(getLabel(LabelEnum.WORK_EXP_ABOVE_THREE_YEAR));
		list.add(getLabel(LabelEnum.WORK_EXP_ABOVE_FIVE_YEAR));
		return list;
	}
	
	@Override
	public List<Label> getBirthYearList() {
		List<Label> list = new ArrayList<Label>();
		int lowerYear = propertyService.getLowerBirthYear();
		DateTime dt = new DateTime();
		int currentYear = dt.getYear();
		for (int i = lowerYear ; i < currentYear; i ++) {
			list.add(new Label(i, String.valueOf(i)));
		}
		
		return list;
	}
	
	protected String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();                        
        return messageSource.getMessage(key, new Object[0], locale);
    }
	
	protected Label getLabel(LabelEnum labelEnum) {
		Label label = new Label(labelEnum.getValue(), getMessage(labelEnum.getKey()));
		return label;
	}

}
