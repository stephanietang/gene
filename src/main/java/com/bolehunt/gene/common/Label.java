package com.bolehunt.gene.common;

public class Label{
	private int labelKey;
	private String labelName;
	
	public Label(int labelKey, String labelName) {
		super();
		this.labelKey = labelKey;
		this.labelName = labelName;
	}
	public int getLabelKey() {
		return labelKey;
	}
	public void setLabelKey(int labelKey) {
		this.labelKey = labelKey;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
