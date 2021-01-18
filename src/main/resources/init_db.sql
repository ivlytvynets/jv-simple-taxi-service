CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8;

CREATE TABLE `taxi_service`.`manufacturers` (
                                                `manufacturer_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                                `manufacturer_name` VARCHAR(45) NOT NULL,
                                                `manufacturer_country` VARCHAR(45) NOT NULL,
                                                `deleted` TINYINT NOT NULL DEFAULT 0,
                                                PRIMARY KEY (`manufacturer_id`),
                                                UNIQUE INDEX `manufacturer_id_UNIQUE` (`manufacturer_id` ASC) VISIBLE);

CREATE TABLE `taxi_service`.`drivers` (
                                          `driver_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                          `driver_name` VARCHAR(45) NOT NULL,
                                          `driver_licence_number` VARCHAR(45) NOT NULL,
                                          `deleted` TINYINT NOT NULL DEFAULT 0,
                                          PRIMARY KEY (`driver_id`),
                                          UNIQUE INDEX `driver_id_UNIQUE` (`driver_id` ASC) VISIBLE);

CREATE TABLE `taxi_service`.`cars` (
                                       `car_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                       `car_model` VARCHAR(45) NOT NULL,
                                       `manufacturer_id` BIGINT(11) NOT NULL,
                                       PRIMARY KEY (`car_id`),
                                       INDEX `cars_manufacturers_fk_idx` (`manufacturer_id` ASC) VISIBLE,
                                       CONSTRAINT `cars_manufacturers_fk`
                                           FOREIGN KEY (`manufacturer_id`)
                                               REFERENCES `taxi_service`.`manufacturers` (`manufacturer_id`)
                                               ON DELETE NO ACTION
                                               ON UPDATE NO ACTION);
ALTER TABLE `taxi_service`.`cars`
    ADD COLUMN `deleted` TINYINT NOT NULL DEFAULT 0 AFTER `manufacturer_id`;

CREATE TABLE `taxi_service`.`cars_drivers` (
                                               `driver_id` BIGINT(11) NOT NULL,
                                               `car_id` BIGINT(11) NOT NULL,
                                               INDEX `cars_drivers_fk_idx` (`car_id` ASC) VISIBLE,
                                               INDEX `drivers_cars_fk_idx` (`driver_id` ASC) VISIBLE,
                                               CONSTRAINT `cars_drivers_fk`
                                                   FOREIGN KEY (`car_id`)
                                                       REFERENCES `taxi_service`.`cars` (`car_id`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION,
                                               CONSTRAINT `drivers_cars_fk`
                                                   FOREIGN KEY (`driver_id`)
                                                       REFERENCES `taxi_service`.`drivers` (`driver_id`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION);

ALTER TABLE `taxi_service`.`cars`
    CHANGE COLUMN `car_id` `id` BIGINT NOT NULL AUTO_INCREMENT ,
    CHANGE COLUMN `car_model` `model` VARCHAR(45) NOT NULL ;

ALTER TABLE `taxi_service`.`drivers`
    CHANGE COLUMN `driver_id` `id` BIGINT NOT NULL AUTO_INCREMENT ,
    CHANGE COLUMN `driver_name` `name` VARCHAR(45) NOT NULL ,
    CHANGE COLUMN `driver_licence_number` `licence_number` VARCHAR(45) NOT NULL ;

ALTER TABLE `taxi_service`.`manufacturers`
    CHANGE COLUMN `manufacturer_id` `id` BIGINT NOT NULL AUTO_INCREMENT ,
    CHANGE COLUMN `manufacturer_name` `name` VARCHAR(45) NOT NULL ,
    CHANGE COLUMN `manufacturer_country` `country` VARCHAR(45) NOT NULL ;
