package com.howtographql.hackernews.graphql.security;

public class AccessTokenGenerator extends ScedarUUID {
    public static String getAccessToken(){
        ScedarUUID scedarUUID = new ScedarUUID();
        return scedarUUID.getScedarUUID().toLowerCase();
    }

}
