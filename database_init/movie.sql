/* DROP TABLE IF EXISTS movie; */
CREATE TABLE movie (
  imdb int NOT NULL,
  owner_id int NOT NULL,
  SHA256 char(64) NOT NULL,
  size int NOT NULL,
  title varchar(9) NOT NULL,
  release_year int DEFAULT NULL,
  duration int DEFAULT NULL,
  plot varchar(255) DEFAULT NULL,
  thumb_url varchar(255) DEFAULT NULL,
  rating double DEFAULT NULL,
  PRIMARY KEY (imdb,owner_id),
  CONSTRAINT movie_ibfk_1 FOREIGN KEY (owner_id) REFERENCES mv_user (id)
)