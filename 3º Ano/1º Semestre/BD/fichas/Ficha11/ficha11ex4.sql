-- 4

-- a)
SELECT d.nome FROM docente d, disciplina uc
WHERE d.codDocente = uc.codDocente;

-- b)
SELECT count(uc.codDocente) AS Total, d.nome 
	FROM Disciplina uc, Docente d
    WHERE uc.codUC = d.codDocente
		GROUP BY uc.codUC
		ORDER BY Total DESC;
        
-- d)
SELECT * FROM Questao q
	WHERE q.idExame = (
		SELECT ed.Exame_idExame
			FROM ExameDisciplina ed
            WHERE ed.anoLetivo = '2013/2014' 
            AND ed.ordem = 1
            
-- e) tem algo errado...
CREATE TRIGGER atualuizaDificuldade AFTER INSERT ON Questao
	FOR EACH 
		update Exame e SET e.dificuldade = (
			select avg(q.dificuldade) FROM questao q
				where q.idExame = new.idExame
		where e.idExame = new.idExame