package com.howtographql.hackernews.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.howtographql.hackernews.domain.User;
import com.howtographql.hackernews.graphql.payloads.AccessToken;
import com.howtographql.hackernews.graphql.payloads.AuthPayload;
import com.howtographql.hackernews.graphql.repositories.AccessTokenRepository;

public class AuthResolver implements GraphQLResolver<AuthPayload> {

    private UserResolver userResolver = new UserResolver();
    private AccessTokenRepository accessTokenRepository = new AccessTokenRepository();

    public User user(String email){
        return userResolver.findByEmail(email);
    }

    public void saveAccessToken(AccessToken accessToken){
        accessTokenRepository.saveAccessToken(accessToken);
    }

    public AccessToken validateAccessToken(String accessToken){
        return accessTokenRepository.validateAccessToken(accessToken);
    }

    public AccessToken validateAccessToken(String accessToken, boolean deletePrevious){
        return accessTokenRepository.validateAccessToken(accessToken, deletePrevious);
    }

}
