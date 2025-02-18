package com.stream.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Entity class representing engagement statistics for a video.
 * <p>
 * This entity tracks metrics related to user interactions with a video, including:
 * <ul>
 *     <li>Impressions (how many times a video is loaded)</li>
 *     <li>Views (how many times a video is played)</li>
 * </ul>
 * <p>
 * This class is mapped to a database table using JPA and is associated with {@link Video} via a one-to-one relationship.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EngagementStats {

    /**
     * Unique identifier for the engagement statistics record.
     */
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "video_id", columnDefinition = "UUID")
    private UUID videoId;

    /**
     * The video associated with these engagement statistics.
     * <p>
     * This represents a one-to-one relationship with the {@link Video} entity,
     * where each video has a corresponding engagement statistics record.
     * </p>
     */
    @OneToOne
    @JoinColumn(name = "video_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Video video;

    /**
     * The number of times the video has been loaded (impressions).
     * <p>
     * An impression is counted each time a video is displayed on a user's screen.
     * </p>
     */
    private int impressions;

    /**
     * The number of times the video has been played (views).
     * <p>
     * A view is counted each time a user actually plays the video.
     * </p>
     */
    private int views;

    /**
     * Increments the impression count by 1.
     * <p>
     * This method is called when a user loads the video page but does not necessarily play the video.
     * </p>
     */
    public void incrementImpressions() {
        this.impressions++;
    }

    /**
     * Increments the view count by 1.
     * <p>
     * This method is called when a user starts playing the video.
     * </p>
     */
    public void incrementViews() {
        this.views++;
    }
}

