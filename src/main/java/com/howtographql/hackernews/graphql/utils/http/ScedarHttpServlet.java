package com.howtographql.hackernews.graphql.utils.http;

import com.google.gson.Gson;
import com.howtographql.hackernews.graphql.messages.SystemMessage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ScedarHttpServlet extends HttpServlet{

    protected Gson gson = new Gson();

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response){

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        HttpUtils.sendAsJson(response, new SystemMessage(
                SystemMessage.METHOD_NOT_ALLOWED,
                "GET requests not allowed"
        ));
    }

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response){

    }

}
