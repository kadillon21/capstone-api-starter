package org.yearup.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.models.User;
import org.yearup.models.dto.OrderDto;
import org.yearup.service.OrderService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("orders")
@CrossOrigin
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderDto> checkout(Principal principal) {

        int userId = getUserId(principal);
        return ResponseEntity.ok(orderService.checkOut(userId));
    }

    private int getUserId(Principal principal) {
        String userName = principal.getName();

        User user = userService.getByUserName(userName);
        return user.getId();
    }
}
