package com.example.demo.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicUserDTO {
    @NotNull
    @Size(min = 3, max = 80)
    private String username;

    @Email
    @NotNull
    private String email;
}
