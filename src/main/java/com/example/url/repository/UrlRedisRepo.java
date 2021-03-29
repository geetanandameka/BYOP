package com.example.url.repository;

import com.example.url.dto.UrlDto;

import java.util.Map;

public interface UrlRedisRepo {
    void save(UrlDto urlManagement);
    Map<String,UrlDto> findAll();
    UrlDto findById(String id);
    void update(UrlDto user);
    void delete(String id);
}
