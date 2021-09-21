package com.example.linkconverterservice.service.impl;

import com.example.linkconverterservice.db.entity.ShortLink;
import com.example.linkconverterservice.db.repository.ShortLinkRepository;
import com.example.linkconverterservice.model.exceptions.ResourceAlreadyExistException;
import com.example.linkconverterservice.model.exceptions.ResourceNotFoundException;
import com.example.linkconverterservice.model.response.ShortLinkResponse;
import com.example.linkconverterservice.service.LinkConverterService;
import com.example.linkconverterservice.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShortLinkServiceImpl implements ShortLinkService {

    private final LinkConverterService linkConverterService;
    private final ShortLinkRepository repository;
    private static final String possibleChars = "ABCDEFGHIJKLMNOPQRSTUWXYZabcdefghijklmnoprstuvwxyz0123456789";

    @Override
    @Transactional
    public ShortLinkResponse createShortLink(String url) {
        repository.findByLink(url).ifPresent(sl -> {
            throw new ResourceAlreadyExistException("ShortLink");
        });
        var shortLink = new ShortLink();
        shortLink.setLink(url);
        shortLink.setShortLink(linkConverterService.generateShortLinkByHash(urlToHash()));
        shortLink = repository.save(shortLink);
        return ShortLinkResponse.builder().id(shortLink.getId()).link(shortLink.getLink()).shortLink(shortLink.getShortLink())
                .created(shortLink.getCreated()).updated(shortLink.getUpdated()).build();
    }

    @Override
    public ShortLinkResponse getById(Long id) {
        var shortLink = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ShortLink", "id", id));
        return ShortLinkResponse.builder()
                .id(shortLink.getId())
                .link(shortLink.getLink())
                .shortLink(shortLink.getShortLink())
                .created(shortLink.getCreated())
                .updated(shortLink.getUpdated())
                .build();
    }

    private String urlToHash() {
        var randomStr = new StringBuilder();
        for (var i = 0; i < 5; i++) {
            randomStr.append(possibleChars.charAt(new Random().nextInt(possibleChars.length())));
        }
        return randomStr.toString();
    }
}
