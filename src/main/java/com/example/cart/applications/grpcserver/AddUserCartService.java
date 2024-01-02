package com.example.cart.applications.grpcserver;

import com.example.cart.domains.Cart;
import com.example.cart.repositories.CartRepository;
import com.example.grpc.AddCartRequest;
import com.example.grpc.AddCartResponse;
import com.example.grpc.AddCartServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AddUserCartService extends AddCartServiceGrpc.AddCartServiceImplBase {

    private final CartRepository cartRepository;

    public AddUserCartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void addCart(AddCartRequest request, StreamObserver<AddCartResponse> responseObserver) {

        long userId = request.getUserId();

        Cart cart = new Cart(userId);

        cartRepository.save(cart);

        AddCartResponse reply = AddCartResponse.newBuilder()
                .setOk(true)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
