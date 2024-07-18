package com.kardoaward.kardo.user.mapper;

import com.kardoaward.kardo.user.model.NewUserRequest;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User newUserRequestToUser(NewUserRequest newUserRequest);

    UserDto userToUserDto(User user);
}
