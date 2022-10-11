CREATE SCHEMA IF NOT EXISTS matcha;

CREATE TABLE IF NOT EXISTS matcha.users
(
    user_id         serial PRIMARY KEY,
    user_auth_id    BIGINT REFERENCES matcha.user_auth (user_id) NOT NULL,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS matcha.user_auth
(
    user_auth_id serial PRIMARY KEY,
    token        VARCHAR(100)                             NOT NULL,
    date         TIMESTAMP DEFAULT now()
);

CREATE TABLE IF NOT EXISTS matcha.images
(
    image_id  serial PRIMARY KEY,
    user_id   BIGINT REFERENCES matcha.users (user_id) ON DELETE CASCADE,
    size      BIGINT       NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    mime      VARCHAR(20)  NOT NULL,
    date      TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS matcha.connection_logs
(
    object_id serial PRIMARY KEY,
    user_id   BIGINT REFERENCES matcha.users (user_id),
    date      TIMESTAMP   NOT NULL,
    ip        VARCHAR(15) NOT NULL
);