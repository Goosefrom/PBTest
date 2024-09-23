package com.goose.pbtest.dto;

import com.goose.pbtest.config.annotation.NumberConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmitentRequestDTO {
    @NotBlank(message = "Should not be emtpy line")
    @Size(min = 16, max = 16, message = "Card number should be 16 digits only")
    @NumberConstraint
    private String card;
}
