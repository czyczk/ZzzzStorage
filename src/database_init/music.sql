/* DROP TABLE IF EXISTS music; */
CREATE TABLE music (
  SHA256 char(64) NOT NULL,
  size int NOT NULL,
  owner_id int NOT NULL,
  title varchar(255) NOT NULL,
  album varchar(255) NOT NULL,
  track int NOT NULL,
  rating int DEFAULT NULL,
  thumb_url varchar(255) DEFAULT NULL,
  PRIMARY KEY (SHA256,size,owner_id),
  CONSTRAINT music_ibfk_1 FOREIGN KEY (owner_id) REFERENCES mv_user (id)
)