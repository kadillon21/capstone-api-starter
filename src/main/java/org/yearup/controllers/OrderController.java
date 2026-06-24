package org.yearup.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.User;
import org.yearup.models.dto.OrderDto;
import org.yearup.service.UserService;
import org.yearup.service.OrderService;

import java.security.Principal;

@RestController
@RequestMapping("orders")
@CrossOrigin
public class OrderController {

    private UserService userService;
    private OrderService orderService;

    public OrderController(UserService userService, OrderService orderService){
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderDto> checkout(Principal principal){

        int userId = getUserId(principal);
        return ResponseEntity.ok(orderService.checkOut(userId));
    }

    private int getUserId (Principal principal) {
        String userName = principal.getName();

        User user = userService.getByUserName(userName);
        return user.getId();
    }
}
