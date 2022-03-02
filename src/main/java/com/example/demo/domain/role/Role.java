package com.example.demo.domain.role;

import com.example.demo.domain.appUser.User;
import com.example.demo.domain.authority.Authority;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.UniqueElements;
//import org.hibernate.validator.constraints.UniqueElements;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
//#from lombok
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @NotNull
    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="authorities")
    @UniqueElements
    @ManyToMany(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    public String toString() {
        return getName();
    }
}