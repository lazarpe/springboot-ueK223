package com.example.demo.domain.userProfile;

import com.example.demo.domain.userProfile.dto.PublicUserProfileDTO;
import org.mapstruct.Mapper;

/**
 * Created by lazar on 3/3/2022.
 * Project name: demo
 **/
@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    PublicUserProfileDTO convertUserProfileToPublicUserProfileDTO(UserProfile userProfile);

    UserProfile convertPublicUserDTOToUser(PublicUserProfileDTO dto);
}
