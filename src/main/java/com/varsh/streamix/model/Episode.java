package com.varsh.streamix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="episode")
public class Episode {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "episode_seq")
    @SequenceGenerator(name="episode_seq", sequenceName = "EPISODE_SEQ", allocationSize = 1)
    private Long id;

    private String title;
    
    @Column(length = 1000)
    private String description;
    private int episodeNumber;
    private Long duration;
    private String videoLink;
    private int IMDBRating;

    @ManyToOne
    @JoinColumn(name="season_id", referencedColumnName = "id")
    private Season season;
}
