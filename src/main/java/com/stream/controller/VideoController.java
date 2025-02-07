package com.stream.controller;

import com.stream.model.Video;
import com.stream.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<Video> publishVideo(@RequestBody Video video) {
        return ResponseEntity.ok(videoService.publishVideo(video));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable UUID id) {
        Optional<Video> video = videoService.getVideo(id);
        return video.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Video>> listVideos() {
        return ResponseEntity.ok(videoService.listVideos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delistVideo(@PathVariable UUID id) {
        videoService.delistVideo(id);
        return ResponseEntity.noContent().build();
    }
}