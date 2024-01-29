package com.seederuserservice.service;

import com.seederuserservice.dto.request.PatchRequest;
import com.seederuserservice.dto.request.PostUserRequest;
import com.seederuserservice.dto.response.GetUserResponse;
import com.seederuserservice.entity.User;
import com.seederuserservice.exception.UserAlreadyExistsException;
import com.seederuserservice.exception.UserNotFoundException;
import com.seederuserservice.repository.UserRepository;
import com.seederuserservice.utils.Constants;
import com.seederuserservice.utils.Converter;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements IUserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final Converter converter;

  @Autowired
  public UserServiceImp(
    UserRepository userRepository,
    BCryptPasswordEncoder passwordEncoder,
    Converter converter
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.converter = converter;
  }

  @Override
  public GetUserResponse saveUser(PostUserRequest postUserRequest) {
    Optional<User> user = userRepository.findByEmail(
            postUserRequest.getEmail()
    );
    if (!user.isPresent()) {
      User signedupUser = converter.convertDtoToEntity(postUserRequest);
      signedupUser.setPassword(
              passwordEncoder.encode(signedupUser.getPassword())
      );
      signedupUser = userRepository.save(signedupUser);
      return converter.convertEntityToDto(signedupUser);
    } else {
      throw new UserAlreadyExistsException(
              Constants.USER_ALREADY_EXISTS_MESSAGE
      );
    }
  }

  @Override
  public GetUserResponse getUserByEmail(String email) {
    return converter.convertEntityToDto(
      userRepository
        .findByEmail(email)
        .orElseThrow(() ->
          new UserNotFoundException(Constants.USER_NOT_FOUND_MESSAGE)
        )
    );
  }

  @Override
  public GetUserResponse updateUserInfo(UUID id, PatchRequest patchRequest) {
    User existingUser = userRepository
      .findById(id)
      .orElseThrow(() ->
        new UserNotFoundException(Constants.USER_NOT_FOUND_MESSAGE)
      );
    if (patchRequest.getCreditAmount() != 0) {
      existingUser.setCreditAmount(patchRequest.getCreditAmount());
    }

    if (patchRequest.getPassword() != null) {
      existingUser.setPassword(patchRequest.getPassword());
    }
    return converter.convertEntityToDto(userRepository.save(existingUser));
  }
}
