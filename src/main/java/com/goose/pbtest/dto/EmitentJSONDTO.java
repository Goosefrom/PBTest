package com.goose.pbtest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmitentJSONDTO {

    private String bin;

    @JsonProperty("min_range")
    private String minRange;

    @JsonProperty("max_range")
    private String maxRange;

    @JsonProperty("alpha_code")
    private String code;

    @JsonProperty("bank_name")
    private String name;
}
