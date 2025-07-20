package org.example.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AutoCompleteRq {
    @JsonProperty("key")
    private String key;
    @JsonProperty("secret")
    private String secret;
    @JsonProperty("q")
    private String query;
    @JsonProperty("format")
    private String format;
    @JsonProperty("delivered")
    private String delivered;
    @JsonProperty("post_box")
    private String postBox;
    @JsonProperty("rural")
    private String rural;
    @JsonProperty("strict")
    private String strict;
    @JsonProperty("region_code")
    private String regionCode;
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("max")
    private String max;
    @JsonProperty("highlight")
    private String highlight;
    @JsonProperty("ascii")
    private String ascii;
}
