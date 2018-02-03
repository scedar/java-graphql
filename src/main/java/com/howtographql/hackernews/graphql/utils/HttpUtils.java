package com.howtographql.hackernews.graphql.utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.Optional;

public class HttpUtils {

    private static Gson gson = new Gson();

    public static String getPayload(HttpServletRequest request){
        try {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            return buffer.toString();
        } catch (IOException IOe){
            return null;
        }
    }

    public static void sendAsJson(
            Gson gson,
            HttpServletResponse response,
            Object obj){
        try {
            response.setContentType("application/json");
            String res = gson.toJson(obj);
            PrintWriter out = response.getWriter();
            out.print(res);
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void sendAsJson(
            HttpServletResponse response,
            Object obj
    ){
        sendAsJson(gson, response, obj);
    }
}
