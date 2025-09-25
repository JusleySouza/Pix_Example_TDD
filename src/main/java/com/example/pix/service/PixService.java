package com.example.pix.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.pix.dto.PixRequest;
import com.example.pix.dto.PixResponse;
import com.example.pix.exception.NotFoundException;
import com.example.pix.mapper.PixMapper;
import com.example.pix.model.PixPayment;
import com.example.pix.repository.PixRepository;

@Service
public class PixService {

	private final PixRepository repository;
	private final PixMapper mapper;

	public PixService(PixRepository repository, PixMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public PixPayment create(PixRequest pix) {
		if (!pix.amountIsValid()) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		return repository.save(mapper.toEntity(pix));
	}

	public PixResponse getById(UUID id) {
		PixPayment pix = repository.findById(id).orElseThrow(() -> new NotFoundException("Pix not found: " + id));		
		return  mapper.toResponse(pix);
	}
	
}
