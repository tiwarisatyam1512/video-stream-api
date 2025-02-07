package com.stream.controller;

import com.stream.model.Video;
import com.stream.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @GetMapping("/{id}/play")
    public ResponseEntity<String> playVideo(@PathVariable UUID id) {
        return ResponseEntity.ok(videoService.playVideo(id));
    }

    @PostMapping
    public ResponseEntity<Video> publishVideo(@RequestBody Video video) {
        return ResponseEntity.ok(videoService.publishVideo(video));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable UUID id) {
        return videoService.getVideo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Video>> listVideos() {
        return ResponseEntity.ok(videoService.listVideos());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Video>> searchByDirector(@RequestParam String director) {
        return ResponseEntity.ok(videoService.searchByDirector(director));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable UUID id, @RequestBody Video video) {
        return ResponseEntity.ok(videoService.updateVideo(id, video));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delistVideo(@PathVariable UUID id) {
        videoService.delistVideo(id);
        return ResponseEntity.noContent().build();
    }
}