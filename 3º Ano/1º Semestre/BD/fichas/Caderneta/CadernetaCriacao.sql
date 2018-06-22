-- Universidade do Minho
-- Mestrado Integrado em Engenharia Informática
-- Lincenciatura em Ciências da Computação
-- Unidade Curricular de Bases de Dados
-- 2016/2017
--
-- Caso de Estudo: "A Caderneta de Cromos" 
-- Criação da base de dados utilizando a script gerada pelo MySQL Workbench.
--

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Caderneta` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `Caderneta` ;

-- -----------------------------------------------------
-- Table `Caderneta`.`Pais`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Caderneta`.`Pais` (
  `Id` INT NOT NULL,
  `Designacao` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Caderneta`.`Localidade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Caderneta`.`Localidade` (
  `Id` CHAR(3) NOT NULL,
  `Designacao` VARCHAR(75) NOT NULL,
  `Pais` INT NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Localidade_Pais1_idx` (`Pais` ASC),
  CONSTRAINT `fk_Localidade_Pais1`
    FOREIGN KEY (`Pais`)
    REFERENCES `Caderneta`.`Pais` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Caderneta`.`Equipa`
-- -----------------------------------------------------
-- DROP TABLE `Caderneta`.`Equipa`;
CREATE TABLE IF NOT EXISTS `Caderneta`.`Equipa` (
  `Id` CHAR(3) NOT NULL,
  `Designacao` VARCHAR(45) NOT NULL,
  `Localidade` CHAR(3) NOT NULL,
  `Treinador` VARCHAR(50) NOT NULL,
  `AnoFundacao` INT NULL,
  `Estadio` VARCHAR(50) NULL,
  `Presidente` VARCHAR(50) NULL,
  `URL` VARCHAR(150) NULL,
  `Observacoes` TEXT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Equipa_Localidade1_idx` (`Localidade` ASC),
  CONSTRAINT `fk_Equipa_Localidade1`
    FOREIGN KEY (`Localidade`)
    REFERENCES `Caderneta`.`Localidade` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Caderneta`.`Posicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Caderneta`.`Posicao` (
  `Id` INT NOT NULL,
  `Designacao` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Caderneta`.`Jogador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Caderneta`.`Jogador` (
  `Nr` INT NOT NULL,
  `Nome` VARCHAR(75) NOT NULL,
  `Equipa` CHAR(3) NOT NULL,
  `Posicao` INT NOT NULL,
  `DataNascimento` DATE NULL,
  `LocalNascimento` CHAR(3) NULL,
  `Altura` DECIMAL(6,2) NULL,
  `Peso` DECIMAL(6,2) NULL,
  `Observacoes` TEXT NULL,
  PRIMARY KEY (`Nr`),
  INDEX `fk_Cromo_Equipa_idx` (`Equipa` ASC),
  INDEX `fk_Cromo_Localidade1_idx` (`LocalNascimento` ASC),
  INDEX `fk_Jogador_Posicao1_idx` (`Posicao` ASC),
  CONSTRAINT `fk_Cromo_Equipa`
    FOREIGN KEY (`Equipa`)
    REFERENCES `Caderneta`.`Equipa` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cromo_Localidade1`
    FOREIGN KEY (`LocalNascimento`)
    REFERENCES `Caderneta`.`Localidade` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Jogador_Posicao1`
    FOREIGN KEY (`Posicao`)
    REFERENCES `Caderneta`.`Posicao` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Caderneta`.`TipoCromo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Caderneta`.`TipoCromo` (
  `Nr` INT NOT NULL,
  `Descricao` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`Nr`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Caderneta`.`Cromo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Caderneta`.`Cromo` (
  `Nr` INT NOT NULL,
  `Tipo` INT NOT NULL,
  `Jogador` INT NULL,
  `PagCaderneta` INT NOT NULL,
  `Descricao` TEXT NULL,
  `Adquirido` CHAR(1) NOT NULL,
  PRIMARY KEY (`Nr`),
  INDEX `fk_Cromo_TipoCromo1_idx` (`Tipo` ASC),
  INDEX `fk_Cromo_Jogador1_idx` (`Jogador` ASC),
  CONSTRAINT `fk_Cromo_TipoCromo1`
    FOREIGN KEY (`Tipo`)
    REFERENCES `Caderneta`.`TipoCromo` (`Nr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cromo_Jogador1`
    FOREIGN KEY (`Jogador`)
    REFERENCES `Caderneta`.`Jogador` (`Nr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- <fim>
--