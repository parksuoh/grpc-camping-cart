package com.example.cart.applications.grpcclient;

import com.example.cart.dtos.GetUserDto;
import com.example.grpc.GetUserRequest;
import com.example.grpc.GetUserResponse;
import com.example.grpc.GetUserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcGetUserService {

    private GetUserServiceGrpc.GetUserServiceBlockingStub getUserServiceBlockingStub;
    private ManagedChannel channel;

    public GrpcGetUserService(@Value("${user.grpc.host}") String grpcHost, @Value("${user.grpc.port}") int grpcPort) {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }

    public GetUserDto getUser(String name) {

        GetUserRequest getUserRequest = GetUserRequest.newBuilder()
                .setName(name)
                .build();

        getUserServiceBlockingStub = GetUserServiceGrpc.newBlockingStub(channel);
        GetUserResponse getUserResponse = getUserServiceBlockingStub.getUser(getUserRequest);

        return new GetUserDto(
                getUserResponse.getId(),
                getUserResponse.getName(),
                getUserResponse.getRole()
        );

    }



}
