package com.example.cart.applications;

import com.example.cart.applications.grpcclient.GrpcGetUserService;
import com.example.cart.applications.grpcclient.GrpcMatchProductIdsService;
import com.example.cart.domains.Cart;
import com.example.cart.domains.CartItem;
import com.example.cart.dtos.GetUserDto;
import com.example.cart.dtos.ProductOptionNotMatch;
import com.example.cart.exceptions.NameNotExist;
import com.example.cart.repositories.CartItemRepository;
import com.example.cart.repositories.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class AddCartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final GrpcGetUserService grpcGetUserService;
    private final GrpcMatchProductIdsService grpcMatchProductIdsService;

    public AddCartService(CartRepository cartRepository, CartItemRepository cartItemRepository, GrpcGetUserService grpcGetUserService, GrpcMatchProductIdsService grpcMatchProductIdsService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.grpcGetUserService = grpcGetUserService;
        this.grpcMatchProductIdsService = grpcMatchProductIdsService;
    }


    public String addCart(String name, Long productId, Long productFirstOptionId, Long productSecondOptionId, Integer quantity) {

        GetUserDto user = grpcGetUserService.getUser(name);

        if (user.id() == 0) {
            throw new NameNotExist();
        }

        boolean isMatchProduct = grpcMatchProductIdsService.matchProductIds(
                productId,
                productFirstOptionId,
                productSecondOptionId
        );

        if (!isMatchProduct){
            throw new ProductOptionNotMatch();
        }

        Cart cart = cartRepository.findTop1ByUserId(user.id());


        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductIdAndProductFirstOptionIdAndProductSecondOptionId(
                        cart.id(),
                        productId,
                        productFirstOptionId,
                        productSecondOptionId);

        if(cartItem == null) {
            cartItem = new CartItem(cart, productId, productFirstOptionId, productSecondOptionId, quantity);
        } else cartItem.addQuantity(quantity);


        cartItemRepository.save(cartItem);

        return "success";


    }


}
