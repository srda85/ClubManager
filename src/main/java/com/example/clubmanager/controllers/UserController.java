package com.example.clubmanager.controllers;

import com.example.clubmanager.models.dto.TokenDTO;
import com.example.clubmanager.models.forms.UserAddForm;
import com.example.clubmanager.models.forms.UserLoginForm;
import com.example.clubmanager.service.user.CustomUserDetailsServiceImpl;
import com.example.clubmanager.tools.JWTProvider;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/ClubManager/user")
public class UserController {
    private final CustomUserDetailsServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    public UserController(CustomUserDetailsServiceImpl userService, AuthenticationManager authenticationManager, JWTProvider jwtProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
            ("/register")
    public void addUser(@Valid @RequestBody UserAddForm form) {
        userService.addUser(form);
    }

    @PostMapping("/login")
    //on passe par un DTO car si je mets en string il généère une erreure dans ANGULAR
    public TokenDTO login(@Valid @RequestBody UserLoginForm form) {
        System.out.println(form);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));
        return new TokenDTO(jwtProvider.createToken(auth));
    }

    @PostMapping ("/test")
    public String test(UserLoginForm form){
        return form.getUsername();
    }

//    @GetMapping("/random")
//    public int random() {
//        Random random = new Random();
//        return random.nextInt(10);
//    }
}
