package com.example.springsecurity.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurity.dto.AddressToUserForm;
import com.example.springsecurity.dto.RoleToUserForm;
import com.example.springsecurity.models.Role;
import com.example.springsecurity.models.User;
import com.example.springsecurity.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<User> getUser(@PathVariable String name){
        return ResponseEntity.ok().body(userService.getUser(name));
    }

    @PostMapping("/user/delete/{name}")
    public ResponseEntity<String> deleteUser(@PathVariable String name){
        return ResponseEntity.ok().body(userService.deleteUser(name));
    }
    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
                path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    @PutMapping("/update/profile")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
                path("/update/profile").toUriString());
        return ResponseEntity.created(uri).body(userService.updateProfile(user));
    }
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
                path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    @PostMapping("/role/addtouser")
    public ResponseEntity<?> saveRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/address/addtouser")
    public ResponseEntity<?> saveAddressToUser(@RequestBody AddressToUserForm form){
        return ResponseEntity.ok().body(userService.addAddressToUser(form));
    }

    @GetMapping("/remove/{user}/{id}")
    public ResponseEntity<?> removeAddressOfUser(@PathVariable String user, @PathVariable Long id){
        return ResponseEntity.ok().body(userService.removeAddressOfUser(user, id));
    }

    @GetMapping("/dummy/{user}")
    public ResponseEntity<User> dummyEndPoint(@PathVariable String user){
        return ResponseEntity.ok().body(userService.getUser(user));
    }
    @GetMapping("/refreshtoken")
    public void refreshtoken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) {
            try {
                log.info("refreshing token arrived");
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("santhosh".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);
                refresh_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 3000 * 60 * 60))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accesstoken", access_token);
                tokens.put("refreshtoken", refresh_token);
                tokens.put("user", user.getUsername());
                response.setContentType(APPLICATION_JSON_VALUE);
                String s = new ObjectMapper().writeValueAsString(tokens);
                PrintWriter out = response.getWriter();
                out.print(s);
                out.flush();
                log.error("refresh token successfully implemented");
            } catch (Exception e) {
                log.error("Error logging {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                response.addHeader("Access-Control-Allow-Origin","*");
                response.addHeader("Access-Control-Allow-Methods","POST,PATCH,OPTIONS,GET");
                String s = new ObjectMapper().writeValueAsString(error);
                PrintWriter out = response.getWriter();
                out.print(s);
                out.flush();
            }
        }else {
            throw new RuntimeException("Refresh token missing");
        }
    }

}

