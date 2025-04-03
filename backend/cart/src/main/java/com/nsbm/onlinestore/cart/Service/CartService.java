package com.nsbm.onlinestore.cart.Service;

import com.nsbm.onlinestore.cart.Model.CartItem;
import com.nsbm.onlinestore.cart.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// cart servicess -> interface
// cartservicessImpm and move this class
// create a custem exception  CartNotFound
//security

@Service

public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<CartItem> getCartItems(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public CartItem addItemToCart(CartItem cartItem) {
        Optional<CartItem> existingCartItem = cartRepository.findByUserIdAndProductId(
                cartItem.getUserId(), cartItem.getProductId());

        if (existingCartItem.isPresent()) {
            CartItem item = existingCartItem.get();
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            return cartRepository.save(item);
        }

        return cartRepository.save(cartItem);
    }

    public CartItem updateCartItem(String id, int quantity) {
        Optional<CartItem> optionalCartItem = cartRepository.findById(id);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(quantity);
            return cartRepository.save(cartItem);
        }
        return null;
    }

    public void removeCartItem(String id) {
        cartRepository.deleteById(id);
    }

    public void clearCart(String userId) {
        List<CartItem> userItems = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(userItems);
    }
}
