package com.nsbm.onlinestore.cart.Controller;

import com.nsbm.onlinestore.cart.Model.CartItem;
import com.nsbm.onlinestore.cart.Service.CartService;
import com.nsbm.onlinestore.cart.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// todo  add comments
// create a utill paccage -> contant package -> hardcode
// basr URL

@RestController
@RequestMapping(Constant.CART_API_URL)
@CrossOrigin("http://localhost:5173") //For React frontend

public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable String userId) {
        return new ResponseEntity<>(cartService.getCartItems(userId), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody CartItem cartItem) {
        return new ResponseEntity<>(cartService.addItemToCart(cartItem), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable String id, @RequestParam int quantity) {
        CartItem updatedItem = cartService.updateCartItem(id, quantity);
        if (updatedItem != null) {
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable String id) {
        cartService.removeCartItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
