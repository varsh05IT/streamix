package com.varsh.streamix.model;

import java.time.LocalTime;

public class SubscriptionPlan {
	private Long id;
	private String name;
	private double price;
	private LocalTime startDate;
	private LocalTime lastDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public LocalTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalTime startDate) {
		this.startDate = startDate;
	}
	public LocalTime getLastDate() {
		return lastDate;
	}
	public void setLastDate(LocalTime lastDate) {
		this.lastDate = lastDate;
	}

}
