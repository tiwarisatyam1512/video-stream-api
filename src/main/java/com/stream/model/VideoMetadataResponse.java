package com.stream.model;

import lombok.*;

@Getter
@AllArgsConstructor
public class VideoMetadataResponse {
    private String title;
    private int runningTime;
    private int views;
}
