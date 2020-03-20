CREATE TABLE user (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL UNIQUE,
    mail VARCHAR(64),
    password_hash VARCHAR(64) NOT NULL,
    token VARCHAR(64),
    PRIMARY KEY (user_id)
);