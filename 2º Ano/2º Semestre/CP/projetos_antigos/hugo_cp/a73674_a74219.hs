--
-- Projecto CP 2015/16
--
-- O projecto consiste em desenvolver testes para o módulo Graph.hs
-- (para grafos orientados e não pesados).
-- Mais concretamente, o projecto consiste em 3 tarefas que são descritas abaixo.
-- O prazo para entrega é o dia 3 de Abril. Cada grupo deve enviar apenas
-- o módulo de testes (este módulo) por email para calculodeprogramas@gmail.com
-- O nome do ficheiro deve identificar os números dos 2 alunos do grupo (numero1_numero2.hs).
-- Certifiquem-se que o módulo de testes desenvolvido compila correctamente antes
-- de submeter. O módulo Graph.hs não deve ser alterado.
-- Os 2 alunos do grupo devem também indentificar-se nos comentários abaixo.
--
-- Aluno 1
-- Número: A73674
-- Nome: Alexandre Lopes Mandim da Silva
-- Curso: MIEI
--
-- Aluno 2
-- Número: A74219
-- Nome: Hugo Alves Carvalho
-- Curso: MIEI
--


module Main where

import Graph
import Test.HUnit hiding (path)
import Test.QuickCheck
import Data.Maybe
import Data.Set as Set

--
-- Teste unitário
--
    
g1 :: Graph Int
g1 = Graph {nodes = fromList [1],
            edges = fromList [Edge 1 1]
           }

-- Nossos grafos

-- Grafos válidos

g2 :: Graph Int
g2 = Graph { nodes = fromList[1,2,3,4], edges = fromList[Edge 1 4, Edge 3 4, Edge 4 2, Edge 3 2, Edge 2 1]}

gTopo :: Graph Int
gTopo = Graph { nodes = fromList[1,2,7,10, 8,69], edges = fromList[Edge 2 1, Edge 7 1 , Edge 10 1, Edge 8 7, Edge 69 10, Edge 8 2, Edge 7 2]}

g2g1 :: Graph Int
g2g1 = Graph { nodes = fromList[1,2,3,4], edges = fromList[Edge 1 4, Edge 3 4, Edge 4 2, Edge 3 2, Edge 2 1, Edge 1 1]}

g2Transpose :: Graph Int
g2Transpose = Graph { nodes = fromList[1,2,3,4], edges = fromList[Edge 4 1, Edge 4 3, Edge 2 4, Edge 2 3, Edge 1 2]}

gsubGraphG2 :: Graph Int
gsubGraphG2 = Graph { nodes = fromList[2,3,4], edges = fromList[Edge 3 4, Edge 4 2, Edge 3 2]}

g3 :: Graph Int
g3 = Graph { nodes = fromList[1,2,3,4,5], edges = fromList[Edge 1 2, Edge 1 3, Edge 1 4, Edge 2 5, Edge 3 5, Edge 5 4]}


gEmpty :: Graph Int
gEmpty = Graph {nodes = Set.empty, edges = Set.empty}

-- DAG
gAciclico :: Graph Int
gAciclico = Graph { nodes = fromList[1,2,3], edges = fromList[Edge 1 2, Edge 1 3, Edge 2 3]}

gForest :: Graph Int
gForest = Graph { nodes = fromList[1,2,3], edges = fromList[Edge 1 2, Edge 2 3]}
-- Grafos inválidos
-- Arestas c/ vertices que nao existem
gInvalido :: Graph Int
gInvalido = Graph { nodes = fromList[1,2,4], edges = fromList[Edge 1 4, Edge 3 4, Edge 4 2, Edge 3 2, Edge 2 1]}


--
-- Tarefa 1
--
-- Defina testes unitários para todas as funções do módulo Graph,
-- tentando obter o máximo de cobertura de expressões, condições, etc.
--
-- swap
test_swap = TestList [swap (Edge 1 4) ~?= (Edge 4 1),
                      swap (Edge 1 1) ~?= (Edge 1 1)]

-- isEmpty
test_isEmpty = TestList [isEmpty g2 ~?= False,
                         isEmpty gEmpty ~?= True]

-- isValid
test_isValid = TestList[isValid gInvalido ~?= False, 
                        isValid gEmpty ~?= True, 
                        isValid g2 ~?= True]
-- isDAG
test_isDAG = TestList[isDAG gAciclico ~?= True,
                      isDAG g2 ~?= False,
                      isDAG g1 ~?= False,
                      isDAG gEmpty ~?= True]

-- isForest
test_isForest = TestList[isForest gForest ~?= True,
                        isForest g2 ~?= False,
                        isForest g1 ~?= False, 
                        isForest gEmpty ~?= True]

-- isSubgraphOf
test_isSubGrapOf = TestList[isSubgraphOf gsubGraphG2 g2 ~?= True,
                            isSubgraphOf gEmpty g2 ~?= True,
                            isSubgraphOf g1 g2 ~?= False,
                            isSubgraphOf g2 gEmpty ~?= False]

-- adj
test_adj = TestList [adj g2 4 ~?= fromList [Edge 4 2],
                      adj gEmpty 1 ~?= Set.empty,
                      adj g2 1 ~?= fromList[Edge 1 4],
                      adj g1 1 ~?= fromList[Edge 1 1]]

-- transpose
test_transpose = TestList[transpose g2 ~?= g2Transpose,
                          transpose g1 ~?= g1,
                          transpose gEmpty ~?= gEmpty]
-- union
test_union = TestList[Graph.union g2 gsubGraphG2 ~?= g2,
                      Graph.union g1 g2 ~?= g2g1,
                      Graph.union g2 gEmpty ~?= g2,
                      Graph.union g2 g2 ~?= g2]

-- bft
aux :: Set Int
aux = fromList[1]

aux2 :: Set Int
aux2 = fromList[3]

test_bft = TestList[bft g3 aux ~?= Graph {nodes = fromList[1,2,3,4,5], edges = fromList[Edge 2 1, Edge 3 1, Edge 4 1, Edge 5 2]},
                    bft g3 aux2 ~?= Graph {nodes = fromList[3,4,5], edges = fromList[Edge 5 3, Edge 4 5]},
                    bft gEmpty aux2 ~?= Graph {nodes = fromList[3], edges = Set.empty}]

-- reachable
test_reachable = TestList[reachable g3 5 ~?= fromList[5,4],
                          reachable g2 3 ~?= fromList[1,2,3,4]]
-- isPathOf
test_isPathOf = TestList[isPathOf [Edge 1 4, Edge 4 5] g3 ~?= False,
                         isPathOf [Edge 1 2, Edge 2 5, Edge 5 4] g3 ~?= True,
                         isPathOf [Edge 1 2] gEmpty ~?= False,
                         isPathOf [] g3 ~?= True]
-- path
test_path = TestList[path g3 1 5 ~?= Just ([Edge 1 2, Edge 2 5]),
                     path g3 1 4 ~?= Just([Edge 1 4]),
                     path g2 2 3 ~?= Nothing,
                     path gEmpty 1 8 ~?= Nothing]

-- topo
test_topo = TestList[topo gTopo ~?= [fromList [8,69],fromList [7,10],fromList [2],fromList [1]],
                     topo gAciclico ~?= [fromList[1], fromList[2],fromList[3]],
                     topo gEmpty ~?= [],
                     topo gForest ~?= [fromList[1], fromList[2,3]]]

main = runTestTT $ TestList [test_swap,test_isEmpty, test_isValid, test_isDAG, test_isForest, test_isSubGrapOf, test_adj, test_transpose,
                             test_union, test_bft, test_reachable, test_isPathOf, test_path, test_topo]

--
-- Teste aleatório
--

--
-- Tarefa 2
--
-- A instância de Arbitrary para grafos definida abaixo gera grafos
-- com muito poucas arestas, como se pode constatar testando a
-- propriedade prop_valid.
-- Defina uma instância de Arbitrary menos enviesada.
-- Este problema ainda é mais grave nos geradores dag e forest que
-- têm como objectivo gerar, respectivamente, grafos que satisfazem
-- os predicados isDag e isForest. Estes geradores serão necessários
-- para testar propriedades sobre estas classes de grafos.
-- Melhore a implementação destes geradores por forma a serem menos enviesados.
--
-- Instância de Arbitrary para arestas
instance Arbitrary v => Arbitrary (Edge v) where
    arbitrary = do s <- arbitrary
                   t <- arbitrary
                   return $ Edge {source = s, target = t}

-- Funcao que gera a lista com o nr de Arestas q cada vertice vai ter
-- Recebe nr vertices e até q vertice geramos; Devolve a lista com o nr de Arestas de cada vertice
nrArestas :: Int -> Int -> Gen [Int]
nrArestas 0 _ =return ([])
nrArestas x n = do y <- elements[0..n]
                   ys <- nrArestas (x-1) n
                   return  (y:ys)


-- Esta funcao dado um vertices e a lista de vertices p cada vertice cria uma lista com as Arestas
verticesToArestas :: [v] -> [[v]] -> [Edge v]
verticesToArestas [] _ = []
verticesToArestas _ [] = []
verticesToArestas (x:xs) (y:ys) = (vAux x y) ++ (verticesToArestas xs ys)
                                  where vAux x [] = []
                                        vAux x (y:ys) = (Edge x y : vAux x ys)

mytake :: Int -> Gen [a] -> Gen [a]
mytake n x = do lista <- x
                return (take n lista)

instance (Ord v, Arbitrary v) => Arbitrary (Graph v) where 
    arbitrary = do nrVertices <- elements [0..35] -- gera o nr vertices entre 0 e um valor
                   arrayVertices <- vector nrVertices  -- cria a lista de vertices ate o valor gerado
                   nrArestasDCadaVertice <- nrArestas nrVertices nrVertices -- cria uma lista com o nr de arestas q cada vertice vai ter
                   listaArestaspCadaAresta <- mapM (\x -> mytake x (shuffle arrayVertices)) nrArestasDCadaVertice -- Da a lista de vertices (das arestas) para cada vertice
                   let edgesFinal = verticesToArestas arrayVertices listaArestaspCadaAresta
                   return $ Graph {nodes = fromList arrayVertices, edges = fromList edgesFinal}




prop_valid :: Graph Int -> Property
prop_valid g = collect (length (edges g)) $ isValid g

-- Gerador de DAGs
dag :: (Ord v, Arbitrary v) => Gen (DAG v)
dag = aux `suchThat`isDAG
      where aux = do nrVertices <- elements [0..30] -- gera o nr vertices entre 0 e um valor
                     arrayVertices <- vector nrVertices  -- cria a lista de vertices ate o valor gerado
                     nrArestasDCadaVertice <- nrArestas nrVertices 1 -- cria uma lista com o nr de arestas q cada vertice vai ter
                     listaArestaspCadaAresta <- mapM (\x -> mytake x (shuffle arrayVertices)) nrArestasDCadaVertice -- Da a lista de vertices (das arestas) para cada vertice
                     let edgesFinal = verticesToArestas arrayVertices listaArestaspCadaAresta
                     return $ Graph {nodes = fromList arrayVertices, edges = fromList edgesFinal}


prop_dag :: Property
prop_dag = forAll (dag :: Gen (DAG Int)) $ \g -> collect (length (edges g)) $ isDAG g

-- Gerador de florestas
forest :: (Ord v, Arbitrary v) => Gen (Forest v)
forest = aux `suchThat`isForest
      where aux = do nrVertices <- elements [0..10] -- gera o nr vertices entre 0 e um valor
                     arrayVertices <- vector nrVertices  -- cria a lista de vertices ate o valor gerado
                     nrArestasDCadaVertice <- nrArestas nrVertices 1 -- cria uma lista com o nr de arestas q cada vertice vai ter
                     listaArestaspCadaAresta <- mapM (\x -> mytake x (shuffle arrayVertices)) nrArestasDCadaVertice -- Da a lista de vertices (das arestas) para cada vertice
                     let edgesFinal = verticesToArestas arrayVertices listaArestaspCadaAresta
                     return $ Graph {nodes = fromList arrayVertices, edges = fromList edgesFinal}
prop_forest :: Property
prop_forest = forAll (forest :: Gen (Forest Int)) $ \g -> collect (length (edges g)) $ isForest g

--
-- Tarefa 3
--
-- Defina propriedades QuickCheck para testar todas as funções
-- do módulo Graph.
--

-- Exemplo de uma propriedade QuickCheck para testar a função adj       
prop_adj :: Graph Int -> Property
prop_adj g = forAll (elements $ elems $ nodes g) $ \v -> adj g v `isSubsetOf` edges g

-- NOSSAS PROPRIEDADES

-- Se um grafo é DAG então o mesmo grafo transposto também o é
prop_dag_transpose2 :: Property
prop_dag_transpose2 = forAll (dag :: Gen (DAG Int)) $ \g -> property $ (isDAG g == isDAG(transpose g))

-- Se fizermos duas vezes swap temos o mesmo edge
prop_swap :: Edge Int -> Property
prop_swap e = property $ (swap (swap e) == e)

-- A U A = A
prop_union_reflex :: Graph Int -> Property
prop_union_reflex g = property $ (Graph.union  g g) == g

-- A soma das quantidades de nodos de dois grafos >= Quantidade de nodos da uniao desses grafos
prop_union_somaNodo :: Graph Int -> Graph Int -> Property
prop_union_somaNodo g1 g2 = property $ ((length $ elems $ nodes g1) + (length $ elems $ nodes g2)) >= length(elems $ nodes $ Graph.union g1 g2)

-- A U (B U C) == (A U B) U C
prop_union_associativa :: Graph Int -> Graph Int -> Graph Int -> Property
prop_union_associativa g1 g2 g3 = property $ (Graph.union g1 $ Graph.union g2 g3) == (Graph.union (Graph.union g1 g2) g3)

-- A U B == B U A
prop_union_comutativa :: Graph Int -> Graph Int -> Property
prop_union_comutativa g1 g2 = property $ Graph.union g1 g2 == Graph.union g2 g1

-- A U 0 = A
prop_union_neutro :: Graph Int -> Property
prop_union_neutro g1 = property $ Graph.union g1 gEmpty == g1

-- transposta da transposta da o grafo original
prop_2xtransposta :: Graph Int -> Property
prop_2xtransposta g1 = property $ transpose(transpose g1) == g1

-- g1 isSubgraph (g1 U g2)
prop_subGraphUnion :: Graph Int -> Graph Int -> Property
prop_subGraphUnion g1 g2 = property $ isSubgraphOf g1 $ Graph.union g1 g2

-- a união da transposta de g1 com um grafo vazio, é igual à transposta de g1
prop_transpostaNeutro :: Graph Int -> Property
prop_transpostaNeutro g1 = property $ Graph.union (transpose g1) gEmpty == (transpose g1) 

-- reachable g1 x <= reachable (g1 U g2) x
prop_unionRecheable :: Graph Int -> Graph Int -> Property
prop_unionRecheable g1 g2 | (length $ elems $ nodes g1) > 0 = forAll (elements $ elems $ nodes g1) $ \node -> (length $ elems $ (reachable g1 node)) <= (length $ elems $ (reachable (Graph.union g1 g2) node))
                          | otherwise = label "Trivial 0" True
-- Nr grafos adjacentes de g1 <= nr grafos adjacentes de g1 U g2 num dado vertice
prop_unionAdj :: Graph Int -> Graph Int -> Property
prop_unionAdj g1 g2 | (length $ elems $ nodes g1) > 0 = forAll(elements $ elems $ nodes g1) $ \node -> (length $ elems $ (adj g1 node)) <= (length $ elems $ (adj (Graph.union g1 g2) node))
                    | otherwise = label "Trivial 0" True

-- o tamanho da lista do reachable em g1 no nodo v <= tamanho da lista dos nodos de g1
prop_reachable :: Graph Int -> Property
prop_reachable g1 | (length $ elems $ nodes g1) > 0 = forAll (elements $ elems $ nodes g1) $ \v -> length (elems (reachable g1 v)) <= length (elems $ nodes g1)
                  | otherwise = label "Trivial" True

-- se existe caminho entre v1 e v2 (/=Nothing) , então o caminho entre v1 e v2 é um caminho do grafo g
prop_isPathOf :: Graph Int -> Property
prop_isPathOf g | (length $ elems $ nodes g) > 0  = forAll (elements $ elems $ nodes g) $ \v1 -> forAll (elements $ elems $ nodes g) $ \v2 -> if ((path g v1 v2) /= Nothing) then isPathOf (fromJust(path g v1 v2)) g else True
                | otherwise = label "Trivial" True

-- se existe caminho entre v1 e v2 (/=Nothing), então essa lista é subset das arestas do grafo g
prop_path :: Graph Int -> Property
prop_path g | (length $ elems $ nodes g) > 0 && not(Set.null(edges g)) = forAll (elements $ elems $ nodes g) $ \v1 -> forAll (elements $ elems $ nodes g) $ \v2 -> if ((path g v1 v2) /= Nothing) then fromList(fromJust(path g v1 v2)) `isSubsetOf` edges g else True
            | otherwise = label "Trivial" True

prop_isForest:: Property
prop_isForest = forAll (forest :: Gen (Forest Int)) $ \g -> property $ (isForest g == isForest(transpose(transpose g)))

-- o nr de nodos de um DAG é o nr de eltos do concat resultante do set da funcao topo
prop_topo :: Property
prop_topo = forAll (dag :: Gen (DAG Int)) $ \g -> (length (toList (nodes g)) == length (concat((Prelude.map toList (topo g)))))

-- A tranposta sobre bft de g é um subgrafo de g
prop_bftTransposeSubGraph ::  Graph Int -> Property
prop_bftTransposeSubGraph g | (length $ elems $ nodes g) > 0 = forAll (elements $ elems $ nodes g) $ \v -> isSubgraphOf (transpose (bft g $ fromList[v])) g
                            | otherwise = label "Trivial" True
