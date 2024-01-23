package com.seederuserservice.utils;

import com.seederuserservice.dto.request.PostUserRequest;
import com.seederuserservice.dto.response.GetUserResponse;
import com.seederuserservice.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converter {

  private ModelMapper modelMapper;

  @Autowired
  public Converter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public User convertDtoToEntity(PostUserRequest postUserRequest) {
    return modelMapper.map(postUserRequest, User.class);
  }

  public GetUserResponse convertEntityToDto(User user) {
    return modelMapper.map(user, GetUserResponse.class);
  }

  public User convertDtoToEntity(GetUserResponse getUserResponse) {
    return modelMapper.map(getUserResponse, User.class);
  }
}
