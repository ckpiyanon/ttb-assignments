package com.chinakrit.crm.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "staffs")
public class Staff {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
    private long id;
	
	@Column(nullable = false)
    @Setter
	private String firstName;
	
	@Column(nullable = false)
    @Setter
	private String lastName;
	
	@Column(nullable = false)
    @Setter
	private long branchId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id")
    private Branch branch;
	
	@OneToMany(fetch = FetchType.LAZY)
    private List<Ticket> tickets;
	
}
