package com.howtographql.hackernews.domain.db;

import java.sql.Connection;
import java.sql.DriverManager;

@Deprecated
public class Conn {
    public Conn(){}

    /**
     *
     * @return
     *      Connection Object for Sky Master Db
     */
    @Deprecated
    public static Connection getConn(){
        Connection conn = null;

        try{

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/graphql_test?autoReconnect=true&useSSL=false",
                    "root",
                    "toor");

            return conn;
        }catch(Exception e){
            System.out.println("Error connecting to DB");
        }

        return null;
    }

    /**
     *
     * @param db
     *      Specific sb to connect to
     * @return
     *      Connection Object to the db in subject
     */
    @Deprecated
    public static Connection getConn(String db) {
        Connection conn = null;

        try{

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+db+"?autoReconnect=true&useSSL=false",
                    "root",
                    "toor");

            return conn;
        }catch(Exception e){
            System.out.println("Error connecting to DB");
        }

        return null;
    }
}
