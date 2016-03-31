package com.bolehunt.gene.common;

public enum LabelEnum {
	
	COUNTRY_CHINA("country.china", 1),
	COUNTRY_US("country.us", 2),
	
	DEGREE_BACHELOR("degree.bachelor", 1),
	DEGREE_MASTER("degree.master", 2),
	DEGREE_PHD("degree.phd", 3),
	DEGREE_ASSOCIATE("degree.associate", 4),
	
	WORK_EXP_GRADUATE("work.exp.graduate", 1),
	WORK_EXP_ABOVE_ONE_YEAR("work.exp.above.one.year", 2),
	WORK_EXP_ABOVE_THREE_YEAR("work.exp.above.three.year", 3),
	WORK_EXP_ABOVE_FIVE_YEAR("work.exp.above.five.year", 4),
	
	SEX_MALE("sex.male", 1),
	SEX_FEMALE("sex.female", 2);
	
	private LabelEnum (String key, int value) {
		this.key = key;
		this.value = value;
	}
	
	private String key;
	private int value;

	public String getKey() {
		return key;
	}
	
	public int getValue() {
		return value;
	}
	
}
