package com.howtographql.hackernews.graphql.endpoints;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.howtographql.hackernews.graphql.Schema;
import com.howtographql.hackernews.graphql.messages.SystemMessage;
import com.howtographql.hackernews.graphql.payloads.AccessToken;
import com.howtographql.hackernews.graphql.resolvers.AuthResolver;
import com.howtographql.hackernews.graphql.utils.security.AuthContext;
import com.howtographql.hackernews.graphql.utils.http.HttpUtils;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

import java.util.Optional;


@WebServlet(urlPatterns = "/graphs")
public class GraphQL extends SimpleGraphQLServlet {

    public GraphQL() {
        super(Schema.buildSchema());
    }

    @Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request,
                                           Optional<HttpServletResponse> response) {

        AccessToken accessToken = null;

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

                return null;

            } else {

                String accessTokenString = authorizationHeader.replace("Bearer ", "");
                accessToken = new AuthResolver().validateAccessToken(accessTokenString);

                if(accessToken == null){

                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    HttpUtils.sendAsJson(httpServletResponse, new SystemMessage(
                            SystemMessage.AUTHORIZATION_FAILURE,
                            "Invalid/Corrupt/Expired access token"
                    ));

                    return null;

                }else{
                    return new AuthContext(accessToken, request, response);
                }
            }
        } else {
            return null;
        }
    }

}
