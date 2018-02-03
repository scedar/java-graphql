package com.howtographql.hackernews.graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import com.howtographql.hackernews.graphql.resolvers.AuthResolver;
import com.howtographql.hackernews.graphql.resolvers.LinkResolver;
import com.howtographql.hackernews.graphql.resolvers.UserResolver;
import graphql.schema.GraphQLSchema;

public class Schema {

    public static GraphQLSchema buildSchema() {

        LinkResolver linkResolver = new LinkResolver();
        UserResolver userResolver = new UserResolver();

        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(
                        new Query(
                                linkResolver,
                                userResolver
                        ),
                        new Mutation(
                                linkResolver,
                                userResolver
                        )
                )
                .build()
                .makeExecutableSchema();
    }
}
