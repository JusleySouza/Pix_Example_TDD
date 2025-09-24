package com.example.pix.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Resposta da API para consultas de transações Pix")
public class PixResponse {

	@Schema(description = "Identificador único da transação Pix", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
	private UUID id;

	@Schema(description = "Identificador do pagador", example = "payer-123")
	private String payer;

	@Schema(description = "Identificador do recebedor", example = "receiver-456")
	private String receiver;

	@Schema(description = "Valor da transação", example = "150.75")
	private BigDecimal amount;

	@Schema(description = "Descrição da transação", example = "Pagamento de serviços")
	private String description;

	@Schema(description = "Status atual da transação", example = "COMPLETED")
	private String status;

	@Schema(description = "Data/hora da criação da transação", example = "2025-09-23T10:15:30")
	private LocalDateTime createdAt;

}
