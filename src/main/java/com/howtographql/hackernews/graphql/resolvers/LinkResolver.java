package com.howtographql.hackernews.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.howtographql.hackernews.graphql.inputs.CreateLinkInput;
import com.howtographql.hackernews.graphql.repositories.LinkRepository;
import com.howtographql.hackernews.domain.Link;

import java.util.List;

public class LinkResolver implements GraphQLResolver<Link> {

    private final LinkRepository linkRepository;

    public LinkResolver() {
        this.linkRepository = new LinkRepository();
    }

    public List<Link> allLinks() {
        return linkRepository.getAllLinks();
    }

    public Link findById(String id){
        return linkRepository.findById(id);
    }

    public Link createLink(CreateLinkInput createLinkInput) {
        return linkRepository.saveLink(createLinkInput);
    }
}
