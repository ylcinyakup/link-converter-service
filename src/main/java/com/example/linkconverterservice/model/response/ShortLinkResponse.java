package com.example.linkconverterservice.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ShortLinkResponse {

    private Long id;
    private String link;
    private String shortLink;
    private LocalDateTime created;
    private LocalDateTime updated;
}
