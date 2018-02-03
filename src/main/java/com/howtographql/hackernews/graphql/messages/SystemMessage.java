package com.howtographql.hackernews.graphql.messages;

import com.howtographql.hackernews.graphql.security.ScedarUUID;

public class SystemMessage {

    public static String AUTHENTICATION_FAILURE = "E100X:Authentication failed.";
    public static String AUTHENTICATION_SUCCESS = "S100X: Authentication successful";

    public static String AUTHORIZATION_FAILURE = "E103X:Authorization failed.";
    public static String AUTHORIZATION_SUCCESS = "S103X: Authorization successful";

    public static String NO_REQUEST = "E101X: No request present";
    public static String NO_AUTHORIZATION_HEADER = "E102X: No authorization header present";

    public static String METHOD_NOT_ALLOWED = "E104X: Method not allowed";

    private String id;
    private String topic;
    private String message;
    private String description;

    public SystemMessage(String id, String topic, String message, String description) {
        this.id = id;
        this.topic = topic;
        this.message = message;
        this.description = description;
    }

    public SystemMessage(){

    }

    public SystemMessage(String message, String description){
        this(new ScedarUUID().getScedarUUID(), "", message, description);
    }

    public SystemMessage(String topic, String message, String description){
        this(new ScedarUUID().getScedarUUID(), topic, message, description);
    }

    public String getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
