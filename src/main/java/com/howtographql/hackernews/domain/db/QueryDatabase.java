package com.howtographql.hackernews.domain.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by elon on 4/11/17.
 */
public class QueryDatabase {

    private static Connection connection;
    private static Statement sqlQueryStatement;
    private static ResultSet rs;
    private static boolean lazyLoad = true;
    private String insertId = "";

    public static ResultSet getResultSet(String query){
        try {
            System.out.println(query);

            connection = Conn.getConn();

            if (connection == null)
                return null;

            if(lazyLoad){
                sqlQueryStatement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
                sqlQueryStatement.setFetchSize(Integer.MIN_VALUE);
            }else{
                sqlQueryStatement = connection.createStatement();
                lazyLoad = true;
            }

            rs = sqlQueryStatement.executeQuery(query);

            if(rs == null)
                return null;

            System.out.println("Executed Query!");

            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet getResultSet(String query, boolean lazyLoadResults){
        lazyLoad = lazyLoadResults;
        return getResultSet(query);
    }

    public static String getInsertId(){
        try(ResultSet generatedKeys = sqlQueryStatement.getGeneratedKeys()) {
            if(generatedKeys.next()){
                return generatedKeys.getString(1);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getInsertIds(){

        List<String> ids = new ArrayList<>();

        try(ResultSet generatedKeys = sqlQueryStatement.getGeneratedKeys()) {

            while(generatedKeys.next()){
                ids.add(generatedKeys.getString(1));
            }

            return ids;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getUniqueResultSet(String query){

        ResultSet rs = getResultSet(query, false);

        try{
            if (rs != null && rs.next()) {
                return rs;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateDatabase(String query){
        try {
            System.out.println(query);

            connection = Conn.getConn();

            if (connection != null) {
                sqlQueryStatement = connection.createStatement();
            }


            if(!(sqlQueryStatement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS)>0))
                return false;

            System.out.println("Executed Query!");

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void collectGarbage(){
        try{

            if(connection != null)
                connection.close();
            sqlQueryStatement = null;
            rs = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

