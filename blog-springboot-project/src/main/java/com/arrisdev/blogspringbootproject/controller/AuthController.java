package com.arrisdev.blogspringbootproject.controller;

import com.arrisdev.blogspringbootproject.entity.Role;
import com.arrisdev.blogspringbootproject.entity.User;
import com.arrisdev.blogspringbootproject.payload.JwtAuthResponse;
import com.arrisdev.blogspringbootproject.payload.LoginDTO;
import com.arrisdev.blogspringbootproject.payload.SignupDTO;
import com.arrisdev.blogspringbootproject.repository.RoleRepository;
import com.arrisdev.blogspringbootproject.repository.UserRepository;
import com.arrisdev.blogspringbootproject.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/v1/signin")
    public ResponseEntity<JwtAuthResponse> authentication(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get token from token provider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("/v1/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signupDTO){
        //adding check if the username exist
        if (userRepository.existsByUsername(signupDTO.getUsername())){
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signupDTO.getEmail())){
            return new ResponseEntity<>("Email is already taken.", HttpStatus.BAD_REQUEST);
        }

        //create new user with roles

        User user = new User();
        user.setFirstName(signupDTO.getFirstname());
        user.setLastName(signupDTO.getLastname());
        user.setUsername(signupDTO.getUsername());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        Role roles = roleRepository.findById(1L).get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!!!", HttpStatus.CREATED);
    }
}
