package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Non authentifié");
        }

        User user = userService.findByEmail(userDetails.getUsername());

        return ResponseEntity.ok(new UserProfileResponse(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getRole().name()
        ));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserDTO userDTO) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Non authentifié");
        }

        // Appelle la mise à jour du profil via le service
        User updatedUser = userService.updateUserProfile(userDetails.getUsername(), userDTO);

        return ResponseEntity.ok(new UserProfileResponse(
            updatedUser.getFirstName(),
            updatedUser.getLastName(),
            updatedUser.getEmail(),
            updatedUser.getRole().name()
        ));
    }

    // DTO de réponse (éviter d’exposer mot de passe, etc.)
    static class UserProfileResponse {
        private String firstName;
        private String lastName;
        private String email;
        private String role;

        public UserProfileResponse(String firstName, String lastName, String email, String role) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.role = role;
        }

        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
    }
}
