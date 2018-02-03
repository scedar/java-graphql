package com.howtographql.hackernews.graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.howtographql.hackernews.domain.Link;
import com.howtographql.hackernews.domain.User;
import com.howtographql.hackernews.graphql.resolvers.LinkResolver;
import com.howtographql.hackernews.graphql.resolvers.UserResolver;

import java.util.List;

public class Query implements GraphQLRootResolver {

    /**
     * Resolvers
     */
    final LinkResolver linkResolver;
    final UserResolver userResolver;

    //The Query constructor with all resolvers
    public Query(
            LinkResolver linkResolver,
            UserResolver userResolver) {
        this.linkResolver = linkResolver;
        this.userResolver = userResolver;


    }

    /**
     * Available Queries
     */
    public List<Link> allLinks() {
        return linkResolver.allLinks();
    }
    public Link link(String id){
        return linkResolver.findById(id);
    }

    public List<User> allUsers(){
        return userResolver.getAllUsers();
    }
    public User user(String id){
        return userResolver.findById(id);
    }
    public User userByEmail(String email){
        return userResolver.findByEmail(email);
    }
}
