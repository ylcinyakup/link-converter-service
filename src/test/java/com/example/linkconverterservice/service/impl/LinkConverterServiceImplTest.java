package com.example.linkconverterservice.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class LinkConverterServiceImplTest {
    @InjectMocks
    private LinkConverterServiceImpl linkConverterServiceImpl;

    @Test
    void should_convertToLink_as_expected() {
        assertThat(this.linkConverterServiceImpl.convertToLink("trendyol:///example").getLink()).isEqualTo("https://www.trendyol.com/example");
    }

    @Test
    void should_convertToDeepLink_as_expected() {
        assertThat(this.linkConverterServiceImpl.convertToDeepLink("https://trendyol.org/example").getDeepLink()).isEqualTo("trendyol:///example");
    }

    @Test
    void testGenerateShortLinkByHash() {
        assertThat(this.linkConverterServiceImpl.generateShortLinkByHash("Hash")).isEqualTo("https://www.trendyol.com/Hash");
    }
}

