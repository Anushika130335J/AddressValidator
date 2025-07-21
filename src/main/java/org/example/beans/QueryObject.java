package org.example.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class QueryObject {

    @Nonnull
    @JsonProperty("q")
    private String query;
}
