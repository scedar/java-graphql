package com.howtographql.hackernews.graphql.repositories;

import com.howtographql.hackernews.domain.db.QueryDatabase;
import com.howtographql.hackernews.domain.User;
import com.howtographql.hackernews.graphql.inputs.CreateUserInput;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public UserRepository() {
    }

    public List<User> getAllUsers() {

        List<User> links = new ArrayList<>();

        ResultSet rs = QueryDatabase.getResultSet("" +
                "SELECT * FROM users");

        try {
            while(rs.next()) {
                links.add(new User(
                        rs.getString("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("password")));
            }
            return links;
        } catch (SQLException e) {
            e.printStackTrace();
            return links;
        }
    }

    public User findById(String id){
        ResultSet rs = QueryDatabase.getUniqueResultSet("" +
                "SELECT * FROM users WHERE id = '"+id+"'");
        try {
            return new User(
                    rs.getString("id"),
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("password"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User findByEmail(String email){
        ResultSet rs = QueryDatabase.getUniqueResultSet("" +
                "SELECT * FROM users WHERE email = '"+email+"'");
        try {
            return new User(
                    rs.getString("id"),
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("password"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User saveUser(CreateUserInput createUserInput) {
        QueryDatabase.updateDatabase("" +
                "INSERT INTO users (email, name, password)" +
                " VALUES (" +
                " '"+createUserInput.getEmail()+"'," +
                " '"+createUserInput.getName()+"'," +
                " '"+createUserInput.getPassword()+"')");
        return findById(QueryDatabase.getInsertId());
    }

}
