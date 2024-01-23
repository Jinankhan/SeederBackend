package com.seederuserservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seederuserservice.dto.request.PatchRequest;
import com.seederuserservice.dto.request.PostUserRequest;
import com.seederuserservice.dto.response.GetUserResponse;
import com.seederuserservice.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private IUserService iUserService;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetUserByEmail() {
        // Mocking the service response
        GetUserResponse mockResponse = new GetUserResponse(/* mock data */);
        when(iUserService.getUserByEmail(anyString())).thenReturn(mockResponse);

        // Calling the controller method
        ResponseEntity<GetUserResponse> responseEntity = userController.getUserByEmail("test@example.com");

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResponse, responseEntity.getBody());
    }

    @Test
    void testPatchUser() {
        // Mocking the service response
        GetUserResponse mockResponse = new GetUserResponse(/* mock data */);
        when(iUserService.patchUserDetails(any(UUID.class), any(PatchRequest.class))).thenReturn(mockResponse);

        // Calling the controller method
        ResponseEntity<GetUserResponse> responseEntity = userController.patchUse(UUID.randomUUID(), new PatchRequest(/* mock data */));

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResponse, responseEntity.getBody());
    }

    @Test
    void testSignUpUser() {
        // Mocking the service response
        GetUserResponse mockResponse = new GetUserResponse(/* mock data */);
        when(iUserService.postUser(any(PostUserRequest.class))).thenReturn(mockResponse);

        // Calling the controller method
        ResponseEntity<GetUserResponse> responseEntity = userController.signUpUser(new PostUserRequest(/* mock data */));

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResponse, responseEntity.getBody());
    }
}
