package com.bolehunt.gene.common;


public class Constant {
	
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
