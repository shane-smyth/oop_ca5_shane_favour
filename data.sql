-- Create and use the database
CREATE DATABASE IF NOT EXISTS jdbc_ca5;
USE jdbc_ca5;

-- 1. Directors Table
CREATE TABLE IF NOT EXISTS directors (
                                         director_id INT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL,
    country VARCHAR(50) NOT NULL
    );

-- Insert Directors
INSERT INTO directors (name, country) VALUES
                                          ('Christopher Nolan', 'UK'),
                                          ('James Cameron', 'USA'),
                                          ('Quentin Tarantino', 'USA'),
                                          ('Bong Joon-ho', 'South Korea'),
                                          ('Greta Gerwig', 'USA'),
                                          ('Ridley Scott', 'UK'),
                                          ('Wes Anderson', 'USA'),
                                          ('Denis Villeneuve', 'Canada'),
                                          ('Tommy Wiseau', 'USA'),          -- For "The Room"
                                          ('George Miller', 'Australia'),   -- For "Mad Max: Fury Road"
                                          ('Barry Jenkins', 'USA');         -- For "Moonlight"

-- 2. Movies Table (strictly no NULLs)
CREATE TABLE IF NOT EXISTS movies (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      title VARCHAR(255) NOT NULL,
    release_year INT NOT NULL,
    rating FLOAT NOT NULL,
    genre VARCHAR(50) NOT NULL,
    duration INT NOT NULL,
    director_id INT NOT NULL,
    FOREIGN KEY (director_id) REFERENCES directors(director_id)
    );

-- Insert Movies (no NULLs, valid director_ids)
INSERT INTO movies (title, release_year, rating, genre, duration, director_id) VALUES
                                                                                   ('The Dark Knight', 2008, 9.0, 'Action', 152, 1),
                                                                                   ('Avatar', 2009, 7.8, 'Sci-Fi', 162, 2),
                                                                                   ('Pulp Fiction', 1994, 8.9, 'Crime', 154, 3),
                                                                                   ('The Grand Budapest Hotel', 2014, 8.1, 'Comedy', 99, 7),
                                                                                   ('Parasite', 2019, 8.5, 'Thriller', 132, 4),
                                                                                   ('La La Land', 2016, 8.0, 'Musical', 128, 5),
                                                                                   ('Gladiator', 2000, 8.5, 'Action', 155, 6),
                                                                                   ('Dune', 2021, 8.0, 'Sci-Fi', 155, 8),
                                                                                   ('Barbie', 2023, 7.0, 'Comedy', 114, 5),
                                                                                   ('The Room', 2003, 5.0, 'Drama', 99, 9),
                                                                                   ('Blade Runner 2049', 2017, 8.0, 'Sci-Fi', 164, 8),
                                                                                   ('Kill Bill: Vol 1', 2003, 8.2, 'Action', 111, 3),
                                                                                   ('Little Women', 2019, 7.8, 'Drama', 135, 5),
                                                                                   ('Mad Max: Fury Road', 2015, 8.1, 'Action', 120, 10),
                                                                                   ('Moonlight', 2016, 7.4, 'Drama', 111, 11);

-- 3. Actors Table
CREATE TABLE IF NOT EXISTS actors (
                                      actor_id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL
    );

-- Insert Actors
INSERT INTO actors (name) VALUES
                              ('Christian Bale'),
                              ('Zendaya'),
                              ('Samuel L. Jackson'),
                              ('Margot Robbie'),
                              ('Ryan Gosling'),
                              ('Uma Thurman'),
                              ('Timothée Chalamet'),
                              ('Emma Stone');

-- 4. Movie-Actors Junction Table
CREATE TABLE IF NOT EXISTS movie_actors (
                                            movie_id INT NOT NULL,
                                            actor_id INT NOT NULL,
                                            FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (actor_id) REFERENCES actors(actor_id)
    );

-- Link Movies to Actors
INSERT INTO movie_actors (movie_id, actor_id) VALUES
                                                  (1, 1),   -- The Dark Knight → Christian Bale
                                                  (2, 2),   -- Avatar → Zendaya
                                                  (3, 3),   -- Pulp Fiction → Samuel L. Jackson
                                                  (4, 7),   -- Grand Budapest Hotel → Timothée Chalamet
                                                  (5, 4),   -- Parasite → Margot Robbie
                                                  (6, 8),   -- La La Land → Emma Stone
                                                  (7, 1),   -- Gladiator → Christian Bale
                                                  (8, 2),   -- Dune → Zendaya
                                                  (9, 4),   -- Barbie → Margot Robbie
                                                  (10, 3),  -- The Room → Samuel L. Jackson
                                                  (11, 7),  -- Blade Runner 2049 → Timothée Chalamet
                                                  (12, 6);  -- Kill Bill → Uma Thurman