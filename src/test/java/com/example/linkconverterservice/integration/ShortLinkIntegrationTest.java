package com.example.linkconverterservice.integration;

import com.example.linkconverterservice.db.entity.ShortLink;
import com.example.linkconverterservice.db.repository.ShortLinkRepository;
import com.example.linkconverterservice.model.request.LinkRequest;
import com.example.linkconverterservice.model.response.ShortLinkResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.Table;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortLinkIntegrationTest extends BaseIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ShortLinkRepository shortLinkRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * If containers are defined as static, they wont be rebuilt for each test. For these purpose you have truncate table.
     * If you want a new db for each test you should NOT define it as static.
     * static container reduces runtime of tests (not first test :D)
     * with this way we can clear our relational db table.
     * look at here https://stackoverflow.com/questions/62870345/how-to-clean-database-tables-after-each-integration-test-when-using-spring-boot/69230955#69230955
     */
    @AfterEach
    public void execute() {
        jdbcTemplate.execute("TRUNCATE TABLE " + ShortLink.class.getAnnotation(Table.class).name());
        jdbcTemplate.execute("ALTER SEQUENCE " + ShortLink.class.getAnnotation(Table.class).name() + "_id_seq RESTART");
    }


    @Test
    public void should_create_shortLink_then_return_201_with_location() {
        //given
        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setUrl("https://trendyol.com/product?contentId=asdqwe");

        String baseUrl = getBaseUrl("/api/short-links");

        //when
        ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl, linkRequest, Void.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getFirst("location")).isEqualTo(baseUrl + "/1");
    }

    @Test
    public void should_create_shortLink_and_get_it_from_db() {
        //given
        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setUrl("https://trendyol.com/product?contentId=asdqwe");

        String baseUrl = getBaseUrl("/api/short-links");

        //when
        ResponseEntity<Void> createShortLinkResponse = restTemplate.postForEntity(baseUrl, linkRequest, Void.class);

        //then
        assertThat(createShortLinkResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        String location = createShortLinkResponse.getHeaders().getFirst("location");
        assertThat(location).isEqualTo(baseUrl + "/1");


        ResponseEntity<ShortLinkResponse> getShortLinkResponse = restTemplate.getForEntity(location, ShortLinkResponse.class);
        assertThat(getShortLinkResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getShortLinkResponse.getBody()).isNotNull();
        assertThat(getShortLinkResponse.getBody().getId()).isEqualTo(1L);
        assertThat(getShortLinkResponse.getBody().getLink()).isEqualTo(linkRequest.getUrl());
    }
}
