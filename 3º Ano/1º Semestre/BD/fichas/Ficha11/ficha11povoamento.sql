
-- Esquema: "Ficha11"
USE `Ficha11` ;

--
-- Permissão para fazer operações de remoção de dados.
SET SQL_SAFE_UPDATES = 0;

--
-- Povoamento da tabela "Docente"
INSERT INTO Docente
	(codDocente, nome, categoria)
	VALUES 
		(1, 'Orlando Belo', 'Cat. 1'),
        (2, 'Jose Bernardo Barros', 'Cat. 1'),
        (3, 'Maria Joao Frade', 'Cat. 2')
	;
--
-- DELETE FROM Docente;
-- SELECT * FROM Docente;

--
-- Povoamento da tabela "Curso"
INSERT INTO Curso
	(idCurso, nome)
	VALUES 
		(1, 'Mestrado Integrado em Engenharia Informatica'),
        (2, 'Licenciatura em Ciencias da Computacao')
	;
--
-- DELETE FROM Curso;
-- SELECT * FROM Curso;

--
-- Povoamento da tabela "Disciplina"
INSERT INTO Disciplina
	(codUC, nome, codDocente)
	VALUES 
		(1, 'Bases de Dados', 1),
		(2, 'Programaçao Imperativa', 2),
        (3, 'Programaçao Funcional', 3)
	;
--
-- DELETE FROM Disciplina;
-- SELECT * FROM Disciplina;

--
-- Povoamento da tabela "CursoDisciplina"
INSERT INTO CursoDisciplina
	(idCurso, codUC)
	VALUES 
		(1, 1),
		(1, 2),
        (1, 3),
        (2, 1),
        (2, 2),
        (2, 3)
	;
--
-- DELETE FROM CursoDisciplina;
-- SELECT * FROM CursoDisciplina;

--
-- Povoamento da tabela "Exame"
INSERT INTO Exame
	(idExame, codUC, Data, dificuldade)
	VALUES 
		(1, 1, 12/12/12, 2.4),
        (2, 2, 12/12/12, 2.4),
        (3, 1, 12/12/12, 4),
        (4, 2, 12/12/12, 4),
        (5, 3, 12/12/12, 4),
        (6, 1, 12/12/12, 3.3),
        (7, 2, 12/12/12, 3.3)
	;
--
-- DELETE FROM Exame;
-- SELECT * FROM Exame;

--
-- Povoamento da tabela "Questão"
INSERT INTO Questão
	(num, enunciado, dificuldade)
	VALUES 
		(1, 'Resolva o exercicio', 2),
        (2, 'Resolva o exercicio', 4),
        (3, 'Resolva o exercicio', 3),
        (4, 'Resolva o exercicio', 2),
        (5, 'Resolva o exercicio', 1),
        
		(6, 'Resolva o exercicio', 2),
        (7, 'Resolva o exercicio', 4),
        (8, 'Resolva o exercicio', 3),
        (9, 'Resolva o exercicio', 2),
        (10, 'Resolva o exercicio', 1)
	;
--
-- DELETE FROM Questão;
-- SELECT * FROM Questão;

--
-- Povoamento da tabela "Exame_Questao"
INSERT INTO Exame_Questao
	(Exame_idExame, Questao_idQuestao, pontuacao, ordem)
	VALUES 
		(1,1,10,1),
        (1,2,10,2),
        (2,3,10,1),
        (2,4,10,2),
        (3,5,10,1),
        (3,6,10,2)
	;
--
-- DELETE FROM Exame_Questao;
-- SELECT * FROM Exame_Questao;
