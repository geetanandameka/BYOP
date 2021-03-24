package com.example.url.service;

import com.example.url.entity.UrlManagement;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface UrlService {

     String createUrl(final String url) throws JsonProcessingException;

     String getOriginalUrl(final String id);

     List<UrlManagement> getAllUrls();
}
