package com.chinakrit.crm.repository;

import org.springframework.data.repository.CrudRepository;

import com.chinakrit.crm.entity.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
