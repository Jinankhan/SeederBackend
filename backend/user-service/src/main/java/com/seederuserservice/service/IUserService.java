package com.seederuserservice.service;

import com.seederuserservice.dto.request.PatchRequest;
import com.seederuserservice.dto.request.PostUserRequest;
import com.seederuserservice.dto.response.GetUserResponse;
import java.util.UUID;

public interface IUserService {
  GetUserResponse saveUser(PostUserRequest postUserRequest);
  GetUserResponse getUserByEmail(String email);
  GetUserResponse updateUserInfo(UUID id, PatchRequest patchRequest);
}
