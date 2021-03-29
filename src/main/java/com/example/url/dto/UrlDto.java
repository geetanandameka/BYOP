package com.example.url.dto;

import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Getter
@AllArgsConstructor
@RedisHash("UrlManagement")
public class UrlDto implements Serializable {

    private final String id;
    private final String url;
    private final Date created;
    private final Date expire;

    public static UrlDto create(final String url) {
        final String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
        return new UrlDto(id, url, new Date(), new Date());
    }

    /*
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static char[] allowedCharacters = allowedString.toCharArray();
    private static int base = allowedCharacters.length;
    */

    /*
        public static String encode(long input){
        StringBuilder encodedString = new StringBuilder();
        if(input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }
        while (input > 0) {
            encodedString.append(allowedCharacters[(int) (input % base)]);
            input = input / base;
        }
        return encodedString.reverse().toString();
    }
     */

}