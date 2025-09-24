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

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@ToString
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request para criação de um pagamento Pix")
public class PixRequest {
	
    @NotBlank
    @Schema(description = "Identificador do pagador", type = "String", example = "payer-123")
    private String payer;
    
    @NotBlank
    @Schema(description = "Identificador do recebedor", type = "String", example = "receiver-456")
    private String receiver;
    
    @NotNull
    @Positive
    @Schema(description = "Valor da transação", type = "BigDecimal", example = "150.75")
    private BigDecimal amount;
    
    @Schema(description = "Descrição da transação", type = "String", example = "Pagamento de serviços")
    private String description;

}
