DELIMITER $$

CREATE TRIGGER fimCarreiraSolo
AFTER UPDATE ON Solo
FOR EACH ROW
BEGIN 
IF NEW.falecimento
THEN
	UPDATE Artista SET artista.fim = year(NEW.falecimento)
		WHERE (NEW.id_artista = artista.id_artista);
END IF;

END; $$