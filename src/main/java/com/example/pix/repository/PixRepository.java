package com.example.pix.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.example.pix.model.PixPayment;

@Repository
public class PixRepository {
    private final Map<String, PixPayment> store = new ConcurrentHashMap<>();

    public PixPayment save(PixPayment pix) {
        if (pix.getId() == null) {
            pix.setId(UUID.randomUUID().toString());
        }
        store.put(pix.getId(), pix);
        return pix;
    }

    public Optional<PixPayment> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
    
}
