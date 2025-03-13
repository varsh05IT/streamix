package com.varsh.streamix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="video_stream")
public class VideoStream {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "video_stream_seq")
    @SequenceGenerator(name="video_stream_seq", sequenceName = "VIDEO_STREAM_SEQ", allocationSize = 1)
    private Long id;
    
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    private String directorName;
    
    @NonNull
    private Long duration;
    
    private Long createdAt;
    
    @NonNull
    private int IMDBRating;
    
    @NonNull
    private String videoLink;
    
    private String thumbnailUrl;
    
    @OneToMany(mappedBy="videoStream",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Season> seasonList;
    
    @Enumerated(EnumType.STRING)
    private StreamType type;
    
    @Enumerated(EnumType.STRING)
    private Genre genre;
    
}
