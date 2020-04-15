package com.github.mambabosso.starterkit.model.token;

import com.github.mambabosso.starterkit.dao.GenericDAO;
import com.github.mambabosso.starterkit.model.user.User;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;

import java.util.Optional;

public class TokenDAO extends GenericDAO<Token> {

    private final QToken token = QToken.token;

    public TokenDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Token create(int lifetimeMinutes, User owner) throws Exception {
        Token token = Token.create(lifetimeMinutes, owner);
        Long id = (Long)create(token);
        if (token.getId().equals(id)) {
            return token;
        }
        throw new Exception();
    }

    public Optional<Token> getByValue(String value) {
        return Optional.ofNullable(query().select(token).from(token).where(token.value.eq(value)).fetchFirst());
    }

    public long updateLastAccess(Token t, DateTime dateTime) {
        return update(token).set(token.lastAccess, dateTime).where(token.id.eq(t.getId())).execute();
    }

}
