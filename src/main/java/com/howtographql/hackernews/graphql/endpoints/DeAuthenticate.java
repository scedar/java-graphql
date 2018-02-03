package com.howtographql.hackernews.graphql.endpoints;

import com.howtographql.hackernews.graphql.messages.SystemMessage;
import com.howtographql.hackernews.graphql.resolvers.AuthResolver;
import com.howtographql.hackernews.graphql.utils.http.HttpUtils;
import com.howtographql.hackernews.graphql.utils.http.ScedarHttpServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/de-auth")
public class DeAuthenticate extends ScedarHttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response){

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            HttpUtils.sendAsJson(response, new SystemMessage(
                    SystemMessage.NO_AUTHORIZATION_HEADER,
                    "No Authorization header"
            ));

        } else {

            String accessTokenString = authorizationHeader.replace("Bearer ", "");
            new AuthResolver().deAuthorizeAccessToken(accessTokenString);

            response.setStatus(HttpServletResponse.SC_OK);
            HttpUtils.sendAsJson(response, new SystemMessage(
                    SystemMessage.DE_AUTHENTICATION_SUCCESS,
                    "Access token was successfully de-authorized"
            ));


        }

    }

}
