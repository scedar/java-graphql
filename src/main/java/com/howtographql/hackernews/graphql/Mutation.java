package com.howtographql.hackernews.graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.howtographql.hackernews.domain.Link;
import com.howtographql.hackernews.domain.User;
import com.howtographql.hackernews.graphql.inputs.AuthInput;
import com.howtographql.hackernews.graphql.inputs.CreateLinkInput;
import com.howtographql.hackernews.graphql.inputs.CreateUserInput;
import com.howtographql.hackernews.graphql.payloads.AccessToken;
import com.howtographql.hackernews.graphql.payloads.AuthPayload;
import com.howtographql.hackernews.graphql.resolvers.AuthResolver;
import com.howtographql.hackernews.graphql.resolvers.LinkResolver;
import com.howtographql.hackernews.graphql.resolvers.UserResolver;
import graphql.GraphQLException;

public class Mutation implements GraphQLRootResolver {

    /**
     * Resolvers
     */
    final LinkResolver linkResolver;
    final UserResolver userResolver;

    //The Mutation constructor with all resolvers
    public Mutation(
            LinkResolver linkResolver,
            UserResolver userResolver) {
        this.linkResolver = linkResolver;
        this.userResolver = userResolver;
    }

    /**
     * Available Mutations
     */
    public Link createLink(CreateLinkInput createLinkInput) {
        return linkResolver.createLink(createLinkInput);
    }

    public User createUser(CreateUserInput createUserInput){

        User user = userResolver.createUser(createUserInput);

        if(user == null){
            throw new GraphQLException("Error! User exists");
        }

        return user;
    }

}
