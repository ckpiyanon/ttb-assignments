package com.chinakrit.crm.controller;

import com.chinakrit.crm.constant.TicketStatus;
import com.chinakrit.crm.controller.impl.TicketControllerImpl;
import com.chinakrit.crm.dto.request.CreateTicketRequest;
import com.chinakrit.crm.dto.request.UpdateTicketStatusRequest;
import com.chinakrit.crm.entity.Ticket;
import com.chinakrit.crm.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {
    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketControllerImpl ticketController;

    @Captor
    private ArgumentCaptor<Ticket> ticketCaptor;

    private final Random random = new Random();

    @Test
    public void shouldGetTicket() {
        Ticket mockTicket = new Ticket();
        mockTicket.setId(random.nextLong());
        doReturn(mockTicket)
                .when(ticketService)
                .getTicketById(anyLong());

        long ticketId = random.nextLong();
        Ticket returnedTicket = ticketController.getTicket(ticketId);

        verify(ticketService).getTicketById(ticketId);
        assertEquals(mockTicket, returnedTicket);
    }

    @Test
    public void shouldCreateTicket() {
        Ticket mockTicket = new Ticket();
        mockTicket.setId(random.nextLong());
        doReturn(mockTicket)
                .when(ticketService)
                .createTicket(any(Ticket.class));

        CreateTicketRequest request = new CreateTicketRequest();
        request.setDescription("request description");
        Ticket returnedTicket = ticketController.createTicket(request);

        verify(ticketService).createTicket(ticketCaptor.capture());
        assertEquals(mockTicket, returnedTicket);
        assertEquals(request.getDescription(), ticketCaptor.getValue().getDescription());
    }

    @Test
    public void shouldUpdateTicketStatus() {
        Ticket mockTicket = new Ticket();
        mockTicket.setDescription("mocked description");
        doReturn(mockTicket)
                .when(ticketService)
                .updateTicketStatus(anyLong(), any(TicketStatus.class));

        long ticketId = random.nextLong();
        UpdateTicketStatusRequest request = new UpdateTicketStatusRequest();
        request.setStatus(TicketStatus.IN_PROGRESS);
        Ticket returnedTicket = ticketController.updateTicketStatus(ticketId, request);

        verify(ticketService).updateTicketStatus(ticketId, TicketStatus.IN_PROGRESS);
        assertEquals(mockTicket, returnedTicket);
    }
}
