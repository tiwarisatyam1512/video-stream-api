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
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private String synopsis;
    private String director;
    @Column(name = "video_cast")  // Fix: Rename `cast` to `video_cast`
    private String videoCast;
    private int yearOfRelease;
    private String genre;
    private int runningTime;

    private boolean isActive = true; // For soft delete

    @Column(nullable = false, updatable = false)
    private long createdAt;

    private long updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
}
