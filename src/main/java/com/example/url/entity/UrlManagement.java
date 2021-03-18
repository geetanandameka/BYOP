package com.example.url.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "URL_MANAGEMENT")
public class UrlManagement {

    @Id
    public String id;
    private String tinyUrl;
    private String longUrl;
    private String key;
}
