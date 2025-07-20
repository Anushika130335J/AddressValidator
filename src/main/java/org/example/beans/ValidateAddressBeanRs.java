package org.example.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ValidateAddressBeanRs {

    @JsonProperty("matched")
    boolean matched;
    @JsonProperty("success")
    boolean rqStatusSuccess;


}
