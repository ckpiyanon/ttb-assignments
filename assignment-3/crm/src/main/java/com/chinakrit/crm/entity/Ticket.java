package com.chinakrit.crm.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.chinakrit.crm.constant.RequestSource;
import com.chinakrit.crm.constant.TicketStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
    private long id;
	
	@CreationTimestamp
    private Date createdOn;
	
	@UpdateTimestamp
    private Date updatedOn;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    @Setter
	private RequestSource source;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    @Setter
	private TicketStatus status = TicketStatus.IN_PROGRESS;
	
	@Column(nullable = false)
    @Setter
	private String description;
	
	@Column(nullable = false)
    @Setter
	private String customerName;
	
	@Column(nullable = true)
    @Setter
	private String customerEmail;
	
	@Column(nullable = false)
    @Setter
	private String customerPhoneNo;
	
	@Column(nullable = false)
    @Setter
	private long staffId;

	@Column(nullable = false)
    @Setter
	private long microserviceId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staff_id")
    private Staff staff;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "microservice_id")
    private Microservice microservice;
}
