/* DROP TABLE IF EXISTS movie_genre; */
CREATE TABLE movie_genre (
  imdb int NOT NULL,
  owner_id int NOT NULL,
  genre varchar(5) NOT NULL,
  PRIMARY KEY (imdb,owner_id,genre),
  CONSTRAINT movie_genre_ibfk_1 FOREIGN KEY (imdb, owner_id) REFERENCES movie (imdb, owner_id) ON DELETE CASCADE ON UPDATE NO ACTION
)