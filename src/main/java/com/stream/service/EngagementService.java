package com.stream.service;

import com.stream.model.EngagementStats;
import com.stream.repository.EngagementStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EngagementService {
    private final EngagementStatsRepository engagementStatsRepository;

    //  Record an impression (when video is loaded)
    public void recordImpression(UUID videoId) {
        EngagementStats stats = engagementStatsRepository.findByVideoId(videoId)
                .orElse(new EngagementStats());
        stats.incrementImpressions();
        engagementStatsRepository.save(stats);
    }

    //  Record a view (when video is played)
    public void recordView(UUID videoId) {
        EngagementStats stats = engagementStatsRepository.findByVideoId(videoId)
                .orElse(new EngagementStats());
        stats.incrementViews();
        engagementStatsRepository.save(stats);
    }

    //  Get engagement statistics
    public Optional<EngagementStats> getStats(UUID videoId) {
        return engagementStatsRepository.findByVideoId(videoId);
    }
}