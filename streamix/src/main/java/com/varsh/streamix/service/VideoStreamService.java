package com.varsh.streamix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varsh.streamix.model.StreamType;
import com.varsh.streamix.model.VideoStream;
import com.varsh.streamix.repository.VideoStreamRepository;

@Service
public class VideoStreamService {

    @Autowired
    private VideoStreamRepository videoStreamRepository;

    public List<VideoStream> searchVideo(String keyword) {
        return videoStreamRepository.searchByKeyword(keyword);
    }

    public VideoStream getVideoDetails(Long id) {
        return videoStreamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));
    }

    public List<VideoStream> getAllVideos() {
        return videoStreamRepository.findAll();
    }

    public void addNewVideo(VideoStream videoStream) {
        videoStreamRepository.save(videoStream);
    }

    public void updateVideoDetails(Long id, VideoStream videoStream) {
        VideoStream existingVideoStream = videoStreamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));
        existingVideoStream.setTitle(videoStream.getTitle());
        existingVideoStream.setGenre(videoStream.getGenre());
        existingVideoStream.setDescription(videoStream.getDescription());
        existingVideoStream.setVideoLink(videoStream.getVideoLink());
        videoStreamRepository.save(existingVideoStream);
    }

    public void deleteVideo(Long id) {
        videoStreamRepository.deleteById(id);
    }
    
    public List<VideoStream> getTop5Movies() {
        return videoStreamRepository.findTop5ByTypeOrderByIMDBRatingDesc(StreamType.MOVIE);
    }

    public List<VideoStream> getTop5Series() {
        return videoStreamRepository.findTop5ByTypeOrderByIMDBRatingDesc(StreamType.SERIES);
    }

}
