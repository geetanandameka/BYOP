package com.example.url.controller;

import com.example.url.dto.ResponseBean;
import com.example.url.dto.UrlDto;
import com.example.url.entity.UrlManagement;
import com.example.url.exception.UrlException;
import com.example.url.repository.UrlManagementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.validator.UrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequestMapping("/api/v1")
@RestController
@Api(value = "UrlManagement", tags = {"UrlManagement"})
public class UrlManagementController {
    private static final Logger LOGGER = LogManager.getLogger(UrlManagementController.class);
    @Autowired
    UrlManagementRepository repo;

    @Autowired
    private RedisTemplate<String, UrlManagement> redisTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("${redis.ttl}")
    private long ttl;

    @ApiOperation(value = "Fetching the urls", tags = {"UrlManagement"})
    @GetMapping("/fetchAllUrls")
    public ResponseEntity getResourceConfig() {

        List<UrlManagement> urls = repo.findAll();

        ResponseBean responseBean = ResponseBean.builder().status(HttpStatus.OK.name())
                .statusMsg(HttpStatus.OK.getReasonPhrase())
                .userMsg(HttpStatus.OK.getReasonPhrase())
                .data(urls)
                .build();
        return ResponseEntity.ok(responseBean);
    }


    //    @CachePut(value="UrlManagement", key="#UrlManagement.id")
    @ApiOperation(value = "Create Url", tags = {"UrlManagement"})
    @PostMapping
    public ResponseEntity create(@RequestBody final String url) throws IOException {
        ResponseBean responseBean;
//         Using commons-validator library to validate the input URL.
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (!urlValidator.isValid(url)) {
            // Invalid url return HTTP 400 bad request.
            LOGGER.info("Invalid URL.");
            return ResponseEntity.badRequest().body(new UrlException("Invalid URL.", null));
        }
        LOGGER.info("valid URL.");
        // If valid URL, generate a hash key using guava's murmur3 hashing algorithm.
        UrlDto urlDto = UrlDto.create(url);

        String json = mapper.writeValueAsString(urlDto);

        UrlManagement urlManagement = mapper.readValue(json, UrlManagement.class);
        LOGGER.info("URL id generated = {}", urlDto.getId());

        String shortUrl = "http://localhost:9080/url/{" + urlManagement.getId() + "}";

        final UrlManagement redis_urlManagement = redisTemplate.opsForValue().get(urlManagement.getId());
        if (!Objects.isNull(redis_urlManagement)) {
            responseBean= ResponseBean.builder().status(HttpStatus.OK.name())
                    .statusMsg(HttpStatus.OK.getReasonPhrase())
                    .userMsg(HttpStatus.OK.getReasonPhrase())
                    .data(shortUrl)
                    .build();

        } else {
            repo.save(urlManagement);
            redisTemplate.opsForValue().set(urlManagement.getId(), urlManagement, ttl);
            LOGGER.info("URL Management = {}", mapper.writeValueAsString(urlManagement));
            responseBean= ResponseBean.builder().status(HttpStatus.OK.name())
                    .statusMsg(HttpStatus.OK.getReasonPhrase())
                    .userMsg(HttpStatus.OK.getReasonPhrase())
                    .data(shortUrl)
                    .build();
        }


        return ResponseEntity.ok(responseBean);
    }

    //    @Cacheable(value="UrlManagement", key="#id" )
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBean> getUrl(@PathVariable final String id, HttpServletResponse httpServletResponse) {

        ResponseBean responseBean;

        // Get from redis.
        final UrlManagement urlManagement = redisTemplate.opsForValue().get(id);
        if (!Objects.isNull(urlManagement)) {
            responseBean = ResponseBean.builder().status(HttpStatus.OK.name())
                    .statusMsg(HttpStatus.OK.getReasonPhrase())
                    .userMsg(HttpStatus.OK.getReasonPhrase())
                    .data(urlManagement.getUrl())
                    .build();
        } else {
            final Optional<UrlManagement> urlDto = repo.findById(id);
            if (!urlDto.isPresent() || urlDto.isEmpty()) {
                responseBean = ResponseBean.builder().status(HttpStatus.OK.name())
                        .statusMsg(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .userMsg(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .data(null)
                        .build();
            } else {
                LOGGER.info("URL retrieved = {}", urlDto.get().getUrl());
                responseBean = ResponseBean.builder().status(HttpStatus.OK.name())
                        .statusMsg(HttpStatus.OK.getReasonPhrase())
                        .userMsg(HttpStatus.OK.getReasonPhrase())
                        .data(urlDto.get().getUrl())
                        .build();
            }
        }

        return ResponseEntity.ok(responseBean);
    }
}
