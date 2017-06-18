/* DROP TABLE IF EXISTS music_artist; */
CREATE TABLE music_artist (
  SHA256 char(64) NOT NULL,
  size int NOT NULL,
  owner_id int NOT NULL,
  artist varchar(255) NOT NULL,
  PRIMARY KEY (SHA256,size,owner_id,artist),
  CONSTRAINT music_artist_ibfk_1 FOREIGN KEY (SHA256, size, owner_id) REFERENCES music (SHA256, size, owner_id) ON DELETE CASCADE ON UPDATE NO ACTION
)
