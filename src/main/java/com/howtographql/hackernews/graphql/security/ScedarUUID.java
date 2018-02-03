package com.howtographql.hackernews.graphql.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.UUID;

public class ScedarUUID {

    private String salt = "abcdefghijklmnopqrstuvwxyz@#$!%^&*(*)1234567890";

    private String getUUID(){
        return UUID.randomUUID().toString();
    }

    public long getCurrentUnixTimestamp(){
        return new Timestamp(System.currentTimeMillis()).getTime();
    }

    public String byteToHexString(byte[] input) {
        StringBuilder output = new StringBuilder();
        for (byte anInput : input) {
            output.append(String.format("%02X", anInput));
        }
        return output.toString();
    }

    public String getScedarUUID(){
        try {
            String value = getCurrentUnixTimestamp()+salt+getUUID();
            MessageDigest md = MessageDigest.getInstance("SHA");
            return byteToHexString(md.digest(value.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
