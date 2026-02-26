SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS artists;
DROP TABLE IF EXISTS stages;
DROP TABLE IF EXISTS attendees;
DROP TABLE IF EXISTS concerts;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS user_roles;
SET FOREIGN_KEY_CHECKS = 1;

-- Tabla Artists
CREATE TABLE IF NOT EXISTS artists
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    genre   VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    image   VARCHAR(255)
);

-- Tabla Stages
CREATE TABLE IF NOT EXISTS stages
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    capacity BIGINT       NOT NULL
);

-- Tabla Attendees
CREATE TABLE IF NOT EXISTS attendees
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    dni   VARCHAR(20)  NOT NULL,
    name  VARCHAR(100) NOT NULL,
    phone VARCHAR(25)  NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Tabla Concerts (Relación con Artists y Stages)
CREATE TABLE IF NOT EXISTS concerts
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_time DATETIME(6) NOT NULL,
    end_time   DATETIME(6) NOT NULL,
    artist_id  BIGINT      NOT NULL,
    stage_id   BIGINT      NOT NULL,
    CONSTRAINT fk_concert_artist FOREIGN KEY (artist_id) REFERENCES artists (id),
    CONSTRAINT fk_concert_stage FOREIGN KEY (stage_id) REFERENCES stages (id)
);

-- Tabla Tickets (Relación con Attendees)
CREATE TABLE IF NOT EXISTS tickets
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    price       DOUBLE      NOT NULL,
    type        VARCHAR(20) NOT NULL,
    is_used     BIT(1)      NOT NULL,
    attendee_id BIGINT      NOT NULL,
    CONSTRAINT fk_ticket_attendee FOREIGN KEY (attendee_id) REFERENCES attendees (id)
);

CREATE TABLE users
(
    id                        BIGINT PRIMARY KEY AUTO_INCREMENT,
    username                  VARCHAR(50) UNIQUE NOT NULL,
    password                  VARCHAR(100)       NOT NULL,
    enabled                   BOOLEAN            NOT NULL,
    first_name                VARCHAR(50)        NOT NULL,
    last_name                 VARCHAR(50)        NOT NULL,
    image                     VARCHAR(255),
    created_date              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE
        CURRENT_TIMESTAMP,
    last_password_change_date TIMESTAMP
);

CREATE TABLE roles
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);