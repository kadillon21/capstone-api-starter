package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Order;
import org.yearup.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository)
    {
        this.orderRepository = orderRepository;
    }

    public Order checkOut(Order order){
        return null;
    }
    
}
