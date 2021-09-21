package com.example.linkconverterservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeepLink extends BaseLink {
    public DeepLink(String uri) {
        super(uri);
    }
}
