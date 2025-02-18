package com.stream.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

/**
 * Entity class representing a video in the system.
 * <p>
 * This entity stores metadata about a video, including its title, director, genre, and release year.
 * It also includes timestamps for tracking creation and updates.
 * <p>
 * The actual video file is not stored in this entity; only metadata is maintained.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    @OneToOne(mappedBy = "video", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EngagementStats engagementStats;

    /**
     * Unique identifier for the video.
     * <p>
     * This is generated automatically using UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    /**
     * A brief synopsis of the video content.
     */
    private String synopsis;
    private String director;

    /**
     * The cast members of the video.
     * <p>
     * This is stored as a comma-separated string.
     */
    @Column(name = "video_cast")  // Fix: Rename `cast` to `video_cast`
    private String videoCast;
    private int yearOfRelease;
    private String genre;

    /**
     * The total running time of the video in minutes.
     */
    private int runningTime;

    /**
     * Indicates whether the video is active.
     * <p>
     * This is used for soft delete functionality.
     * If `isActive` is false, the video is considered deleted but remains in the database.
     */
    private boolean isActive = true; // For soft delete

    @Column(nullable = false, updatable = false)
    private long createdAt;

    /**
     * The timestamp when the video record was last updated.
     * <p>
     * This is updated automatically whenever the entity is modified.
     */
    private long updatedAt;

    /**
     * Lifecycle hook to set `createdAt` before the entity is persisted.
     * <p>
     * This method is executed automatically when the entity is created.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
    }

    /**
     * Lifecycle hook to update `updatedAt` before the entity is updated.
     * <p>
     * This method is executed automatically when the entity is modified.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
}
