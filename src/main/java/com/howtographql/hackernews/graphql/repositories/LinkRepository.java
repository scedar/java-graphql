package com.howtographql.hackernews.graphql.repositories;

import com.howtographql.hackernews.domain.db.QueryDatabase;
import com.howtographql.hackernews.domain.Link;
import com.howtographql.hackernews.graphql.inputs.CreateLinkInput;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LinkRepository {

    public LinkRepository() {
    }

    public List<Link> getAllLinks() {

        List<Link> links = new ArrayList<>();

        ResultSet rs = QueryDatabase.getResultSet("" +
                "SELECT * FROM link");

        try {
            while(rs.next()) {
                links.add(new Link(
                        rs.getString("id"),
                        rs.getString("url"),
                        rs.getString("description"),
                        new UserRepository().findById(
                                rs.getString("user_id"))));
            }
            return links;
        } catch (SQLException e) {
            e.printStackTrace();
            return links;
        }
    }

    public Link findById(String id) {
        ResultSet rs = QueryDatabase.getUniqueResultSet("" +
                "SELECT * FROM link WHERE id = '"+id+"'");
        try {
            return new Link(
                    rs.getString("id"),
                    rs.getString("url"),
                    rs.getString("description"),
                    new UserRepository().findById(
                            rs.getString("user_id")));
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    public Link saveLink(CreateLinkInput link) {
        QueryDatabase.updateDatabase("" +
                "INSERT INTO link (url, description, user_id)" +
                " VALUES (" +
                " '"+link.getUrl()+"'," +
                " '"+link.getDescription()+"'," +
                " '"+link.getPostedBy()+"')");
        return findById(QueryDatabase.getInsertId());
    }

}
