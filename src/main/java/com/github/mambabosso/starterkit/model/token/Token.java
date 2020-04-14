package com.github.mambabosso.starterkit.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.util.TokenGenerator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "token")
public final class Token implements Serializable {

    @Setter(AccessLevel.NONE)
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @NotNull
    @Column(name = "value", unique = true)
    private String value;

    @NotNull
    @Column(name = "created")
    private DateTime created;

    @NotNull
    @Column(name = "last_access")
    private DateTime lastAccess;

    @NotNull
    @Column(name = "lifetime_minutes")
    private int lifetimeMinutes;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private User owner;

    private Token() {
    }

    public boolean expired() {
        return Minutes.minutesBetween(created, DateTime.now()).getMinutes() > lifetimeMinutes;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Token) {
            Token t = (Token)other;
            return this.value.contentEquals(t.value);
        }
        return false;
    }

    public static Token create(int lifetimeMinutes, User owner) {
        Objects.requireNonNull(owner);
        Token token = new Token();
        token.setValue(TokenGenerator.generate());
        token.setCreated(DateTime.now());
        token.setLastAccess(token.getCreated());
        token.setLifetimeMinutes(Math.max(lifetimeMinutes, 5));
        token.setOwner(owner);
        return token;
    }

}
