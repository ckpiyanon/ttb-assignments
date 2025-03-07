package com.chinakrit.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sales")
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private long id;
	
	@Getter
	@Setter
	private long customerId;
	
	@Getter
	@Setter
	private Double saleAmount;
	
	@Getter
	@Setter
	private Date saleDate;
	
	@CreationTimestamp
	@Getter
	private Date createdOn;
	
	@UpdateTimestamp
	@Getter
	private Date modifiedOn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	@Getter
	private Customer customer;
}
