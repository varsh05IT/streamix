package com.varsh.streamix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varsh.streamix.model.Season;
import com.varsh.streamix.model.VideoStream;
import com.varsh.streamix.repository.SeasonRepository;
import com.varsh.streamix.repository.VideoStreamRepository;

@Service
public class SeasonService {
    
    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private VideoStreamRepository videoStreamRepository;

    public Season createSeason(Long videoStreamId, int seasonNumber) {
        VideoStream videoStream = videoStreamRepository.findById(videoStreamId)
                .orElseThrow(() -> new RuntimeException("VideoStream not found"));
        Season season = new Season();
        season.setVideoStream(videoStream);
        season.setSeasonNumber(seasonNumber);
        return seasonRepository.save(season);
    }
    
    public List<Season> getSeasonsByVideoStream(Long videoStreamId) {
        return seasonRepository.findByVideoStreamId(videoStreamId);
    }



    public void deleteSeason(Long seasonId) {
        seasonRepository.deleteById(seasonId);
    }
}
