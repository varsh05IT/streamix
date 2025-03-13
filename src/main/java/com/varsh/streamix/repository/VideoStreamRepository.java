package com.varsh.streamix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.varsh.streamix.model.StreamType;
import com.varsh.streamix.model.VideoStream;

@Repository
public interface VideoStreamRepository extends JpaRepository<VideoStream,Long>{
    @Query("SELECT v FROM VideoStream v WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(v.genre) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<VideoStream> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT v FROM VideoStream v WHERE v.type = :type ORDER BY v.IMDBRating DESC")
    List<VideoStream> findTop5ByTypeOrderByIMDBRatingDesc(StreamType type);

    List<VideoStream> findByType(StreamType series);

}
