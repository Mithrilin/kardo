package com.kardoaward.kardo.user.mapper;

import com.kardoaward.kardo.user.model.dto.NewUserRequest;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.model.dto.UserDto;
import com.kardoaward.kardo.user.model.dto.UserShortDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User newUserRequestToUser(NewUserRequest newUserRequest);

    UserDto userToUserDto(User user);

    UserShortDto userToUserShortDto(User user);

    List<UserShortDto> userListToUserShortDtoList(List<User> userList);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateUser(UpdateUserRequest request, @MappingTarget User user);
}
