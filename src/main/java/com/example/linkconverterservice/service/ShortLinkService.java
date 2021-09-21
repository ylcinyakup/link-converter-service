package com.example.linkconverterservice.service;

import com.example.linkconverterservice.model.response.ShortLinkResponse;

public interface ShortLinkService {
    ShortLinkResponse createShortLink(String url);

    ShortLinkResponse getById(Long id);
}
