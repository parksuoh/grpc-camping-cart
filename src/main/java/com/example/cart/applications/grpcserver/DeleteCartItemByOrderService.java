package com.example.cart.applications.grpcserver;

import com.example.cart.domains.CartItem;
import com.example.cart.repositories.CartItemRepository;
import com.example.grpc.DeleteCartItemByOrderRequest;
import com.example.grpc.DeleteCartItemByOrderResponse;
import com.example.grpc.DeleteCartItemByOrderServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
public class DeleteCartItemByOrderService extends DeleteCartItemByOrderServiceGrpc.DeleteCartItemByOrderServiceImplBase {

    private final CartItemRepository cartItemRepository;

    public DeleteCartItemByOrderService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void deleteCartItem(DeleteCartItemByOrderRequest request, StreamObserver<DeleteCartItemByOrderResponse> responseObserver) {
        long cartItemId = request.getCartItemId();
        boolean ok;

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if (!cartItem.isPresent()) {
            ok = false;
        } else {
            cartItemRepository.delete(cartItem.get());
            ok = true;
        }

        DeleteCartItemByOrderResponse reply = DeleteCartItemByOrderResponse.newBuilder()
                .setOk(ok)
                .build();


        responseObserver.onNext(reply);
        responseObserver.onCompleted();

    }
}
