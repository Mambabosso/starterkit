DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL UNIQUE,
    mail VARCHAR(64) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL,
    token VARCHAR(64),
    PRIMARY KEY (user_id)
);

CREATE TABLE role (
    role_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL UNIQUE,
    level SMALLINT NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE user_role (
    user BIGINT NOT NULL,
    role BIGINT NOT NULL,
    PRIMARY KEY (user, role),
    CONSTRAINT fk_user FOREIGN KEY (user) REFERENCES user (user_id),
    CONSTRAINT fk_role FOREIGN KEY (role) REFERENCES role (role_id)
);
