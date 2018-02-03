package com.howtographql.hackernews.graphql.endpoints;

import com.google.gson.Gson;
import com.howtographql.hackernews.domain.User;
import com.howtographql.hackernews.graphql.inputs.AuthInput;
import com.howtographql.hackernews.graphql.messages.SystemMessage;
import com.howtographql.hackernews.graphql.payloads.AccessToken;
import com.howtographql.hackernews.graphql.repositories.UserRepository;
import com.howtographql.hackernews.graphql.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(urlPatterns = "/auth")
public class Authenticate extends ScedarHttpServlet{

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response){

        String payload = HttpUtils.getPayload(request);

        try {
            AuthInput authInput = gson.fromJson(payload, AuthInput.class);

            UserRepository userRepository = new UserRepository();
            User user = userRepository.findByEmail(authInput.getEmail());

            if(user.getPassword().equals(authInput.getPassword())){
                response.setStatus(HttpServletResponse.SC_OK);
                HttpUtils.sendAsJson(response,
                        new AccessToken(user.getId())
                                .setMessage(SystemMessage.AUTHENTICATION_SUCCESS));
            }else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                HttpUtils.sendAsJson(response, new SystemMessage(
                        SystemMessage.AUTHENTICATION_FAILURE,
                        "Invalid credentials"
                ));
            }

        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            HttpUtils.sendAsJson(response, new SystemMessage(
                    SystemMessage.AUTHENTICATION_FAILURE,
                    "Invalid credentials"
            ));
        }

    }
}
