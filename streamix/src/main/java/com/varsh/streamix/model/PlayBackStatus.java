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
public class PlayBackStatus {
	private String id;
	private StreamType type;
	private String image;
	private Long pauseTime;
}


