package com.howtographql.hackernews.graphql.resolvers;

import com.howtographql.hackernews.domain.User;
import com.howtographql.hackernews.graphql.inputs.CreateUserInput;
import com.howtographql.hackernews.graphql.repositories.UserRepository;

import java.util.List;

public class UserResolver {

    public final UserRepository userRepository;


    public UserResolver() {
        this.userRepository = new UserRepository();
    }

    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    public User findById(String id){
        return userRepository.findById(id);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User createUser(CreateUserInput createUserInput){
        return userRepository.saveUser(createUserInput);
    }
}
