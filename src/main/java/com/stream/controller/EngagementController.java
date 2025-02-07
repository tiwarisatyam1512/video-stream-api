package com.stream.controller;

import com.stream.model.EngagementStats;
import com.stream.service.EngagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/engagement")
@RequiredArgsConstructor
public class EngagementController {
    private final EngagementService engagementService;

    @PostMapping("/{videoId}/impression")
    public ResponseEntity<Void> recordImpression(@PathVariable UUID videoId) {
        engagementService.recordImpression(videoId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{videoId}/view")
    public ResponseEntity<Void> recordView(@PathVariable UUID videoId) {
        engagementService.recordView(videoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<EngagementStats> getStats(@PathVariable UUID videoId) {
        return engagementService.getStats(videoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}