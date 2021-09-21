package com.example.linkconverterservice.service.impl;

import com.example.linkconverterservice.model.dto.DeepLink;
import com.example.linkconverterservice.model.dto.Link;
import com.example.linkconverterservice.model.response.DeepLinkResponse;
import com.example.linkconverterservice.model.response.LinkResponse;
import com.example.linkconverterservice.service.LinkConverterService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LinkConverterServiceImpl implements LinkConverterService {

    @Override
    public LinkResponse convertToLink(String url) {
        return LinkResponse.builder().link(convertToLink(new DeepLink(url))).build();
    }

    @Override
    public DeepLinkResponse convertToDeepLink(String url) {
        return DeepLinkResponse.builder().deepLink(convertToDeepLink(new Link(url))).build();
    }

    @Override
    public String generateShortLinkByHash(String hash) {
        var builder = UriComponentsBuilder.newInstance();
        return builder.scheme("https").host("www.trendyol.com").path(hash).build().toUriString();
    }

    private String convertToLink(DeepLink deepLink) {
        var builder = UriComponentsBuilder.newInstance();
        deepLink.getQueryParams().forEach(builder::queryParam);
        return builder.scheme("https").host("www.trendyol.com").path(deepLink.getPath()).build().toUriString();
    }

    private String convertToDeepLink(Link link) {
        var builder = UriComponentsBuilder.newInstance();
        link.getQueryParams().forEach(builder::queryParam);
        return builder.scheme("trendyol").host("").path(link.getPath()).build().toUriString();
    }


}
