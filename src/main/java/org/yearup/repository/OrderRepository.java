package org.yearup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yearup.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>
{
    Order getByOrderId(int orderId);
    Order getByUserId(int userId);
    List<Order> findByUserId(int userId);
}
