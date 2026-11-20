package com.energy_tracker.user_service.controller;

import com.energy_tracker.user_service.dto.UserDto;
import com.energy_tracker.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register-user")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto data=userService.createUser(userDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.getUserByEmail(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.updateUser(id,userDto),HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.deleteUserById(id),HttpStatus.OK);
    }
}
