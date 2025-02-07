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

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    private Video video;
    private UUID videoId;

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

    @Test
    void shouldPublishVideo() {
        when(videoRepository.save(any(Video.class))).thenReturn(video);
        Video savedVideo = videoService.publishVideo(video);
        assertNotNull(savedVideo);
        assertEquals("Inception", savedVideo.getTitle());
    }

    @Test
    void shouldLoadVideoById() {
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        Optional<Video> retrievedVideo = videoService.getVideo(videoId);
        assertTrue(retrievedVideo.isPresent());
        assertEquals("Inception", retrievedVideo.get().getTitle());
    }

    @Test
    void shouldReturnMockVideoContentWhenPlayed() {
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        String result = videoService.playVideo(videoId);
        assertEquals("Streaming video content for: Inception", result);
    }

    @Test
    void shouldThrowExceptionForDelistedVideo() {
        video.setActive(false);
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        Exception exception = assertThrows(RuntimeException.class, () -> videoService.playVideo(videoId));
        assertEquals("Video not found or is delisted", exception.getMessage());
    }
}