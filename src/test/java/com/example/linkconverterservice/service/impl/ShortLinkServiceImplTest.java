package com.example.linkconverterservice.service.impl;

import com.example.linkconverterservice.db.entity.ShortLink;
import com.example.linkconverterservice.db.repository.ShortLinkRepository;
import com.example.linkconverterservice.model.exceptions.ResourceAlreadyExistException;
import com.example.linkconverterservice.model.exceptions.ResourceNotFoundException;
import com.example.linkconverterservice.model.response.ShortLinkResponse;
import com.example.linkconverterservice.service.LinkConverterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShortLinkServiceImplTest {

    @InjectMocks
    private ShortLinkServiceImpl shortLinkService;

    @Mock
    private LinkConverterService linkConverterService;

    @Mock
    private ShortLinkRepository repository;

    @Test
    void should_throw_ResourceAlreadyExistException_when_shortLink_is_present() {
        //givin
        var shortLink = ShortLink.builder()
                .id(123L)
                .created((LocalDateTime.of(1, 1, 1, 1, 1)))
                .updated((LocalDateTime.of(1, 1, 1, 1, 1)))
                .link("https://www.trendyol.com/home")
                .shortLink("https://www.trenyol.com/ppyaU").build();
        Optional<ShortLink> shortLinkOpt = Optional.of(shortLink);
        doReturn(shortLinkOpt).when(this.repository).findByLink(any());
        //when-then
        assertThrows(ResourceAlreadyExistException.class,
                () -> this.shortLinkService.createShortLink("https://example.org/example"));
        verify(this.repository).findByLink(any());
    }

    @Test
    void should_return_shortLink_as_expected() {
        //given
        var shortLink = ShortLink.builder()
                .id(123L)
                .created((LocalDateTime.of(1, 1, 1, 1, 1, 1)))
                .updated((LocalDateTime.of(1, 1, 1, 1, 1, 1)))
                .link("https://www.trendyol.com/home")
                .shortLink("https://www.trendyol.com/ppyaU").build();
        doReturn(Optional.empty()).when(this.repository).findByLink(any());
        doReturn(shortLink).when(this.repository).save(any());
        doReturn("asdf").when(this.linkConverterService).generateShortLinkByHash(any());

        //when
        ShortLinkResponse actualCreateShortLinkResult = this.shortLinkService.createShortLink("");

        //then
        assertEquals(123L, actualCreateShortLinkResult.getId().longValue());
        assertEquals("https://www.trendyol.com/home", actualCreateShortLinkResult.getLink());
        assertEquals("https://www.trendyol.com/ppyaU", actualCreateShortLinkResult.getShortLink());
        assertEquals(LocalDateTime.of(1, 1, 1, 1, 1, 1), actualCreateShortLinkResult.getCreated());
        assertEquals(LocalDateTime.of(1, 1, 1, 1, 1, 1), actualCreateShortLinkResult.getUpdated());
        verify(this.repository).findByLink(any());
        verify(this.repository).save(any());
        verify(this.linkConverterService).generateShortLinkByHash(any());
    }

    @Test
    void should_return_shortLink_by_id() {
        //givin
        var shortLink = ShortLink.builder()
                .id(123L)
                .link("https://www.trendyol.com/home")
                .shortLink("https://www.trendyol.com/ppyaU").build();
        Optional<ShortLink> shortLinkOpt = Optional.of(shortLink);
        doReturn(shortLinkOpt).when(this.repository).findById(any());

        //when
        ShortLinkResponse shortLinkResponse = this.shortLinkService.getById(123L);

        //then
        assertThat(shortLinkResponse.getId()).isEqualTo(123L);
        assertThat(shortLinkResponse.getLink()).isEqualTo("https://www.trendyol.com/home");
        assertThat(shortLinkResponse.getShortLink()).isEqualTo("https://www.trendyol.com/ppyaU");
        verify(this.repository).findById(any());
    }

    @Test
    void should_throw_ResourceNotFoundException_when_findById_returns_optional_empty() {
        //given
        doReturn(Optional.empty()).when(this.repository).findById(any());
        //when then
        assertThrows(ResourceNotFoundException.class, () -> this.shortLinkService.getById(123L));
        verify(this.repository).findById(any());
    }
}

