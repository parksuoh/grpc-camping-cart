package com.example.cart.security;

import com.example.cart.dtos.AuthUserDto;
import com.example.grpc.TokenRequest;
import com.example.grpc.TokenResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessTokenService {

    private final GrpcTokenAuthService grpcTokenAuthService;

    public AccessTokenService(GrpcTokenAuthService grpcTokenAuthService) {
        this.grpcTokenAuthService = grpcTokenAuthService;
    }

    public Authentication authenticate(String accessToken) {

        TokenRequest tokenRequest = TokenRequest.newBuilder()
                .setToken(accessToken)
                .build();

        TokenResponse tokenResponse = grpcTokenAuthService.grpcAuth(tokenRequest);

        if(tokenResponse.getName() == "null") {
            return null;
        }

        AuthUserDto authUser = new AuthUserDto(tokenResponse.getName(), tokenResponse.getRole(), accessToken);

        Authentication authentication = UsernamePasswordAuthenticationToken
                .authenticated( authUser, null, List.of(authUser::role));

        return authentication;
    }


}