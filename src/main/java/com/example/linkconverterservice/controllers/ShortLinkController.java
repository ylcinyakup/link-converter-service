package com.example.linkconverterservice.controllers;

import com.example.linkconverterservice.model.request.LinkRequest;
import com.example.linkconverterservice.model.response.ShortLinkResponse;
import com.example.linkconverterservice.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;


@RequiredArgsConstructor
@RestController
@RequestMapping("/short-links")
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @PostMapping
    public ResponseEntity<Void> createShortLink(UriComponentsBuilder uriComponentsBuilder, @RequestBody @Valid LinkRequest linkRequest) {
        var shortLink = shortLinkService.createShortLink(linkRequest.getUrl());
        var location = uriComponentsBuilder.path("/short-links/{id}").buildAndExpand(shortLink.getId()).toUriString();
        return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShortLinkResponse> getShortLink(@PathVariable Long id) {
        return ResponseEntity.ok(shortLinkService.getById(id));
    }


}
