package com.example.linkconverterservice.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Getter
@Setter
public class BaseLink {
    private String uri;
    private String path;
    private String scheme;
    private String host;
    private List<String> pathSegments;
    private MultiValueMap<String, String> queryParams;

    public BaseLink(String uri) {
        var uriComponents = UriComponentsBuilder.fromUriString(uri).build();
        this.uri = uri;
        this.path = uriComponents.getPath();
        this.scheme = uriComponents.getScheme();
        this.host = uriComponents.getHost();
        this.pathSegments = uriComponents.getPathSegments();
        this.queryParams = uriComponents.getQueryParams();
    }
}
