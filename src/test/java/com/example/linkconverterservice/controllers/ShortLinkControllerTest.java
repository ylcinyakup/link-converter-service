package com.example.linkconverterservice.controllers;

import com.example.linkconverterservice.model.exceptions.ResourceAlreadyExistException;
import com.example.linkconverterservice.model.exceptions.ResourceNotFoundException;
import com.example.linkconverterservice.model.request.LinkRequest;
import com.example.linkconverterservice.model.response.ShortLinkResponse;
import com.example.linkconverterservice.service.ShortLinkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ShortLinkController.class)
class ShortLinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortLinkService shortLinkService;

    @Test
    void should_return_expected_shortLink() throws Exception {
        //given
        ShortLinkResponse shortLinkResponse = ShortLinkResponse.builder()
                .id(1L)
                .link("https://trendyol.com/product?contentId=asdqwe")
                .shortLink("https://trendyol.com/asdqwe")
                .created((LocalDateTime.of(1, 1, 1, 1, 1, 1)))
                .updated((LocalDateTime.of(1, 1, 1, 1, 1, 1)))
                .build();
        doReturn(shortLinkResponse).when(this.shortLinkService).getById(any());

        //when then
        mockMvc.perform(get("/short-links/{id}", 1L)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.link").value("https://trendyol.com/product?contentId=asdqwe"))
                .andExpect(jsonPath("$.shortLink").value("https://trendyol.com/asdqwe"))
                .andExpect(jsonPath("$.created").value(LocalDateTime.of(1, 1, 1, 1, 1, 1).toString()))
                .andExpect(jsonPath("$.updated").value(LocalDateTime.of(1, 1, 1, 1, 1, 1).toString()))
                .andReturn();

    }

    @Test
    void should_throw_ResourceNotFoundException_when_shortLink_not_found() throws Exception {
        //given
        doThrow(ResourceNotFoundException.class).when(this.shortLinkService).getById(any());

        //when then
        mockMvc.perform(get("/short-links/{id}", 1L)
        ).andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void should_create_shortLink() throws Exception {
        ShortLinkResponse shortLinkResponse = ShortLinkResponse.builder()
                .id(1L)
                .link("https://trendyol.com/product?contentId=asdqwe")
                .shortLink("https://trendyol.com/asdqwe")
                .created((LocalDateTime.of(1, 1, 1, 1, 1)))
                .updated((LocalDateTime.of(1, 1, 1, 1, 1)))
                .build();
        doReturn(shortLinkResponse).when(this.shortLinkService).createShortLink(any());

        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setUrl("https://trendyol.com/product?contentId=asdqwe");
        String content = (new ObjectMapper()).writeValueAsString(linkRequest);

        mockMvc.perform(post("/short-links", 1L).contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("location", "http://localhost/short-links/1"))
                .andReturn();
    }

    @Test
    void should_throw_ResourceAlreadyExistException_when_shortLink_already_exist() throws Exception {
        //given
        doThrow(ResourceAlreadyExistException.class).when(this.shortLinkService).createShortLink(any());

        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setUrl("https://trendyol.com/product?contentId=asdqwe");
        String content = (new ObjectMapper()).writeValueAsString(linkRequest);

        //when then
        mockMvc.perform(post("/short-links", 1L).contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}

