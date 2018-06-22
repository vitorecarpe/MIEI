-- MySQL Workbench Synchronization
-- Generated: 2017-11-24 01:14
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Vitor

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `MuDBa` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `MuDBa`.`Artista` (
  `id_artista` INT(11) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `biografia` VARCHAR(500) NOT NULL,
  `inicio` YEAR NOT NULL,
  `fim` YEAR NULL DEFAULT NULL,
  `localidade` INT(11) NOT NULL,
  PRIMARY KEY (`id_artista`),
  INDEX `fk_Artista_Localidade1_idx` (`localidade` ASC),
  CONSTRAINT `fk_Artista_Localidade1`
    FOREIGN KEY (`localidade`)
    REFERENCES `MuDBa`.`Localidade` (`id_localidade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `MuDBa`.`Gravacao` (
  `id_album` INT(11) NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `ano` YEAR NOT NULL,
  `descricao` VARCHAR(500) NOT NULL,
  `pontuacao` INT(11) NOT NULL,
  `id_tipo` INT(11) NOT NULL,
  `id_genero` INT(11) NOT NULL,
  `id_artista` INT(11) NOT NULL,
  PRIMARY KEY (`id_album`),
  INDEX `fk_Gravacao_Tipo1_idx` (`id_tipo` ASC),
  INDEX `fk_Gravacao_Genero1_idx` (`id_genero` ASC),
  INDEX `fk_Gravacao_Artista1_idx` (`id_artista` ASC),
  CONSTRAINT `fk_Gravacao_Tipo1`
    FOREIGN KEY (`id_tipo`)
    REFERENCES `MuDBa`.`Tipo` (`id_tipo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Gravacao_Genero1`
    FOREIGN KEY (`id_genero`)
    REFERENCES `MuDBa`.`Genero` (`id_genero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Gravacao_Artista1`
    FOREIGN KEY (`id_artista`)
    REFERENCES `MuDBa`.`Artista` (`id_artista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `MuDBa`.`Banda` (
  `id_artista` INT(11) NOT NULL,
  `membros` INT(11) NOT NULL,
  PRIMARY KEY (`id_artista`),
  CONSTRAINT `fk_Banda_Artista1`
    FOREIGN KEY (`id_artista`)
    REFERENCES `MuDBa`.`Artista` (`id_artista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `MuDBa`.`Faixa` (
  `id_faixa` INT(11) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `duracao` TIME NOT NULL,
  `numero` INT(11) NOT NULL,
  `id_album` INT(11) NOT NULL,
  PRIMARY KEY (`id_faixa`),
  INDEX `fk_Faixa_Gravacao1_idx` (`id_album` ASC),
  CONSTRAINT `fk_Faixa_Gravacao1`
    FOREIGN KEY (`id_album`)
    REFERENCES `MuDBa`.`Gravacao` (`id_album`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `MuDBa`.`Tipo` (
  `id_tipo` INT(11) NOT NULL,
  `nome_tipo` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_tipo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `MuDBa`.`Solo` (
  `id_artista` INT(11) NOT NULL,
  `nascimento` DATE NOT NULL,
  `falecimento` DATE NULL DEFAULT NULL,
  `sexo` CHAR(1) NOT NULL,
  PRIMARY KEY (`id_artista`),
  CONSTRAINT `fk_Solo_Artista1`
    FOREIGN KEY (`id_artista`)
    REFERENCES `MuDBa`.`Artista` (`id_artista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `MuDBa`.`Membro` (
  `id_membro` INT(11) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `nascimento` DATE NOT NULL,
  `falecimento` DATE NULL DEFAULT NULL,
  `sexo` CHAR(1) NOT NULL,
  `instrumento` VARCHAR(45) NOT NULL,
  `localidade` INT(11) NOT NULL,
  PRIMARY KEY (`id_membro`),
  INDEX `fk_Membro_Localidade1_idx` (`localidade` ASC),
  CONSTRAINT `fk_Membro_Localidade1`
    FOREIGN KEY (`localidade`)
    REFERENCES `MuDBa`.`Localidade` (`id_localidade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `MuDBa`.`Genero` (
  `id_genero` INT(11) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_genero`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `MuDBa`.`Membro_pertence_Banda` (
  `id_artista` INT(11) NOT NULL,
  `id_membro` INT(11) NOT NULL,
  PRIMARY KEY (`id_artista`, `id_membro`),
  INDEX `fk_Banda_has_Membro_Membro1_idx` (`id_membro` ASC),
  INDEX `fk_Banda_has_Membro_Banda1_idx` (`id_artista` ASC),
  CONSTRAINT `fk_Banda_has_Membro_Banda1`
    FOREIGN KEY (`id_artista`)
    REFERENCES `MuDBa`.`Banda` (`id_artista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Banda_has_Membro_Membro1`
    FOREIGN KEY (`id_membro`)
    REFERENCES `MuDBa`.`Membro` (`id_membro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `MuDBa`.`Localidade` (
  `id_localidade` INT(11) NOT NULL,
  `cidade` VARCHAR(45) NOT NULL,
  `pais` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_localidade`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
