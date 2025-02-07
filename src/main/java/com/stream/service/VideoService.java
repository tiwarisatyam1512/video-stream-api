package com.stream.service;

import com.stream.model.Video;
import com.stream.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;

    public Video publishVideo(Video video) {
        return videoRepository.save(video);
    }

    public Optional<Video> getVideo(UUID id) {
        return videoRepository.findById(id);
    }

    public List<Video> listVideos() {
        return videoRepository.findByIsActiveTrue();
    }

    public void delistVideo(UUID id) {
        videoRepository.findById(id).ifPresent(video -> {
            video.setActive(false);
            videoRepository.save(video);
        });
    }
}