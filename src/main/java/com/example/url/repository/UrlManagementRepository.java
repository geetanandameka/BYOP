package com.example.url.repository;

import com.example.url.entity.UrlManagement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlManagementRepository extends MongoRepository<UrlManagement, String> {


//    @Cacheable("UrlManagement")
//    Optional<UrlManagement> save(UrlManagement urlManagement);
}
