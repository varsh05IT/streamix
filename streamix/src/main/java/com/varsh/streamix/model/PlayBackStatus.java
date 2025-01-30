package com.varsh.streamix.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="playback_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayBackStatus {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "play_back_seq")
	@SequenceGenerator(name="play_back_seq", sequenceName = "PLAY_BACK_SEQ", allocationSize = 1)
	private String id;
	
	@Enumerated(EnumType.STRING)
	private StreamType type;
	
	private String image;
	private Long pauseTime;
}


