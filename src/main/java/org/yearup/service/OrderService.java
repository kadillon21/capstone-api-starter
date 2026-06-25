package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.*;
import org.yearup.models.dto.OrderDto;
import org.yearup.repository.OrderLineItemRepository;
import org.yearup.repository.OrderRepository;
import org.yearup.repository.ProfileRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final ProfileRepository profileRepository;
    private final OrderLineItemRepository orderLineItemRepository;

    public OrderService(
            OrderRepository orderRepository,
            ShoppingCartService shoppingCartService,
            ProfileRepository profileRepository,
            OrderLineItemRepository orderLineItemRepository) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
        this.profileRepository = profileRepository;
        this.orderLineItemRepository = orderLineItemRepository;
    }

    public OrderDto checkOut(int userId) {

        ShoppingCart cart = shoppingCartService.getByUserId(userId);
        Profile profile = profileRepository.findById(userId).orElseThrow();

        Order order = createOrder(userId, profile);
        List<OrderLineItem> lineItems = createLineItems(cart, order);

        shoppingCartService.delete(userId);

        return new OrderDto(order, lineItems);
    }

    private Order createOrder(int userId, Profile profile) {

        Order order = new Order();
        LocalDate date = LocalDate.now();

        order.setUserId(userId);
        order.setDate(date.toString());
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());

        orderRepository.save(order);

        return order;
    }

    private List<OrderLineItem> createLineItems(ShoppingCart cart, Order order) {

        List<OrderLineItem> lineItems = new ArrayList<>();
        for (ShoppingCartItem item : cart.getItems().values()) {
            OrderLineItem lineItem = new OrderLineItem();
            lineItem.setOrderId(order.getOrderId());
            lineItem.setProductId(item.getProductId());
            lineItem.setSalesPrice(item.getProduct().getPrice());
            lineItem.setQuantity(item.getQuantity());
            lineItem.setDiscount(item.getDiscountPercent());
            lineItems.add(lineItem);
            orderLineItemRepository.save(lineItem);
        }

        return lineItems;
    }


}
