% Base do Conhecimento

:- ensure_loaded(main).

% Extensao do predicado utente: IDU,Nome,Idade,Morada,Genero -> {V,F,D}
utente(1,'Raul',20,'Campos','Masculino').
utente(2,'Francisco',20,'Joane','Masculino').
utente(3,'Vitor',20,'Vermoim','Masculino').
utente(4,'Carlos',7,'Campos','Masculino').
utente(5,'Bruno',20,'Campos','Masculino').
utente(6,'Ana',3,'Cerveira','Feminino').
utente(7,'Susana',20,'Cerveira','Feminino').
utente(8,'Cristina',40,'Cerveira','Feminino').
utente(9,'Fatima',77,'Braga','Feminino').
utente(10,'Filipe',33,'Braga','Masculino').
utente(11,'Carla',11,'Porto','Feminino').
utente(12,'Fabio',88,'Famalicao','Masculino').

% Extenão do predicado prestador: IDP,Nome,Especialidade,Instituição -> {V,F,D}
prestador(1,'Tiago','Ortopedia','Hospital de Braga').
prestador(2,'Guilherme','Urologia','Hospital de Braga').
prestador(3,'Renato','Radiologia','Hospital de Santa Maria').
prestador(4,'Filipe','Psiquiatria','Hospital de Santa Maria').
prestador(5,'Tiago','Cirurgia','Hospital de Braga').
prestador(6,'Vitor','Pediatria','Hospital de Santo Antonio').
prestador(7,'Gil','Cirurgia','Hospital de Braga').
prestador(8,'Joao','Ortopedia','Hospital de Braga').
prestador(9,'Diana','Psiquiatria','Hospital de Braga').

% Extenão do predicado cuidado: Data,IDU,IDP,Descrição,Custo -> {V,F,D}
cuidado('01-01-2018', 3, 1,'Dor de barriga', 15). 
cuidado('04-04-2018', 7, 2,'Braco partido',20).    
cuidado('23-01-2018', 4, 2,'Perna partida', 30).   
cuidado('01-05-2018', 2, 3,'Reacao alergica', 5).  
cuidado('03-03-2018', 12,8, 'Febre', 4).  
cuidado('31-12-2018', 10,5, 'Febre', 5). 
cuidado('30-03-2018', 7, 9,'Analises', 5).  
cuidado('31-12-2018', 7, 6,'Urgencia', 200).  
cuidado('31-12-2018', 1, 6,'Braco partido', 1).  
cuidado('07-08-2018', 11, 9,'Reacao alergica', 5). 
cuidado('01-04-2018', 1, 4,'Dor de cabeca', 30).  

