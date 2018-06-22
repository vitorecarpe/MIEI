DELIMITER $$

CREATE TRIGGER limitaSolo
BEFORE INSERT ON Solo
FOR EACH ROW
BEGIN 
DECLARE msg VARCHAR (200);

IF (NEW.id_artista IN (SELECT id_artista FROM Banda WHERE Banda.id_artista = NEW.id_artista))
THEN
	SET msg = 'Uma Banda nao pode ser Artista Solo!';
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
END IF;

END; $$
