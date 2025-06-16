package abp.project.anime.controller;

import abp.project.anime.model.User;
import abp.project.anime.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        return userService.login(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable int id){
        return userService.getUser(id);
    }

    @PostMapping("/register")
    public ResponseEntity insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity updateUser(@PathVariable int id,@RequestBody User user){
        return userService.updateUser(id, user);
    }


}
