package com.solbeg.mapper;

import com.solbeg.dto.UserDTO;
import com.solbeg.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.inject.Singleton;

@RequiredArgsConstructor
@Singleton
public class UserMapper {
    private final ModelMapper modelMapper;

    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
