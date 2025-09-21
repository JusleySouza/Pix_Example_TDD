package com.example.pix.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Generated
@AllArgsConstructor
@NoArgsConstructor
public class PixResponse {
    private String id;
    private String payer;
    private String receiver;
    private BigDecimal amount;

}
