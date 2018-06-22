DELIMITER $$

CREATE TRIGGER limitaBanda
BEFORE INSERT ON Banda
FOR EACH ROW
BEGIN 
DECLARE msg VARCHAR (200);

IF (NEW.id_artista IN (SELECT id_artista FROM Solo WHERE Solo.id_artista = NEW.id_artista))
THEN
	SET msg = 'Um Artista Solo nao pode ser Banda!';
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
END IF;

END; $$