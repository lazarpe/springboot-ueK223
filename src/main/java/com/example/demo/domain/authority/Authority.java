package com.example.demo.domain.authority;

import com.example.demo.domain.role.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "authority")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Authority  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @NotNull
    @Column(name="name")
    private String name;

}