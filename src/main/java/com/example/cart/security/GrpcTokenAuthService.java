package com.example.cart.security;

import com.example.grpc.TokenAuthServiceGrpc;
import com.example.grpc.TokenRequest;
import com.example.grpc.TokenResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcTokenAuthService {

    private TokenAuthServiceGrpc.TokenAuthServiceBlockingStub tokenAuthServiceBlockingStub;
    private ManagedChannel channel;

    public GrpcTokenAuthService(@Value("${user.grpc.host}") String grpcHost, @Value("${user.grpc.port}") int grpcPort) {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }

    public TokenResponse grpcAuth(TokenRequest tokenRequest) {

        tokenAuthServiceBlockingStub = TokenAuthServiceGrpc.newBlockingStub(channel);

        TokenResponse tokenResponse = tokenAuthServiceBlockingStub.tokenAuth(tokenRequest);

        return tokenResponse;
    }
}
