package com.stream.repository;

import com.stream.model.EngagementStats;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface EngagementStatsRepository extends JpaRepository<EngagementStats, UUID> {
    Optional<EngagementStats> findByVideoId(UUID videoId);
}