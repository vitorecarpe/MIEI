// Ficha 7 - Neo4j

// 1
match(p:Person)-[r:ACTED_IN]->(TheMatrix:Movie)
    return p.name, r.roles

// 2
match(p:Person)-[:ACTED_IN]->(m:Movie) 
    where p.name="Tom Hanks"
    return m

// 3
match(m:Movie) where m.released>=2000 
    return m

match(p:Person)-[:ACTED_IN]->(m:Movie) 
    where p.name="Tom Hanks" AND m.released>=2000
    return m

// 4
match(p:Person)-[:DIRECTED]->(m:Movie),
	 (p:Person)-[:ACTED_IN]->(m:Movie)
    return p

// 5
match(p:Person)-[r:ACTED_IN]->(m:Movie)
    where p.name="Keanu Reeves" AND "Neo" IN r.roles
    return m

// 6 
// Ator + lista dos seus diretores
match(a:Person)-[:ACTED_IN]->(m:Movie),
	 (d:Person)-[:DIRECTED]->(m:Movie)
    with a, collect(d.name) AS directors
    return {ator:a.name,diretores:directors}
// Tabela de pares Ator-Diretor
match(a:Person)-[:ACTED_IN]->(m:Movie)<-[:DIRECTED]-(d:Person)
    return a.name AS ACTOR,d.name AS DIRECTOR
// Lista de pares Ator-Diretor
match(a:Person)-[:ACTED_IN]->(m:Movie)<-[:DIRECTED]-(d:Person)
    return {ACTOR:a.name, DIRECTOR:d.name}

// 7
match(a:Person)-[:ACTED_IN]->(m:Movie)
    with a, count(m) AS numMovies
    return {ator:a.name,numMovies:numMovies}
    order by a.name

// 8
match(a:Person)-[:ACTED_IN]->(m:Movie),
	 (d:Person)-[:DIRECTED]->(m:Movie)
    with a,d, count(m) AS numMovies
    return {ator:a.name,diretor:d.name,numMovies:numMovies}
    order by numMovies DESC

// 9
match(a:Person)-[:ACTED_IN]->(m:Movie)
    with a, count(m) AS numMovies
    return {ator:a.name,numMovies:numMovies}
    order by numMovies DESC
    limit 5


