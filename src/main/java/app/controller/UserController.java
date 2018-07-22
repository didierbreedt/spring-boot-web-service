package app.controller;

import app.exception.NotFound;
import app.model.User;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User readUser(@PathVariable int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFound());
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        this.userRepository.save(user);
        return user;
    }

    @PutMapping("/user/{id}")
    public ResponseEntity updateUser(@Valid @RequestBody User user, @PathVariable int id) {
        User currentUser = this.userRepository.findById(id)
                .orElseThrow(()->new NotFound());
        user.setId(id);
        this.userRepository.save(user);

        return ResponseEntity.noContent().build();
    }
}
