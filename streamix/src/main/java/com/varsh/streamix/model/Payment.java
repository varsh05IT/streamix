package com.varsh.streamix.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
	private Long id; 
	private String paymentMethod; 
	private double amount;
	private UserDetails userDetails;
}
