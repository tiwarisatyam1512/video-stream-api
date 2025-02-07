package com.stream.service;

import com.stream.model.EngagementStats;
import com.stream.repository.EngagementStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EngagementService {
    private final EngagementStatsRepository engagementStatsRepository;

    public void recordImpression(UUID videoId) {
        EngagementStats stats = engagementStatsRepository.findByVideoId(videoId)
                .orElse(new EngagementStats());
        stats.incrementImpressions();
        engagementStatsRepository.save(stats);
    }

    public void recordView(UUID videoId) {
        EngagementStats stats = engagementStatsRepository.findByVideoId(videoId)
                .orElse(new EngagementStats());
        stats.incrementViews();
        engagementStatsRepository.save(stats);
    }
}