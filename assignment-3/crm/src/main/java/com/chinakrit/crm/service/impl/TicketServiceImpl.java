package com.chinakrit.crm.service.impl;

import com.chinakrit.crm.constant.TicketStatus;
import com.chinakrit.crm.entity.Microservice;
import com.chinakrit.crm.entity.Ticket;
import com.chinakrit.crm.exception.HttpException;
import com.chinakrit.crm.repository.MicroserviceRepository;
import com.chinakrit.crm.repository.StaffRepository;
import com.chinakrit.crm.repository.TicketRepository;
import com.chinakrit.crm.service.HttpService;
import com.chinakrit.crm.service.TicketService;
import jakarta.transaction.Transactional;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MicroserviceRepository microserviceRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private HttpService httpService;

    public Ticket getTicketById(long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
    }

    @Transactional
    public Ticket createTicket(Ticket ticket) {
        staffRepository.findById(ticket.getStaffId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found"));
        Microservice service = microserviceRepository.findById(ticket.getMicroserviceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Microservice not found"));
        Ticket createdTicket = ticketRepository.save(ticket);
        try {
            httpService.post(service.getRequestUrl(), createdTicket).close();
        } catch (HttpException e) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Unable to call microservice");
        }
        return createdTicket;
    }

    public Ticket updateTicketStatus(long id, TicketStatus status) {
        Ticket ticket = getTicketById(id);
        ticket.setStatus(status);
        return ticketRepository.save(ticket);
    }
}
