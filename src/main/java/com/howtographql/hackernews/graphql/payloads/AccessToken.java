package com.howtographql.hackernews.graphql.payloads;

import com.howtographql.hackernews.graphql.repositories.AccessTokenRepository;
import com.howtographql.hackernews.graphql.resolvers.AuthResolver;
import com.howtographql.hackernews.graphql.utils.security.AccessTokenGenerator;

public class AccessToken {

    private String userId;
    private String accessToken;
    private String type;
    private String expiry;
    private String timeUnit;
    private String message = "";

    public AccessToken(String userId, String type, String expiry, String timeUnit) {
        this.userId = userId;
        this.type = type;
        this.accessToken = AccessTokenGenerator.getAccessToken();
        this.expiry = expiry;
        this.timeUnit = timeUnit;

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        AccessTokenRepository accessTokenRepository =
                                new AccessTokenRepository();
                        accessTokenRepository.markTokenAsExpired(accessToken, userId);
                    }
                },
                Integer.parseInt(expiry)*1000
        );
    }

    public AccessToken(String userId){
        this(userId, "Bearer", "900", "seconds");
        new AuthResolver().saveAccessToken(this);
    }

    public AccessToken(){

    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getType() {
        return type;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getMessage() {
        return message;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public AccessToken setMessage(String message) {
        this.message = message;
        return this;
    }
}
