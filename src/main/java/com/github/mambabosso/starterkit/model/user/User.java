package com.github.mambabosso.starterkit.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.mambabosso.starterkit.model.role.Role;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.Principal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user")
public final class User implements Principal, Serializable {

    @Setter(AccessLevel.NONE)
    @JsonIgnore
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "user_id")
    private String id;

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @JsonIgnore
    @NotNull
    @Column(name = "mail", unique = true)
    private String mail;

    @JsonIgnore
    @NotNull
    @Column(name = "password_hash")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user"), inverseJoinColumns = @JoinColumn(name = "role"))
    private Set<Role> roles;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof User) {
            User u = (User)other;
            return this.name.contentEquals(u.name);
        }
        return false;
    }

}
