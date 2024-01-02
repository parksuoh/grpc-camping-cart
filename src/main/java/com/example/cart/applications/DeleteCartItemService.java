package com.example.cart.applications;

import com.example.cart.applications.grpcclient.GrpcGetUserService;
import com.example.cart.domains.Cart;
import com.example.cart.domains.CartItem;
import com.example.cart.dtos.GetUserDto;
import com.example.cart.exceptions.CartAndCartItemNotMatch;
import com.example.cart.exceptions.CartItemNotExist;
import com.example.cart.exceptions.NameNotExist;
import com.example.cart.repositories.CartItemRepository;
import com.example.cart.repositories.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteCartItemService {


    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final GrpcGetUserService grpcGetUserService;

    public DeleteCartItemService(CartRepository cartRepository, CartItemRepository cartItemRepository, GrpcGetUserService grpcGetUserService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.grpcGetUserService = grpcGetUserService;
    }

    public String deleteCartItem(String name, Long cartItemId) {

        GetUserDto user = grpcGetUserService.getUser(name);

        if (user.id() == 0) {
            throw new NameNotExist();
        }


        Cart cart = cartRepository.findTop1ByUserId(user.id());

        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(CartItemNotExist::new);

        if (cart.id() != cartItem.cart().id()){
            throw new CartAndCartItemNotMatch();
        }

        cartItemRepository.delete(cartItem);

        return "success";
    }
}
