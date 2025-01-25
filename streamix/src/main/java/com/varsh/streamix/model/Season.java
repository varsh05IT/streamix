package com.varsh.streamix.model;

import java.util.List;

public class Season {
	private String id ;
	private String description;
	private List<Episode> episodeList;
	private Long duration;
	private int seasonNumber;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Episode> getEpisodeList() {
		return episodeList;
	}
	public void setEpisodeList(List<Episode> episodeList) {
		this.episodeList = episodeList;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public int getSeasonNumber() {
		return seasonNumber;
	}
	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}
	
}
