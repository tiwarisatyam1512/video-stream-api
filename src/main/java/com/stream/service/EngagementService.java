package com.stream.service;

import com.stream.model.EngagementStats;
import com.stream.repository.EngagementStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing engagement statistics related to videos.
 * <p>
 * This service handles the recording of video impressions and views,
 * as well as retrieving engagement statistics.
 * <p>
 * Uses {@link EngagementStatsRepository} for database interactions.
 */
@Service
@RequiredArgsConstructor
public class EngagementService {
    private final EngagementStatsRepository engagementStatsRepository;

    /**
     * Records an impression for a given video.
     * <p>
     * An impression is counted each time a video page is loaded, even if the video is not played.
     * If an engagement record does not already exist for the video, a new one is created.
     *
     * @param videoId The unique identifier of the video.
     */
    public void recordImpression(UUID videoId) {
        EngagementStats stats = engagementStatsRepository.findByVideoId(videoId)
                .orElse(new EngagementStats());
        stats.incrementImpressions();
        engagementStatsRepository.save(stats);
    }

    /**
     * Records a view for a given video.
     * <p>
     * A view is counted when a user starts playing the video.
     * If an engagement record does not already exist for the video, a new one is created.
     *
     * @param videoId The unique identifier of the video.
     */
    public void recordView(UUID videoId) {
        EngagementStats stats = engagementStatsRepository.findByVideoId(videoId)
                .orElse(new EngagementStats());
        stats.incrementViews();
        engagementStatsRepository.save(stats);
    }

    /**
     * Retrieves engagement statistics for a specific video.
     * <p>
     * This method fetches engagement data, including the number of impressions and views.
     *
     * @param videoId The unique identifier of the video.
     * @return An {@link Optional} containing the engagement statistics if found,
     *         or an empty Optional if no engagement data exists for the video.
     */
    public Optional<EngagementStats> getStats(UUID videoId) {
        return engagementStatsRepository.findByVideoId(videoId);
    }
}