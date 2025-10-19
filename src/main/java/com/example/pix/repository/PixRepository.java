package com.example.pix.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pix.model.PixPayment;

import lombok.Generated;

@Repository
@Generated
public interface PixRepository extends JpaRepository<PixPayment, UUID> { }
