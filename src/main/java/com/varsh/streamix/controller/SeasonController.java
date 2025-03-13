package com.varsh.streamix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.varsh.streamix.model.Season;
import com.varsh.streamix.model.VideoStream;
import com.varsh.streamix.repository.VideoStreamRepository;
import com.varsh.streamix.service.SeasonService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/stream")
public class SeasonController {
    
    @Autowired
    private SeasonService seasonService;
    
    @Autowired
    private VideoStreamRepository videoStreamRepository;

    @PostMapping("/{videoStreamId}/season")
    public ResponseEntity<Season> addSeason(@PathVariable Long videoStreamId, @RequestParam int seasonNumber) {
        return ResponseEntity.ok(seasonService.createSeason(videoStreamId, seasonNumber));
    }

    @GetMapping("/seasons/{videoStreamId}")
    public String getSeasons(@PathVariable Long videoStreamId, Model model) {
        VideoStream videoStream = videoStreamRepository.findById(videoStreamId).orElse(null);
        if(videoStream == null) {
            return "redirect:/stream/tvshows";
        }
        List<Season> seasons = videoStream.getSeasonList();
        model.addAttribute("videoStream", videoStream);
        model.addAttribute("seasons", seasons);
        return "seasons";
        
    }

    @DeleteMapping("/season/{seasonId}")
    public ResponseEntity<Void> deleteSeason(@PathVariable Long seasonId) {
        seasonService.deleteSeason(seasonId);
        return ResponseEntity.noContent().build();
    }
}
