package com.aditya.mobilews.ui.model.request;

import javax.validation.constraints.NotNull;

public class UpdateUserDetailsRequestModel {
	@NotNull(message = "firstName can't be null")
	private String firstName;
	@NotNull(message = "lastName can't be null")
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
