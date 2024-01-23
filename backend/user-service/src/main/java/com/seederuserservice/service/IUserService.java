package com.seederuserservice.service;

import com.seederuserservice.dto.request.PatchRequest;
import com.seederuserservice.dto.request.PostUserRequest;
import com.seederuserservice.dto.response.GetUserResponse;
import java.util.UUID;

public interface IUserService {
  GetUserResponse postUser(PostUserRequest postUserRequest);
  GetUserResponse getUserByEmail(String email);
  GetUserResponse patchUserDetails(UUID id, PatchRequest patchRequest);
}
