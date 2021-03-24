package com.example.url.service.impl;

import com.example.url.controller.UrlManagementController;
import com.example.url.dto.ResponseBean;
import com.example.url.dto.UrlDto;
import com.example.url.entity.UrlManagement;
import com.example.url.exception.UrlException;
import com.example.url.repository.UrlManagementRepository;
import com.example.url.service.UrlService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.validator.UrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {

    private static final Logger LOGGER = LogManager.getLogger(UrlServiceImpl.class);

    @Autowired
    UrlManagementRepository repo;

    @Autowired
    private RedisTemplate<String, UrlManagement> redisTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("${redis.ttl}")
    private long ttl;

    @Override
    public String createUrl(String url) throws JsonProcessingException {
        // Using commons-validator library to validate the input URL.
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (!urlValidator.isValid(url)) {
            LOGGER.info("Invalid URL.");
           throw new UrlException("Invalid URL.", null);
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
         return shortUrl;

        } else {
            repo.save(urlManagement);
            redisTemplate.opsForValue().set(urlManagement.getId(), urlManagement, ttl);
            LOGGER.info("URL Management = {}", mapper.writeValueAsString(urlManagement));
            return shortUrl;
        }
    }

    @Override
    public String getOriginalUrl(String id) {
        String originalUrl = null;
        // Get from redis.
        final UrlManagement redis_urlManagement = redisTemplate.opsForValue().get(id);
        if (!Objects.isNull(redis_urlManagement)) {
            originalUrl = redis_urlManagement.getUrl();
        } else {
            final Optional<UrlManagement> urlDto = repo.findById(id);
            if (!urlDto.isPresent() || urlDto.isEmpty()) {
                return originalUrl;
            } else {
                LOGGER.info("URL retrieved = {}", urlDto.get().getUrl());
                originalUrl= urlDto.get().getUrl();
            }
        }

        return originalUrl;
    }

    @Override
    public List<UrlManagement> getAllUrls() {
        List<UrlManagement> urls = repo.findAll();
        return urls;
    }
}
