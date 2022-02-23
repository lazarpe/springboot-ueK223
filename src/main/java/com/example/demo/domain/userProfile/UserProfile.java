package com.example.demo.domain.userProfile;

import com.example.demo.domain.appUser.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity()
@Getter@Setter
@NoArgsConstructor @AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String location;
    private String profilePictureURL;
    private LocalDate dateOfBirth;
    private String biography;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
