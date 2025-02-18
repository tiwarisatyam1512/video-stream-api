package com.stream.service;

import com.stream.model.EngagementStats;
import com.stream.model.Video;
import com.stream.model.VideoMetadataResponse;
import com.stream.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.stream.repository.EngagementStatsRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing video-related operations.
 * <p>
 * This service handles video streaming, publishing, searching, updating,
 * and soft deletion. It interacts with the {@link VideoRepository} for database operations.
 */
@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final EngagementStatsRepository engagementStatsRepository;

    /**
     * Retrieves and returns a message indicating the video is being streamed.
     * <p>
     * If the video exists and is active, it returns a message simulating video streaming.
     * Otherwise, it throws a {@link RuntimeException}.
     *
     * @param id The unique identifier of the video.
     * @return A string message indicating the video is being streamed.
     * @throws RuntimeException if the video is not found or has been delisted.
     */
    public String playVideo(UUID id) {
        return videoRepository.findById(id)
                .filter(Video::isActive)
                .map(video -> "Streaming video content for: " + video.getTitle())
                .orElseThrow(() -> new RuntimeException("Video not found or is delisted"));
    }

    /**
     * Publishes a new video by saving its metadata in the database.
     *
     * @param video The video details to be saved.
     * @return The saved video entity.
     */
    public Video publishVideo(Video video) {
        return videoRepository.save(video);
    }

    /**
     * Retrieves a video by its ID.
     *
     * @param id The unique identifier of the video.
     * @return An {@link Optional} containing the video if found, or empty if not found.
     */
    public Optional<Video> getVideo(UUID id) {
        return videoRepository.findById(id);
    }

    /**
     * Retrieves a list of all active (non-deleted) videos.
     *
     * @return A list of active videos.
     */
    public List<Video> listVideos() {
        return videoRepository.findByIsActiveTrue();
    }

    /**
     * Searches for videos by director's name.
     * <p>
     * Performs a case-insensitive search for videos that match the given director's name.
     *
     * @param director The partial or full name of the director.
     * @return A list of videos directed by the given director.
     */
    public List<Video> searchByDirector(String director) {
        return videoRepository.findByDirectorContainingIgnoreCase(director);
    }

    /**
     * Updates metadata of an existing video.
     * <p>
     * If the video exists, it updates its metadata fields. Otherwise, it throws an exception.
     *
     * @param id          The unique identifier of the video.
     * @param updatedData The updated video details.
     * @return The updated video entity.
     * @throws RuntimeException if the video is not found.
     */
    public Video updateVideo(UUID id, Video updatedData) {
        return videoRepository.findById(id).map(existingVideo -> {
            existingVideo.setTitle(updatedData.getTitle());
            existingVideo.setSynopsis(updatedData.getSynopsis());
            existingVideo.setDirector(updatedData.getDirector());
            existingVideo.setVideoCast(updatedData.getVideoCast());
            existingVideo.setYearOfRelease(updatedData.getYearOfRelease());
            existingVideo.setGenre(updatedData.getGenre());
            existingVideo.setRunningTime(updatedData.getRunningTime());
            return videoRepository.save(existingVideo);
        }).orElseThrow(() -> new RuntimeException("Video not found"));
    }

    /**
     * Soft deletes a video by marking it as inactive.
     * <p>
     * The video remains in the database but is no longer considered available.
     *
     * @param id The unique identifier of the video.
     */
    public void delistVideo(UUID id) {
        videoRepository.findById(id).ifPresent(video -> {
            video.setActive(false);
            videoRepository.save(video);
        });
    }

    /**
     * Retrieves metadata for a video, including title, duration, and total views.
     *
     * @param videoId The unique identifier of the video.
     * @return A {@link VideoMetadataResponse} containing video title, running time, and total views.
     * @throws ResponseStatusException if the video is not found (returns 404 NOT FOUND).
     */
    public VideoMetadataResponse getVideoMetadata(UUID videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found"));

        int views = engagementStatsRepository.findByVideoId(videoId)
                .map(EngagementStats::getViews)
                .orElse(0);

        return new VideoMetadataResponse(video.getTitle(), video.getRunningTime(), views);
    }

}