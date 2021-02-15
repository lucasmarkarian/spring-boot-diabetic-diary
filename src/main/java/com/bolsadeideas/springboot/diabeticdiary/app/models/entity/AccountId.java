package com.bolsadeideas.springboot.diabeticdiary.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AccountId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username_match;
	private Date date;
	
	public AccountId() {
	}

	public AccountId(String usernameMatch, Date date) {
		this.username_match = usernameMatch;
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.username_match, this.date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		AccountId accountId = (AccountId) obj;
		return this.username_match.equals(accountId.username_match) && 
				this.date.equals(accountId.date);
	}
	
	

}
