-- 1. ARTISTAS
INSERT IGNORE INTO artists (name, genre, country, image)
VALUES ('Gojira', 'Progressive Death Metal', 'France', 'a442766e-0726-41fa-bf1a-ee8fa3c46f1b.jpg'),
       ('Loathe', 'Metalcore', 'UK', '5d785915-02aa-482e-8f4f-348b599d03c3.jpg'),
       ('Rammstein', 'Industrial Metal', 'Germany', 'd7fafd4f-0877-4682-b0fb-1957896f76a0.jpg'),
       ('Faetooth', 'Doom Metal', 'USA', '3c3c40ac-ff35-44e0-b018-92857e4dc6b4.jpg'),
       ('Deftones', 'Alternative Metal', 'USA', '5fdeb6e4-9ee6-4236-b1f7-034b39eb76c7.jpg'),
       ('Behemoth', 'Blackened Death Metal', 'Poland', '62681927-bda2-4bc4-acef-e56c1f8cfb20.jpg'),
       ('Fit For An Autopsy', 'Deathcore', 'USA', '6a94c2d2-7fcf-401e-944f-1d5d6165d757.jpg'),
       ('Lorna Shore', 'Deathcore', 'USA', '587c1e00-5704-4e23-b1c5-e980191d81b1.jpg'),
       ('Opeth', 'Progressive Metal', 'Sweden', 'bc1a747d-3e2e-4e7c-93e3-a70c1bf3b7f0.jpg'),
       ('Jinjer', 'Progressive Metal', 'Ukraine', '062594f5-0bd7-4548-bea8-959fa89364b5.jpg'),
       ('Metallica', 'Thrash Metal', 'USA', 'e0953cdc-a848-43c6-a02f-4a40f9875282.jpg'),
       ('Mastodon', 'Progressive Metal', 'USA', '66b6cf22-1826-4120-8e7c-0254a2180a64.jpg'),
       ('Evanescence', 'Nu Metal', 'USA', '615ad887-c6ae-4388-8e00-a50e1650fdfb.webp'),
       ('Tool', 'Progressive Metal', 'USA', '1321c08f-bff0-4428-9033-72eefa9dc38c.jpg'),
       ('Whitechapel', 'Deathcore', 'USA', '2c97a201-a77d-47df-b853-b2dc6fa4e472.jpg');

-- 2. ESCENARIOS
INSERT IGNORE INTO stages (name, capacity)
VALUES ('Main Stage of Hell', 50000), -- ID 1
       ('Purgatory Stage', 15000),    -- ID 2
       ('The Abyss', 5000);
-- ID 3

-- 3. CONCIERTOS

-- DÍA 1 (10 Julio): Rammstein cierra. Gojira sub-cabeza.
INSERT IGNORE INTO concerts (start_time, end_time, artist_id, stage_id)
VALUES
    -- Escenario Pequeño (The Abyss)
    ('2025-07-10 17:00:00', '2025-07-10 18:00:00', 2, 3),  -- Loathe
    ('2025-07-10 18:30:00', '2025-07-10 19:30:00', 7, 3),  -- Fit For An Autopsy

    -- Escenario Mediano (Purgatory)
    ('2025-07-10 19:00:00', '2025-07-10 20:30:00', 13, 2), -- Evanescence
    ('2025-07-10 21:00:00', '2025-07-10 22:30:00', 10, 2), -- Jinjer

    -- Escenario Principal (Main)
    ('2025-07-10 20:30:00', '2025-07-10 22:30:00', 1, 1),  -- Gojira (Telonero de lujo)
    ('2025-07-10 23:00:00', '2025-07-11 01:00:00', 3, 1);
-- Rammstein (Cierre)

-- DÍA 2 (11 Julio): Metallica cierra. Deftones sub-cabeza.
INSERT IGNORE INTO concerts (start_time, end_time, artist_id, stage_id)
VALUES
    -- Escenario Pequeño (The Abyss)
    ('2025-07-11 17:00:00', '2025-07-11 18:00:00', 4, 3),  -- Faetooth
    ('2025-07-11 18:30:00', '2025-07-11 19:30:00', 15, 3), -- Whitechapel

    -- Escenario Mediano (Purgatory)
    ('2025-07-11 19:30:00', '2025-07-11 21:00:00', 12, 2), -- Mastodon
    ('2025-07-11 21:30:00', '2025-07-11 23:00:00', 6, 2),  -- Behemoth

    -- Escenario Principal (Main)
    ('2025-07-11 20:30:00', '2025-07-11 22:30:00', 5, 1),  -- Deftones
    ('2025-07-11 23:00:00', '2025-07-12 01:30:00', 11, 1);
-- Metallica

-- DÍA 3 (12 Julio): Tool cierra. Opeth sub-cabeza.
INSERT IGNORE INTO concerts (start_time, end_time, artist_id, stage_id)
VALUES
    -- Escenario Pequeño/Mediano (Purgatory día más fuerte)
    ('2025-07-12 18:00:00', '2025-07-12 19:30:00', 8, 2), -- Lorna Shore (Purgatory)

    -- Escenario Principal (Main)
    ('2025-07-12 20:00:00', '2025-07-12 22:00:00', 9, 1), -- Opeth
    ('2025-07-12 22:30:00', '2025-07-13 00:30:00', 14, 1);
-- Tool


-- 4. ASISTENTES
INSERT IGNORE INTO attendees (dni, name, phone, email)
VALUES ('12345678A', 'Juan García', '600111222', 'juan.garcia@email.com'),
       ('87654321B', 'María López', '600333444', 'maria.lopez@email.com'),
       ('11223344C', 'Carlos Martínez', '600555666', 'carlos.mtz@email.com'),
       ('44332211D', 'Laura Sánchez', '600777888', 'laura.sanchez@email.com'),
       ('99887766E', 'Pedro Gómez', '600999000', 'pedro.gomez@email.com'),
       ('55667788F', 'Ana Fernández', '611222333', 'ana.fernandez@email.com'),
       ('22334455G', 'David Díaz', '622333444', 'david.diaz@email.com'),
       ('66778899H', 'Lucía Pérez', '633444555', 'lucia.perez@email.com'),
       ('77889900J', 'Javier Ruiz', '644555666', 'javier.ruiz@email.com'),
       ('00112233K', 'Elena Jiménez', '655666777', 'elena.jimenez@email.com'),
       ('33445566L', 'Sergio Moreno', '666777888', 'sergio.moreno@email.com'),
       ('99001122M', 'Carmen Muñoz', '677888999', 'carmen.munoz@email.com'),
       ('55443322N', 'Antonio Álvarez', '688999000', 'antonio.alvarez@email.com'),
       ('11002299P', 'Isabel Romero', '699000111', 'isabel.romero@email.com'),
       ('22113300Q', 'Miguel Navarro', '700111222', 'miguel.navarro@email.com');

-- 5. TICKETS
INSERT IGNORE INTO tickets (price, type, is_used, attendee_id)
VALUES (150.00, 'GENERAL', 1, 1),
       (150.00, 'GENERAL', 1, 2),
       (250.00, 'VIP', 0, 3),
       (150.00, 'GENERAL', 1, 4),
       (150.00, 'GENERAL', 1, 5),
       (250.00, 'VIP', 1, 6),
       (150.00, 'GENERAL', 0, 7),
       (150.00, 'GENERAL', 1, 8),
       (250.00, 'VIP', 0, 9),
       (150.00, 'GENERAL', 1, 10),
       (150.00, 'GENERAL', 0, 11),
       (150.00, 'GENERAL', 1, 12),
       (250.00, 'VIP', 1, 13),
       (150.00, 'GENERAL', 0, 14),
       (150.00, 'GENERAL', 1, 15),
       (150.00, 'GENERAL', 0, 1),
       (250.00, 'VIP', 1, 3),
       (150.00, 'GENERAL', 1, 5),
       (150.00, 'GENERAL', 0, 9),
       (250.00, 'VIP', 0, 15);

INSERT IGNORE INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_MANAGER'),
       (3, 'ROLE_USER');

INSERT IGNORE INTO users (id, username, password, enabled, first_name, last_name, image, created_date,
                          last_modified_date, last_password_change_date)
VALUES (1, 'admin', '$2a$12$mTsYJAmrs9cQMpLEEOXk7ucbP8mkp5DuO5ehczmEC5/GrEgR61M8S', true, 'Admin', 'User',
        '/images/admin.jpg', NOW(), NOW(), NOW()),
       (2, 'manager', '$2a$12$7N3vp6nZkEst.azVw.pBDOQ.4NlXrG3IH1Rm3Vu8fOyroS6N0oO1i', true, 'Manager', 'User',
        '/images/manager.jpg', NOW(), NOW(), NOW()),
       (3, 'user', '$2a$12$q74pSPlUShwaCVvuV8FEk.0Ah2zBBuTM.nJHkXTpOEPn1CNKpaF7W', true, 'Regular', 'User',
        '/images/user.jpg', NOW(), NOW(), NOW());

INSERT IGNORE INTO user_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);