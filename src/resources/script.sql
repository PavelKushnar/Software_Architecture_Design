ALTER TABLE `teatrdb`.`bron` 
DROP FOREIGN KEY `fk_br_to_seans`,
DROP FOREIGN KEY `fk_br_to_person`,
DROP FOREIGN KEY `fk_br_to_mesto`;

ALTER TABLE `teatrdb`.`bron` 
DROP INDEX `fk_br_to_mesto_idx` ,
DROP INDEX `fk_br_to_seans_idx` ,
DROP INDEX `fk_br_to_person_idx` ;

ALTER TABLE `teatrdb`.`spectacle` 
DROP FOREIGN KEY `fk_spectacle_to_person`;

ALTER TABLE `teatrdb`.`spectacle` 
DROP INDEX `fk_spectacle_to_person_idx` ;

ALTER TABLE `teatrdb`.`seans` 
DROP FOREIGN KEY `fk_seans_to_spectacle`;

ALTER TABLE `teatrdb`.`seans` 
DROP INDEX `fk_seans_to_spectacle_idx` ;


DROP TABLE IF EXISTS mesto; 
DROP TABLE IF EXISTS bron;
DROP TABLE IF EXISTS seans;
DROP TABLE IF EXISTS spectacle;
DROP TABLE IF EXISTS person;





CREATE TABLE mesto (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
  `ryad` INT(11) NULL DEFAULT NULL,
  `number` INT(11) NULL DEFAULT NULL,
  `cost` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `person` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `login` VARCHAR(45) NULL DEFAULT NULL,
  `pass` VARCHAR(45) NULL DEFAULT NULL,
  `persontype` VARCHAR(45) NULL DEFAULT NULL,
  `cash` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `spectacle` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cost` INT(11) NULL DEFAULT NULL,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `info` VARCHAR(500) NULL DEFAULT NULL,
  `spectaclestatus` VARCHAR(45) NULL DEFAULT NULL,
  `rejpostid` INT(11) NULL DEFAULT NULL,
  `time` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_spectacle_to_person_idx` (`rejpostid` ASC),
  CONSTRAINT `fk_spectacle_to_person`
    FOREIGN KEY (`rejpostid`)
    REFERENCES `teatrdb`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE `seans` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `spectacleid` INT(11) NOT NULL,
  `timestart` TIMESTAMP NULL DEFAULT NULL,
  `timeend` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_seans_to_spectacle_idx` (`spectacleid` ASC),
  CONSTRAINT `fk_seans_to_spectacle`
    FOREIGN KEY (`spectacleid`)
    REFERENCES `teatrdb`.`spectacle` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE `bron` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `bronstatus` VARCHAR(45) NULL DEFAULT NULL,
  `clientid` INT(11) NULL DEFAULT NULL,
  `seansid` INT(11) NULL DEFAULT NULL,
  `mestoid` INT(11) NULL DEFAULT NULL,
  `cost` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_br_to_person_idx` (`clientid` ASC),
  INDEX `fk_br_to_seans_idx` (`seansid` ASC),
  INDEX `fk_br_to_mesto_idx` (`mestoid` ASC),
  CONSTRAINT `fk_br_to_mesto`
    FOREIGN KEY (`mestoid`)
    REFERENCES `teatrdb`.`mesto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_br_to_person`
    FOREIGN KEY (`clientid`)
    REFERENCES `teatrdb`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_br_to_seans`
    FOREIGN KEY (`seansid`)
    REFERENCES `teatrdb`.`seans` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '1', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '2', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '3', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '4', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '5', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '6', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '7', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '8', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '9', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '10', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '11', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '12', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('1', '13', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '1', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '2', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '3', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '4', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '5', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '6', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '7', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '8', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '9', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '10', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '11', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '12', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('2', '13', '50');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '1', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '2', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '3', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '4', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '5', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '6', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '7', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '8', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '9', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '10', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '11', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '12', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('3', '13', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '1', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '2', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '3', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '4', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '5', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '6', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '7', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '8', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '9', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '10', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '11', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('4', '12', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '1', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '2', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '3', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '4', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '5', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '6', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '7', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '8', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '9', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '10', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '11', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '12', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('5', '13', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '1', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '2', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '3', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '4', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '5', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '6', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '7', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '8', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '9', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '10', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '11', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('6', '12', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '1', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '2', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '3', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '4', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '5', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '6', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '7', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '8', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '9', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '10', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '11', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '12', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '13', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '14', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '15', '150');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '16', '100');
INSERT INTO `teatrdb`.`mesto` (`ryad`, `number`, `cost`) VALUES ('7', '17', '100');

