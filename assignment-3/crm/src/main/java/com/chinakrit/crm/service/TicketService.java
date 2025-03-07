package com.chinakrit.crm.service;

import com.chinakrit.crm.constant.TicketStatus;
import com.chinakrit.crm.entity.Ticket;

public interface TicketService {
	Ticket getTicketById(long id);
	Ticket createTicket(Ticket ticket);
	Ticket updateTicketStatus(long id, TicketStatus status);
}
