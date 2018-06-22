DELIMITER $$
CREATE PROCEDURE inserirGravacao (IN id_album INT,
								  IN titulo VARCHAR(45),
								  IN ano YEAR,
								  IN descricao VARCHAR(500),
								  IN pontuacao INT,
								  IN id_tipo INT,
								  IN id_genero INT,
								  IN id_artista INT)

BEGIN
DECLARE erro BOOL DEFAULT 0; 
DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro=1;
START TRANSACTION;

INSERT INTO gravacao VALUES (id_album, titulo, ano, descricao, pontuacao, id_tipo, id_genero, id_artista);

IF erro
THEN ROLLBACK;
ELSE COMMIT;
END IF;
END
$$