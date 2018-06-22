
-- ------------------------------------------------------
-- ------------------------------------------------------
-- Universidade do Minho
-- Mestrado Integrado em Engenharia Informática
-- Unidade Curricular de Bases de Dados
-- 
-- Caso de Estudo: MuDBa - Base de Dados Musical
-- Povoamento inicial da base de dados
-- Novembro/2017
-- ------------------------------------------------------
-- ------------------------------------------------------

--
-- Esquema: "MuDBa"
USE `MuDBa` ;

--
-- Povoamento da tabela "Localidade"
INSERT INTO Localidade
	(id_localidade, cidade, pais)
	VALUES 
		(1, "Eau Claire", "Estados Unidos da América"),
		(2, "Oxford", "Reino Unido"),
		(3, "Los Angeles", "Estados Unidos da América"),
		(4, "Londres", "Reino Unido"),
		(5, "Las Vegas", "Estados Unidos da América"),
		(6, "Atlanta", "Estados Unidos da América"),
		(7, "Wellingborough", "Reino Unido"),
		(8, "Abingdon", "Reino Unido"),
		(9, "Grand Rapids", "Estados Unidos da América"),
		(10,"Melbourne", "Austrália"),
		(11,"Saint Paul", "Estados Unidos da América"),
		(12,"Exeter", "Reino Unido"),
		(13,"Kirkcaldy", "Reino Unido"),
		(14,"Southampton", "Reino Unido"),
		(15,"Henderson", "Estados Unidos da América"),
		(16,"Pela", "Estados Unidos da América"),
		(17,"Houston", "Estados Unidos da América")
	;

--
-- DELETE FROM Localidade;
-- SELECT * FROM Localidade;


--
-- Povoamento da tabela "Artista"
INSERT INTO Artista
	(id_artista, nome, biografia, inicio, fim, localidade)
	VALUES 
		(1, "Bon Iver",
		 "Bon Iver é uma banda de Indie Folk fundada em 2007 em Eau Claire, Wisconsin, Estados Unidos e liderada pelo
		 cantor e compositor Justin Vernon, juntamente com Michael Noyce, Sean Carey e Matt McCaughan. Justin lançou
		 de forma independente o primeiro álbum da banda «For Emma, Forever Ago» em 2007 quando se fechou numa cabana
		 durante três meses. O nome deriva do francês «Bom Inverno».",
		 '2006',
		 NULL,
		 1),
		(2,
		 "Radiohead",
		 "Radiohead é uma banda inglesa formada em 1985 por Thom Yorke, Jonny Greenwood, Ed O'Brien,
		 Colin Greenwood e Philip Selway. Lançaram «Creep» em 1992 e o primeiro álbum «Pablo Honey»
		 em 1993. A popularidade no Reino Unido aumentou com «The Bends» em 1995. No entanto o álbum mais
		 memorável é «OK Computer», de 1997. É ainda hoje considerado um marco dos anos 90, com o seu som
		 expansivo, a textura atmosférica das guitarras e o falsetto de Thom Yorke.",
		 '1985',
		 NULL,
		 2),
		(3,
		 "Red Hot Chili Peppers",
		 "Red Hot Chili Peppers é uma banda de rock formada em LA, 1983. O estilo consiste principalmente em rock,
		 bem como funk, punk e rock psicadélico. A banda é
		 constituída por Anthony Kiedis, Flea, Chad Smith e Josh Klinghoffer. Blood Sugar Sex Magik foi o primeiro
		 grande sucesso, com 15 milhões de cópias vendidas. Pelo meio lançaram outros álbuns memoráveis como
		 Stadium Arcadium ou Californication. Ganharam até hoje 6 Grammy Awards e já venderam mais de 75 milhões de
		 discos.",
		 '1983',
		 NULL,
		 3),
		(4,
		 "Coldplay",
		 "Coldplay é uma banda britânica formada em 1996 pelo vocalista Chris Martin e
		 guitarrista Jonny Buckland na University College of London. Guy Berryman e Will Champion,
		 colegas na mesma universidade, acabariam por se juntar ao grupo. Alcançaram a fama
		 mundial com Yellow em 2000, do álbum «Parachutes». Contam com sete álbuns até hoje que
		 contabilizam um total de 60 milhões de vendas em todo o mundo, tendo arrecadado 7 Grammy's.",
		 '1996',
		 NULL,
		 4),
		(5,
		 "The Killers",
		 "The Killers é uma banda de Indie Rock de Las Vegas formada em 2002 e composta por Flowers, Keuning, Stoermer e Vannucci Jr. 
		 A banda começou a atrair atenções das gravadoras em Londres, onde fecharam
		 um acordo em 2002. O álbum de estreia «Hot Fuss» lançado em 2004 teve sucesso mundial com as canções «Mr. Brightside»
		 e «Somebody Told Me». A maior inspiração dos The Killers vem da «New Wave» britânica dos anos 80. Os quatros álbuns
		 já venderam mais de 15 milhões de cópias pelo mundo.",
		 '2002',
		 NULL,
		 5),
		(6,
		 "Kanye West",
		 "Kanye West é um rapper americano, vencedor de 21 Grammy's'. Nasceu em Atlanta, mas aos 3 foi viver 
		 para Chicago. Na escola já fazia «beats» para alguns artistas. Kanye alcançou a fama
		 após lançar o álbum The College Dropout em 2004. A sua palete musical expressa influências que
		 incorporam R&B, Pop, Alt Rock e até música clássica. Kanye é caraterizado pelo seu
		 estilo autêntico e pelo seu ego incomparável no mundo na música. Em 2014 casou-se
		 com a norteamericana Kim Kardashian.",
		 '1996',
		 NULL,
		 6)
	;
--
-- DELETE FROM Artista;
-- SELECT * FROM Artista;

--
-- Povoamento da tabela "Tipo"
INSERT INTO Tipo
	(id_tipo, nome_tipo)
	VALUES 
		(1, "Álbum"),
		(2, "EP"),
		(3, "Single"),
		(4, "Remix")
	;

--
-- DELETE FROM Tipo;
-- SELECT * FROM Tipo;

--
-- Povoamento da tabela "Genero"
INSERT INTO Genero
	(id_genero, nome)
	VALUES 
		(1, "Rock"),
		(2, "Pop"),
		(3, "Indie"),
		(4, "Rap"),
		(5, "Altenativo"),
		(6, "Eletrónica"),
		(7, "Folk"),
		(8, "EDM"),
		(9, "Jazz"),
		(10,"Shoegaze")
	;
--
-- DELETE FROM Genero;
-- SELECT * FROM Genero;


-- Povoamento da tabela "Dourado"
INSERT INTO Gravacao
	(id_album, titulo, ano, descricao, pontuacao, id_artista, id_tipo, id_genero)
	VALUES 
		(1, "For Emma, Forever Ago", '2008', "Um dos maiores álbuns de separação de sempre, para não falar de uma das revelações desta era.
			Justin Vernon, recuperando de uma relação acabada e o fim da sua antiga banda, refugia-se numa cabana no interior do Wisconsin
			com a sua guitarra acústica. Passou o inverno a cortar madeira, crescer a barba e escrever música. Apesar do som folclórico, é
			um álbum acolhedor e confortável com o falsetto de Vernon.", 8, 1, 1, 3),
		(2, "OK Computer", '1997', "OK Computer é o terceiro álbum de estúdio dos Radiohead. Foi lançado para o reconhecimento internacional
		    e é frequentemente considerado um dos melhores álbuns de sempre. O sucesso de «Creep» e «The Bends» deu liberdade total da 
		    gravadora para o processo criativo da banda. O resultado é este clássico que definiu novos estilos musicais. As letras das 
		    canções são muito à frente do seu tempo, caraterizando uma sociedade obcecada pela tecnologia e o isolamento e paranóia.", 10, 2, 1, 1),
		(3, "Californication", '1999', "Este álbum marca o regresso de John Frusciante após a sua recuperação do vício em drogas trazendo uma
			melhoria musical que o seu substituto, Dave Navarro, não conseguia dar. O álbum contem temas relacionados com insinuações sexuais,
			geralmente associadas à banda, mas também outros temas como luxo, morte, suicídio, Califórnia e drogas. É o maior sucesso comercial
			dos Red Hot Chili Peppers com 15 milhões de cópias vendidas no mundo.", 7, 3, 1, 1),
		(4, "Kaleidoscope EP", '2017', "Kaleidoscope é o mais recente EP dos Coldplay, irmão do álbum A Head Full Of Dreams, lançado 18 meses antes.
			Este EP é tudo o que A Head Full Of Dreams deveria ter sido. Claramente perde o otimismo radiante do seu antecessor e mistura colaborações
			pop com a dupla «The Chainsmokers» mas ao mesmo tempo leva-nos para o lado mais experimentalista e rock que a banda nos habituou
			nos seus primeiros anos com as canções All I Can Think About Is You e A L I E N S.", 6, 4, 2, 1),
		(5, "Parachutes", '2000', "Parachutes é o álbum de estreia dos Coldplay, após terem lançado três EPs. Este álbum trouxe ao mundo o hit 
			Yellow que atingiu o número 1 no Reino Unido e deu a conhecer a banda à Europa por onde fizeram uma pequena digressão que contou
			com uma passagem pelo Festival Paredes de Coura em 2000. O álbum tem um ritmo bastante acústico e melancólico com influências de Tom Waits,
			Travis e Radiohead.", 7, 4, 1, 7)
	;
--
-- DELETE FROM Gravacao;
-- SELECT * FROM Gravacao;

--
-- Povoamento da tabela "Banda"
INSERT INTO Banda
	(id_artista, membros)
	VALUES 
		(1, 4),
		(2, 5),
		(3, 4),
		(4, 4),
		(5, 4)
	;
--
-- DELETE FROM Banda;
-- SELECT * FROM Banda;

--
-- Povoamento da tabela "Faixa"
INSERT INTO Faixa
	(id_faixa, nome, duracao, numero, id_album)
	VALUES 
		(1,  "Flume",         	                  '00:03:39', 1, 1),
		(2,  "Lump Sum",                          '00:03:21', 2, 1),
		(3,  "Skinny Love",                       '00:03:59', 3, 1),
		(4,  "The Wolves",                        '00:05:22', 4, 1),
		(5,  "Blindsided",                        '00:05:29', 5, 1),
		(6,  "Creature Fear",                     '00:03:06', 6, 1),
		(7,  "Team",                              '00:01:57', 7, 1),
		(8,  "For Emma",                          '00:03:41', 8, 1),
		(9,  "re:Stacks",                         '00:06:41', 9, 1),
		(10, "Airbag",                            '00:04:45', 1, 2),
		(11, "Paranoid Android",                  '00:06:25', 2, 2),
		(12, "Subterranean Homesick Alien",       '00:04:26', 3, 2),
		(13, "Exit Music (For a Film)",           '00:04:26', 4, 2),
		(14, "Let Down",                          '00:04:58', 5, 2),
		(15, "Karma Police",                      '00:04:22', 6, 2),
		(16, "Fitter Happier",                    '00:01:56', 7, 2),
		(17, "Electioneering",                    '00:03:49', 8, 2),
		(18, "Climbing Up The Walls",             '00:04:44', 9, 2),
		(19, "No Surprises",                      '00:03:51', 10,2),
		(20, "Lucky", 							  '00:04:16', 11,2),
		(21, "The Tourist",                       '00:05:25', 12,2),
		(22, "Around the World",                  '00:03:58', 1, 3),
		(23, "Parallel Universe",                 '00:04:30', 2, 3),
		(24, "Scar Tissue",                       '00:03:35', 3, 3),
		(25, "Otherside",                         '00:04:15', 4, 3),
		(26, "Get on Top",                        '00:03:18', 5, 3),
		(27, "Californication",                   '00:05:21', 6, 3),
		(28, "Easily", 							  '00:03:51', 7, 3),
		(29, "Porcelain",                         '00:02:43', 8, 3),
		(30, "Emit Remmus",                       '00:04:00', 9, 3),
		(31, "I Like Dirt", 					  '00:02:37', 10,3),
		(32, "This Velvet Glove",                 '00:03:45', 11,3),
		(33, "Savior",                            '00:04:52', 12,3),
		(34, "Purple Stain",                      '00:04:13', 13,3),
		(35, "Right on Time",                     '00:02:08', 14,3),
		(36, "Road Trippin'",                     '00:03:25', 15,3),
		(37, "All I Can Think About Is You",      '00:04:34', 1, 4),
		(38, "Miracles (Someone Special)",        '00:04:36', 2, 4),
		(39, "A L I E N S",                       '00:04:42', 3, 4),
		(40, "Something Just Like This",          '00:04:34', 4, 4),
		(41, "Hypnotised",                        '00:05:55', 5, 4),
		(42, "Don't Panic",						  '00:02:17', 1, 5),
		(43, "Shiver", 							  '00:05:04', 2, 5),
		(44, "Spies", 							  '00:05:19', 3, 5),
		(45, "Sparks", 							  '00:03:47', 4, 5),
		(46, "Yellow", 							  '00:04:27', 5, 5),
		(47, "Trouble", 						  '00:04:33', 6, 5),
		(48, "Parachutes", 						  '00:00:46', 7, 5),
		(49, "High Speed", 						  '00:04:16', 8, 5),
		(50, "We Never Change",					  '00:04:09', 9, 5),
		(51, "Everything's Not Lost", 			  '00:07:16', 10,5)
	;
--
-- DELETE FROM Faixa;
-- SELECT * FROM Faixa;

--
-- Povoamento da tabela "Solo"
INSERT INTO Solo
	(id_artista, nascimento, falecimento, sexo)
	VALUES 
		(6, '19770608', NULL, 'm')
	;
--
-- DELETE FROM Solo;
-- SELECT * FROM Solo;

--
-- Povoamento da tabela "Membro"
INSERT INTO Membro
	(id_membro, nome, nascimento, falecimento, sexo, localidade, instrumento)
	VALUES
		(1, "Justin Vernon", '19810430', NULL, 'm', 1, "voz"),
		(2, "Michael Noyce", '19850112', NULL, 'm', 1, "guitarra"),
		(3, "Sean Carey", '19800922', NULL, 'm', 1, "bateria"),
		(4, "Matt McCaughan", '19820801', NULL, 'm', 1, "baixo"),
		(5, "Thom Yorke", '19681007', NULL, 'm', 7, "voz"),
		(6, "Ed O'Brien", '19680415', NULL, 'm', 2, "guitarra"),
		(7, "Jonny Greenwood", '19710905', NULL, 'm', 2, "guitarra"),
		(8, "Colin Greenwood", '19690626', NULL, 'm', 2, "baixo"),
		(9, "Phil Selway", '19670523', NULL, 'm', 8, "bateria"),
		(10,"Anthony Kiedis", '19621101', NULL, 'm', 9, "voz"),
		(11,"Flea", '19621016', NULL, 'm', 10, "baixo"),
		(12,"Josh Klinghoffer", '19791003', NULL, 'm', 3, "guitarra"),
		(13,"Chad Smith", '19611025', NULL, 'm', 11, "bateria"),
		(14,"Chris Martin", '19770302', NULL, 'm', 12, "voz"),
		(15,"Jonny Buckland", '19770911', NULL, 'm', 4, "guitarra"),
		(16,"Guy Berryman", '19780412', NULL, 'm', 13, "baixo"),
		(17,"Will Champion", '19780731', NULL, 'm', 14, "bateria"),
		(18,"Brandon Flowers", '19810621', NULL, 'm', 15, "voz"),
		(19,"Dave Keuning", '19760328', NULL, 'm', 16, "guitarra"),
		(20,"Ronnie Vannucci", '19760215', NULL, 'm', 5, "bateria"),
		(21,"Mark Stoermer", '19770628', NULL, 'm', 17, "baixo")
	;
--
-- DELETE FROM Membro;
-- SELECT * FROM Membro;

-- Povoamento da tabela "Membro Pertence a Banda"
INSERT INTO Membro_pertence_Banda
	(id_artista, id_membro)
	VALUES
		(1, 1),
		(1, 2),
		(1, 3),
		(1, 4),
		(2, 5),
		(2, 6),
		(2, 7),
		(2, 8),
		(2, 9),
		(3, 10),
		(3, 11),
		(3, 12),
		(3, 13),
		(4, 14),
		(4, 15),
		(4, 16),
		(4, 17),
		(5, 18),
		(5, 19),
		(5, 20),
		(5, 21)
	;
--
-- DELETE FROM Membro_pertence_Banda
-- SELECT * FROM Membro_pertence_Banda

-- ------------------------------------------------------
-- <fim>
--
-- Francisco Oliveira - a78416
-- João Carvalho - a79073
-- Raul Vilas Boas - a79614
-- Vitor Peixoto - a79175
-- 
-- 01/11/2017
-- ------------------------------------------------------
