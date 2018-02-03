package com.howtographql.hackernews.graphql.payloads;

import com.howtographql.hackernews.domain.User;

public class AuthPayload {

    private AccessToken accessToken;
    private User user;

    public AuthPayload(AccessToken accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public AuthPayload() {

    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public User getUser() {
        return user;
    }


}
