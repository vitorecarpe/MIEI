CREATE VIEW membrosBanda AS
  SELECT m.id_membro, m.nome AS membro, m.nascimento, m.sexo, m.instrumento, a.id_artista, a.nome AS banda, a.biografia, a.inicio, a.fim
	FROM Artista a, Membro_pertence_Banda mpb, Membro m
		WHERE a.id_artista = mpb.id_artista
		AND mpb.id_membro = m.id_membro;
