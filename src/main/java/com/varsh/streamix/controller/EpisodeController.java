package com.varsh.streamix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.varsh.streamix.service.EpisodeService;
import com.varsh.streamix.model.Episode;
import com.varsh.streamix.model.Season;
import com.varsh.streamix.repository.SeasonRepository;

import org.springframework.ui.Model;

@RequestMapping("/stream")
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;
    
    @Autowired
    private SeasonRepository seasonRepository;


    @DeleteMapping("/episode/{episodeId}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long episodeId) {
        episodeService.deleteEpisode(episodeId);
        return ResponseEntity.noContent().build();
    }
}
