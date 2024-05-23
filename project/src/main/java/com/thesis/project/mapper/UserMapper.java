package com.thesis.project.mapper;

import com.thesis.project.dto.SignUpDto;
import com.thesis.project.dto.UserDto;
import com.thesis.project.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    public UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    public User signUpToUser(SignUpDto signUpDto);

}
