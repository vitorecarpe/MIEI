### FICHA ###

# 1
CREATE OR REPLACE VIEW tit_reagge AS
    SELECT t.titulo
    FROM titulo t
    INNER JOIN genero g ON t.id_genero = g.id_genero
    WHERE g.nome='Reagge';
## a)       
INSERT INTO titulo VALUES (50, 'woah', 10, to_date('01-02-2018', 'dd-mm-yyyy'), 1, 4, 5, 1);
SELECT * FROM tit_reagge;

# 2
ALTER TABLE titulo
add val_comercial NUMBER(3,0);
## a)
UPDATE titulo SET val_comercial = preco;
SELECT * FROM titulo;
## b)
CREATE OR REPLACE PROCEDURE act_val(ano NUMBER, perc NUMBER) AS
BEGIN
    UPDATE titulo t1
    SET t1.val_comercial = (1+perc)*(SELECT avg(t2.preco) FROM titulo t2
                                     WHERE extract(year FROM to_date(t2.data_compra, 'dd-mm-yyyy')) = ano)
    WHERE extract(year FROM to_date(t1.data_compra, 'dd-mm-yyyy')) = ano;
END;
## c)
CALL act_val(2010, 0.30);
## d)
SELECT titulo, preco, preco - val_comercial AS diferenca FROM titulo
WHERE preco - val_comercial > 0;

# 3
CREATE OR REPLACE FUNCTION devolve_preco (id NUMBER)
return NUMBER is
    preco NUMBER(4,0);
BEGIN
    SELECT t.preco into preco
    FROM titulo t
    WHERE t.id_titulo = id
    fetch first 1 rows only;
    return preco;
END;
SELECT devolve_preco(48) FROM dual;
## a)
SELECT id_titulo, titulo, preco FROM titulo
WHERE devolve_preco(id_titulo) = 10;
## b)
CREATE OR REPLACE PROCEDURE rem_titulos(price NUMBER) AS
BEGIN
    DELETE FROM review
    WHERE devolve_preco(review.id_titulo) = price;
    DELETE FROM musica
    WHERE devolve_preco(musica.id_titulo) = price;
    DELETE FROM titulo
    WHERE titulo.preco = price;
END;
CALL rem_titulos(10);

# 4
CREATE TABLE valor (
    val_total NUMBER(6,0) NOT NULL ENABLE,
    val_com_total NUMBER(6,0) NOT NULL ENABLE );

CREATE OR REPLACE TRIGGER single_row_constraint
BEFORE INSERT ON valor
DECLARE 
    rn NUMBER;
BEGIN
    SELECT count(*) into rn FROM valor;
    IF rn > 0
    THEN
        Raise_application_error(-20100,'Can not insert more than one row');
    END IF;
END;
## a)
INSERT INTO valor
    VALUES ((SELECT sum(titulo.preco) FROM titulo), (SELECT sum(titulo.val_comercial) FROM titulo));

UPDATE valor
SET val_total = (SELECT sum(titulo.preco) FROM titulo),
    val_com_total = (SELECT sum(titulo.val_comercial) FROM titulo);
## b)
SELECT max(id_titulo)+1 FROM titulo;

CREATE SEQUENCE titulo_sq 
    start WITH 50 -- valor do ultimo titulo
    increment by 1;
## c)
CREATE OR REPLACE TRIGGER valor_total
after INSERT OR DELETE OR UPDATE ON titulo
BEGIN
    UPDATE valor
    SET val_total = (SELECT sum(titulo.preco) FROM titulo),
        val_com_total = (SELECT sum(titulo.val_comercial) FROM titulo);
END;
## d)
INSERT INTO titulo
    (id_titulo, titulo, preco, dta_compra, 
    id_editora, 
    id_suporte, 
    id_genero, 
    id_autor, 
    val_comercial)
    VALUES
    (titulo_sq.NEXTVAL, 'Enjoy Triggers', 20, '22-FEB-2000',
    (SELECT id_editora FROM editora
        WHERE nome = 'Vevo'
        fetch first 1 rows only),
    (SELECT id_suporte FROM suporte
        WHERE nome = 'Spotify'
        fetch first 1 rows only),
    (SELECT id_genero FROM genero
        WHERE nome = 'Reagge'
        fetch first 1 rows only),
    (SELECT id_autor FROM autor
        WHERE nome = 'Big Nelo'
        fetch first 1 rows only),
    22);
## e)
UPDATE titulo
    SET preco = 30
    WHERE titulo = 'get get get get';

SELECT * FROM valor;
## f)
DELETE FROM review
    WHERE id_titulo =  (SELECT id_titulo FROM titulo
                        WHERE titulo = 'go head'
                        fetch first 1 rows only);
DELETE FROM musica
    WHERE id_titulo =  (SELECT id_titulo FROM titulo
                        WHERE titulo = 'go head'
                        fetch first 1 rows only);
DELETE FROM titulo
    WHERE titulo = 'go head';

SELECT * FROM valor;



