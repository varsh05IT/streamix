package com.varsh.streamix.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="episodes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "review_seq")
	@SequenceGenerator(name="review_seq", sequenceName = "REVIEW_SEQ", allocationSize = 1)
	private String id; 
	private String content; 
	private int rating;
}
