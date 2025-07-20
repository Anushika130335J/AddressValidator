package org.example.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ValidateAddressBeanRq {
    @JsonProperty("key")
    private String key;
    @JsonProperty("secret")
    private String secret;
    @JsonProperty("format")
    private String format;
    @JsonProperty("q")
    private String q;
    @JsonProperty("post_box")
    private String post_box;
    @JsonProperty("region_code")
    private String region_code;
    @JsonProperty("census")
    private String census;
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("ascii")
    private String ascii;
}

