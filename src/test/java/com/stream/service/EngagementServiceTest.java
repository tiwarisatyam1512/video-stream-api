package com.stream.service;

import com.stream.model.EngagementStats;
import com.stream.repository.EngagementStatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EngagementServiceTest {

    @Mock
    private EngagementStatsRepository engagementStatsRepository;

    @InjectMocks
    private EngagementService engagementService;

    private UUID videoId;
    private EngagementStats engagementStats;

    @BeforeEach
    void setUp() {
        videoId = UUID.randomUUID();
        engagementStats = new EngagementStats();
        engagementStats.setImpressions(0);
        engagementStats.setViews(0);
    }

    @Test
    void shouldRecordImpression() {
        when(engagementStatsRepository.findByVideoId(videoId)).thenReturn(Optional.of(engagementStats));
        engagementService.recordImpression(videoId);
        assertEquals(1, engagementStats.getImpressions());
    }

    @Test
    void shouldRecordView() {
        when(engagementStatsRepository.findByVideoId(videoId)).thenReturn(Optional.of(engagementStats));
        engagementService.recordView(videoId);
        assertEquals(1, engagementStats.getViews());
    }
}