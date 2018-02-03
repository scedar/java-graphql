package com.howtographql.hackernews.graphql.utils.security;

import com.howtographql.hackernews.graphql.payloads.AccessToken;
import graphql.servlet.GraphQLContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AuthContext extends GraphQLContext {

    private final AccessToken accessToken;

    public AuthContext(AccessToken accessToken, Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        super(request, response);
        this.accessToken = accessToken;
    }

    public AccessToken getAccessToken(){
        return this.accessToken;
    }
}
