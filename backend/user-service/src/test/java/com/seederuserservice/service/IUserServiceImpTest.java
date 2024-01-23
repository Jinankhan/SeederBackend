package com.seederuserservice.service;
import com.seederuserservice.dto.request.PatchRequest;
import com.seederuserservice.dto.request.PostUserRequest;
import com.seederuserservice.dto.response.GetUserResponse;
import com.seederuserservice.entity.User;
import com.seederuserservice.exception.UserAlreadyExistsException;
import com.seederuserservice.exception.UserNotFoundException;
import com.seederuserservice.repository.UserRepository;
import com.seederuserservice.utils.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class IUserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private Converter converter;

    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostUser_SuccessfulRegistration() {
        PostUserRequest request = new PostUserRequest();
        User userEntity = new User();
        GetUserResponse expectedResponse = new GetUserResponse();

        when(userRepository.findByEmail(eq(request.getEmail()))).thenReturn(Optional.empty());
        when(converter.convertDtoToEntity(request)).thenReturn(userEntity);
        when(passwordEncoder.encode(eq(userEntity.getPassword()))).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(userEntity);
        when(converter.convertEntityToDto(userEntity)).thenReturn(expectedResponse);


        GetUserResponse actualResponse = userService.postUser(request);


        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(userRepository, times(1)).findByEmail(eq(request.getEmail()));
        verify(converter, times(1)).convertDtoToEntity(request);
        verify(userRepository, times(1)).save(any());
        verify(converter, times(1)).convertEntityToDto(userEntity);
    }

    @Test
    void testPostUser_UserAlreadyExistsException() {
        // Arrange
        PostUserRequest request = new PostUserRequest();
        User existingUser = new User();
        when(userRepository.findByEmail(eq(request.getEmail()))).thenReturn(Optional.of(existingUser));

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.postUser(request));
        verify(userRepository, times(1)).findByEmail(eq(request.getEmail()));
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
        verify(converter, never()).convertEntityToDto(any());
    }

    @Test
    void testGetUserByEmail_Successful() {
        // Arrange
        String email = "test@example.com";
        User userEntity = new User();
        GetUserResponse expectedResponse = new GetUserResponse();

        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.of(userEntity));
        when(converter.convertEntityToDto(userEntity)).thenReturn(expectedResponse);

        // Act
        GetUserResponse actualResponse = userService.getUserByEmail(email);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(userRepository, times(1)).findByEmail(eq(email));
        verify(converter, times(1)).convertEntityToDto(userEntity);
    }

    @Test
    void testGetUserByEmail_UserNotFoundException() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(email));
        verify(userRepository, times(1)).findByEmail(eq(email));
        verify(converter, never()).convertEntityToDto(any());
    }

    @Test
    void testPatchUserDetails_SuccessfulPatch() {
        // Arrange
        UUID userId = UUID.randomUUID();
        PatchRequest patchRequest = new PatchRequest();
        User existingUser = new User();
        GetUserResponse expectedResponse = new GetUserResponse();

        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(converter.convertEntityToDto(existingUser)).thenReturn(expectedResponse);

        // Act
        GetUserResponse actualResponse = userService.patchUserDetails(userId, patchRequest);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(userRepository, times(1)).findById(eq(userId));
        verify(userRepository, times(1)).save(existingUser);
        verify(converter, times(1)).convertEntityToDto(existingUser);
    }

    @Test
    void testPatchUserDetails_UserNotFoundException() {
        // Arrange
        UUID userId = UUID.randomUUID();
        PatchRequest patchRequest = new PatchRequest();
        when(userRepository.findById(eq(userId))).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.patchUserDetails(userId, patchRequest));
        verify(userRepository, times(1)).findById(eq(userId));
        verify(userRepository, never()).save(any());
        verify(converter, never()).convertEntityToDto(any());
    }
}
