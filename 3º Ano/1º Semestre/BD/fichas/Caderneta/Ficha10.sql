/*
1. Em que páginas da caderneta estão os jogadores do ‘Sporting Clube de Braga’ e
do ‘Rio Ave Futebol Clube’ que jogam na posição ‘Defesa’? */

SELECT PagCaderneta FROM Cromo
INNER JOIN Jogador ON Cromo.Jogador = Jogador.Nr
INNER JOIN Posicao ON Jogador.Posicao = Posicao.Id
INNER JOIN Equipa ON Jogador.Equipa = Equipa.Id
WHERE (Equipa.designacao='Sporting Clube de Braga' OR Equipa.designacao='Rio Ave Futebol Clube')
AND Posicao.Designacao='Defesa';

/*
2. Quais os números dos cromos dos jogadores que não jogam
como ‘Médio’ ou ‘Defesa’, cujos treinadores são ‘Jorge Jesus’ e ‘Nuno Espírito Santo’.
Apresente a lista ordenada, de forma crescente, por número de cromo. */

SELECT Cromo.Nr, J.Nome FROM Cromo
JOIN Jogador AS J ON Cromo.Jogador = J.Nr
JOIN Equipa AS EQ ON J.Equipa = EQ.Id
JOIN Posicao AS P ON P.Id = J.Posicao
WHERE P.Designacao NOT IN ('Defesa','Medio') AND EQ.Treinador IN ('Jorge Jesus', 'Nuno Espirito Santo');

/*
3. Definir uma vista (view) que permita apresentar a lista dos cromos em falta, apresentando
o número do cromo, nome do jogador e nome da equipa à qual pertencem. */

/*
4. Implementar um procedimento (procedure) que, dado o nome de uma equipa apresente
a lista completa dos cromos que a ela dizem respeito, ordenando-a por número de página
e número do cromo. */
DELIMITER $$

CREATE PROCEDURE cromoEquipa (IN nome VARCHAR(75))
BEGIN
	SELECT * FROM Cromo
		INNER JOIN Jogador ON Cromo.Jogador=Jogador.Nr
		INNER JOIN Equipa ON Jogador.Equipa=Equipa.Id
		WHERE Equipa.Designacao = nome
        ORDER BY Cromo.PagCaderneta AND Cromo.Nr;
END
$$

CALL cromoEquipa("Sporting Clube de Braga");

/*
6. Implementar uma função (function) que, dado o número de um cromo, indique se o
cromo é ou não repetido. */

DELIMITER $$
CREATE FUNCTION alinea6_10 (cromo_id INT)
	RETURNS CHAR(1)
BEGIN
	DECLARE repetido CHAR(1);
    SET repetido = (SELECT adquirido FROM Cromo WHERE Nr=cromo_id);
    RETURN repetido;
END

$$

