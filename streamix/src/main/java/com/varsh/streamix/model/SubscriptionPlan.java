package com.varsh.streamix.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="subcription_plan")
public class SubscriptionPlan {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "subscription_plan_seq")
	@SequenceGenerator(name="subscription_plan_seq", sequenceName = "SUBSCRIPTION_PLAN_SEQ", allocationSize = 1)
	private String id;
	private String name;
	private double price;
}
