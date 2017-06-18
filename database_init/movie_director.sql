/* DROP TABLE IF EXISTS movie_director; */
CREATE TABLE movie_director (
  imdb int NOT NULL,
  owner_id int NOT NULL,
  director varchar(255) NOT NULL,
  PRIMARY KEY (imdb,owner_id,director),
  CONSTRAINT movie_director_ibfk_1 FOREIGN KEY (imdb, owner_id) REFERENCES movie (imdb, owner_id) ON DELETE CASCADE ON UPDATE NO ACTION
)