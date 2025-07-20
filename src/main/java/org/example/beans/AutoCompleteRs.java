package org.example.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AutoCompleteRs {

    @JsonProperty("completions")
    private List<AutoCmpltdAddress> completions;
    @JsonProperty("paid")
    private boolean paid;
    @JsonProperty("demo")
    private boolean demo;
    @JsonProperty("success")
    private boolean success;

}
