package com.github.mambabosso.starterkit.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.Principal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public final class User implements Principal, Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private Long id;

    @NotNull
    @Column(name = "Name", unique = true)
    private String name;

    @Column(name = "Mail")
    private String mail;

    @JsonIgnore
    @NotNull
    @Column(name = "Password")
    private String password;

    @JsonIgnore
    @Column(name = "Token")
    private String token;

    @Override
    public String getName() {
        return name;
    }

    public static User create(String name, String plain_password) {
        User user = new User();
        user.setName(name);
        user.setPassword(BCrypt.hashpw(plain_password, BCrypt.gensalt(12)));
        return user;
    }

}
