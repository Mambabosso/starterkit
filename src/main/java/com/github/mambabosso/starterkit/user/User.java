package com.github.mambabosso.starterkit.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.mambabosso.starterkit.role.Role;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.Principal;
import java.util.Objects;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user")
public final class User implements Principal, Serializable {

    @Setter(AccessLevel.NONE)
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Column(name = "mail", unique = true)
    private String mail;

    @JsonIgnore
    @NotNull
    @Column(name = "password_hash")
    private String password;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user"), inverseJoinColumns = @JoinColumn(name = "role"))
    private Set<Role> roles;

    @JsonIgnore
    @Column(name = "token")
    private String token;

    private User() {
    }

    @Override
    public String getName() {
        return name;
    }

    public static User create(String name, String mail, String plain_password) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(mail);
        Objects.requireNonNull(plain_password);
        User user = new User();
        user.setName(name);
        user.setMail(mail);
        user.setPassword(BCrypt.hashpw(plain_password, BCrypt.gensalt(14)));
        return user;
    }

}
