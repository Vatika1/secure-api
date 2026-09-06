package com.vatika.secureapi.service;

import com.vatika.secureapi.entity.OrderEntity;
import com.vatika.secureapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AuditService auditService;

    @Transactional
    public void createOrder(String clientId, String item){
        auditService.record("attempt: " + clientId + " ordering " + item);
        orderRepository.save(new OrderEntity(clientId, item));
        throw new RuntimeException("boom");
    }
}
