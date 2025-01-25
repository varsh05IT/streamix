package com.varsh.streamix.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoStream {
	private Long id;
	private String title;
	private String description;
	private String director;
	private Long duration;
	private Long createdAt;
	private List<Season> seasonList;
	private StreamType type;
	private Genre genre;
}
