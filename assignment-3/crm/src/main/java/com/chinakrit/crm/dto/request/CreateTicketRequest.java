package com.chinakrit.crm.dto.request;

import com.chinakrit.crm.constant.RequestSource;
import com.chinakrit.crm.constant.TicketStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateTicketRequest {
	private RequestSource source;

	private TicketStatus status;
	
	private String description;
	
	private String customerName;
	
	private String customerEmail;
	
	private String customerPhoneNo;
	
	private long staffId;
	
	private long microserviceId;
}
