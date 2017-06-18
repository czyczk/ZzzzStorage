/* DROP TABLE IF EXISTS tv_show_genre; */
CREATE TABLE tv_show_genre (
  imdb int NOT NULL,
  season int NOT NULL,
  owner_id int NOT NULL,
  genre varchar(255) NOT NULL,
  PRIMARY KEY (imdb,season,owner_id,genre),
  CONSTRAINT tv_show_genre_ibfk_1 FOREIGN KEY (imdb, season, owner_id) REFERENCES tv_show (imdb, season, owner_id) ON DELETE CASCADE ON UPDATE NO ACTION
)