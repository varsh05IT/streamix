package com.varsh.streamix.model;

import java.util.List;

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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	public List<Season> getSeasonList() {
		return seasonList;
	}
	public void setSeasonList(List<Season> seasonList) {
		this.seasonList = seasonList;
	}
	public StreamType getType() {
		return type;
	}
	public void setType(StreamType type) {
		this.type = type;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	
}
