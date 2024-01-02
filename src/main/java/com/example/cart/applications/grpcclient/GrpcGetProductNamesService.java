package com.example.cart.applications.grpcclient;

import com.example.cart.dtos.ProductNamesDto;
import com.example.grpc.GetProductNamesRequest;
import com.example.grpc.GetProductNamesResponse;
import com.example.grpc.GetProductNamesServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcGetProductNamesService {

    private GetProductNamesServiceGrpc.GetProductNamesServiceBlockingStub getProductNamesServiceBlockingStub;
    private ManagedChannel channel;

    public GrpcGetProductNamesService(@Value("${product.grpc.host}") String grpcHost, @Value("${product.grpc.port}") int grpcPort) {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }

    public ProductNamesDto getProductNames(Long productId, Long firstOptionId, Long secondOptionId) {
        GetProductNamesRequest getProductNamesRequest = GetProductNamesRequest.newBuilder()
                .setProductId(productId)
                .setFirstOptionId(firstOptionId)
                .setSecondOptionId(secondOptionId)
                .build();

        getProductNamesServiceBlockingStub = GetProductNamesServiceGrpc.newBlockingStub(channel);
        GetProductNamesResponse getProductNamesResponse = getProductNamesServiceBlockingStub.getProductNames(getProductNamesRequest);

        return new ProductNamesDto(
                getProductNamesResponse.getProductName(),
                getProductNamesResponse.getProductPrice(),
                getProductNamesResponse.getFirstOptionName(),
                getProductNamesResponse.getFirstOptionPrice(),
                getProductNamesResponse.getSecondOptionName(),
                getProductNamesResponse.getSecondOptionPrice()
        );

    }


}
