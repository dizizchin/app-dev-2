package com.rosal.service;

import com.rosal.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User create(User user);
    User update(int id, User user);
    void delete(Integer id);
    User findById(int id); // Add this line

}
