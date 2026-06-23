package org.yearup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        // load the user's cart rows, look up each product, and build the ShoppingCart
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId); 
        Map<Integer, ShoppingCartItem> shoppingCartItems = new HashMap<>();

        int i = 0;
        for (CartItem cartItem : cartItems) {
            i++;
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setProduct(productService.getById(cartItem.getProductId()));
            shoppingCartItems.put(i, shoppingCartItem);
        }
         
        ShoppingCart shoppingCart = new ShoppingCart(); 
        shoppingCart.setItems(shoppingCartItems);
        return shoppingCart;
    }

    public CartItem add(int productId, int userId) {

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(0);
        cartItem.setProductId(productId);
        cartItem.setUserId(userId);

        return shoppingCartRepository.save(cartItem);
    } 

    // add additional methods here
}
