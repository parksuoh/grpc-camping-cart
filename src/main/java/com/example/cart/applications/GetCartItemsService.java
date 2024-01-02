package com.example.cart.applications;

import com.example.cart.applications.grpcclient.GrpcGetProductNamesService;
import com.example.cart.applications.grpcclient.GrpcGetUserService;
import com.example.cart.domains.Cart;
import com.example.cart.domains.CartItem;
import com.example.cart.dtos.CartItemsResponseDto;
import com.example.cart.dtos.GetUserDto;
import com.example.cart.dtos.ProductNamesDto;
import com.example.cart.exceptions.NameNotExist;
import com.example.cart.repositories.CartItemRepository;
import com.example.cart.repositories.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetCartItemsService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final GrpcGetUserService grpcGetUserService;

    private final GrpcGetProductNamesService grpcGetProductNamesService;

    public GetCartItemsService(CartRepository cartRepository, CartItemRepository cartItemRepository, GrpcGetUserService grpcGetUserService, GrpcGetProductNamesService grpcGetProductNamesService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.grpcGetUserService = grpcGetUserService;
        this.grpcGetProductNamesService = grpcGetProductNamesService;
    }

    public List<CartItemsResponseDto> getCartItems(String name) {

        GetUserDto user = grpcGetUserService.getUser(name);

        if (user.id() == 0) {
            throw new NameNotExist();
        }

        Cart cart = cartRepository.findTop1ByUserId(user.id());

        List<CartItem> cartItems = cartItemRepository.findByCart_Id(cart.id());

        return cartItems
                .stream()
                .map(cartItem -> {
                    ProductNamesDto productNames = grpcGetProductNamesService.getProductNames(
                            cartItem.productId(),
                            cartItem.productFirstOptionId(),
                            cartItem.productSecondOptionId()
                    );

                    Long itemUnitPrice =  productNames.productPrice() + productNames.firstOptionPrice() + productNames.secondOptionPrice();
                    Long itemTotalPrice =  itemUnitPrice * cartItem.quantity();


                    return new CartItemsResponseDto(
                            cartItem.id(),
                            cartItem.quantity(),
                            cartItem.productId(),
                            productNames.productName(),
                            productNames.productPrice(),
                            cartItem.productFirstOptionId(),
                            productNames.firstName(),
                            productNames.firstOptionPrice(),
                            cartItem.productSecondOptionId(),
                            productNames.secondName(),
                            productNames.secondOptionPrice(),
                            itemUnitPrice,
                            itemTotalPrice
                    );

                })
                .toList();
    }
}
