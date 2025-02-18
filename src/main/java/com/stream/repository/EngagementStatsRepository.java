package com.stream.repository;

import com.stream.model.EngagementStats;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing engagement statistics in the database.
 * <p>
 * This repository provides CRUD operations for the {@link EngagementStats} entity
 * and includes a custom query method for retrieving engagement data by video ID.
 * <p>
 * Extends {@link JpaRepository} to leverage Spring Data JPA functionalities.
 */
public interface EngagementStatsRepository extends JpaRepository<EngagementStats, UUID> {

    /**
     * Retrieves engagement statistics for a specific video by its ID.
     * <p>
     * This method finds the engagement record associated with a given video,
     * allowing access to the number of impressions and views.
     *
     * @param videoId The unique identifier of the video.
     * @return An {@link Optional} containing the engagement statistics if found,
     *         or an empty Optional if no engagement data exists for the video.
     */
    Optional<EngagementStats> findByVideoId(UUID videoId);
}