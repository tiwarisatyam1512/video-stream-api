package com.stream.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EngagementStats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;

    private int impressions;
    private int views;

    public void incrementImpressions() {
        this.impressions++;
    }

    public void incrementViews() {
        this.views++;
    }
}

