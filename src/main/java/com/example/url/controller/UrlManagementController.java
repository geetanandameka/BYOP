package com.example.url.controller;

import com.example.url.dto.ResponseBean;
import com.example.url.entity.UrlManagement;
import com.example.url.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private UrlService urlService;

    public UrlManagementController(UrlService urlService) {
        this.urlService = urlService;
    }

    @ApiOperation(value = "Fetching the urls", tags = {"UrlManagement"})
    @GetMapping("/Urls")
    public ResponseEntity getAllCreatedUrls() {

        List<UrlManagement> urls = urlService.getAllUrls();
        ResponseBean responseBean = ResponseBean.builder().status(HttpStatus.OK.name())
                .statusMsg(HttpStatus.OK.getReasonPhrase())
                .userMsg(HttpStatus.OK.getReasonPhrase())
                .data(urls)
                .build();
        return ResponseEntity.ok(responseBean);
    }


    @ApiOperation(value = "Create Url", tags = {"UrlManagement"})
    @PostMapping
    public ResponseEntity create(@RequestBody final String url) throws IOException {
        ResponseBean responseBean;
        String shortUrl = urlService.createUrl(url);
        responseBean= ResponseBean.builder().status(HttpStatus.OK.name())
                    .statusMsg(HttpStatus.OK.getReasonPhrase())
                    .userMsg(HttpStatus.OK.getReasonPhrase())
                    .data(shortUrl)
                    .build();
        return ResponseEntity.ok(responseBean);
    }


    @ApiOperation(value = "Fetching original url", tags = {"UrlManagement"})
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBean> getUrl(@PathVariable final String id, HttpServletResponse httpServletResponse) {

        String originalUrl = urlService.getOriginalUrl(id);
        ResponseBean responseBean = ResponseBean.builder().status(HttpStatus.OK.name())
                .statusMsg(HttpStatus.OK.getReasonPhrase())
                .userMsg(HttpStatus.OK.getReasonPhrase())
                .data(originalUrl)
                .build();
        return ResponseEntity.ok(responseBean);
    }
}
