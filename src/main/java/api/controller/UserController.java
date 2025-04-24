package api.controller;

import api.model.User;
import org.springframework.web.bind.annotation.*;
import response.ResponseEnvelope;
import service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(params = "id")
    public User getUser(@RequestParam Integer id) {
        Optional user = userService.getUser(id);
        if(user.isPresent()) {
            return (User) user.get();
        }
        return null;
    }

    @PostMapping("/register")
    public ResponseEnvelope registerUser(@RequestParam Integer id,
                                         @RequestParam String password) {
        if(password.length() > 6) {
            userService.registerUser(id, password);
            return new ResponseEnvelope(true, "Registration successful!");
        }
        return new ResponseEnvelope(false, "Registration unsuccessful!");
    }

    @PostMapping("/login")
    public ResponseEnvelope loginUser(@RequestParam Integer id,
                                      @RequestParam String password) {

        boolean successful = userService.loginUser(id, password);
        if(successful) {
            return new ResponseEnvelope(true, "Login successful!");
        }
        return new ResponseEnvelope(false, "Login unsuccessful!");
    }
}
