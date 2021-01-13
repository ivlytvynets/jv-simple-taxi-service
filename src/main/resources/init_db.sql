CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8;
CREATE TABLE `taxi_service`.`manufacturers` (
                                               `manufacturer_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                               `name` VARCHAR(125) NOT NULL,
                                               `country` VARCHAR(125) NOT NULL,
                                               PRIMARY KEY (`manufacturer_id`),
                                               UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);
