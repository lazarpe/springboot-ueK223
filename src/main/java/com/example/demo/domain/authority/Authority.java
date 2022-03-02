package com.example.demo.domain.authority;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.NotNull;
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