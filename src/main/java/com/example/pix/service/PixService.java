package com.example.pix.service;

import com.example.pix.dto.PixRequest;
import com.example.pix.enuns.PaymentStatus;
import com.example.pix.exception.NotFoundException;
import com.example.pix.mapper.PixMapper;
import com.example.pix.model.PixPayment;
import com.example.pix.repository.PixRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class PixService {

	private final PixRepository repository;
	private final PixMapper mapper;

	public PixService(PixRepository repository, PixMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public PixPayment create(PixRequest pix) {
		
		if (pix.getAmount() == null || pix.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		
		PixPayment newPix = mapper.toEntity(pix);

		newPix.setStatus(PaymentStatus.PROCESSING.toString());
		newPix.setCreatedAt(LocalDateTime.now()); 
		
		return repository.save(newPix);
	}

	public PixPayment getById(UUID id) {
		return repository.findById(id).orElseThrow(() -> new NotFoundException("Pix not found: " + id));
	}
	
}
