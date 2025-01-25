package com.varsh.streamix.model;

public class PlayBackStatus {
	private String id;
	private StreamType type;
	private String image;
	private Long pauseTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public StreamType getType() {
		return type;
	}
	public void setType(StreamType type) {
		this.type = type;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getPauseTime() {
		return pauseTime;
	}
	public void setPauseTime(Long pauseTime) {
		this.pauseTime = pauseTime;
	}
}
