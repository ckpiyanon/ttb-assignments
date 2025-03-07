package com.chinakrit.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private long id;
	
	@Getter
	@Setter
	private String firstName;
	
	@Getter
	@Setter
	private String lastName;
	
	@Getter
	@Setter
	private Date customerDate;
	
	@Getter
	@Setter
	private Boolean isVip;
	
	@Getter
	@Setter
	private String statusCode;
	
	@CreationTimestamp
	@Getter
	private Date createdOn;
	
	@UpdateTimestamp
	@Getter
	private Date modifiedOn;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customers")
	@Getter
	private List<Sale> sales;
}
