/* DROP TABLE IF EXISTS music_genre; */
CREATE TABLE music_genre (
  SHA256 char(64) NOT NULL,
  size int NOT NULL,
  owner_id int NOT NULL,
  genre varchar(255) NOT NULL,
  PRIMARY KEY (SHA256,size,owner_id,genre),
  CONSTRAINT music_genre_ibfk_1 FOREIGN KEY (SHA256, size, owner_id) REFERENCES music (SHA256, size, owner_id) ON DELETE CASCADE ON UPDATE NO ACTION
)