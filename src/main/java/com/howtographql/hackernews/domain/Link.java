package com.howtographql.hackernews.domain;

import com.howtographql.hackernews.graphql.payloads.AccessToken;

public class Link {

    private final String id;
    private final String url;
    private final String description;
    private final User postedBy;

    public Link(String url, String description, User postedBy) {
        this.postedBy = postedBy;
        this.id = null;
        this.url = url;
        this.description = description;
    }

    public Link(String id, String url, String description, User postedBy) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.postedBy = postedBy;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public User getPostedBy(){
        return postedBy;
    }

}
