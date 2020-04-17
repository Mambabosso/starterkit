package com.github.mambabosso.starterkit.model.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public boolean equals(Object other) {
        if (other instanceof Role) {
            Role r = (Role)other;
            return this.name.contentEquals(r.name);
        }
        return false;
    }

}
