package com.varsh.streamix.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="season")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Season {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "season_seq")
	@SequenceGenerator(name="season_seq", sequenceName = "SEASON_SEQ", allocationSize = 1)
	private Long id ;
	
	private String description;
	
	private int seasonNumber;
	
    private String thumbnailUrl;
		
	@OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
	private List<Episode> episodeList;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="video_id")
	private VideoStream videoStream;
	
}
