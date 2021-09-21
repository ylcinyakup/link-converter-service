package com.example.linkconverterservice.service;

import com.example.linkconverterservice.model.response.DeepLinkResponse;
import com.example.linkconverterservice.model.response.LinkResponse;

public interface LinkConverterService {
    LinkResponse convertToLink(String url);

    DeepLinkResponse convertToDeepLink(String url);

    String generateShortLinkByHash(String hash);
}
