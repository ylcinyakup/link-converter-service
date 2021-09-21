package com.example.linkconverterservice.controllers;

import com.example.linkconverterservice.model.request.DeepLinkRequest;
import com.example.linkconverterservice.model.request.LinkRequest;
import com.example.linkconverterservice.model.response.DeepLinkResponse;
import com.example.linkconverterservice.model.response.LinkResponse;
import com.example.linkconverterservice.service.LinkConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/convert")
public class LinkConverterController {

    private final LinkConverterService linkConverterService;

    @PostMapping("/link-to-deepLink")
    public ResponseEntity<DeepLinkResponse> convertToDeepLink(@RequestBody @Valid LinkRequest linkRequest) {
        var deepLinkResponse = linkConverterService.convertToDeepLink(linkRequest.getUrl());
        return ResponseEntity.ok(deepLinkResponse);
    }

    @PostMapping("/deepLink-to-link")
    public ResponseEntity<LinkResponse> convertToLink(@RequestBody @Valid DeepLinkRequest deepLinkRequest) {
        var linkResponse = linkConverterService.convertToLink(deepLinkRequest.getUrl());
        return ResponseEntity.ok(linkResponse);
    }
}
