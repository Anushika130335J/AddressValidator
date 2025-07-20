package org.example.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AutoCmpltdAddress {

    @JsonProperty("a")
    private String address;
    @JsonProperty("pxid")
    private String pxid;
    @JsonProperty("v")
    private int version;

}
