package com.bolsadeideas.springboot.diabeticdiary.app.models.entity;

import org.hibernate.validator.constraints.Length;


public class PasswordChange {
	
	private String oldPassword;
	
	@Length(min = 8)
	private String newPassword;
	
	@Length(min = 8)
	private String repeatedNewPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String password) {
		this.oldPassword = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatedNewPassword() {
		return repeatedNewPassword;
	}

	public void setRepeatedNewPassword(String repeatedNewPassword) {
		this.repeatedNewPassword = repeatedNewPassword;
	}

}
