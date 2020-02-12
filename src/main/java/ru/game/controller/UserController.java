package ru.game.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.game.domain.ApplicationUser;
import ru.game.repos.UserRepo;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class UserController {

    private UserRepo applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepo applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @PostMapping("/sign-up")
    public HashMap<String, String> addUser(@RequestBody  ApplicationUser applicationUser){
        HashMap<String, String> response = new HashMap<>();
        if (applicationUser.getUsername()!=null) {
            ApplicationUser userFromDB = applicationUserRepository.findByUsername(applicationUser.getUsername());
            if (userFromDB != null) {
                response.put("Resp", "User exists");
                return response;
            }
            applicationUser.setPassword(applicationUser.getPassword());
            applicationUser.setAverage(0);
            applicationUser.setPoints(0);
            applicationUser.setGames(0);
            applicationUserRepository.save(applicationUser);
            response.put("Resp", "Success");
            return response;
        }
        response.put("Resp", "Error, null");
       return response;
    }
}
