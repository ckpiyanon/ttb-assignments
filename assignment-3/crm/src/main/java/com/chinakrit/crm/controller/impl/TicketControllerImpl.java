package com.chinakrit.crm.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.chinakrit.crm.controller.TicketController;
import com.chinakrit.crm.dto.request.CreateTicketRequest;
import com.chinakrit.crm.dto.request.UpdateTicketStatusRequest;
import com.chinakrit.crm.entity.Ticket;
import com.chinakrit.crm.service.TicketService;

@Controller
@RequestMapping(path = "/tickets")
public class TicketControllerImpl implements TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public Ticket getTicket(@RequestParam("id") Long id) {
        return this.ticketService.getTicketById(id);
    }

    @PostMapping
    public Ticket createTicket(@RequestBody CreateTicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setSource(request.getSource());
        ticket.setStatus(request.getStatus());
        ticket.setDescription(request.getDescription());
        ticket.setCustomerName(request.getCustomerName());
        ticket.setCustomerEmail(request.getCustomerEmail());
        ticket.setCustomerPhoneNo(request.getCustomerPhoneNo());
        ticket.setMicroserviceId(request.getMicroserviceId());
        ticket.setStaffId(request.getStaffId());

        return this.ticketService.createTicket(ticket);
    }

    @PatchMapping("/{id}")
    public Ticket updateTicketStatus(@RequestParam("id") Long id, @RequestBody UpdateTicketStatusRequest request) {
        return this.ticketService.updateTicketStatus(id, request.getStatus());
    }

}
