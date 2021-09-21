package com.example.linkconverterservice.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeepLinkResponse {
    private String deepLink;
}
