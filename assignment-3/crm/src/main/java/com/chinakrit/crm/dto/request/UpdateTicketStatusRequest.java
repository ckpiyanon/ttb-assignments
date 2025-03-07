package com.chinakrit.crm.dto.request;

import com.chinakrit.crm.constant.TicketStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateTicketStatusRequest {
	private TicketStatus status;
}
