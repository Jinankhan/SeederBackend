package com.seederuserservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.seederuserservice.dto.request.PatchRequest;
import com.seederuserservice.dto.request.PostUserRequest;
import com.seederuserservice.dto.response.GetUserResponse;
import com.seederuserservice.service.IUserService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock
  private IUserService iUserService;

  @InjectMocks
  private UserController userController;

  private final String email = "test@gmail.com";

  private GetUserResponse createMockResponse() {
    return new GetUserResponse(UUID.randomUUID(), 880000, email, "test");
  }

  @Test
  void testGetUserByEmail() {
    GetUserResponse mockResponse = createMockResponse();

    when(iUserService.getUserByEmail(email)).thenReturn(mockResponse);

    ResponseEntity<GetUserResponse> responseEntity = userController.getUserByEmail(
      email
    );
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(mockResponse, responseEntity.getBody());
  }

  @Test
  void testPatchUser() {
    GetUserResponse mockResponse = createMockResponse();
    when(
      iUserService.patchUserDetails(any(UUID.class), any(PatchRequest.class))
    )
      .thenReturn(mockResponse);

    ResponseEntity<GetUserResponse> responseEntity = userController.patchUse(
      UUID.randomUUID(),
      new PatchRequest()
    );
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(mockResponse, responseEntity.getBody());
  }

  @Test
  void testSignUpUser() {
    GetUserResponse mockResponse = createMockResponse();
    when(iUserService.postUser(any(PostUserRequest.class)))
      .thenReturn(mockResponse);

    ResponseEntity<GetUserResponse> responseEntity = userController.signUpUser(
      new PostUserRequest()
    );
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(mockResponse, responseEntity.getBody());
  }
}
