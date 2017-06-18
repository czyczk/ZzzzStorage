DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `SHA256` char(64) NOT NULL,
  `size` bigint(20) unsigned NOT NULL,
  `value` longblob NOT NULL,
  PRIMARY KEY (`SHA256`,`size`),
  KEY `SHA256` (`SHA256`) USING BTREE
);