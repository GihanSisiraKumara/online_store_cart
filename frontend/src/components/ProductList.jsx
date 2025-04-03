import React, { useState } from 'react';
import axios from 'axios';
import { CART_API_URL } from '../constants/apiConstants';

const ProductList = () => {
  const [products] = useState([
    { id: '1', name: 'Product 1', price: 19.99 },
    { id: '2', name: 'Product 2', price: 29.99 },
    { id: '3', name: 'Product 3', price: 39.99 }
  ]);
  const [error, setError] = useState(null);
  const userId = 'user123'; // In a real app, get from auth context

  const addToCart = (product) => {
    const cartItem = {
      userId: userId,
      productId: product.id,
      productName: product.name,
      price: product.price,
      quantity: 1
    };

    axios.post(`${CART_API_URL}/add`, cartItem)
      .then(response => {
        alert(`${product.name} added to cart!`);
      })
      .catch(error => {
        console.error('Error adding to cart:', error);
        setError('Failed to add item to cart. Please try again.');
      });
  };

  return (
    <div className="product-list">
      {error && <div className="error-message">{error}</div>}
      <h2>Products</h2>
      <div className="products">
        {products.map(product => (
          <div key={product.id} className="product-card">
            <h3>{product.name}</h3>
            <p>${product.price.toFixed(2)}</p>
            <button onClick={() => addToCart(product)}>Add to Cart</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductList;