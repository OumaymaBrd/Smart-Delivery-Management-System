package org.example.smart_delivry.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivry.enums.Role;


@Entity
@Table(name="users")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    public User(){}


}
