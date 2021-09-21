package com.example.linkconverterservice.controllers;

import com.example.linkconverterservice.model.request.LinkRequest;
import com.example.linkconverterservice.model.response.DeepLinkResponse;
import com.example.linkconverterservice.model.response.LinkResponse;
import com.example.linkconverterservice.service.LinkConverterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LinkConverterController.class)
class LinkConverterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LinkConverterService linkConverterService;


    @Test
    void should_get_deepLink_from_link() throws Exception {
        //given
        DeepLinkResponse deepLinkResponse = DeepLinkResponse.builder().deepLink("trendyol:///product?contentId=asdqwe").build();
        doReturn(deepLinkResponse).when(this.linkConverterService).convertToDeepLink(any());

        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setUrl("https://trendyol.com/product?contentId=asdqwe");
        String content = (new ObjectMapper()).writeValueAsString(linkRequest);

        //when then
        mockMvc.perform(post("/convert/link-to-deepLink")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deepLink").value("trendyol:///product?contentId=asdqwe"))
                .andReturn();
    }

    @Test
    void should_get_link_from_deeplink() throws Exception {
        //given
        LinkResponse linkResponse = LinkResponse.builder().link("https://trendyol.com/product?contentId=asdqwe").build();
        doReturn(linkResponse).when(this.linkConverterService).convertToLink(any());

        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setUrl("trendyol:///product?contentId=asdqwe");
        String content = (new ObjectMapper()).writeValueAsString(linkRequest);

        //when then
        mockMvc.perform(post("/convert/deepLink-to-link")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link").value("https://trendyol.com/product?contentId=asdqwe"))
                .andReturn();
    }
}

