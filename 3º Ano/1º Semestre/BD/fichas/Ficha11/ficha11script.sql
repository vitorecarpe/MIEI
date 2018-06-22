-- MySQL Workbench Synchronization
-- Generated: 2017-11-28 10:21
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Vitor Peixoto

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `Ficha11` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `Ficha11`.`Disciplina` (
  `codUC` INT(11) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `codDocente` INT(11) NOT NULL,
  PRIMARY KEY (`codUC`),
  INDEX `fk_Disciplina_Docente_idx` (`codDocente` ASC),
  CONSTRAINT `fk_Disciplina_Docente`
    FOREIGN KEY (`codDocente`)
    REFERENCES `Ficha11`.`Docente` (`codDocente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Ficha11`.`Curso` (
  `idCurso` INT(11) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCurso`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Ficha11`.`Docente` (
  `codDocente` INT(11) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `categoria` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`codDocente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Ficha11`.`Exame` (
  `idExame` INT(11) NOT NULL,
  `dificuldade` DECIMAL(3,2) NOT NULL,
  PRIMARY KEY (`idExame`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Ficha11`.`Questão` (
  `num` INT(11) NOT NULL,
  `enunciado` TEXT(150) NOT NULL,
  `dificuldade` TINYINT(4) NOT NULL,
  `idExame` INT(11) NOT NULL,
  `numero` TINYINT(4) NOT NULL,
  `pontuacao` DECIMAL(3,1) NOT NULL,
  PRIMARY KEY (`num`),
  INDEX `fk_Questão_Exame1_idx` (`idExame` ASC),
  CONSTRAINT `fk_Questão_Exame1`
    FOREIGN KEY (`idExame`)
    REFERENCES `Ficha11`.`Exame` (`idExame`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `dificuldade` CHECK (dificuldade >= 1 AND dificuldade <= 5))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Ficha11`.`CursoDisciplina` (
  `idCurso` INT(11) NOT NULL,
  `codUC` INT(11) NOT NULL,
  PRIMARY KEY (`idCurso`, `codUC`),
  INDEX `fk_Curso_has_Disciplina_Disciplina1_idx` (`codUC` ASC),
  INDEX `fk_Curso_has_Disciplina_Curso1_idx` (`idCurso` ASC),
  CONSTRAINT `fk_Curso_has_Disciplina_Curso1`
    FOREIGN KEY (`idCurso`)
    REFERENCES `Ficha11`.`Curso` (`idCurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Curso_has_Disciplina_Disciplina1`
    FOREIGN KEY (`codUC`)
    REFERENCES `Ficha11`.`Disciplina` (`codUC`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Ficha11`.`ExameDisciplina` (
  `Exame_idExame` INT(11) NOT NULL,
  `Disciplina_codUC` INT(11) NOT NULL,
  `anoLetivo` VARCHAR(9) NULL DEFAULT NULL,
  `ordem` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`Exame_idExame`, `Disciplina_codUC`),
  INDEX `fk_Exame_has_Disciplina_Disciplina1_idx` (`Disciplina_codUC` ASC),
  INDEX `fk_Exame_has_Disciplina_Exame1_idx` (`Exame_idExame` ASC),
  CONSTRAINT `fk_Exame_has_Disciplina_Exame1`
    FOREIGN KEY (`Exame_idExame`)
    REFERENCES `Ficha11`.`Exame` (`idExame`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Exame_has_Disciplina_Disciplina1`
    FOREIGN KEY (`Disciplina_codUC`)
    REFERENCES `Ficha11`.`Disciplina` (`codUC`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;