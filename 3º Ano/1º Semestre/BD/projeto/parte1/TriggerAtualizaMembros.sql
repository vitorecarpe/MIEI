DELIMITER $$

CREATE TRIGGER atualizaMembros
AFTER UPDATE ON Membro
FOR EACH ROW
BEGIN 
IF NEW.falecimento
THEN
	UPDATE Banda, membro_pertence_banda SET membros = membros - 1
		WHERE (banda.id_artista=membro_pertence_banda.id_artista)
			AND (membro_pertence_banda.id_membro=NEW.id_membro);
END IF;

END; $$