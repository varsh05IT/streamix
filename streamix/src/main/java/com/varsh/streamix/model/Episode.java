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
public class Episode {
	private String id;
	private String title;
	private String description;
	private int episodeNumber;
	private Long duration;
	private Long createdAt;
}
