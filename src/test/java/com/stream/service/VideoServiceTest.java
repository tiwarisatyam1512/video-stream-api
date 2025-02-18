package com.stream.service;

import com.stream.model.Video;
import com.stream.repository.VideoRepository;
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
 * Unit test class for {@link VideoService}.
 * <p>
 * This test verifies the functionality of video management operations,
 * including publishing, retrieving, and playing videos.
 * It uses Mockito to mock dependencies and JUnit 5 for testing.
 */
@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    /**
     * Mocked instance of {@link VideoRepository} to simulate database interactions.
     */
    @Mock
    private VideoRepository videoRepository;

    /**
     * Instance of {@link VideoService} with mocked dependencies injected.
     */
    @InjectMocks
    private VideoService videoService;

    private Video video;
    private UUID videoId;

    /**
     * Sets up test data before each test execution.
     * <p>
     * Initializes a random video ID and creates a sample {@link Video} object.
     */
    @BeforeEach
    void setUp() {
        videoId = UUID.randomUUID();
        video = Video.builder()
                .id(videoId)
                .title("Inception")
                .director("Christopher Nolan")
                .isActive(true)
                .build();
    }

    /**
     * Tests whether a video is successfully published (saved to the repository).
     * <p>
     * Given a video object, when it is saved, it should return the same video with an assigned ID.
     */
    @Test
    void shouldPublishVideo() {
        when(videoRepository.save(any(Video.class))).thenReturn(video);
        Video savedVideo = videoService.publishVideo(video);
        assertNotNull(savedVideo);
        assertEquals("Inception", savedVideo.getTitle());
    }

    /**
     * Tests whether a video can be loaded by its ID.
     * <p>
     * Given a valid video ID, when the video is retrieved, it should return the correct video metadata.
     */
    @Test
    void shouldLoadVideoById() {
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        Optional<Video> retrievedVideo = videoService.getVideo(videoId);
        assertTrue(retrievedVideo.isPresent());
        assertEquals("Inception", retrievedVideo.get().getTitle());
    }

    /**
     * Tests whether the correct message is returned when playing an active video.
     * <p>
     * Given an active video ID, when the play method is called, it should return a streaming message.
     */
    @Test
    void shouldReturnMockVideoContentWhenPlayed() {
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        String result = videoService.playVideo(videoId);
        assertEquals("Streaming video content for: Inception", result);
    }

    /**
     * Tests whether an exception is thrown when attempting to play a delisted video.
     * <p>
     * Given a video that has been soft deleted (isActive = false), when play is called,
     * it should throw a {@link RuntimeException} with the expected message.
     */
    @Test
    void shouldThrowExceptionForDelistedVideo() {
        video.setActive(false);
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        Exception exception = assertThrows(RuntimeException.class, () -> videoService.playVideo(videoId));
        assertEquals("Video not found or is delisted", exception.getMessage());
    }
}