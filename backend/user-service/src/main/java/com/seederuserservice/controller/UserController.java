package com.seederuserservice.controller;

import com.seederuserservice.dto.request.PatchRequest;
import com.seederuserservice.dto.request.PostUserRequest;
import com.seederuserservice.dto.response.GetUserResponse;
import com.seederuserservice.service.IUserService;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {

  private final IUserService userService;

  @Autowired
  public UserController(IUserService iUserService) {
    this.userService = iUserService;
  }

  @GetMapping("/email")
  public ResponseEntity<GetUserResponse> getUserByEmail(
    @RequestParam String email
  ) {
    return ResponseEntity.ok(userService.getUserByEmail(email));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetUserResponse> updateUser(
    @PathVariable UUID id,
    @RequestBody PatchRequest patchRequest
  ) {
    return ResponseEntity.ok(userService.updateUserInfo(id, patchRequest));
  }

  @PostMapping
  public ResponseEntity<GetUserResponse> signUpUser(
    @Valid @RequestBody PostUserRequest postUserRequest
  ) {
    return ResponseEntity.ok(userService.saveUser(postUserRequest));
  }
}
