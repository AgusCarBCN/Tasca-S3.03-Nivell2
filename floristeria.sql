-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema floristeria
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema floristeria
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `floristeria` ;
USE `floristeria` ;

-- -----------------------------------------------------
-- Table `floristeria`.`Decoracion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `floristeria`.`Decoracion` (
  `idDecoracion` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `material` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idDecoracion`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `floristeria`.`Arboles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `floristeria`.`Arboles` (
  `idArboles` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `altura` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idArboles`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `floristeria`.`Flores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `floristeria`.`Flores` (
  `idFlores` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `color` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idFlores`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `floristeria`.`Stock`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `floristeria`.`Stock` (
  `idProducto` INT UNSIGNED NOT NULL,
  `cantidad` INT NOT NULL,
  `descripcion` VARCHAR(45) NOT NULL,
  `precio` DOUBLE NOT NULL,
  `idDecoracion` INT UNSIGNED NULL,
  `idArboles` INT UNSIGNED NULL,
  `idFlores` INT UNSIGNED NULL,
  `fueraCatalogo` TINYINT NOT NULL,
  PRIMARY KEY (`idProducto`),
  INDEX `fk_Decoracion_Decoracion1_idx` (`idDecoracion` ASC) VISIBLE,
  INDEX `fk_Decoracion_Arboles1_idx` (`idArboles` ASC) VISIBLE,
  INDEX `fk_Stock_Flores1_idx` (`idFlores` ASC) VISIBLE,
  CONSTRAINT `fk_Decoracion_Decoracion1`
    FOREIGN KEY (`idDecoracion`)
    REFERENCES `floristeria`.`Decoracion` (`idDecoracion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Decoracion_Arboles1`
    FOREIGN KEY (`idArboles`)
    REFERENCES `floristeria`.`Arboles` (`idArboles`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Stock_Flores1`
    FOREIGN KEY (`idFlores`)
    REFERENCES `floristeria`.`Flores` (`idFlores`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `floristeria`.`tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `floristeria`.`tickets` (
  `idtickets` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `importe` DOUBLE NOT NULL,
  `fecha` DATE NOT NULL,
  PRIMARY KEY (`idtickets`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `floristeria`.`DetalleTicket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `floristeria`.`DetalleTicket` (
  `cantidad` INT NOT NULL,
  `precio` DOUBLE NOT NULL,
  `importeparcial` DOUBLE NOT NULL,
  `idProducto` INT UNSIGNED NOT NULL,
  `idtickets` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idProducto`, `idtickets`),
  INDEX `fk_DetalleTicket_tickets1_idx` (`idtickets` ASC) VISIBLE,
  CONSTRAINT `fk_DetalleTicket_Decoracion1`
    FOREIGN KEY (`idProducto`)
    REFERENCES `floristeria`.`Stock` (`idProducto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DetalleTicket_tickets1`
    FOREIGN KEY (`idtickets`)
    REFERENCES `floristeria`.`tickets` (`idtickets`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
INSERT INTO 
	Arboles(altura)
VALUES
	("de 1,5 a 1,8 m."),
	("de 1,8 a 2 m."),
    ("mas de 2 m.");
INSERT INTO 
	Flores(color)
VALUES
	("blanco"),
	("amarillo"),
    ("naranja"),
    ("violeta");
   INSERT INTO 
	Decoracion(material)
VALUES
	("madera"),
	("plastico");
INSERT INTO `floristeria`.`Stock` (`idProducto`, `cantidad`, `descripcion`, `precio`, `idFlores`, `fueraCatalogo`) VALUES ('1', '10', 'Flor blanca.             ', '5.5', '1', '0');
INSERT INTO `floristeria`.`Stock` (`idProducto`, `cantidad`, `descripcion`, `precio`, `idFlores`, `fueraCatalogo`) VALUES ('2', '10', 'Flor amarilla.           ', '6.5', '2', '0');
INSERT INTO `floristeria`.`Stock` (`idProducto`, `cantidad`, `descripcion`, `precio`, `idFlores`, `fueraCatalogo`) VALUES ('3', '10', 'Flor naranja.            ', '7.5', '3', '0');
INSERT INTO `floristeria`.`Stock` (`idProducto`, `cantidad`, `descripcion`, `precio`, `idFlores`, `fueraCatalogo`) VALUES ('4', '10', 'Flor violeta.             ', '8.5', '4', '0');
INSERT INTO `floristeria`.`Stock` (`idProducto`, `cantidad`, `descripcion`, `precio`, `idDecoracion`, `fueraCatalogo`) VALUES ('5', '20', 'Decoracion madera', '45', '1', '0');
INSERT INTO `floristeria`.`Stock` (`idProducto`, `cantidad`, `descripcion`, `precio`, `idDecoracion`, `fueraCatalogo`) VALUES ('6', '20', 'Decoracion plastico', '15', '2', '0');
INSERT INTO `floristeria`.`Stock` (`idProducto`, `cantidad`, `descripcion`, `precio`, `idArboles`, `fueraCatalogo`) VALUES ('7', '5', 'Arbol entre 1,5 y 1,8 m.', '20', '1', '0');
INSERT INTO `floristeria`.`Stock` (`idProducto`, `cantidad`, `descripcion`, `precio`, `idArboles`, `fueraCatalogo`) VALUES ('8', '5', 'Arbol entre 1,8 y 2 m.', '30', '2', '0');
INSERT INTO `floristeria`.`Stock` (`idProducto`, `cantidad`, `descripcion`, `precio`, `idArboles`, `fueraCatalogo`) VALUES ('9', '3', 'Arbol de mas de 2 m.', '50', '3', '0');