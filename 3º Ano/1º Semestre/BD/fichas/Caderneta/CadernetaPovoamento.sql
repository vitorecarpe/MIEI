-- Universidade do Minho
-- Mestrado Integrado em Engenharia Informática
-- Lincenciatura em Ciências da Computação
-- Unidade Curricular de Bases de Dados
-- 2016/2017
--
-- Caso de Estudo: "A Caderneta de Cromos" 
-- Povoamento parcial da base de dados.
-- Belo, O.

-- Base de dados de trabalho
USE Caderneta;

-- Povoamento da tabela "TipoCromo"
INSERT INTO TipoCromo
	(Nr, Descricao)
	VALUES 
		(1, 'Emblema'),
		(2, 'Jogador'),
		(3, 'Equipa'),
		(4, 'Equipamento');
-- SELECT * FROM TipoCromo;

-- Povoamento da tabela "Posicao"
INSERT INTO Posicao
	(Id, Designacao)
	VALUES 
		(1, 'Guarda-Redes'),
		(2, 'Defesa'),
		(3, 'Médio'),
		(4, 'Avançado');
-- SELECT * FROM POsicao;

-- Povoamento da tabela "Pais"
INSERT INTO Pais
	(Id, Designacao)
	VALUES 
		(1, 'Portugal'),
		(2, 'Brasil');
-- SELECT * FROM Pais;

-- Povoamento da tabela "Localidade"
INSERT INTO Localidade
	(Id, Designacao, Pais)
	VALUES 
		('ARO','Arouca', 1),
		('LIS','Lisboa', 1),
		('POR','Porto', 1),
		('BRG','Braga', 1),
		('CHA','Chaves', 1),
		('EST','Estoril', 1),
		('SMF','Santa Maria da Feira', 1),
		('FUN','Funchal', 1),
		('MCO','Moreira de Cónegos', 1),
		('PFE','Paços de Ferreira', 1),
		('VDC','Vila do Conde', 1),
		('TON','Tondela', 1),
		('GUI','Guimarães', 1),
		('SET','Setúbal', 1);
-- SELECT * FROM Localidade;

-- Povoamento da tabela "Equipa"
INSERT INTO Equipa
	(Id, Designacao, Localidade, Treinador, AnoFundacao, Estadio, Presidente, URL)
	VALUES 
		('FCA','Futebol Clube de Arouca','ARO','Lito Vidigal',1951,'Municipal de Arouca','Carlos Pinho','www,fcarouca.eu'),
		('CFB','Clube de Futebol os Belenenses','LIS','Júlio Velásquez',1919,'Restelo','Rui Pedro Soares','www,osbelenenses.com'),
		('SLB','Sport Lisboa e Benfica','LIS','Rui Vitória',1904,'Luz','Luis Filipe Vieira','www,slbenfica.pt'),
		('BFC','Boavista Futebol Clube','POR','Erwin Sánchez',1903,'Bessa Século XXI','João Loureiro','www,boavistafc.pt'),
		('SCB','Sporting Clube de Braga','BRG','José Peseiro',1921,'Axa','António Salvador','www,scbraga.pt'),
		('GDC','Grupo Desportivo de Chaves','CHA','Jorge Simão',1949,'Municipal Eng Manuel Branco Teixeira','Francisco José Carvalho','www,gdchaves.pt'),
		('GDE','Grupo Desportivo Estoril-Praia','EST','Fabiano Soares',1939,'António Coimbra da Mota','Frederico Pena','www,estorilpraia.pt'),
		('CDF','Clube Desportivo Feirense','SMF','José Mota',1918,'Marcolino de Castro','Rodrigo Nunes','www,cdfeirense.pt'),
		('CSM','Clube Sport Marítimo','FUN','Paulo Gusmão',1910,'Barreiros','Carlos Pereira','www,csmaritimo.org.pt'),
		('MFC','Moreirense Futebol Clube','MCO','Pepa',1938,'Comendador Joaquim de Almeida Freitas','Vitor Magalhães','www,moreirensefc.pt'),
		('CDN','Clube Desportivo Nacional','FUN','Manuel Machado',1910,'Madeira','Rui Alves','www,cdnacional.pt'),
		('FCF','Futebol Clube Paços de Ferreira','PFE','Carlos Pinto',1950,'Capital do Móvel','Rui Seabra','www,fcpf.pt'),
		('FCP','Futebol Clube do Porto','POR','Nuno espírito Santo',1893,'Dragão','Pinto da Costa','www,fcporto.pt'),
		('RAF','Rio Ave Futebol Clube','VDC','Nuno Capucho',1939,'Rio Ave FC','António Silva Campos','www,rioavefc.pt'),
		('SCP','Sporting Clube de Portugal','LIS','Jorge Jesus',1906,'José Alvalade','Bruno de Carvalho','www,sporting.pt'),
		('CDT','Clube Desportivo Tondela','TON','Petit',1933,'João Cardoso','Gilberto Coimbra','www,cdtondelapt.pt'),
		('VSC','Vitória Sport Clube','GUI','Júlio Mendes',1922,'D Afonso Henriques','Júlio Mendes','www,vitoriasc.pt'),
		('VFC','Vitória Futebol Clube','SET','José Couceiro',1910,'Bonfim','Fernando Oliveira','www,vfc.pt');
-- SELECT * FROM Equipa;

-- Povoamento da tabela "Jogador"
INSERT INTO Jogador
	(Nr, Nome, Equipa, Posicao)
	VALUES 
		(2,'Bracali','FCA',1),
		(3,'Anderson Luis','FCA',2),
		(19,'Ventura','CFB',1),
		(20,'João Diogo','CFB',2),
		(36,'Ederson','SLB',1),
		(37,'André Almeida','SLB',2),
		(69,'Mika','BFC',1),
		(70,'Tiago Mesquita','BFC',2),
		(86,'Marafona','SCB',1),
		(87,'Marcelo Goiano','SCB',2),
		(238,'Casillas','FCP',1),
		(239,'Maxi Pereira','FCP',2),
		(288,'Rui Patrício','SCP',1),
		(289,'João Pereira','SCP',2),
		(321,'Cladio Ramos','CDT',1),
		(322,'Jailson','CDT',2),
		(338,'Miguel Silva','VSC',1),
		(339,'Bruno Gaspar','VSC',2);
-- SELECT * FROM Jogador;

-- Povoamento da tabela "Cromo"
INSERT INTO Cromo
	(Nr, Tipo, Jogador, PagCaderneta, Descricao, Adquirido)
	VALUES 
		(1,1,NULL,1,'Emblema do Arouca','S'),
		(2,2,2,1,NULL,'S'),
		(3,2,3,1,NULL,'S'),
		(18,1,NULL,6,'Emblema do Belenenses','S'),
		(19,2,19,6,NULL,'S'),
		(20,2,20,6,NULL,'S'),
		(35,1,NULL,8,'Emblema do Benfica','S'),
		(36,2,36,8,NULL,'S'),
		(37,2,37,8,NULL,'N'),
		(68,1,NULL,12,'Emblema do Boavista','N'),
		(69,2,69,12,NULL,'S'),
		(70,2,70,12,NULL,'N'),
		(85,1,NULL,14,'Emblema do SC Braga','S'),
		(86,2,86,14,NULL,'N'),
		(87,2,87,14,NULL,'N'),
		(237,1,NULL,32,'Emblema do FC Porto','N'),
		(238,2,238,32,NULL,'S'),
		(239,2,239,32,NULL,'S'),
		(287,1,NULL,38,'Emblema do Sporting','N'),
		(288,2,288,38,NULL,'S'),
		(289,2,289,38,NULL,'S'),
		(320,1,NULL,42,'Emblema do Tondela','S'),
		(321,2,321,42,NULL,'S'),
		(322,2,322,42,NULL,'S'),		
		(337,1,NULL,44,'Emblema do Vitória de Guimarães','N'),
		(338,2,338,44,NULL,'S'),
		(339,2,339,44,NULL,'S');
-- SELECT * FROM Cromo;

-- 
-- A coleção de cromos
/*SELECT C.Nr, J.Nome, P.Designacao, E.Designacao, C.Descricao, Adquirido 
	FROM Cromo AS C 
		LEFT OUTER JOIN Jogador AS J
		ON C.Jogador=J.Nr
			LEFT OUTER JOIN Equipa AS E
			ON J.Equipa=E.Id
				LEFT OUTER JOIN Posicao AS P
				ON J.Posicao=P.Id;
*/

-- <fim>
--

