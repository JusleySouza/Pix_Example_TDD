package com.example.pix.service;

import com.example.pix.exception.NotFoundException;
import com.example.pix.model.PixPayment;
import com.example.pix.repository.PixRepository;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class PixService {

	private final PixRepository repository;

	public PixService(PixRepository repository) {
		this.repository = repository;
	}

	public PixPayment create(PixPayment pix) {
		if (pix.getAmount() == null || pix.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		return repository.save(pix);
	}

	public PixPayment getById(String id) {
		return repository.findById(id).orElseThrow(() -> new NotFoundException("Pix not found: " + id));
	}
	
}
