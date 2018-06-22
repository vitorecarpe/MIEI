-- MySQL Workbench Synchronization
-- Generated: 2017-12-29 11:25
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Vitor Peixoto

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `Horarios` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `Horarios`.`Turno` (
  `numeroTurno` INT(11) NOT NULL,
  `dia` VARCHAR(10) NOT NULL,
  `hora` INT(11) NOT NULL,
  `codigoUC` INT(11) NOT NULL,
  `capSala` INT(11) NOT NULL,
  `idTipo` INT(11) NOT NULL,
  `idDocente` INT(11) NOT NULL,
  PRIMARY KEY (`numeroTurno`, `codigoUC`),
  INDEX `fk_Turno_UC1_idx` (`codigoUC` ASC),
  INDEX `fk_Turno_TipoAula1_idx` (`idTipo` ASC),
  INDEX `fk_Turno_Docente1_idx` (`idDocente` ASC),
  CONSTRAINT `fk_Turno_UC1`
    FOREIGN KEY (`codigoUC`)
    REFERENCES `Horarios`.`UC` (`codigoUC`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Turno_TipoAula1`
    FOREIGN KEY (`idTipo`)
    REFERENCES `Horarios`.`TipoAula` (`idTipo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Turno_Docente1`
    FOREIGN KEY (`idDocente`)
    REFERENCES `Horarios`.`Docente` (`idDocente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Horarios`.`Aluno` (
  `idAluno` INT(11) NOT NULL,
  `estatuto` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `ano` INT(11) NOT NULL,
  `semestre` INT(11) NOT NULL,
  PRIMARY KEY (`idAluno`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Horarios`.`UC` (
  `codigoUC` INT(11) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `ano` INT(11) NOT NULL,
  `semestre` INT(11) NOT NULL,
  `idDocente` INT(11) NOT NULL,
  `abreviatura` VARCHAR(10) NOT NULL,
  `idCurso` INT(11) NOT NULL,
  PRIMARY KEY (`codigoUC`),
  INDEX `fk_UC_Docente1_idx` (`idDocente` ASC),
  INDEX `fk_UC_Curso1_idx` (`idCurso` ASC),
  CONSTRAINT `fk_UC_Docente1`
    FOREIGN KEY (`idDocente`)
    REFERENCES `Horarios`.`Docente` (`idDocente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UC_Curso1`
    FOREIGN KEY (`idCurso`)
    REFERENCES `Horarios`.`Curso` (`idCurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Horarios`.`Docente` (
  `idDocente` INT(11) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `estatuto` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idDocente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Horarios`.`TipoAula` (
  `idTipo` INT(11) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `limite` INT(11) NOT NULL,
  PRIMARY KEY (`idTipo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Horarios`.`Troca` (
  `idTroca` INT(11) NOT NULL AUTO_INCREMENT,
  `idAlunoRequerente` INT(11) NOT NULL,
  `idAlunoRequerido` INT(11) NOT NULL,
  `codigoUC` INT(11) NOT NULL,
  `numeroTurnoRequerente` INT(11) NOT NULL,
  `numeroTurnoRequerido` INT(11) NOT NULL,
  PRIMARY KEY (`idTroca`),
  INDEX `fk_Trocas_Aluno1_idx` (`idAlunoRequerente` ASC),
  INDEX `fk_Trocas_Aluno2_idx` (`idAlunoRequerido` ASC),
  INDEX `fk_Trocas_UC1_idx` (`codigoUC` ASC),
  INDEX `fk_Trocas_Turno1_idx` (`numeroTurnoRequerente` ASC),
  INDEX `fk_Troca_Turno1_idx` (`numeroTurnoRequerido` ASC),
  CONSTRAINT `fk_Trocas_Aluno1`
    FOREIGN KEY (`idAlunoRequerente`)
    REFERENCES `Horarios`.`Aluno` (`idAluno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Trocas_Aluno2`
    FOREIGN KEY (`idAlunoRequerido`)
    REFERENCES `Horarios`.`Aluno` (`idAluno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Trocas_UC1`
    FOREIGN KEY (`codigoUC`)
    REFERENCES `Horarios`.`UC` (`codigoUC`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Trocas_Turno1`
    FOREIGN KEY (`numeroTurnoRequerente`)
    REFERENCES `Horarios`.`Turno` (`numeroTurno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Troca_Turno1`
    FOREIGN KEY (`numeroTurnoRequerido`)
    REFERENCES `Horarios`.`Turno` (`codigoUC`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Horarios`.`Curso` (
  `idCurso` INT(11) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `idDirCurso` INT(11) NOT NULL,
  PRIMARY KEY (`idCurso`),
  INDEX `fk_Curso_Docente1_idx` (`idDirCurso` ASC),
  CONSTRAINT `fk_Curso_Docente1`
    FOREIGN KEY (`idDirCurso`)
    REFERENCES `Horarios`.`Docente` (`idDocente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Horarios`.`Registo` (
  `idAluno` INT(11) NOT NULL,
  `numeroTurno` INT(11) NOT NULL,
  `codigoUC` INT(11) NOT NULL,
  `faltas` INT(11) NOT NULL,
  `aulas` INT(11) NOT NULL,
  PRIMARY KEY (`idAluno`, `numeroTurno`, `codigoUC`),
  INDEX `fk_Aluno_has_Turno_Turno1_idx` (`numeroTurno` ASC, `codigoUC` ASC),
  INDEX `fk_Aluno_has_Turno_Aluno1_idx` (`idAluno` ASC),
  CONSTRAINT `fk_Aluno_has_Turno_Aluno1`
    FOREIGN KEY (`idAluno`)
    REFERENCES `Horarios`.`Aluno` (`idAluno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Aluno_has_Turno_Turno1`
    FOREIGN KEY (`numeroTurno` , `codigoUC`)
    REFERENCES `Horarios`.`Turno` (`numeroTurno` , `codigoUC`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Horarios`.`InfoExtra` (
  `fase` INT(11) NOT NULL,
  `id` BINARY NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

SET GLOBAL max_connections = 1024; 
