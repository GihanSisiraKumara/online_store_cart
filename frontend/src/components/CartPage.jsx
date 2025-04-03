import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { CART_API_URL } from '../constants/apiConstants';

const CartPage = () => {
  const [cartItems, setCartItems] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const userId = 'user123'; // In a real app, get from auth context

  useEffect(() => {
    fetchCartItems();
  }, []);

  const fetchCartItems = () => {
    setIsLoading(true);
    setError(null);

    axios.get(`${CART_API_URL}/${userId}`)
      .then(response => {
        setCartItems(response.data);
        setIsLoading(false);
      })
      .catch(error => {
        console.error('Error fetching cart items:', error);
        setError('Failed to fetch cart items. Please try again.');
        setIsLoading(false);
      });
  };

  const updateQuantity = (id, newQuantity) => {
    if (newQuantity < 1) return;
    
    axios.put(`${CART_API_URL}/update/${id}?quantity=${newQuantity}`)
      .then(response => {
        fetchCartItems();
      })
      .catch(error => {
        console.error('Error updating cart item:', error);
        setError('Failed to update item quantity. Please try again.');
      });
  };

  const removeItem = (id) => {
    axios.delete(`${CART_API_URL}/remove/${id}`)
      .then(response => {
        fetchCartItems();
      })
      .catch(error => {
        console.error('Error removing cart item:', error);
        setError('Failed to remove item from cart. Please try again.');
      });
  };

  const calculateTotal = () => {
    return cartItems.reduce((total, item) => total + item.totalPrice, 0).toFixed(2);
  };

  if (isLoading) {
    return <div>Loading cart items...</div>;
  }

  if (error) {
    return <div className="error-message">{error}</div>;
  }

  return (
    <div className="cart-page">
      <h2>Your Shopping Cart</h2>
      {cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <>
          <div className="cart-items">
            {cartItems.map(item => (
              <div key={item.id} className="cart-item">
                <div className="item-details">
                  <h3>{item.productName}</h3>
                  <p>${item.price.toFixed(2)}</p>
                </div>
                <div className="item-actions">
                  <div className="quantity-control">
                    <button onClick={() => updateQuantity(item.id, item.quantity - 1)}>-</button>
                    <span>{item.quantity}</span>
                    <button onClick={() => updateQuantity(item.id, item.quantity + 1)}>+</button>
                  </div>
                  <p className="item-total">${item.totalPrice.toFixed(2)}</p>
                  <button className="remove-btn" onClick={() => removeItem(item.id)}>Remove</button>
                </div>
              </div>
            ))}
          </div>
          <div className="cart-summary">
            <h3>Total: ${calculateTotal()}</h3>
            <button className="checkout-btn">Proceed to Checkout</button>
          </div>
        </>
      )}
    </div>
  );
};

export default CartPage;