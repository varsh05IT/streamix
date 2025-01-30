package com.varsh.streamix.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="seasons")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Season {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "season_seq")
	@SequenceGenerator(name="season_seq", sequenceName = "SEASON_SEQ", allocationSize = 1)
	private String id ;
	
	private String description;
	
	@OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
	private List<Episode> episodeList;
	
	@NonNull
	private Long duration;
	
	private int seasonNumber;
	
}
