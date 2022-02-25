package com.example.demo.domain.userProfile;

import com.example.demo.domain.appUser.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name="userProfile")
@Getter@Setter
@NoArgsConstructor @AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @Column(name="location")
    private String location;

    //Regex could still be added
    @Column(name="profilePictureURL")
    private String profilePictureURL;

    @Column(name="dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name="biography")
    private String biography;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public UserProfile(String location, String profilePicture, LocalDate dateOfBirth, String biography, User user) {
        this.location = location;
        this.profilePictureURL = profilePicture;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
        this.user = user;
    }
}
