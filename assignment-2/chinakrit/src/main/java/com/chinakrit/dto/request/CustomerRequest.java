package com.chinakrit.dto.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class CustomerRequest {
	@Getter
	@Setter
	private String firstName;
	
	@Getter
	@Setter
	private String lastName;
	
	@Getter
	@Setter
	private Date customerDate;
	
	@Getter
	@Setter
	private Boolean isVip;
	
	@Getter
	@Setter
	private String statusCode;
}
