package com.example.demo.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by lazar on 3/2/2022.
 * Project name: demo
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrivateUserDTO {
    @NotNull
    @Size(min = 3, max = 80)
    private String username;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min=4)
    private String password;
}