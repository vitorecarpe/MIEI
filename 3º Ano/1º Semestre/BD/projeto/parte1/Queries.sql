-- Query extra
SELECT sec_to_time(SUM(time_to_sec(duracao))) AS Duraçao FROM Faixa f
    WHERE f.id_album=2;
         
-- Query 1
SELECT a.nome, g.titulo, g.ano, t.nome_tipo 
FROM Gravacao g, Tipo t, Artista a
  WHERE (g.ano = '1997') 
    AND (a.nome = 'Radiohead')
    AND (g.id_artista = a.id_artista)
	AND (g.id_tipo = t.id_tipo);
         
-- Query 2
SELECT f.nome AS Musica, a.nome AS Artista
FROM Artista a
  INNER JOIN Localidade AS l ON l.id_localidade = a.localidade
  INNER JOIN Gravacao AS g ON g.id_artista = a.id_artista 
  INNER JOIN Faixa AS f ON f.id_album = g.id_album
    WHERE (l.pais = 'Reino Unido')
    ORDER BY Musica;

-- Query 3
SELECT f.nome, m.nome, a.nome
FROM Faixa f
  INNER JOIN Gravacao AS g ON g.id_album = f.id_album
  INNER JOIN Artista AS a ON a.id_artista = g.id_artista
  INNER JOIN Membro_pertence_Banda AS mpb ON mpb.id_artista = a.id_artista
  INNER JOIN Membro AS m ON mpb.id_membro = m.id_membro
    WHERE (m.id_membro = 15)
	  AND (m.instrumento = 'guitarra');