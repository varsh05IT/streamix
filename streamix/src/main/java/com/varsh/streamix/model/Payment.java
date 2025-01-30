package com.varsh.streamix.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "payment_seq")
	@SequenceGenerator(name="payment_seq", sequenceName = "PAYMENT_SEQ", allocationSize = 1)
	private String id; 
	private String paymentMethod; 
	private double amount;
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	private UserDetails userDetails; 
}
