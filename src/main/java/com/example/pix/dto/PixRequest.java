package com.example.pix.dto;

import jakarta.validation.constraints.*;
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
	
    @NotBlank(message = "{payer.not.blank}")
    @Size(min = 3, max = 50, message = "{payer.size}")
    @Schema(description = "Identificador do pagador", type = "String", example = "payer-123")
    private String payer;
    
    @NotBlank(message = "{receiver.not.blank}")
    @Size(min = 3, max = 50, message = "{receiver.size}")
    @Schema(description = "Identificador do recebedor", type = "String", example = "receiver-456")
    private String receiver;
    
    @NotNull(message = "{amount.not.null}")
    @Positive(message = "{amount.positive}")
    @Digits(integer = 8, fraction = 2, message = "{amount.digits}")
    @Schema(description = "Valor da transação", type = "BigDecimal", example = "150.75")
    private BigDecimal amount;

    @Size(max = 255, message = "{description.size}")
    @Schema(description = "Descrição da transação", type = "String", example = "Pagamento de serviços")
    private String description;

    public boolean amountIsValid() {
		return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;	
    }

}
