package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingCartService {
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId) {
        // load the user's cart rows, look up each product, and build the ShoppingCart
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);
        Map<Integer, ShoppingCartItem> shoppingCartItems = new HashMap<>();
        ShoppingCart shoppingCart = new ShoppingCart();

        int i = 0;
        for (CartItem cartItem : cartItems) {
            i++;
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setProduct(productService.getById(cartItem.getProductId()));
            shoppingCartItem.setQuantity(cartItem.getQuantity());
            shoppingCartItems.put(i, shoppingCartItem);
        }

        shoppingCart.setItems(shoppingCartItems);
        return shoppingCart;
    }

    public ShoppingCart add(int productId, int userId) {

        // Checks if product is already in users cart if and if it is increases the quantity by one.
        if (shoppingCartRepository.findByUserIdAndProductId(userId, productId) != null) {
            CartItem existing = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
            int quantity = existing.getQuantity();
            existing.setQuantity(quantity += 1);
            shoppingCartRepository.save(existing);
            return getByUserId(userId);
        }

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(0);
        cartItem.setProductId(productId);
        cartItem.setUserId(userId);
        shoppingCartRepository.save(cartItem);

        return getByUserId(userId);
    }

    public ShoppingCart update(int productId, int userId, int quantity) {

        CartItem existing = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        existing.setQuantity(quantity);
        shoppingCartRepository.save(existing);
        return getByUserId(userId);
    }

    public ShoppingCart delete(int userId) {
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);

        for (CartItem cartItem : cartItems) {
            shoppingCartRepository.deleteById(cartItem.getCartItemId());
        }
        return getByUserId(userId);
    }
}
