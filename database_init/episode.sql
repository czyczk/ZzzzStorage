/* DROP TABLE IF EXISTS episode; */
CREATE TABLE episode (
  imdb int NOT NULL,
  season int NOT NULL,
  owner_id int NOT NULL,
  episode_no int NOT NULL,
  SHA256 char(64) NOT NULL,
  size int NOT NULL,
  title varchar(255) DEFAULT NULL,
  runtime int DEFAULT NULL,
  storyline varchar(255) DEFAULT NULL,
  thumb_url varchar(255) DEFAULT NULL,
  rating double DEFAULT NULL,
  PRIMARY KEY (owner_id,imdb,season,episode_no),
  CONSTRAINT episode_ibfk_1 FOREIGN KEY (imdb, season, owner_id) REFERENCES tv_show (imdb, season, owner_id) ON DELETE CASCADE ON UPDATE NO ACTION
)
