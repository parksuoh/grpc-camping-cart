package com.example.cart.applications.grpcserver;

import com.example.cart.domains.CartItem;
import com.example.cart.repositories.CartItemRepository;
import com.example.grpc.GetCartItemProductInfoRequest;
import com.example.grpc.GetCartItemProductInfoResponse;
import com.example.grpc.GetCartItemProductInfoServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
public class GetCartItemProductInfoService extends GetCartItemProductInfoServiceGrpc.GetCartItemProductInfoServiceImplBase {

    private final CartItemRepository cartItemRepository;

    public GetCartItemProductInfoService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void getCartItemProductInfo(GetCartItemProductInfoRequest request, StreamObserver<GetCartItemProductInfoResponse> responseObserver) {
        long cartItemId = request.getCartItemId();
        GetCartItemProductInfoResponse reply;

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if (!cartItem.isPresent()) {
            reply = GetCartItemProductInfoResponse.newBuilder()
                    .setProductId(0L)
                    .setFirstOptionId(0L)
                    .setSecondOptionId(0L)
                    .setQuantity(0)
                    .build();
        } else {
            reply = GetCartItemProductInfoResponse.newBuilder()
                    .setProductId(cartItem.get().productId())
                    .setFirstOptionId(cartItem.get().productFirstOptionId())
                    .setSecondOptionId(cartItem.get().productSecondOptionId())
                    .setQuantity(cartItem.get().quantity())
                    .build();
        }

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
