DELIMITER $$
CREATE PROCEDURE atualizaArtista (IN nID INT,
								  IN nNome VARCHAR(45),
								  IN nBiografia VARCHAR (500),
                                  IN nInicio YEAR,
                                  IN nFim YEAR,
                                  IN nLocalidade INT)
BEGIN
DECLARE erro BOOL DEFAULT 0; 
DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro=1;
START TRANSACTION;

UPDATE Artista a SET a.nome=nNome,
									  a.biografia = nBiografia, 
                                      a.inicio = nInicio,
                                      a.fim = nFim,
                                      a.localidade = nLocalidade
							 WHERE id_artista=nID;

IF erro
THEN ROLLBACK;
ELSE COMMIT;
END IF;
END
$$