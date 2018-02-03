package com.howtographql.hackernews.graphql.endpoints;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.howtographql.hackernews.graphql.Schema;
import com.howtographql.hackernews.graphql.messages.SystemMessage;
import com.howtographql.hackernews.graphql.payloads.AccessToken;
import com.howtographql.hackernews.graphql.resolvers.AuthResolver;
import com.howtographql.hackernews.graphql.security.AuthContext;
import com.howtographql.hackernews.graphql.utils.HttpUtils;
import graphql.GraphQLException;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;


@WebServlet(urlPatterns = "/graphs")
public class GraphQL extends SimpleGraphQLServlet {

    public GraphQL() {
        super(Schema.buildSchema());
    }

    @Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request,
                                           Optional<HttpServletResponse> response) {

        if (request.isPresent() && response.isPresent()) {

            HttpServletRequest httpServletRequest = request.get();
            HttpServletResponse httpServletResponse = response.get();
            String authorizationHeader = httpServletRequest.getHeader("Authorization");

            if (authorizationHeader == null) {

                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                HttpUtils.sendAsJson(httpServletResponse, new SystemMessage(
                        SystemMessage.NO_AUTHORIZATION_HEADER,
                        "No Authorization header"
                ));

            } else {

                String accessTokenString = authorizationHeader.replace("Bearer ", "");
                AccessToken accessToken = new AuthResolver().validateAccessToken(accessTokenString);

                if(accessToken == null){

                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    HttpUtils.sendAsJson(httpServletResponse, new SystemMessage(
                            SystemMessage.AUTHORIZATION_FAILURE,
                            "Invalid/Corrupt/Expired access token"
                    ));
                }else{
//                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//                    HttpUtils.sendAsJson(httpServletResponse, accessToken);
                    return new AuthContext(accessToken, request, response);
                }

                return null;
            }
        } else {
            return null;
        }
        return null;
    }

}
