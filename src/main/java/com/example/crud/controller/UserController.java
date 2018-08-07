package com.example.crud.controller;

import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        if (users.isEmpty()){
            return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
        }
        return new  ResponseEntity<List<User>>(users,HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userRepository.getUserById(id);
        if (user == null){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        List<User> users = userRepository.getAllUsers();
        if (users.contains(user)){
            return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
        }
        userRepository.saveUser(user);
        return new  ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User user){
        User currentUser = userRepository.getUserById(id);
        if (currentUser == null){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        userRepository.saveUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id){
        User user = userRepository.getUserById(id);
        if (user == null){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
    }
}
