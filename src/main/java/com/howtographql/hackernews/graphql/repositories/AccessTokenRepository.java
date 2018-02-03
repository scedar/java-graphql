package com.howtographql.hackernews.graphql.repositories;

import com.howtographql.hackernews.domain.db.QueryDatabase;
import com.howtographql.hackernews.graphql.messages.SystemMessage;
import com.howtographql.hackernews.graphql.payloads.AccessToken;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessTokenRepository {

    private boolean deletePrevious = false;

    public AccessTokenRepository(){

    }

    public void saveAccessToken(AccessToken accessToken){
        QueryDatabase.updateDatabase("" +
                "INSERT INTO access_token (" +
                " access_token, " +
                " user_id," +
                " expiry," +
                " time_unit," +
                " type)" +
                " VALUES (" +
                " '"+accessToken.getAccessToken()+"'," +
                " '"+accessToken.getUserId()+"',"+
                " '"+accessToken.getExpiry()+"',"+
                " '"+accessToken.getTimeUnit()+"',"+
                " '"+accessToken.getType()+"')");
    }

    public AccessToken validateAccessToken(String accessToken){
        ResultSet rs = QueryDatabase.getResultSet("" +
                "SELECT * from access_token" +
                " WHERE access_token = '"+accessToken+"'" +
                " AND expired = '0'");

        try {

            if(rs.next()){

                if(deletePrevious){
                    deleteAccessToken(accessToken);
                    deletePrevious = false;

                    return new AccessToken(
                            rs.getString("user_id")
                    ).setMessage(SystemMessage.AUTHORIZATION_SUCCESS);
                }

                return new AccessToken();

            }else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public AccessToken validateAccessToken(String accessToken, boolean deletePrevious){
        this.deletePrevious = deletePrevious;
        return validateAccessToken(accessToken);
    }

    public void deAuthorizeAccessToken(String accessToken){
        deleteAccessToken(accessToken);
    }

    public boolean markTokenAsExpired(String accessToken, String userId){
        return QueryDatabase.updateDatabase("" +
                "UPDATE access_token SET expired = '1'" +
                " WHERE access_token = '"+accessToken+"'" +
                " AND user_id = '"+userId+"'");
    }

    private void deleteAccessToken(String accessToken){
        QueryDatabase.updateDatabase("" +
                "DELETE FROM access_token " +
                " WHERE access_token = '"+accessToken+"'");
    }

}
