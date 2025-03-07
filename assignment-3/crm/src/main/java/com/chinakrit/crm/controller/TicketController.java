package com.chinakrit.crm.controller;

import com.chinakrit.crm.dto.request.CreateTicketRequest;
import com.chinakrit.crm.dto.request.UpdateTicketStatusRequest;
import com.chinakrit.crm.entity.Ticket;

public interface TicketController {
	Ticket createTicket(CreateTicketRequest request);
	Ticket updateTicketStatus(Long id, UpdateTicketStatusRequest request);
}
