package com.seederuserservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.seederuserservice.dto.request.PatchRequest;
import com.seederuserservice.dto.request.PostUserRequest;
import com.seederuserservice.dto.response.GetUserResponse;
import com.seederuserservice.entity.User;
import com.seederuserservice.exception.UserAlreadyExistsException;
import com.seederuserservice.exception.UserNotFoundException;
import com.seederuserservice.repository.UserRepository;
import com.seederuserservice.utils.Converter;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class IUserServiceImpTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private BCryptPasswordEncoder passwordEncoder;

  @Mock
  private Converter converter;

  @InjectMocks
  private UserServiceImp userService;

  private final String email = "test@gmail.com";

  @Test
  void postUser_UserDoesNotExist_Success() {
    // Arrange
    PostUserRequest postUserRequest = new PostUserRequest();
    postUserRequest.setEmail(email);
    User user = new User();
    user.setEmail(email);

    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
    when(converter.convertDtoToEntity(postUserRequest)).thenReturn(user);
    when(userRepository.save(any(User.class))).thenReturn(user);
    when(converter.convertEntityToDto(any(User.class)))
      .thenReturn(new GetUserResponse());

    GetUserResponse result = userService.postUser(postUserRequest);

    assertNotNull(result);
    verify(userRepository, times(1)).findByEmail(email);
    verify(userRepository, times(1)).save(any(User.class));
    verify(converter, times(1)).convertDtoToEntity(postUserRequest);
    verify(converter, times(1)).convertEntityToDto(any(User.class));
  }

  @Test
  void postUser_UserAlreadyExists_ThrowsUserAlreadyExistsException() {
    PostUserRequest postUserRequest = new PostUserRequest();
    postUserRequest.setEmail(email);
    User user = new User();
    user.setEmail(email);

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    assertThrows(
      UserAlreadyExistsException.class,
      () -> userService.postUser(postUserRequest)
    );
    verify(userRepository, times(1)).findByEmail(email);
    verify(userRepository, never()).save(any(User.class));
    verify(converter, never()).convertDtoToEntity(any(PostUserRequest.class));
    verify(converter, never()).convertEntityToDto(any(User.class));
  }

  @Test
  void getUserByEmail_UserExists_Success() {
    User user = new User();
    user.setEmail(email);

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    when(converter.convertEntityToDto(user)).thenReturn(new GetUserResponse());

    GetUserResponse result = userService.getUserByEmail(email);

    assertNotNull(result);
    verify(userRepository, times(1)).findByEmail(email);
    verify(converter, times(1)).convertEntityToDto(user);
  }

  @Test
  void getUserByEmail_UserNotFound_ThrowsUserNotFoundException() {
    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    assertThrows(
      UserNotFoundException.class,
      () -> userService.getUserByEmail(email)
    );
    verify(userRepository, times(1)).findByEmail(email);
    verify(converter, never()).convertEntityToDto(any(User.class));
  }

  @Test
  void patchUserDetails_UserExists_Success() {
    UUID userId = UUID.randomUUID();
    PatchRequest patchRequest = new PatchRequest();
    patchRequest.setCreditAmount(100);
    User existingUser = new User();
    existingUser.setId(userId);
    existingUser.setCreditAmount(50);

    when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
    when(userRepository.save(any(User.class))).thenReturn(existingUser);
    when(converter.convertEntityToDto(any(User.class)))
      .thenReturn(new GetUserResponse());

    GetUserResponse result = userService.patchUserDetails(userId, patchRequest);

    assertNotNull(result);
    assertEquals(100, existingUser.getCreditAmount());
    verify(userRepository, times(1)).findById(userId);
    verify(userRepository, times(1)).save(any(User.class));
    verify(converter, times(1)).convertEntityToDto(any(User.class));
  }

  @Test
  void patchUserDetails_UserNotFound_ThrowsUserNotFoundException() {
    UUID userId = UUID.randomUUID();
    PatchRequest patchRequest = new PatchRequest();
    patchRequest.setCreditAmount(100);

    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    assertThrows(
      UserNotFoundException.class,
      () -> userService.patchUserDetails(userId, patchRequest)
    );
    verify(userRepository, times(1)).findById(userId);
    verify(userRepository, never()).save(any(User.class));
    verify(converter, never()).convertEntityToDto(any(User.class));
  }
}
