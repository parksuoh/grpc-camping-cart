package com.example.cart.applications.grpcclient;

import com.example.grpc.MatchProductIdsRequest;
import com.example.grpc.MatchProductIdsResponse;
import com.example.grpc.MatchProductIdsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcMatchProductIdsService {

    private MatchProductIdsServiceGrpc.MatchProductIdsServiceBlockingStub matchProductIdsServiceBlockingStub;
    private ManagedChannel channel;


    public GrpcMatchProductIdsService(@Value("${product.grpc.host}") String grpcHost, @Value("${product.grpc.port}") int grpcPort) {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }


    public boolean matchProductIds(Long productId, Long firstOptionId, Long secondOptionId) {

        MatchProductIdsRequest matchProductIdsRequest = MatchProductIdsRequest.newBuilder()
                .setProductId(productId)
                .setFirstOptionId(firstOptionId)
                .setSecondOptionId(secondOptionId)
                .build();

        matchProductIdsServiceBlockingStub = MatchProductIdsServiceGrpc.newBlockingStub(channel);
        MatchProductIdsResponse matchProductIdsResponse = matchProductIdsServiceBlockingStub.matchProductIds(matchProductIdsRequest);

        return matchProductIdsResponse.getOk();
    }




}
