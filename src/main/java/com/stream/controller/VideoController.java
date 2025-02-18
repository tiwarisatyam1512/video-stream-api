package com.stream.controller;

import com.stream.model.Video;
import com.stream.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

/**
 * REST Controller for managing video-related operations.
 * <p>
 * This controller provides endpoints to:
 * <ul>
 *     <li>Play a video</li>
 *     <li>Publish (upload) a video</li>
 *     <li>Retrieve video metadata</li>
 *     <li>List all available videos</li>
 *     <li>Search videos by director</li>
 *     <li>Update video details</li>
 *     <li>Soft delete (delist) a video</li>
 * </ul>
 *
 * <p>Uses {@link VideoService} to handle business logic.</p>
 */
@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    /**
     * Streams a video for playback.
     * <p>Returns a URL or streaming link to play the requested video.</p>
     *
     * @param id The unique identifier of the video.
     * @return ResponseEntity containing the streaming URL or playback link.
     */
    @GetMapping("/{id}/play")
    public ResponseEntity<String> playVideo(@PathVariable UUID id) {
        return ResponseEntity.ok(videoService.playVideo(id));
    }

    /**
     * Publishes a new video.
     * <p>Saves the video metadata in the database and prepares it for streaming.</p>
     *
     * @param video The video details to be published.
     * @return ResponseEntity containing the saved video metadata.
     */
    @PostMapping
    public ResponseEntity<Video> publishVideo(@RequestBody Video video) {
        return ResponseEntity.ok(videoService.publishVideo(video));
    }

    /**
     * Retrieves metadata for a specific video.
     *
     * @param id The unique identifier of the video.
     * @return ResponseEntity containing video metadata if found,
     *         or 404 (Not Found) if the video does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable UUID id) {
        return videoService.getVideo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lists all available videos.
     *
     * @return ResponseEntity containing a list of all published videos.
     */
    @GetMapping
    public ResponseEntity<List<Video>> listVideos() {
        return ResponseEntity.ok(videoService.listVideos());
    }

    /**
     * Searches videos by the director's name.
     *
     * @param director The name of the director to filter videos.
     * @return ResponseEntity containing a list of videos directed by the given director.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Video>> searchByDirector(@RequestParam String director) {
        return ResponseEntity.ok(videoService.searchByDirector(director));
    }

    /**
     * Updates the metadata of an existing video.
     *
     * @param id    The unique identifier of the video.
     * @param video The updated video details.
     * @return ResponseEntity containing the updated video metadata.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable UUID id, @RequestBody Video video) {
        return ResponseEntity.ok(videoService.updateVideo(id, video));
    }

    /**
     * Soft deletes (delists) a video by marking it inactive.
     *
     * @param id The unique identifier of the video.
     * @return ResponseEntity with HTTP status 204 (No Content) if successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delistVideo(@PathVariable UUID id) {
        videoService.delistVideo(id);
        return ResponseEntity.noContent().build();
    }
}