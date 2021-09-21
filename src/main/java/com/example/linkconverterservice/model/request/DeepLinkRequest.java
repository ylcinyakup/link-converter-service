package com.example.linkconverterservice.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class DeepLinkRequest {

    @NotBlank(message = "url can not be null or empty")
    private String url;
}
