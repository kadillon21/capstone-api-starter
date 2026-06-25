package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.ShoppingCart;
import org.yearup.models.dto.UpdateCartItemDto;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;


import java.security.Principal;

// convert this class to a REST controller
// only logged in users should have access to these actions
@RestController
@RequestMapping("cart")
@CrossOrigin
public class ShoppingCartController
{
    // a shopping cart controller depends on the service layer
    private ShoppingCartService shoppingCartService;
    private UserService userService;


    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService){
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ShoppingCart> getCart(Principal principal)
    {
        if (principal == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        int userId = getUserId(principal);
        
        return ResponseEntity.ok(shoppingCartService.getByUserId(userId));
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be added)
    // return the updated cart with status 201 Created

    @PostMapping("/products/{productId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ShoppingCart> addProduct (Principal principal, @PathVariable int productId){
        if (principal == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        int userId = getUserId(principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCartService.add(productId, userId));
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated; return the cart (200 OK)
    @PutMapping("/products/{productId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ShoppingCart> updateProduct (Principal principal, @PathVariable int productId, @RequestBody UpdateCartItemDto request) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        int userId = getUserId(principal);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCartService.update(productId, userId, request.getQuantity()));
    }

    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart  - return the (now empty) cart so the front end can refresh it (200 OK)
    @DeleteMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ShoppingCart> deleteProdcut (Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCartService.delete(getUserId(principal)));
    }

    private int getUserId (Principal principal) {
        String userName = principal.getName();

        User user = userService.getByUserName(userName);
        return user.getId();
    }
}
