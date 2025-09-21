package com.example.pix.controller;

import com.example.pix.dto.PixRequest;
import com.example.pix.dto.PixResponse;
import com.example.pix.model.PixPayment;
import com.example.pix.service.PixService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/pix")
public class PixController {

    private final PixService service;

    public PixController(PixService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PixRequest request, UriComponentsBuilder uriBuilder) {
        PixPayment created = service.create(new PixPayment(null, request.getPayer(), request.getReceiver(), request.getAmount()));
        URI location = uriBuilder.path("/api/pix/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PixResponse> getById(@PathVariable String id) {
        PixPayment pix = service.getById(id);
        return ResponseEntity.ok(new PixResponse(pix.getId(), pix.getPayer(), pix.getReceiver(), pix.getAmount()));
    }
}
