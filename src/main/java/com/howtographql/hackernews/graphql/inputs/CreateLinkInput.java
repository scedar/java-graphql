package com.howtographql.hackernews.graphql.inputs;

import com.howtographql.hackernews.domain.User;

public class CreateLinkInput {

    private String url;
    private String description;
    private String postedBy;

    public CreateLinkInput(String url, String description, String postedBy) {
        this.url = url;
        this.description = description;
        this.postedBy = postedBy;
    }

    public CreateLinkInput(){

    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
}
