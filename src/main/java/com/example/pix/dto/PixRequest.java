package com.example.pix.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Generated
@AllArgsConstructor
@NoArgsConstructor
public class PixRequest {
    @NotBlank
    private String payer;
    @NotBlank
    private String receiver;
    @NotNull
    @Positive
    private BigDecimal amount;

}
