package com.dx.Booker.serviceinterface;

import com.dx.Booker.generator.po.Cart;

public interface CartService {
    public void updateBookInCart(Cart cart);
    public void insertCart(Cart cart);
    public void deleteCart(Cart cart);
    public void test();
}
