-- Create tables
CREATE TABLE IF NOT EXISTS Genre (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Genre (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


-- Insert data into Genre table
INSERT INTO Genre (name) VALUES
('Fiction'),
('Non-fiction'),
('Mystery'),
('Thriller'),
('Romance'),
('Science Fiction'),
('Fantasy'),
('Horror'),
('Biography'),
('Autobiography'),
('Historical Fiction'),
('Adventure'),
('Young Adult'),
('Children''s'),
('Poetry'),
('Self-help'),
('Business'),
('Travel'),
('Philosophy'),
('Religion');

INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');