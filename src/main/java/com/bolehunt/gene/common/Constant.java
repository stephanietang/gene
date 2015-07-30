package com.bolehunt.gene.common;

import java.text.SimpleDateFormat;

public class Constant {
	
	public static final SimpleDateFormat YEAR_MONTH_DATE_FORMAT = new SimpleDateFormat("yyyy-MM");
	
	public enum VerifyTokenType{
		
		VERIFICATION_EMAIL (1),
		REGISTRATION_EMAIL(2),
		LOST_PASSWORD_EMAIL (3);
		
		private int value;
		
		private VerifyTokenType(int value){
			this.value = value;
		}

		public int getValue() {
			return value;
		}
		
	}
	
	public enum UserEnableType{
		
		NOT_ENABLED (0),
		ENABLED (1);
		
		private int value;
		
		private UserEnableType(int value){
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public enum TokenVerifyType{
		
		NOT_VERIFIED(0),
		VERIFIED(1);
		
		private int value;
		
		private TokenVerifyType(int value){
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}

	
}
