package com.howtographql.hackernews.graphql.endpoints;

import com.howtographql.hackernews.graphql.messages.SystemMessage;
import com.howtographql.hackernews.graphql.payloads.AccessToken;
import com.howtographql.hackernews.graphql.resolvers.AuthResolver;
import com.howtographql.hackernews.graphql.utils.HttpUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/refresh/token")
public class RefreshAccessToken extends ScedarHttpServlet{

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            HttpUtils.sendAsJson(response, new SystemMessage(
                    SystemMessage.NO_AUTHORIZATION_HEADER,
                    "No Authorization header"
            ));

        } else {

            String accessTokenString = authorizationHeader.replace("Bearer ", "");
            AccessToken accessToken = new AuthResolver().validateAccessToken(accessTokenString, true);

            if (accessToken == null) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                HttpUtils.sendAsJson(response, new SystemMessage(
                        SystemMessage.AUTHORIZATION_FAILURE,
                        "Invalid/Corrupt/Expired access token"
                ));
            } else {

                response.setStatus(HttpServletResponse.SC_OK);
                HttpUtils.sendAsJson(response, accessToken);

            }

        }
    }

}
