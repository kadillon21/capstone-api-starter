package org.yearup.controllers;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.models.CartItem;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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



    // each method in this controller requires a Principal object as a parameter
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ShoppingCart getCart(Principal principal)
    {
        int userId = getUserID(principal);
        // use the shoppingCartService to get all items in the cart and return the cart
        return shoppingCartService.getByUserId(userId);
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be added)
    // return the updated cart with status 201 Created

    @PostMapping("/products/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CartItem> addProduct (Principal principal, @RequestBody CartItem cartItem){
        CartItem saved = shoppingCartService.add(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated; return the cart (200 OK)
    @PutMapping("{id}")
    @PreAuthorize("permitAll()")
    public ShoppingCart updateProduct (Principal principal, int id) {
        int userId = getUserID(principal);
        return null;
    }

    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart  - return the (now empty) cart so the front end can refresh it (200 OK)
    @DeleteMapping("{id}")
    @PreAuthorize("permitAll()")
    public ShoppingCart deleteProdcut (Principal principal, int id) {
        int userId = getUserID(principal);
        return null;
    }

    private int getUserID (Principal principal) {
        String userName = principal.getName();

        User user = userService.getByUserName(userName);
        return user.getId();
    }
}
