package com.example.demo.domain.appUser;

import com.example.demo.domain.role.Role;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity(name="users")
//from lombok
@Getter@Setter
@NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String username;
    private String email;
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;



}
