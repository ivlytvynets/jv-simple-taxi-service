CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8;

CREATE TABLE `taxi_service`.`manufacturers` (
                                                `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                                `name` VARCHAR(45) NOT NULL,
                                                `country` VARCHAR(45) NOT NULL,
                                                `deleted` TINYINT NOT NULL DEFAULT 0,
                                                PRIMARY KEY (`id`),
                                                UNIQUE INDEX `manufacturer_id_UNIQUE` (`id` ASC) VISIBLE);

CREATE TABLE `taxi_service`.`drivers` (
                                          `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                          `name` VARCHAR(45) NOT NULL,
                                          `licence_number` VARCHAR(45) NOT NULL,
                                          `deleted` TINYINT NOT NULL DEFAULT 0,
                                          PRIMARY KEY (`id`),
                                          UNIQUE INDEX `driver_id_UNIQUE` (`id` ASC) VISIBLE);

CREATE TABLE `taxi_service`.`cars` (
                                       `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                       `model` VARCHAR(45) NOT NULL,
                                       `manufacturer_id` BIGINT(11) NOT NULL,
                                       PRIMARY KEY (`id`),
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
