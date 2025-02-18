package com.stream.controller;

import com.stream.model.EngagementStats;
import com.stream.service.EngagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

/**
 * REST Controller for managing video engagement statistics.
 * This controller handles API endpoints related to tracking video impressions,
 * views, and retrieving engagement statistics.
 *
 * <p>Endpoints include:
 * <ul>
 *     <li>Recording an impression when a video is loaded</li>
 *     <li>Recording a view when a video is played</li>
 *     <li>Retrieving engagement statistics for a specific video</li>
 * </ul>
 *
 * <p>Uses {@link EngagementService} to process engagement data.</p>
 */
@RestController
@RequestMapping("/engagement")
@RequiredArgsConstructor
public class EngagementController {
    private final EngagementService engagementService;

    /**
     * Records an impression for a given video.
     * <p>An impression is registered when a user loads the video page.</p>
     *
     * @param videoId The unique identifier of the video
     * @return ResponseEntity with HTTP status 200 (OK) if successful
     */
    @PostMapping("/{videoId}/impression")
    public ResponseEntity<Void> recordImpression(@PathVariable UUID videoId) {
        engagementService.recordImpression(videoId);
        return ResponseEntity.ok().build();
    }

    /**
     * Records a view for a given video.
     * <p>A view is counted when a user actually plays the video.</p>
     *
     * @param videoId The unique identifier of the video
     * @return ResponseEntity with HTTP status 200 (OK) if successful
     */
    @PostMapping("/{videoId}/view")
    public ResponseEntity<Void> recordView(@PathVariable UUID videoId) {
        engagementService.recordView(videoId);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves engagement statistics for a specific video.
     * <p>Returns metrics such as the number of impressions and views.</p>
     *
     * @param videoId The unique identifier of the video
     * @return ResponseEntity containing the engagement statistics if found,
     *         or a 404 (Not Found) response if no stats are available.
     */
    @GetMapping("/{videoId}")
    public ResponseEntity<EngagementStats> getStats(@PathVariable UUID videoId) {
        return engagementService.getStats(videoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}