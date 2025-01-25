package com.varsh.streamix.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionPlan {
	private String id;
	private String name;
	private double price;
	private LocalTime startDate;
	private LocalTime lastDate;
}
