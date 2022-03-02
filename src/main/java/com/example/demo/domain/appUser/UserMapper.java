package com.example.demo.domain.appUser;
import com.example.demo.domain.appUser.dto.PublicUserDTO;
import org.mapstruct.Mapper;

/**
 * Created by lazar on 3/2/2022.
 * Project name: demo
 **/
@Mapper(componentModel = "spring")
public interface UserMapper {
    PublicUserDTO convertUserToPublicUserDTO(User user);
    User convertPublicUserDTOToUser(PublicUserDTO dto);
}
