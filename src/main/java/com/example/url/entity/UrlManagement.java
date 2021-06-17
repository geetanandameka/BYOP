package com.example.url.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "UrlManagement")
public class UrlManagement implements Serializable {

    @Id
    private String id;
    private String url;
    private Date created;
    private Date expire;
}
