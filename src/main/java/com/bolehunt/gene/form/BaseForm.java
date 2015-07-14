package com.bolehunt.gene.form;

import com.bolehunt.gene.domain.User;

public class BaseForm {
	
	private User user;
	private boolean remembered = false;
	private boolean authenticated = false;
	private boolean logined = false;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean isRemembered() {
		return remembered;
	}

	public void setRemembered(boolean remembered) {
		this.remembered = remembered;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	public boolean isLogined(){
		if(remembered || authenticated){
			logined = true;
		}else{
			logined = false;
		}
		return logined;
	}
	
}
