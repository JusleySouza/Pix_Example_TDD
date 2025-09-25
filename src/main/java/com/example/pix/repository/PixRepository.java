package com.example.pix.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.example.pix.model.PixPayment;

import lombok.Generated;

@Repository
@Generated
public class PixRepository {
    private final Map<UUID, PixPayment> store = new ConcurrentHashMap<>();

    public PixPayment save(PixPayment pix) {
        store.put(pix.getId(), pix);
        return pix;
    }

    public Optional<PixPayment> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }
    
}
