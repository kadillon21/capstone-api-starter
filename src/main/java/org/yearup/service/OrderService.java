package org.yearup.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.yearup.models.Order;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.OrderLineItem;
import org.yearup.models.dto.OrderDto;
import org.yearup.models.Profile;
import org.yearup.repository.OrderRepository;
import org.yearup.repository.ProfileRepository;
import org.yearup.repository.OrderLineItemRepository;

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
            OrderLineItemRepository orderLineItemRepository)
    {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
        this.profileRepository = profileRepository;
        this.orderLineItemRepository = orderLineItemRepository;
    }

    public OrderDto checkOut(int userId){

        LocalDate date = LocalDate.now();
        ShoppingCart cart = shoppingCartService.getByUserId(userId);
        Profile profile = profileRepository.findById(userId).orElseThrow();
        List<OrderLineItem> lineItems = new ArrayList<>();
        Order order = new Order();

        order.setUserId(userId);
        order.setDate(date.toString());
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());

        Order savedOrder = orderRepository.save(order);

        for(ShoppingCartItem item: cart.getItems().values()){
            OrderLineItem lineItem = new OrderLineItem();
            lineItem.setOrderId(savedOrder.getOrderId());
            lineItem.setProductId(item.getProductId());
            lineItem.setSalesPrice(item.getProduct().getPrice());
            lineItem.setQuantity(item.getQuantity());
            lineItem.setDiscount(item.getDiscountPercent());
            lineItems.add(lineItem);
            orderLineItemRepository.save(lineItem);
        }

        orderRepository.save(order);
        shoppingCartService.delete(userId);

        return new OrderDto(order, lineItems);
    }
    
}
