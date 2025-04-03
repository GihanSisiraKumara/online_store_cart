package com.nsbm.onlinestore.cart.util;

public class Constant {
    public static final String BASE_API_URL = "/api";
    public static final String CART_API_URL = BASE_API_URL + "/cart";

    public static final String CART_ADD_URL = CART_API_URL + "/add";
    public static final String CART_UPDATE_URL = CART_API_URL + "/update";
    public static final String CART_REMOVE_URL = CART_API_URL + "/remove";
    public static final String CART_GET_URL = CART_API_URL + "/{userId}";
}
