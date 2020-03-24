package com.github.mambabosso.starterkit.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "role")
public final class Role implements Serializable {

    @Setter(AccessLevel.NONE)
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Column(name = "level")
    private int level;

    private Role() {
    }

    public static Role create(String name, int level) {
        Objects.requireNonNull(name);
        Role role = new Role();
        role.setName(name);
        role.setLevel(level);
        return role;
    }

}
