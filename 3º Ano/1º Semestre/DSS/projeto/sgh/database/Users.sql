DROP USER IF EXISTS 'aluno'@'localhost';
DROP USER IF EXISTS 'docente'@'localhost';
DROP USER IF EXISTS 'admin'@'localhost';

FLUSH PRIVILEGES;

-- CRIACAO DE USERS

CREATE USER 'admin'@'localhost';
	SET PASSWORD FOR 'admin'@'localhost' = 'admin';
    
CREATE USER 'aluno'@'localhost';
	SET PASSWORD FOR 'aluno'@'localhost' = 'aluno';
    
CREATE USER 'docente'@'localhost';
	SET PASSWORD FOR 'docente'@'localhost' = 'docente';

-- PERMISSOES DOS USERS

GRANT ALL PRIVILEGES ON horarios.* TO 'admin'@'localhost';

GRANT SELECT ON horarios.Aluno TO 'aluno'@'localhost';
REVOKE INSERT, DELETE, UPDATE ON horarios.Aluno FROM 'aluno'@'localhost';
GRANT ALL PRIVILEGES ON horarios.Troca TO 'aluno'@'localhost';
GRANT SELECT, UPDATE ON horarios.Registo TO 'aluno'@'localhost';
REVOKE INSERT, DELETE ON horarios.Registo FROM 'aluno'@'localhost';
GRANT SELECT ON horarios.Turno TO 'aluno'@'localhost';
REVOKE INSERT, DELETE, UPDATE ON horarios.Turno FROM 'aluno'@'localhost';
GRANT SELECT ON horarios.TipoAula TO 'aluno'@'localhost';
REVOKE INSERT, DELETE, UPDATE ON horarios.TipoAula FROM 'aluno'@'localhost';
GRANT SELECT ON horarios.UC TO 'aluno'@'localhost';
REVOKE INSERT, DELETE, UPDATE ON horarios.UC FROM 'aluno'@'localhost';
GRANT SELECT ON horarios.Docente TO 'aluno'@'localhost';
REVOKE INSERT, DELETE, UPDATE ON horarios.Docente FROM 'aluno'@'localhost';
GRANT SELECT ON horarios.Curso TO 'aluno'@'localhost';
REVOKE INSERT, DELETE, UPDATE ON horarios.Curso FROM 'aluno'@'localhost';
GRANT SELECT ON horarios.InfoExtra TO 'aluno'@'localhost';
REVOKE INSERT, DELETE, UPDATE ON horarios.InfoExtra FROM 'aluno'@'localhost';

GRANT ALL PRIVILEGES ON horarios.Aluno TO 'docente'@'localhost';
GRANT ALL PRIVILEGES ON horarios.Troca TO 'docente'@'localhost';
GRANT ALL PRIVILEGES ON horarios.Registo TO 'docente'@'localhost';
GRANT ALL PRIVILEGES ON horarios.Turno TO 'docente'@'localhost';
GRANT SELECT ON horarios.TipoAula TO 'docente'@'localhost';
REVOKE INSERT, DELETE, UPDATE ON horarios.TipoAula FROM 'docente'@'localhost';
GRANT ALL PRIVILEGES ON horarios.UC TO 'docente'@'localhost';
GRANT SELECT, INSERT, UPDATE ON horarios.Docente TO 'docente'@'localhost';
REVOKE DELETE ON horarios.Docente FROM 'docente'@'localhost';
GRANT SELECT, UPDATE ON horarios.Curso TO 'docente'@'localhost';
REVOKE INSERT, DELETE ON horarios.Curso FROM 'docente'@'localhost';
GRANT ALL PRIVILEGES ON horarios.InfoExtra TO 'docente'@'localhost';

