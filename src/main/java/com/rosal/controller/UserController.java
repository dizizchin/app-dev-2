package com.rosal.controller;

import com.rosal.model.User;
import com.rosal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/api/user")
    public ResponseEntity<?> getUsers() {
        try {
            List<User> users = userService.getUsers();
            return ResponseEntity.ok(users);
        } catch (Exception ex) {
            log.error("Failed to retrieve order items: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/api/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        log.info("Create Input >> {}", user);
        try {
            User newUser = userService.create(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception ex) {
            log.error("Failed to create order item: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        log.info("Updating User with ID {} >> {}", id, user);
        try {
            // Check if the item with the given ID exists
            User updatedUser = userService.update(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException ex) {
            log.error("User with id {} not found: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Failed to update order item: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the order item.");
        }
    }


    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        log.info("Input >> {}", id);
        try {
            userService.delete(id);
            return ResponseEntity.ok(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        log.info("Fetching user with ID: {}", id);
        try {
            User user = userService.findById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve user: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
