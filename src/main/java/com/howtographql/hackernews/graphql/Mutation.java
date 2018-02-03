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
    final AuthResolver authResolver;

    //The Mutation constructor with all resolvers
    public Mutation(
            LinkResolver linkResolver,
            UserResolver userResolver,
            AuthResolver authResolver) {
        this.linkResolver = linkResolver;
        this.userResolver = userResolver;
        this.authResolver = authResolver;
    }

    /**
     * Available Mutations
     */
    public Link createLink(CreateLinkInput createLinkInput) {
        return linkResolver.createLink(createLinkInput);
    }

    public User createUser(CreateUserInput createUserInput){
        return userResolver.createUser(createUserInput);
    }

    public AuthPayload authenticateUser(AuthInput authInput)
            throws IllegalAccessException{
        User user = userResolver.findByEmail(authInput.getEmail());
        if (user.getPassword().equals(authInput.getPassword())){

            return new AuthPayload(new AccessToken(user.getId()), user);
        }

        throw new GraphQLException("Invalid credentials");
    }

}
