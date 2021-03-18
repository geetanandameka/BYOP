package com.example.url.controller;

import com.example.url.dto.ResponseBean;
import com.example.url.entity.UrlManagement;
import com.example.url.repository.UrlManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class UrlManagementController {

    @Autowired
    UrlManagementRepository repo;

    @GetMapping("/fetchAllUrls")
    public ResponseEntity getResourceConfig(){

        List<UrlManagement> urls = repo.findAll();

        ResponseBean responseBean = ResponseBean.builder().status(HttpStatus.OK.name())
                .statusMsg(HttpStatus.OK.getReasonPhrase())
                .userMsg(HttpStatus.OK.getReasonPhrase())
                .data(urls)
                .build();
        return ResponseEntity.ok(responseBean);
    }
}
