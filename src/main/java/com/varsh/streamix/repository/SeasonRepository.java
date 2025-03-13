package com.varsh.streamix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varsh.streamix.model.Season;

public interface SeasonRepository extends JpaRepository<Season, Long>{
    
    List<Season> findByVideoStreamId(Long videoStreamId);
}
