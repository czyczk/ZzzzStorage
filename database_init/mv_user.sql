/* DROP TABLE IF EXISTS user; */
CREATE TABLE mv_user (
  id int NOT NULL,
  username varchar(7) NOT NULL,
  password varchar(7) NOT NULL,
  email varchar(7) NOT NULL,
  avatar_id int DEFAULT NULL,
  PRIMARY KEY (id)
)
