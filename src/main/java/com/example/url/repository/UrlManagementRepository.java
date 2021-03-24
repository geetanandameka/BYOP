package com.example.url.repository;

import com.example.url.entity.UrlManagement;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface  UrlManagementRepository extends MongoRepository<UrlManagement, String> {

//    @Cacheable("UrlManagement")
//    Optional<UrlManagement> save(UrlManagement urlManagement);
}
