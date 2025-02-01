package com.varsh.streamix.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name="play_back_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class PlayBackStatus {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "play_back_seq")
	@SequenceGenerator(name="play_back_seq", sequenceName = "PLAY_BACK_SEQ", allocationSize = 1)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private StreamType type;
	
	private String image;
	private Long pauseTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserDetails user;
	
	
}


