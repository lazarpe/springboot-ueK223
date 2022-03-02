package com.example.demo.domain.user;

import com.example.demo.domain.appUser.dto.PublicUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    PublicUserDTO convertUserToPublicUserDTO(User user);
    User convertPublicUserDTOToUser(PublicUserDTO dto);
}
