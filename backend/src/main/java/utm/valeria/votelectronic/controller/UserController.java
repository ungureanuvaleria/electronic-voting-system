package utm.valeria.votelectronic.controller;

import org.springframework.web.bind.annotation.*;
import utm.valeria.votelectronic.model.SimpleResponse;
import utm.valeria.votelectronic.model.User;
import utm.valeria.votelectronic.service.UserService;

import javax.inject.Inject;
import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {
    private static final String BASE_URL = "/api/users";
    
    private UserService userService;
    
    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping(BASE_URL)
    public SimpleResponse addUser(@RequestBody User user) {
        this.userService.addUser(user);
        return new SimpleResponse("ADDED");
    }
    
    @GetMapping(BASE_URL)
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }
    
    @DeleteMapping(BASE_URL)
    public void deleteUser(@RequestBody User user) {
        this.userService.deleteUser(user);
        System.out.println("DELETED USER");
    }
}
