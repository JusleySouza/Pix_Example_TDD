package com.example.pix.mapper;

import org.springframework.stereotype.Component;

import com.example.pix.dto.PixRequest;
import com.example.pix.dto.PixResponse;
import com.example.pix.model.PixPayment;

import lombok.Generated;

@Component
@Generated
public class PixMapper {

    public PixPayment toEntity(PixRequest request) {
    	
        if (request == null) {
            return null;
        }
        
        PixPayment payment = new PixPayment();
        payment.setPayer(request.getPayer());
        payment.setReceiver(request.getReceiver());
        payment.setAmount(request.getAmount());
        payment.setDescription(request.getDescription());
        return payment;
    }

    public PixResponse toResponse(PixPayment payment) {
    	
        if (payment == null) {
            return null;
        }
        
        PixResponse response = new PixResponse();
        response.setId(payment.getId());
        response.setPayer(payment.getPayer());
        response.setReceiver(payment.getReceiver());
        response.setAmount(payment.getAmount());
        response.setDescription(payment.getDescription());
        response.setStatus(payment.getStatus());
        response.setCreatedAt(payment.getCreatedAt());
        return response;
    }
}
