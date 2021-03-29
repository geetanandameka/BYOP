package com.example.url.repository.impl;

import com.example.url.dto.UrlDto;
import com.example.url.repository.UrlRedisRepo;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UrlRedisRepoImpl implements UrlRedisRepo {

    private RedisTemplate<String, UrlDto> redisTemplate;
    private HashOperations hashOperations; //to access Redis cache

    public UrlRedisRepoImpl(RedisTemplate<String, UrlDto> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(UrlDto urlDto) {
        hashOperations.put("UrlManagement", urlDto.getId(), urlDto);
    }


    @Override
    public Map<String, UrlDto> findAll() {
        return hashOperations.entries("UrlManagement");
    }

    @Override
    public UrlDto findById(String id) {
        return (UrlDto)hashOperations.get("UrlManagement",id);
    }

    @Override
    public void update(UrlDto urlDto) {
        hashOperations.put("UrlManagement", urlDto.getId(), urlDto);
    }


    @Override
    public void delete(String id) {
        hashOperations.delete("UrlManagement",id);
    }
}
