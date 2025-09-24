package com.example.pix.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
public class PixPayment {
	
    private UUID id;
    private String payer;
    private String receiver;
    private BigDecimal amount;
    private String description;
    private String status;
    private LocalDateTime createdAt;

}