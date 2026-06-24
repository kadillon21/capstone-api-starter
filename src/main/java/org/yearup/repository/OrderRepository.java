package org.yearup.repository;

import org.apache.catalina.servlets.DefaultServlet.SortManager.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yearup.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>
{
    Order getByOrderId(int orderId);
}
