package com.example.demo.domain.userProfile.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * Created by lazar on 3/3/2022.
 * Project name: demo
 **/
@Data
public class PublicUserProfileDTO {
    private String location;
    private String profilePictureURL;
    private LocalDate dateOfBirth;
    //Still going to decide on this
    //@Transient
    //private Integer age;
    private String biography;
    //private User user;

    @Override
    public String toString() {
        return "PublicUserProfileDTO{" +
                "location='" + location + '\'' +
                ", profilePictureURL='" + profilePictureURL + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", biography='" + biography + '\'' +
                '}';
    }
}
