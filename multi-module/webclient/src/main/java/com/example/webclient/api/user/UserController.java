package com.example.webclient.api.user;

import com.example.core.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/v1/users")
    public ResponseEntity<?> registerUser() {
        return ResponseEntity.ok(userService.getUser(1L));
    }

}
