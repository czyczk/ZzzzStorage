/* DROP TABLE IF EXISTS `tv_show`; */
CREATE TABLE tv_show (
  imdb int NOT NULL,
  season int NOT NULL,
  owner_id int NOT NULL,
  title varchar(7) NOT NULL,
  release_year int DEFAULT NULL,
  runtime int DEFAULT NULL,
  plot varchar(255) DEFAULT NULL,
  thumb_url varchar(255) DEFAULT NULL,
  rating double DEFAULT NULL,
  PRIMARY KEY (imdb,season,owner_id),
  CONSTRAINT tv_show_ibfk_1 FOREIGN KEY (owner_id) REFERENCES mv_user (id)
)