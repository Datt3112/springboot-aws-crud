package com.crud.aws.controller;

import com.crud.aws.entity.User;
import com.crud.aws.exception.ResourceNotFoundException;
import com.crud.aws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    //Get all users

    @GetMapping
    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    //get user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value="id") Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+id));
    }
    //create user
    @PostMapping()
    public User createUser(@RequestBody User user){
        return this.userRepository.save(user);

    }

    //update user
    @PutMapping("/{id}")
    public  User updateUser(@RequestBody User user,@PathVariable("id") Long userId) {
        User existingUser= this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with id : "+userId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return this.userRepository.save(existingUser);

    }
    //delete user b id

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable(value = "id")Long userId) throws Exception{
        User existingUser= this.userRepository.findById(userId).
                orElseThrow(()->new ResourceNotFoundException("User not found with id : "+userId));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }
}
