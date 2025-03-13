package com.varsh.streamix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varsh.streamix.model.Episode;
import com.varsh.streamix.model.Season;


public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findBySeasonId(Long seasonId);
}
