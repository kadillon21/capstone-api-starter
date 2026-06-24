package org.yearup.controllers;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.service.ProfileService;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.UserService;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController 
{
    private final ProfileService profileService; 
    private final UserService userService;

    public ProfileController(ProfileService profileService, UserService userService)
    {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Profile> getByUserId(Principal principal)
    {
        int userId = getUserId(principal);
        return ResponseEntity.ok(profileService.getByUserId(userId)); 
    }

    @PutMapping("")
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<Profile> updateProfile(Principal principal, @RequestBody Profile profile)
    {
        int userId = getUserId(principal);
        return ResponseEntity.ok(profileService.updateProfile(userId, profile));
    }

    private int getUserId (Principal principal) {
        String userName = principal.getName();

        User user = userService.getByUserName(userName);
        return user.getId();
    }

}
