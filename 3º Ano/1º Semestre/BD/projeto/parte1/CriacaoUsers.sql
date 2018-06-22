DROP USER IF EXISTS 'admin'@'localhost';
DROP USER IF EXISTS 'artista1'@'localhost';
DROP USER IF EXISTS 'artista2'@'localhost';
DROP USER IF EXISTS 'artista3'@'localhost';
DROP USER IF EXISTS 'artista4'@'localhost';
DROP USER IF EXISTS 'artista5'@'localhost';
DROP USER IF EXISTS 'artista6'@'localhost';
DROP USER IF EXISTS 'visitante'@'localhost';

FLUSH PRIVILEGES;

-- CRIACAO DE USERS

-- Administrador
CREATE USER 'admin'@'localhost';
	SET PASSWORD FOR 'admin'@'localhost' = 'mudbadmin';
    
-- Artistas
CREATE USER 'artista1'@'localhost';
	SET PASSWORD FOR 'artista1'@'localhost' = 'artista1';
    
CREATE USER 'artista2'@'localhost';
	SET PASSWORD FOR 'artista2'@'localhost' = 'artista2';

CREATE USER 'artista3'@'localhost';
	SET PASSWORD FOR 'artista3'@'localhost' = 'artista3';

CREATE USER 'artista4'@'localhost';
	SET PASSWORD FOR 'artista4'@'localhost' = 'artista4';

CREATE USER 'artista5'@'localhost';
  SET PASSWORD FOR 'artista5'@'localhost' = 'artista5';

CREATE USER 'artista6'@'localhost';
  SET PASSWORD FOR 'artista6'@'localhost' = 'artista6';

-- Visitante
CREATE USER 'visitante'@'localhost';
  SET PASSWORD FOR 'visitante'@'localhost' = 'visit';

    
-- PERMISSOES DOS USERS

-- Administrador
GRANT ALL PRIVILEGES ON mudba.* TO 'admin'@'localhost';

-- Artistas em Artista
GRANT SELECT, UPDATE ON mudba.Artista TO 'artista1'@'localhost', 
										                     'artista2'@'localhost', 
                                         'artista3'@'localhost',
                                         'artista4'@'localhost',
                                         'artista5'@'localhost',
                                         'artista6'@'localhost';

REVOKE INSERT, DELETE ON mudba.Artista FROM 'artista1'@'localhost', 
                                            'artista2'@'localhost', 
                                            'artista3'@'localhost',
                                            'artista4'@'localhost',
                                            'artista5'@'localhost',
                                            'artista6'@'localhost';
-- Artistas em Banda
GRANT SELECT, UPDATE ON mudba.Banda TO 'artista1'@'localhost', 
                                       'artista2'@'localhost', 
                                       'artista3'@'localhost',
                                       'artista4'@'localhost',
                                       'artista5'@'localhost',
                                       'artista6'@'localhost';

REVOKE INSERT, DELETE ON mudba.Banda FROM 'artista1'@'localhost', 
                                          'artista2'@'localhost', 
                                          'artista3'@'localhost',
                                          'artista4'@'localhost',
                                          'artista5'@'localhost',
                                          'artista6'@'localhost';
-- Artistas em Solo
GRANT SELECT, UPDATE ON mudba.Solo TO 'artista1'@'localhost', 
                                      'artista2'@'localhost', 
                                      'artista3'@'localhost',
                                      'artista4'@'localhost',
                                      'artista5'@'localhost',
                                      'artista6'@'localhost';

REVOKE INSERT, DELETE ON mudba.Solo FROM 'artista1'@'localhost', 
                                         'artista2'@'localhost', 
                                         'artista3'@'localhost',
                                         'artista4'@'localhost',
                                         'artista5'@'localhost',
                                         'artista6'@'localhost';
-- Artistas em Membro
GRANT SELECT, INSERT, DELETE, UPDATE ON mudba.Membro TO 'artista1'@'localhost', 
                                                        'artista2'@'localhost', 
                                                        'artista3'@'localhost',
                                                        'artista4'@'localhost',
                                                        'artista5'@'localhost',
                                                        'artista6'@'localhost';
-- Artistas em Membro_pertence_Banda
GRANT SELECT, INSERT, DELETE, UPDATE ON mudba.Membro_pertence_Banda TO 'artista1'@'localhost', 
                                                        'artista2'@'localhost', 
                                                        'artista3'@'localhost',
                                                        'artista4'@'localhost',
                                                        'artista5'@'localhost',
                                                        'artista6'@'localhost';
-- Artistas em Gravação
GRANT SELECT, INSERT ON mudba.Gravacao TO 'artista1'@'localhost', 
                                          'artista2'@'localhost', 
                                          'artista3'@'localhost',
                                          'artista4'@'localhost',
                                          'artista5'@'localhost',
                                          'artista6'@'localhost';

REVOKE UPDATE, DELETE ON mudba.Gravacao FROM 'artista1'@'localhost', 
                                             'artista2'@'localhost', 
                                             'artista3'@'localhost',
                                             'artista4'@'localhost',
                                             'artista5'@'localhost',
                                             'artista6'@'localhost';  
-- Artistas em Faixa
GRANT SELECT, INSERT ON mudba.Faixa TO 'artista1'@'localhost', 
                                       'artista2'@'localhost', 
                                       'artista3'@'localhost',
                                       'artista4'@'localhost',
                                       'artista5'@'localhost',
                                       'artista6'@'localhost';

REVOKE UPDATE, DELETE ON mudba.Faixa FROM 'artista1'@'localhost', 
                                          'artista2'@'localhost', 
                                          'artista3'@'localhost',
                                          'artista4'@'localhost',
                                          'artista5'@'localhost',
                                          'artista6'@'localhost'; 
-- Artistas em Tipo
GRANT SELECT ON mudba.Tipo TO 'artista1'@'localhost', 
                                       'artista2'@'localhost', 
                                       'artista3'@'localhost',
                                       'artista4'@'localhost',
                                       'artista5'@'localhost',
                                       'artista6'@'localhost';

REVOKE INSERT, UPDATE, DELETE ON mudba.Tipo FROM 'artista1'@'localhost', 
                                          'artista2'@'localhost', 
                                          'artista3'@'localhost',
                                          'artista4'@'localhost',
                                          'artista5'@'localhost',
                                          'artista6'@'localhost'; 
-- Artistas em Género
GRANT SELECT ON mudba.Genero TO 'artista1'@'localhost', 
                                       'artista2'@'localhost', 
                                       'artista3'@'localhost',
                                       'artista4'@'localhost',
                                       'artista5'@'localhost',
                                       'artista6'@'localhost';

REVOKE INSERT, UPDATE, DELETE ON mudba.Genero FROM 'artista1'@'localhost', 
                                          'artista2'@'localhost', 
                                          'artista3'@'localhost',
                                          'artista4'@'localhost',
                                          'artista5'@'localhost',
                                          'artista6'@'localhost'; 
-- Artistas em Localidade
GRANT SELECT ON mudba.Localidade TO 'artista1'@'localhost', 
                                       'artista2'@'localhost', 
                                       'artista3'@'localhost',
                                       'artista4'@'localhost',
                                       'artista5'@'localhost',
                                       'artista6'@'localhost';

REVOKE INSERT, UPDATE, DELETE ON mudba.Localidade FROM 'artista1'@'localhost', 
                                          'artista2'@'localhost', 
                                          'artista3'@'localhost',
                                          'artista4'@'localhost',
                                          'artista5'@'localhost',
                                          'artista6'@'localhost'; 

-- Visitante
GRANT SELECT ON mudba.* TO 'visitante'@'localhost';

REVOKE UPDATE, INSERT, DELETE ON mudba.* FROM 'visitante'@'localhost';


