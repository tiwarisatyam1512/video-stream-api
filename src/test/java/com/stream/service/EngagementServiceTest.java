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

/**
 * Unit test class for {@link EngagementService}.
 * <p>
 * This test verifies the functionality of engagement tracking,
 * specifically recording impressions and views.
 * It uses Mockito to mock dependencies and JUnit 5 for testing.
 */
@ExtendWith(MockitoExtension.class)
class EngagementServiceTest {

    /**
     * Mocked instance of {@link EngagementStatsRepository} to simulate database interactions.
     */
    @Mock
    private EngagementStatsRepository engagementStatsRepository;

    /**
     * Instance of {@link EngagementService} with mocked dependencies injected.
     */
    @InjectMocks
    private EngagementService engagementService;

    private UUID videoId;
    private EngagementStats engagementStats;

    /**
     * Sets up test data before each test execution.
     * <p>
     * Initializes a random video ID and an empty {@link EngagementStats} object.
     */
    @BeforeEach
    void setUp() {
        videoId = UUID.randomUUID();
        engagementStats = new EngagementStats();
        engagementStats.setImpressions(0);
        engagementStats.setViews(0);
    }

    /**
     * Tests whether an impression is recorded correctly.
     * <p>
     * Given an existing engagement record, when an impression is recorded,
     * the count should increase by 1.
     */
    @Test
    void shouldRecordImpression() {
        when(engagementStatsRepository.findByVideoId(videoId)).thenReturn(Optional.of(engagementStats));
        engagementService.recordImpression(videoId);
        assertEquals(1, engagementStats.getImpressions());
    }

    /**
     * Tests whether a view is recorded correctly.
     * <p>
     * Given an existing engagement record, when a view is recorded,
     * the count should increase by 1.
     */
    @Test
    void shouldRecordView() {
        when(engagementStatsRepository.findByVideoId(videoId)).thenReturn(Optional.of(engagementStats));
        engagementService.recordView(videoId);
        assertEquals(1, engagementStats.getViews());
    }
}