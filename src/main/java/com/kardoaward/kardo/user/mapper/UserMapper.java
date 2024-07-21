package com.kardoaward.kardo.user.mapper;

import com.kardoaward.kardo.user.model.dto.NewUserRequest;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User newUserRequestToUser(NewUserRequest newUserRequest);

    UserDto userToUserDto(User user);

    List<UserDto> userListToUserDtoList(List<User> userList);
}
