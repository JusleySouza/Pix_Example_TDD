package com.example.pix.controller;

import com.example.pix.dto.PixRequest;
import com.example.pix.dto.PixResponse;
import com.example.pix.model.PixPayment;
import com.example.pix.service.PixService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/pix")
@Tag(name = "Pix", description = "Operações relacionadas ao Pix")
public class PixController {

    private final PixService service;

    public PixController(PixService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar pagamento Pix", description = "Cria um pagamento Pix com os detalhes fornecidos")
    public ResponseEntity<Object> create(@Valid @RequestBody PixRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento Pix por ID", description = "Retorna detalhes de uma transação Pix")
    public ResponseEntity<PixResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));   
    }
    
}
