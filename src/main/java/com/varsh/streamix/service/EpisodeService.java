package com.varsh.streamix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varsh.streamix.model.Episode;
import com.varsh.streamix.model.Season;
import com.varsh.streamix.repository.EpisodeRepository;
import com.varsh.streamix.repository.SeasonRepository;

@Service
public class EpisodeService {
    
    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    public Episode createEpisode(Long seasonId, int episodeNumber, String title, String videoLink) {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new RuntimeException("Season not found")); 
        Episode episode = new Episode();
        episode.setSeason(season);
        episode.setEpisodeNumber(episodeNumber);
        episode.setTitle(title);
        episode.setVideoLink(videoLink);   

        return episodeRepository.save(episode);

    }

    public List<Episode> getEpisodesBySeason(Long seasonId) {
        return episodeRepository.findBySeasonId(seasonId);
    }

    public void deleteEpisode(Long episodeId) {
        episodeRepository.deleteById(episodeId);
    }

}
