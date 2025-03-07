package com.chinakrit.crm.service;

import com.chinakrit.crm.constant.TicketStatus;
import com.chinakrit.crm.entity.Microservice;
import com.chinakrit.crm.entity.Staff;
import com.chinakrit.crm.entity.Ticket;
import com.chinakrit.crm.exception.HttpException;
import com.chinakrit.crm.repository.MicroserviceRepository;
import com.chinakrit.crm.repository.StaffRepository;
import com.chinakrit.crm.repository.TicketRepository;
import com.chinakrit.crm.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Closeable;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private HttpService httpService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private MicroserviceRepository microserviceRepository;

    @Spy
    @InjectMocks
    private TicketServiceImpl ticketService;

    @Captor
    private ArgumentCaptor<Ticket> ticketCaptor;

    private final Random random = new Random();

    @Test
    void getTicketByIdShouldGetTicketById() {
        long id = random.nextLong();
        Ticket mockTicket = new Ticket();
        mockTicket.setId(random.nextLong());
        doReturn(Optional.of(mockTicket))
                .when(ticketRepository)
                .findById(anyLong());

        Ticket returnedTicket = ticketService.getTicketById(id);

        assertEquals(mockTicket, returnedTicket);
        verify(ticketRepository).findById(id);
    }

    @Test
    void getTicketByIdShouldThrowExceptionWhenTicketIsNotFound() {
        doReturn(Optional.empty())
                .when(ticketRepository)
                .findById(anyLong());

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> ticketService.getTicketById(random.nextLong())
        );

        assertEquals("Ticket not found", e.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
    }

    @Test
    void createTicketShouldCreateTicket() throws HttpException {
        Staff staff = new Staff();
        staff.setId(random.nextLong());
        Microservice service = new Microservice();
        service.setId(random.nextLong());
        service.setRequestUrl("mock request url");
        Ticket mockTicket = new Ticket();
        mockTicket.setId(random.nextLong());

        doReturn(Optional.of(staff))
                .when(staffRepository)
                .findById(anyLong());
        doReturn(Optional.of(service))
                .when(microserviceRepository)
                .findById(anyLong());
        doReturn(mockTicket)
                .when(ticketRepository)
                .save(any(Ticket.class));
        doReturn((Closeable) () -> {})
                .when(httpService)
                .post(anyString(), any(Ticket.class));

        Ticket ticketToCreate = new Ticket();
        ticketToCreate.setId(random.nextLong());
        ticketToCreate.setStaffId(random.nextLong());
        ticketToCreate.setMicroserviceId(random.nextLong());
        Ticket returnedTicket = ticketService.createTicket(ticketToCreate);

        verify(staffRepository).findById(ticketToCreate.getStaffId());
        verify(microserviceRepository).findById(ticketToCreate.getMicroserviceId());
        verify(httpService).post(service.getRequestUrl(), mockTicket);
        assertEquals(mockTicket, returnedTicket);
    }

    @Test
    void createTicketShouldThrowExceptionWhenThereIsErrorCallingService() throws HttpException {
        Staff staff = new Staff();
        staff.setId(random.nextLong());
        Microservice service = new Microservice();
        service.setId(random.nextLong());
        service.setRequestUrl("mock request url");
        Ticket mockTicket = new Ticket();
        mockTicket.setId(random.nextLong());

        doReturn(Optional.of(staff))
                .when(staffRepository)
                .findById(anyLong());
        doReturn(Optional.of(service))
                .when(microserviceRepository)
                .findById(anyLong());
        doReturn(mockTicket)
                .when(ticketRepository)
                .save(any(Ticket.class));
        doThrow(new HttpException("error"))
                .when(httpService)
                .post(anyString(), any(Ticket.class));

        Ticket ticketToCreate = new Ticket();
        ticketToCreate.setId(random.nextLong());
        ticketToCreate.setStaffId(random.nextLong());
        ticketToCreate.setMicroserviceId(random.nextLong());
        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> ticketService.createTicket(ticketToCreate)
        );

        verify(httpService).post(service.getRequestUrl(), mockTicket);
        assertEquals("Unable to call microservice", e.getMessage());
        assertEquals(HttpStatus.FAILED_DEPENDENCY, e.getStatusCode());
    }

    @Test
    void createTicketShouldThrowErrorWhenServiceIsNotFound() throws HttpException {
        Staff staff = new Staff();
        staff.setId(random.nextLong());

        doReturn(Optional.of(staff))
                .when(staffRepository)
                .findById(anyLong());
        doReturn(Optional.empty())
                .when(microserviceRepository)
                .findById(anyLong());


        Ticket ticketToCreate = new Ticket();
        ticketToCreate.setId(random.nextLong());
        ticketToCreate.setStaffId(random.nextLong());
        ticketToCreate.setMicroserviceId(random.nextLong());
        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> ticketService.createTicket(ticketToCreate)
        );

        verify(ticketRepository, never()).findById(any());
        verify(httpService, never()).post(anyString(), any(Ticket.class));
        assertEquals("Microservice not found", e.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
    }

    @Test
    void createTicketShouldThrowErrorWhenStaffIsNotFound() throws HttpException {
        doReturn(Optional.empty())
                .when(staffRepository)
                .findById(anyLong());

        Ticket ticketToCreate = new Ticket();
        ticketToCreate.setId(random.nextLong());
        ticketToCreate.setStaffId(random.nextLong());
        ticketToCreate.setMicroserviceId(random.nextLong());
        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> ticketService.createTicket(ticketToCreate)
        );

        verify(microserviceRepository, never()).findById(anyLong());
        verify(ticketRepository, never()).findById(anyLong());
        verify(httpService, never()).post(anyString(), any(Ticket.class));
        assertEquals("Staff not found", e.getMessage());
    }

    @Test
    void updateTicketStatusShouldUpdateTicketStatus() {
        long id = random.nextLong();
        Ticket mockReturnedTicket = new Ticket();
        mockReturnedTicket.setId(random.nextLong());
        Ticket mockUpdatedTicket = new Ticket();
        mockUpdatedTicket.setId(random.nextLong());

        doReturn(mockReturnedTicket)
                .when(ticketService)
                .getTicketById(anyLong());
        doReturn(mockUpdatedTicket)
                .when(ticketRepository)
                .save(any(Ticket.class));

        Ticket returnedTicket = ticketService.updateTicketStatus(id, TicketStatus.COMPLETED);

        verify(ticketService).getTicketById(id);
        verify(ticketRepository).save(ticketCaptor.capture());
        assertEquals(TicketStatus.COMPLETED, ticketCaptor.getValue().getStatus());
        assertEquals(mockUpdatedTicket, returnedTicket);
    }

    @Test
    void updateTicketStatusShouldThrowErrorWhenTicketIsNotFound() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"))
                .when(ticketService)
                .getTicketById(anyLong());

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> ticketService.updateTicketStatus(random.nextLong(), TicketStatus.COMPLETED)
        );

        verify(ticketRepository, never()).save(any(Ticket.class));
        assertEquals("Ticket not found", e.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
    }
}
