\documentclass[a4paper]{article}
\usepackage[a4paper,left=3cm,right=2cm,top=2.5cm,bottom=2.5cm]{geometry}
\usepackage{palatino}
\usepackage[colorlinks=true,linkcolor=blue,citecolor=blue]{hyperref}
\usepackage{graphicx}
\usepackage{cp1617t}
\usepackage{color}
\usepackage{indentfirst}
\definecolor{cardinal}{rgb}{0.77, 0.12, 0.23}
%================= lhs2tex=====================================================%
%include polycode.fmt 
%format (div (x)(y)) = x "\div " y
%format succ = "\succ "
%format map = "\map "
%format length = "\length "
%format fst = "\p1"
%format p1  = "\p1"
%format snd = "\p2"
%format p2  = "\p2"
%format Left = "i_1"
%format Right = "i_2"
%format i1 = "i_1"
%format i2 = "i_2"
%format >< = "\times"
%format >|<  = "\bowtie "
%format |-> = "\mapsto"
%format . = "\comp "
%format (kcomp (f)(g)) = f "\kcomp " g
%format -|- = "+"
%format conc = "\mathsf{conc}"
%format summation = "{\sum}"
%format (either (a) (b)) = "\alt{" a "}{" b "}"
%format (frac (a) (b)) = "\frac{" a "}{" b "}"
%format (uncurry f) = "\uncurry{" f "}"
%format (const f) = "\underline{" f "}"
%format TLTree = "\mathsf{TLTree}"
% -- desactivados:
%format cond p f g = "\mcond{" p "}{" f "}{" g "}"
%format (split (x) (y)) = "\conj{" x "}{" y "}"
%format for f i = "\mathsf{for}\ " f "\ " i
%format B_tree = "\mathsf{B}\mbox{-}\mathsf{tree} "
\def\ana#1{\mathopen{[\!(}#1\mathclose{)\!]}}
%format (cataA (f) (g)) = "\cata{" f "~" g "}_A"
%format (anaA (f) (g)) = "\ana{" f "~" g "}_A"
%format (cataB (f) (g)) = "\cata{" f "~" g "}_B"
%format (anaB (f) (g)) = "\ana{" f "~" g "}_B"
%format (cata (f)) = "\cata{ "f" }"
%format (ana (f)) = "\ana{ "f" }"
%format (cataList (f)) = "\cata{ "f" }"
%format Either a b = a "+" b 
%format fmap = "\mathsf{fmap}"
%format NA   = "\textsc{na}"
%format NB   = "\textsc{nb}"
%format inT = "\mathsf{in}"
%format outT = "\mathsf{out}"
%format Null = "1"
%format (Prod (a) (b)) = a >< b
%format fF = "\fun F "
%format e1 = "e_1 "
%format e2 = "e_2 "
%format Dist = "\fun{Dist}"
%format IO = "\fun{IO}"
%format BTree = "\fun{BTree} "
%format LTree = "\mathsf{LTree}"
%format Nat0 = "\mathbb N_0"
%format Nat = "\mathbb N"
%format (lcbr (x)(y)) = "\begin{lcbr}" x "\\" y "\end{lcbr}"
%format out = "\mathbf{out}"
%format nil = "\mathbf{nil}"
%format cataB_tree f = "\cata{ "f" }"
%format ana_Algae_A f g = "\ana{ "f" "g" }_A"
%format ana_Algae_B f g = "\ana{ "f" "g" }_B"
%format cata_Algae_A f g = "\cata{ "f" "g" }_A"
%format cata_Algae_B f g = "\cata{ "f" "g" }_B"
%format ** = "\textsuperscript{*}"
%format (longcond (c)(t)(e)) = "\begin{array}{ll}\multicolumn{2}{l}{" c -> "}\\& " t ",\\& " e "\end{array}"
%format succ = "\mathsf{succ}"
%-------------- interface with pdbc.lhs ------------------------------------
\def\monadification{4.10}
%---------------------------------------------------------------------------

\title{
       	    Cálculo de Programas
\\
       	Trabalho Prático
\\
       	MiEI+LCC --- Ano Lectivo de 2016/17
}

\author{
       	\dium
\\
       	Universidade do Minho
}


\date\mydate

\makeindex

\begin{document}

\maketitle

\begin{center}\large
\begin{tabular}{ll}
\textbf{Grupo} nr. & 58
\\\hline
a73909 & Francisco Lira
\\
a77249  & Gil Cunha
\\
a79742 & Nuno Faria
\end{tabular}
\end{center}

\tableofcontents

\newpage

\section{Preâmbulo}

A disciplina de Cálculo de Programas tem como objectivo principal ensinar
a progra\-mação de computadores como uma disciplina científica. Para isso
parte-se de um repertório de \emph{combinadores} que formam uma álgebra da
programação (conjunto de leis universais e seus corolários) e usam-se esses
combinadores para construir programas \emph{composicionalmente}, isto é,
agregando programas já existentes.
  
Na sequência pedagógica dos planos de estudo dos dois cursos que têm esta
disciplina, restringe-se a aplicação deste método ao desenvolvimento de programas
funcionais na linguagem \Haskell.

O presente trabalho tem por objectivo concretizar na prática os objectivos
da disciplina, colocando os alunos perante problemas de programação que
deverão ser abordados composicionalmente e implementados em \Haskell.
Há ainda um outro objectivo: o de ensinar a documentar programas e
a produzir textos técnico-científicos de qualidade.

\section{Documentação}
Para cumprir de forma integrada os objectivos enunciados acima vamos recorrer
a uma técnica de programa\-ção dita ``\litp{literária}'' \cite{Kn92}, cujo
princípio base é o seguinte:
\begin{quote}\em
Um programa e a sua documentação devem coincidir.
\end{quote}
Por outras palavras, o código fonte e a sua documentação deverão constar
do mesmo documento (ficheiro).

O ficheiro \texttt{cp1617t.pdf} que está a ler é já um exemplo de \litp{programação
literária}: foi gerado a partir do texto fonte \texttt{cp1617t.lhs}\footnote{O
suffixo `lhs' quer dizer \emph{\lhaskell{literate Haskell}}.} que encontrará
no \MaterialPedagogico\ desta disciplina descompactando o ficheiro \texttt{cp1617t.zip}
e executando
\begin{Verbatim}[fontsize=\small]
    lhs2TeX cp1617t.lhs > cp1617t.tex
    pdflatex cp1617t
\end{Verbatim}
em que \texttt\LhsToTeX\ é um pre-processador que faz ``pretty printing''
de código Haskell em \Latex\ e que deve desde já instalar a partir do endereço
\begin{quote}\tt\small
\lhstotex{https://hackage.haskell.org/package/lhs2tex}.
\end{quote}
Por outro lado, o mesmo ficheiro \texttt{cp1617t.lhs} é executável e contém
o ``kit'' básico, escrito em \Haskell, para realizar o trabalho. Basta executar
\begin{Verbatim}[fontsize=\small]
    ghci cp1617t.lhs
\end{Verbatim}
para ver que assim é: 
\begin{quote}
\begin{Verbatim}[fontsize=\small]
GHCi, version 8.0.2: http://www.haskell.org/ghc/  :? for help
[ 1 of 11] Compiling Show             ( Show.hs, interpreted )
[ 2 of 11] Compiling ListUtils        ( ListUtils.hs, interpreted )
[ 3 of 11] Compiling Probability      ( Probability.hs, interpreted )
[ 4 of 11] Compiling Cp               ( Cp.hs, interpreted )
[ 5 of 11] Compiling Nat              ( Nat.hs, interpreted )
[ 6 of 11] Compiling List             ( List.hs, interpreted )
[ 7 of 11] Compiling LTree            ( LTree.hs, interpreted )
[ 8 of 11] Compiling St               ( St.hs, interpreted )
[ 9 of 11] Compiling BTree            ( BTree.hs, interpreted )
[10 of 11] Compiling Exp              ( Exp.hs, interpreted )
[11 of 11] Compiling Main             ( cp1617t.lhs, interpreted )
Ok, modules loaded: BTree, Cp, Exp, LTree, List, ListUtils, Main, Nat,
Probability, Show, St.
\end{Verbatim}
\end{quote}
O facto de o interpretador carregar as bibliotecas do \MaterialPedagogico\ da
disciplina, entre outras, deve-se ao facto de, neste mesmo sítio do texto
fonte, se ter inserido o seguinte código \Haskell:

\begin{code}
import Cp
import List 
import Nat  
import Exp
import BTree
import LTree
import St 
import Probability hiding (cond, choose)
import Data.List
import Test.QuickCheck hiding ((><))
import System.Random  hiding (split)
import GHC.IO.Exception
import System.IO.Unsafe
\end{code}

\noindent Abra o ficheiro \texttt{cp1617t.lhs} no seu editor de texto preferido
e verifique que assim é: todo o texto que se encontra dentro do ambiente
\begin{quote}\small\tt
\verb!\begin{code}!
\\ ... \\
\verb!\end{code}!
\end{quote}
vai ser seleccionado pelo \GHCi\ para ser executado.

\section{Como realizar o trabalho}
Este trabalho teórico-prático deve ser realizado por grupos de três alunos.
Os detalhes da avaliação (datas para submissão do relatório e sua defesa
oral) são os que forem publicados na \cp{página da disciplina} na \emph{internet}.
%
Recomenda-se uma abordagem equilibrada e participativa dos membros do grupo
de trabalho por forma a poderem responder às questões que serão colocadas
na defesa oral do relatório.

Em que consiste, então, o \emph{relatório} a que se refere o parágrafo anterior?
É a edição do texto que está a ser lido, preenchendo o anexo \ref{sec:resolucao}
com as suas respostas. O relatório deverá conter ainda a identificação dos membros
do grupo de trabalho, no local respectivo da folha de rosto.

Para gerar o PDF integral do relatório deve-se ainda correr os comando seguintes,
que actualizam a bibliografia (com \Bibtex) e o índice remissivo (com \Makeindex),
\begin{Verbatim}[fontsize=\small]
    bibtex cp1617t.aux
    makeindex cp1617t.idx
\end{Verbatim}
e recompilar o texto como acima se indicou. Dever-se-á ainda instalar o utilitário
\QuickCheck\ \footnote{Para uma breve introdução ver
e.g.\ \url{https://en.wikipedia.org/wiki/QuickCheck}.}
que ajuda a validar programas em \Haskell.

\section*{Problema 1}

O controlador de um processo físico baseia-se em dezenas de sensores que enviam
as suas leituras para um sistema central, onde é feito o respectivo processamento.

Verificando-se que o sistema central está muito sobrecarregado, surgiu a
ideia de equipar cada sensor com um microcontrolador que faça algum pré-processamento
das leituras antes de as enviar ao sistema central. Esse tratamento envolve
as operações (em vírgula flutuante) de soma, subtracção, multiplicação e divisão. 

Há, contudo, uma dificuldade: o código da divisão não cabe na memória do
microcontrolador, e não se pretende investir em novos microcontroladores
devido à sua elevada quantidade e preço.

Olhando para o código a replicar pelos microcontroladores, alguém verificou que
a divisão só é usada para calcular inversos, |frac 1 x|. Calibrando os sensores foi
possível garantir que os valores a inverter estão entre $1 < x <2$,
podendo-se então recorrer à \taylor{série de Maclaurin}
\begin{eqnarray*}
|frac 1 x| = |summation|_{i=0}^\infty (1-x)^i
\end{eqnarray*}
para calcular |frac 1 x| sem fazer divisões.
Seja então
\begin{quote}
|inv x n| = $|summation|_{i=0}^n(1-x)^i$
\end{quote}
a função que aproxima |frac 1 x| com |n| iterações da série de MacLaurin.
Mostre que |inv x| é um ciclo-for, implementando-o em Haskell (e opcionalmente em C).
Deverá ainda apresentar testes em \QuickCheck\ que verifiquem o funcionamento
da sua solução. (\textbf{Sugestão:} inspire-se no problema semelhante relativo
à função |ns| da secção 3.16 dos apontamentos \cite{Ol05}.)

\section*{Problema 2}
Se digitar \wc{|man wc|} na shell do Unix (Linux) obterá:
\begin{quote}\small
\begin{verbatim}
NAME
     wc -- word, line, character, and byte count

SYNOPSIS
     wc [-clmw] [file ...]

DESCRIPTION
    The wc utility displays the number of lines, words, and bytes contained in
    each input file,  or standard input (if no file is specified) to the stan-
    dard  output.  A line is defined as  a string of characters delimited by a
    <newline> character.  Characters beyond the final <newline> character will
    not be included in the line count.
    (...)
    The following options are available:
    (...)
        -w   The number of words in each input file is written to the standard 
             output.
    (...)
\end{verbatim}
\end{quote}
Se olharmos para o código da função que, em C, implementa esta funcionalidade
\cite{KR78} e nos focarmos apenas na parte que implementa a opção \verb!-w!,
verificamos que a poderíamos escrever, em Haskell, da forma seguinte:
\begin{code}
wc_w :: [Char] -> Int
wc_w []    = 0
wc_w (c:l) =
     if not (sep c) && lookahead_sep l
     then wc_w l + 1
     else wc_w l
       where
          sep c = ( c == ' ' || c == '\n' || c == '\t')
          lookahead_sep []    = True
          lookahead_sep (c:l) = sep c
\end{code}
Re-implemente esta função segundo o modelo \emph{|worker|/|wrapper|} onde
|wrapper| deverá ser um catamorfismos de listas. Apresente os cálculos que
fez para chegar a essa sua versão de |wc_w| e inclua testes em \QuickCheck\
que verifiquem o funcionamento da sua solução. (\textbf{Sugestão:} aplique
a lei de recursividade múltipla às funções |wc_w| e |lookahead_sep|.)

\section*{Problema 3}

Uma ``\btree{B-tree}" é uma generalização das árvores binárias do módulo \BTree\
a mais do que duas sub-árvores por nó:
\begin{code}
data B_tree a = Nil | Block  { leftmost :: B_tree a, block :: [(a, B_tree a)] } deriving (Show,Eq)
\end{code}
Por exemplo, a B-tree\footnote{Créditos: figura extraída de \url{https://en.wikipedia.org/wiki/B-tree}.}
\begin{center}
       \includegraphics[width=0.5\textwidth]{cp1617t_media/B-tree.jpg}
\end{center}
é representada no tipo acima por:
\begin{code}
t = Block {
      leftmost = Block {
                 leftmost = Nil,
                 block = [ (1,Nil),(2,Nil),(5,Nil),(6,Nil)]},
      block = [
               (7,Block {
                          leftmost = Nil,
                          block = [(9,Nil),(12,Nil)]}),
               (16,Block {
                          leftmost = Nil,
                          block = [(18,Nil),(21,Nil)]})
              ]}
\end{code}
Pretende-se, neste problema:
\begin{enumerate}
\item	Construir uma biblioteca para o tipo |B_tree| da forma habitual
        (in + out; ana + cata + hylo; instância na classe |Functor|).
\item
	Definir como um catamorfismo a função |inordB_tree :: B_tree t -> [t]|
        que faça travessias "inorder" de árvores deste tipo.
\item
	Definir como um catamorfismo a função |largestBlock :: B_tree a -> Int|
        que detecta o tamanho do maior bloco da árvore argumento.
\item
	Definir como um anamorfismo a função |mirrorB_tree :: B_tree a -> B_tree a|
        que roda a árvore argumento de 180º
\item
	Adaptar ao tipo |B_tree| o hilomorfismo "quick sort" do módulo |BTree|.
	O respectivo anamorfismo deverá basear-se no gene |lsplitB_tree|
	cujo funcionamento se sugere a seguir:
\begin{quote}
|lsplitB_tree [] = Left ()|
\\
|lsplitB_tree [7] = Right ([],[(7,[])])|
\\
|lsplitB_tree [5,7,1,9] = Right ([1],[(5,[]),(7,[9])])|
\\
|lsplitB_tree [7,5,1,9] = Right ([1],[(5,[]),(7,[9])])|
\end{quote}

\item	A biblioteca \Exp\ permite representar árvores-expressão em formato
        DOT, que pode ser lido por aplicações como por exemplo \Graphviz, produzindo
        as respectivas imagens. Por exemplo, para o caso de árvores \BTree, se definirmos
\begin{code}
dotBTree :: Show a => BTree a -> IO ExitCode
dotBTree = dotpict . bmap nothing (Just . show) . cBTree2Exp
\end{code}
        executando |dotBTree t| para
\begin{quote}\small
|t= Node (6,(Node (3,(Node (2,(Empty,Empty)),Empty)), Node (7,(Empty,Node (9,(Empty,Empty))))))|
\end{quote}
        obter-se-á a imagem
\begin{center}
       \includegraphics[width=0.4\textwidth]{cp1617t_media/dot1.jpg}
\end{center}
        Escreva de forma semelhante uma função |dotB_tree| que permita mostrar em \Graphviz\footnote{Como alternativa a instalar \Graphviz, podem usar \WebGraphviz\ num browser.}
        árvores |B_tree| tal como se ilustra a seguir,
\begin{center}
       \includegraphics[width=0.9\textwidth]{cp1617t_media/dot2.jpg}
\end{center}
        para a árvora dada acima.
\end{enumerate}

\section*{Problema 4}
Nesta disciplina estudaram-se funções mutuamente recursivas e como lidar com elas.
Os tipos indutivos de dados podem, eles próprios, ser mutuamente recursivos.
Um exemplo dessa situação são os chamados \LSystems.

Um \LSystem\ é um conjunto de regras de produção que podem ser usadas para
gerar padrões por re-escrita sucessiva, de acordo com essas mesmas regras.
Tal como numa gramática, há um axioma ou símbolo inicial, de onde se parte
para aplicar as regras. Um exemplo célebre é o do crescimento de algas formalizado
por Lindenmayer\footnote{Ver \url{https://en.wikipedia.org/wiki/Aristid_Lindenmayer}.}
no sistema:
\begin{quote}
\textbf{Variáveis:} |A| e |B|
\\
\textbf{Constantes:} nenhuma
\\
\textbf{Axioma:} |A|
\\
\textbf{Regras:} |A -> A B, B -> A|.
\end{quote}
Quer dizer, em cada iteração do ``crescimento" da alga, cada |A| deriva num par |A B| e
cada |B| converte-se num |A|. Assim, ter-se-á, onde |n| é o número de iterações
desse processo:
\begin{itemize}
\item	|n=0|: |A|
\item	|n=1|: |A B|
\item	|n=2|: |A B A|
\item	|n=3|: |A B A A B|
\item	etc
\end{itemize}

Este \LSystem\ pode codificar-se em Haskell considerando cada variável um
tipo, a que se adiciona um caso de paragem para poder expressar as sucessivas
iterações:
\begin{code}
type Algae = A
data A = NA | A A B deriving Show
data B = NB | B A deriving Show
\end{code}
Observa-se aqui já que |A| e |B| são mutuamente recursivos.
Os isomorfismos |inT|/|outT| são definidos da forma habitual:
\begin{code}
inA :: Either Null (Prod A B) -> A
inA = either (const NA)(uncurry A)

outA :: A -> Either Null (Prod A B)
outA NA = Left ()
outA (A a b) = Right (a,b)

inB :: Either Null A -> B
inB = either (const NB) B

outB :: B -> Either Null A
outB NB = Left ()
outB (B a) = Right a
\end{code}
O functor é, em ambos os casos, |fF X = 1 + X|. Contudo, os catamorfismos
de |A| têm de ser estendidos com mais um gene, de forma a processar também
os |B|,
\begin{code}
cataA :: (Either Null (Prod c d) -> c) -> (Either Null c -> d) -> A -> c
cataA ga gb = ga . (id -|- cataA ga gb >< cataB ga gb) . outA
\end{code}
e a mesma coisa para os |B|s:
\begin{code}
cataB :: (Either Null (Prod c d) -> c) -> (Either Null c -> d) -> B -> d
cataB ga gb = gb . (id -|- cataA ga gb) . outB
\end{code}
Pretende-se, neste problema:
\begin{enumerate}
\item	A definição dos anamorfimos dos tipos |A| e |B|.
\item	A definição da função
\begin{code}
generateAlgae :: Int -> Algae
\end{code}
	como anamorfismo de |Algae| e da função
\begin{code}
showAlgae :: Algae -> String
\end{code}
	como catamorfismo de |Algae|.
\item	Use \QuickCheck\ para verificar a seguinte propriedade:
\begin{quote}
	|length . showAlgae . generateAlgae = fib . succ|
\end{quote}
\end{enumerate}

\section*{Problema 5}
O ponto de partida deste problema é um conjunto de equipas de futebol, por exemplo:
\begin{code}
equipas :: [Equipa]
equipas = [
   "Arouca","Belenenses","Benfica","Braga","Chaves","Feirense",
   "Guimaraes","Maritimo","Moreirense","Nacional","P.Ferreira",
   "Porto","Rio Ave","Setubal","Sporting","Estoril"
   ]
\end{code}
Assume-se que há uma função |f(e1,e2)| que dá --- baseando-se em informação
acumulada historicamente, e.g.\ estatística --- qual a probabilidade de |e1|
ou |e2| ganharem um jogo entre si.\footnote{Tratando-se de jogos eliminatórios,
não há lugar a empates.} Por exemplo, |f("Arouca","Braga")| poderá dar como
resultado a distribuição
\[
\begin{array}{ll}
Arouca & \rule{05.72mm}{3pt}\ 28.6\%\\
Braga  & \rule{14.20mm}{3pt}\ 71.4\%
\end{array}
\]
indicando que há |71.4%| de probabilidades de |"Braga"| ganhar a |"Arouca"|.

Para lidarmos com probabilidades vamos usar o mónade |Dist a| que vem descrito no apêndice
\ref{sec:Dist} e que está implementado na biblioteca \Probability\ \cite{EK06}
--- ver definição (\ref{eq:Dist}) mais adiante.
A primeira parte do problema consiste em sortear \emph{aleatoriamente} os jogos das equipas.
O resultado deverá ser uma \LTree\ contendo, nas folhas, os jogos da primeira eliminatória
e cujos nós indicam quem joga com quem (vencendo), à medida que a eliminatória prossegue:
\begin{center}
       \includegraphics[width=1.00\textwidth]{cp1617t_media/sorteio.jpg}
\end{center}

A segunda parte do problema consiste em processar essa árvore usando a função
\begin{quote}
|jogo :: (Equipa, Equipa) -> Dist Equipa|
\end{quote}
que foi referida acima. Essa função simula um qualquer jogo, como foi acima
dito, dando o resultado de forma probabilística. Por exemplo, para o sorteio
acima e a função |jogo| que é dada neste enunciado\footnote{Pode, se desejar,
criar a sua própria função |jogo|, mas para efeitos de avaliação terá que ser
usada a que vem dada neste enunciado. Uma versão de |jogo| realista teria que ter
em conta todas as estatísticas de jogos entre as equipas em jogo, etc etc.},
a probabilidade de cada equipa vir a ganhar a competição vem dada na distribuição
seguinte:
\[
\begin{array}{ll}
|Porto| & \rule{36.89mm}{3pt}\ 21.7\%\\
|Sporting| & \rule{36.379999999999995mm}{3pt}\ 21.4\%\\
|Benfica| & \rule{32.3mm}{3pt}\ 19.0\%\\
|Guimaraes| & \rule{15.98mm}{3pt}\ 9.4\%\\
|Braga| & \rule{8.67mm}{3pt}\ 5.1\%\\
|Nacional| & \rule{8.33mm}{3pt}\ 4.9\%\\
|Maritimo| & \rule{6.969999999999999mm}{3pt}\ 4.1\%\\
|Belenenses| & \rule{5.95mm}{3pt}\ 3.5\%\\
|Rio Ave| & \rule{3.9099999999999997mm}{3pt}\ 2.3\%\\
|Moreirense| & \rule{3.23mm}{3pt}\ 1.9\%\\
|P.Ferreira| & \rule{2.38mm}{3pt}\ 1.4\%\\
|Arouca| & \rule{2.38mm}{3pt}\ 1.4\%\\
|Estoril| & \rule{2.38mm}{3pt}\ 1.4\%\\
|Setubal| & \rule{2.38mm}{3pt}\ 1.4\%\\
|Feirense| & \rule{1.19mm}{3pt}\ 0.7\%\\
|Chaves| & \rule{0.68mm}{3pt}\ 0.4\%\\
\end{array}
\]

Assumindo como dada e fixa a função |jogo| acima referida,
juntando as duas partes obteremos um \emph{hilomorfismo} de tipo
|[Equipa] -> Dist Equipa|,
\begin{code}
quem_vence :: [Equipa] -> Dist Equipa
quem_vence = eliminatoria . sorteio
\end{code}
com características especiais: é aleatório no anamorfismo (sorteio) e
probabilístico no catamorfismo (eliminatória).

O anamorfismo |sorteio :: [Equipa] -> LTree Equipa| tem a seguinte arquitectura,
\footnote{A função |envia| não é importante para o processo; apenas se destina
a simplificar a arquitectura monádica da solução.}
\begin{code}
sorteio = anaLTree lsplit . envia . permuta
\end{code}
reutilizando o anamorfismo do algoritmo de ``merge sort'', da biblioteca
\LTree, para construir a árvore de jogos a partir de uma permutação aleatória
das equipas gerada pela função genérica
\begin{code}
permuta :: [a] -> IO [a]
\end{code}
A presença do mónade de |IO| tem a ver com a geração de números aleatórios\footnote{Quem
estiver interessado em detalhes deverá consultar
\href{https://hackage.haskell.org/package/random-1.1/docs/System-Random.html}{System.Random}.}.
\begin{enumerate}
\item	Defina a função monádica |permuta| sabendo que tem já disponível
\begin{quote}
|getR :: [a] -> IO (a, [a])|
\end{quote}
|getR x| dá como resultado um par |(h,t)| em que |h| é um elemento de |x| tirado à
sorte e |t| é a lista sem esse elemento -- mas esse par vem encapsulado dentro de |IO|.

\item A segunda parte do exercício consiste em definir a função monádica
\begin{code}
eliminatoria :: LTree Equipa -> Dist Equipa
\end{code}
que, assumindo já disponível a função |jogo| acima referida, dá como resultado
a distribuição de equipas vencedoras do campeonato.
\end{enumerate}
\textbf{Sugestão:} inspire-se na secção \monadification\ (\emph{`Monadification'
of Haskell code made easy}) dos apontamentos \cite{Ol05}. 

%----------------- Bibliografia (exige bibtex) --------------------------------%

\bibliographystyle{plain}
\bibliography{cp1617t}

%----------------- Programa, bibliotecas e código auxiliar --------------------%

\newpage

\part*{Anexos}

\appendix

\section{Mónade para probabilidades e estatística}\label{sec:Dist}
%format B = "\mathit B"
%format C = "\mathit C"
Mónades são functores com propriedades adicionais que nos permitem obter
efeitos especiais em progra\-mação. Por exemplo, a biblioteca \Probability\
oferece um mónade para abordar problemas de probabilidades. Nesta biblioteca,
o conceito de distribuição estatística é captado pelo tipo
\begin{eqnarray}
	|newtype Dist a = D {unD :: [(a, ProbRep)]}|
	\label{eq:Dist}
\end{eqnarray}
em que |ProbRep| é um real de |0| a |1|, equivalente a uma escala de |0| a |100%|.

Cada par |(a,p)| numa distribuição |d::Dist a| indica que a probabilidade
de |a| é |p|, devendo ser garantida a propriedade de  que todas as probabilidades
de |d| somam |100%|.
Por exemplo, a seguinte distribuição de classificações por escalões de $A$ a $E$,
\[
\begin{array}{ll}
A & \rule{2mm}{3pt}\ 2\%\\
B & \rule{12mm}{3pt}\ 12\%\\
C & \rule{29mm}{3pt}\ 29\%\\
D & \rule{35mm}{3pt}\ 35\%\\
E & \rule{22mm}{3pt}\ 22\%\\
\end{array}
\]
será representada pela distribuição
\begin{code}
d1 :: Dist Char
d1 = D [('A',0.02),('B',0.12),('C',0.29),('D',0.35),('E',0.22)]
\end{code}
que o \GHCi\ mostrará assim:
\begin{Verbatim}[fontsize=\small]
'D'  35.0%
'C'  29.0%
'E'  22.0%
'B'  12.0%
'A'   2.0%
\end{Verbatim}
É possível definir geradores de distribuições, por exemplo distribuições \emph{uniformes},
\begin{code}
d2 = uniform (words "Uma frase de cinco palavras")
\end{code}
isto é
\begin{Verbatim}[fontsize=\small]
     "Uma"  20.0%
   "cinco"  20.0%
      "de"  20.0%
   "frase"  20.0%
"palavras"  20.0%
\end{Verbatim}
distribuição \emph{normais}, eg.\
\begin{code}
d3 = normal [10..20]
\end{code}
etc.\footnote{Para mais detalhes ver o código fonte de \Probability, que é uma adaptação da
biblioteca \PFP\ (``Probabilistic Functional Programming''). Para quem quiser souber mais
recomenda-se a leitura do artigo \cite{EK06}.}

|Dist| forma um \textbf{mónade} cuja unidade é |return a = D [(a,1)]| e cuja composição de Kleisli
é (simplificando a notação)
\begin{spec}
  ((kcomp f g)) a = [(y,q*p) | (x,p) <- g a, (y,q) <- f x]
\end{spec}
em que |g: A -> Dist B| e |f: B -> Dist C| são funções \textbf{monádicas} que representam
\emph{computações probabilísticas}.

Este mónade é adequado à resolução de problemas de \emph{probabilidades e
estatística} usando programação funcional, de forma elegante e como caso
particular de programação monádica.

\section{Definições auxiliares}\label{sec:helper_functions}
São dadas: a função que simula jogos entre equipas,
\begin{code}
type Equipa = String
 
jogo :: (Equipa, Equipa) -> Dist Equipa
jogo(e1,e2) = D [ (e1,1-r1/(r1+r2)),(e2,1-r2/(r1+r2)) ] where
              r1 = rank e1
              r2 = rank e2
              rank = pap ranks
              ranks = [
                  ("Arouca",5),
                  ("Belenenses",3),
                  ("Benfica",1),
                  ("Braga",2),
                  ("Chaves",5),
                  ("Feirense",5),
                  ("Guimaraes",2),
                  ("Maritimo",3),
                  ("Moreirense",4),
                  ("Nacional",3),
                  ("P.Ferreira",3),
                  ("Porto",1),
                  ("Rio Ave",4),
                  ("Setubal",4),
                  ("Sporting",1),
                  ("Estoril",5)]
\end{code}
a função (monádica) que parte uma lista numa cabeça e cauda \emph{aleatórias},
\begin{code}
getR :: [a] -> IO (a, [a])
getR x = do {
               i <- getStdRandom (randomR (0,length x-1));
               return (x!!i,retira i x)
             } where retira i x = take i x ++ drop (i+1) x
\end{code}
e algumas funções auxiliares de menor importância: uma que ordena
listas com base num atributo (função que induz uma pré-ordem),
\begin{code}
presort :: (Ord a, Ord b) => (b -> a) -> [b] -> [b]
presort f = map snd . sort . (map (fork f id)) 
\end{code}
e outra que converte ``look-up  tables" em funções (parciais):
\begin{code}
pap :: Eq a => [(a, t)] -> a -> t
pap m k = unJust (lookup k m) where unJust (Just a) = a
\end{code}

%----------------- Soluções propostas -----------------------------------------%

\section{Soluções propostas}\label{sec:resolucao}
Os alunos devem colocar neste anexo as suas soluções aos exercícios
propostos, de acordo com o ``layout'' que se fornece. Não podem ser
alterados os nomes das funções dadas, mas pode ser adicionado texto e / ou 
outras funções auxiliares que sejam necessárias.

\newpage

\subsection*{Problema 1}

\subsubsection*{\sl{Pointwise}}
Primeiro desenvolvemos a solução em \textit{pointwise} para nos ajudar a chegar a uma \textit{pointfree}.

\begin{code}
inv_ x 0 = 1
inv_ x (n+1) = (f (1-x) (n+1)) + (inv_ x n)
               where 
                    f x 0 = 1
                    f x (n+1) = x * (f x n)
\end{code}

Onde \textbf{f} será a função potência.

\subsubsection*{\sl{Pointfree}}
Ao fazer a função \textcolor{cardinal}{\textbf{inv}} em \textit{pointwise} verificou-se que podia ser decomposta em duas, com uma função \textcolor{cardinal}{\textbf{f}} auxiliar. A partir daqui notamos que seria possível obter um ciclo for (catamorfismo) pela lei de Fokkinga (ou lei de recursividade mútua), dada por:

\begin{eqnarray*}
|lcbr (f y.in = h.fF (split (f y) (inv x))) ((inv x).in = k.fF (split (f y) (inv x))| \equiv |split (f y) (inv x) = cata (split h k)|
\end{eqnarray*}

Para derivar \textcolor{cardinal}{\textit{h}} e \textcolor{cardinal}{\textit{k}}, precisamos converter primeiro \textbf{f} e \textbf{inv} em \textit{pointfree}.

Comecemos por \textbf{f y}:

\begin{eqnarray*}
\start
|lcbr (f y 0 = 1) (f y (n+1) = y * f y n)|
%
\just \equiv {Mudança de variável  y = 1-x, |succ n = n+1|}
%
|lcbr (f (1-x) 0 = 1) (f (1-x) (succ n) = (1-x) * (f (1-x) n)|
%
\just \equiv {Igualdade Extensional, |const 0 _ = 0|, |const 1 _ = 1|, Eq-+, |in = either (const 0) succ|}
%
|f (1-x) . in = either (const 1) ((1-x) * (f (1-x)))|
%
\just \equiv {Absorção-|+|, Natural-|id|}
%
|f (1-x) . in = (either (const 1) ((1-x)*)) . (id + f(1-x))|
%
\just \equiv {Cancelamento-|><|}
%
|f (1-x) . in = (either (const 1) ((1-x)*)) . (id + p1 . split (f(1-x)) (inv x))|
\just \equiv {Functor-|+|, Natural-|id|}
%
|f (1-x) . in = (either (const 1) ((1-x)*)) . (id + p1) . (id + split (f(1-x)) (inv x))|
%
\just \equiv {|h = (either (const 1) ((1-x)*)) . (id + p1)|, |fF (split (f(1-x)) (inv x)) = (id + split (f(1-x)) (inv x))|}
%
|f(1-x) . in = h . fF(split (f (1-x)) (inv x))|
%
\end{eqnarray*}

Vejamos agora \textbf{inv x}:\\
\begin{eqnarray*}
\start
|lcbr (inv x 0 = 1) (inv x (n+1) = f (1-x) (n+1) + inv x n|
%
\just \equiv {|succ n = n+1|, Def-|add|}
%
|lcbr (inv x 0 = 1) (inv x (n+1) = add . split (f (1-x) (succ n)) (inv x n))|
%
\just \equiv { |f(1-x) (succ n) = (1-x) * f (1-x) |, Igualdade Extensional}
%
|lcbr (inv x 0 = 1) (inv x (n+1) = add . split ((1-x) * f (1-x)) (inv x))|
%
\just \equiv {Eq-|+|, |in = either (const 0) succ|, |succ n = n+1|}
%
|inv x . in = either (const 1) (add . split ((1-x) * f(1-x)) (inv x))|
%
\just \equiv {Absorção-|><|, Natural-|id|}
%
|inv x . in = either (const 1) (add . (((1-x)*) >< id) . split (f(1-x)) (inv x))|
%
\just \equiv {Absorção-|+|}
%
|inv x . in = either (const 1) (add . (((1-x)*) >< id)) . (id + split (f(1-x)) (inv x))|
%
\just \equiv {|k = either (const 1) (add (((1-x)*) >< id)), fF (split (f(1-x)) (inv x)) = id + split (f(1-x)) (inv x)|}
%
|inv x . in = k . fF (split (f(1-x)) (inv x))|
\end{eqnarray*}


Agora podemos juntar as duas funções e aplicar a lei de Fokkinga:
\begin{eqnarray*}
\start
|lcbr (f (1-x) . in = h . fF(split (f (1-x)) (inv x))) (inv x . in = k . fF (split (f(1-x)) (inv x)))|
%
\just \equiv {Fokkinga}
%
|split (f(1-x)) (inv x) = cata (split h k)|
%
\just \equiv {Def-|h|, Def-|k|}
%
|split (f(1-x)) (inv x) = cata (split ((either (const 1) ((1-x)*)) . (id + p1)) (either (const 1) (add (((1-x)*) >< id))))|
\end{eqnarray*}

Tendo-se que \textcolor{cardinal}{|inv x = p2 . split (f(1-x)) (inv x) = p2 . cata (split h k)|}. Com isto já podemos fazer a função:
\begin{code}
inv x = wrap . cataNat (split h k)
    where
        h = (either (const 1) ((1-x)*)).(id -|- p1)
        k  =  either (const 1) (uncurry(+).(((1-x)*) >< id ))
        wrap =  p2
\end{code}

Podemos agora simplificar esta função usando um ciclo-\textit{for}, sendo |for b i = cata (either (const i) b)|

\begin{code}
invFor x = p2 . (for (split (((1-x)*).p1) (uncurry (+) . (((1-x)*) >< id))) (1,1))
\end{code}


\subsubsection*{Alternativa com a regra de Horner}
Uma alternativa à resolução deste problema seria através da aplicação da regra de Horner\footnote{\url{https://pt.wikipedia.org/wiki/Esquema_de_Horner}}.
\[
	\sum_{i=0}^{\infty} {(1-x)^i}
\]

\[ =	1 + (1-x) + (1-x)^2 + (1-x)^3 + ...\]

\[ =	1 + (1-x)(1 + (1-x)(1 + (1-x)(...) ...\]

O que é equivalente a:
\begin{code}
inv_alt x = for (succ.((1-x)*)) 1
\end{code}

\noindent\rule{10cm}{0.02cm}

\subsubsection*{Testes \small(Com incerteza de 0.001\% para 3000 iterações)}
\begin{code}
inv_test :: Double -> Bool
inv_test x = abs (inv x 3000 - 1/x) < 0.001

prop_inv :: Property
prop_inv = forAll (choose(1,2)) $ \x -> inv_test x
\end{code}
%$
\begin{minipage}[t]{.5\textwidth}

\end{minipage}
\newpage

\subsection*{Problema 2}
Pela sugestão do enunciado, iremos aplicar a lei de recursividade (lei de Fokkinga) múltipla às funções \textcolor{cardinal}{\textbf{wc\_w}} (\textit{wc}) e \textcolor{cardinal}{\textbf{lookahead\_sep}} (\textit{look}).

\begin{eqnarray*}
\start
|split wc look|
%
\just \equiv {Fokkinga}
%
|lcbr (wc . in = h . (id + id >< (split wc look))) (look . in = k . (id + id >< (split wc look))|
%
\just \equiv {Def-|+|(x2)}
%
|lcbr (wc . in = h . either (i1 . id) (i2 . id >< (split wc look))) (look . in = k . either (i1 . id) (i2 . id >< (split wc look)))|
%
\just \equiv {Fusão-|+|(x2), Natural-|id|(x4)}
%
|lcbr (wc . in = either (h . i1) (h . i2 . (id >< (split wc look)))) (look . in =either (k . i1) (h . i2 . (id >< (split wc look))))|
%
\just \equiv {|in = either nil cons|, Eq-|+|(x2)}
%
|lcbr (lcbr (wc . nil = h . i1) (wc . cons = h . i2 . (id >< (split wc look)))) (lcbr (look . nil = k . i1) (look . cons = k . i2 . (id >< (split wc look))))|
%
\just \equiv {|h = either h1 h2|, |k = either k1 k2|, Cancelamento-|+| (x2)}
%
|lcbr (lcbr (wc . nil = h1) (wc . cons = h2 . (id >< (split wc look)))) (lcbr (look . nil = k1) (look . cons = k2 . (id >< (split wc look))))|
\end{eqnarray*}

Derivando \textit{h}:
\begin{eqnarray*}
\start
|lcbr (wc . nil = h1) (wc . cons = h2 . (id >< (split wc look)))|
%
\just \equiv {|wc . nil = const 0|, |wc . cons = cond (uncurry (&&) . split (not (sep . p1))  (look.p2)) (succ . wc . p2) (wc . p2)|}
%
|lcbr (const 0 = h1) (cond (uncurry (&&) . split (not (sep . p1))  (look.p2)) (succ . wc . p2) (wc . p2) = h2 . (id >< (split wc look)))|
%
\just \equiv {Cancelamento-|><|(x3)}
%
|lcbr (const 0 = h1) (longcond ((uncurry(&&) . (split (not (sep . p1)) (p2 . (split wc look) . p2)))) (succ . p1 . (split wc look) . p2) (p1 . (split wc look) . p2) = h2 . (id >< (split wc look)))|
%
\just \equiv {Def-|><|}
%
|lcbr (const 0 = h1) (longcond ((uncurry(&&) . ((not sep) >< (p2 . (split wc look))))) (succ . p1 . (split wc look) . p2) (p1 . (split wc look) . p2) = h2 . (id >< (split wc look)))|
%
\just \equiv {Natural-|p2|(x2)}
%
|lcbr (const 0 = h1) (longcond ((uncurry(&&) . ((not sep) >< (p2.(split wc look))))) (succ . p1 . p2 . (id >< (split wc look))) (p1 . p2 . (id >< (split wc look))) = h2.(id >< (split wc look)))|
%
\just \equiv {Natural-|id|, Fuctor-|><|}
%
|lcbr (const 0 = h1) (longcond ((uncurry(&&) . ((not sep) >< p2) . (id >< (split wc look)))) (succ . p1 . p2 (id >< (split wc look))) (p1 . p2 . (id >< (split wc look))) = h2.(id >< (split wc look)))|
%
\just \equiv {2ª Lei de fusão do condicional}
%
|lcbr (const 0 = h1) ((cond (uncurry(&&) . ((not sep) >< p2)) (succ p1 . p2)  (p1 . p2)) . (id >< (split wc look)) = h2.(id >< (split wc look)))|
%
\just \equiv {Leibniz}
%
|lcbr (const 0 = h1) (cond (uncurry(&&) . ((not sep) >< p2)) (succ . p1 . p2)  (p1 . p2) = h2)|
\end{eqnarray*}

Derivando \textit{k}:
\begin{eqnarray*}
\start
|lcbr (look . nil = k1) (look . cons = k2 . (id >< (split wc look)))|
%
\just \equiv {|look . nil = const True|, |look . cons = sep . p1|}
%
|lcbr (const True = k1) (sep . p1 = k2 . (id >< (split wc look)))|
%
\just \equiv {Natural-|id|}
%
|lcbr (const True = k1) (sep . id . p1 = k2 . (id >< (split wc look)))|
%
\just \equiv {Natural-|p1|}
%
|lcbr (const True = k1) (sep . p1 . (id >< (split wc look)) = k2 . (id >< (split wc look)))|
%
\just \equiv {Leibniz}
%
|lcbr (const True = k1) (sep . p1 = k2)|
\end{eqnarray*}

Com os valores de \textit{h} e \textit{k} podemos finalmente definir \textcolor{cardinal}{\textbf{wc\_w}} através de um modulo \textit{worker/wrapper}.

\begin{code}
wc_w_final :: [Char] -> Int
wc_w_final = wrapper . worker

wrapper = p1

worker = cataList (split (either h1 h2) (either k1 k2))
             where
                 h1 = const 0
                 h2 = cond (uncurry(&&).((not.sep) >< p2)) (succ.p1.p2) (p1.p2)
                 k1 = const True
                 k2 = sep . p1

\end{code}

\noindent\rule{10cm}{0.02cm}

\subsubsection*{Testes}

\begin{code}
char_gen :: Gen Char
char_gen = elements (conc (['0'..'z'], [' ', '\n', '\t']))

text_gen :: Gen String
text_gen = vectorOf 10000 char_gen

prop_wc :: Property
prop_wc = forAll text_gen $ \x -> wc_w_final x == wc_w x
\end{code}
%$

\newpage

\subsection*{Problema 3}

\subsubsection*{Biblioteca para o tipo \textcolor{cardinal}{B-tree}}

\begin{code}
inB_tree = either (const Nil) (uncurry Block)

outB_tree  Nil   = i1 ()
outB_tree (Block l b) = i2 (l,b)

recB_tree f = id -|- f >< (map (id >< f))

baseB_tree g f = id -|- f >< (map (g >< f))

cataB_tree g = g . (recB_tree (cataB_tree g)) . outB_tree

anaB_tree g = inB_tree . (recB_tree (anaB_tree g) ) . g

hyloB_tree f g = (cataB_tree f) . (anaB_tree g)

instance Functor B_tree
          where fmap f = cataB_tree ( inB_tree . baseB_tree f id )

\end{code}

\subsubsection*{Inord\footnote{|inordB_gene| será mais tarde usada em |qSortB_tree|}}

\xymatrix@@C=3.8cm@@R=1.8cm{
	|B_tree A|
		\ar[d]_{|inord|}
		\ar@@/_0.5cm/[r]_-{|out|}
&
	|1 + B_tree A >< (A >< (Btree A))**|
		\ar@@/_0.5cm/[l]^-{|in|}
		\ar[d]||{|id + inord >< map (id >< inord)|}
\\
	|A**|
&
	|1 + A** >< (A >< A**)**|
		\ar[l]^{|inordB_gene = either nil f|}
\\
&
	|A** >< (A >< A**)**|
		\ar[u]||{|i2|}
		\ar[d]||{|id >< (map . conc . (singl >< id))|}
		\ar@@/^3.3cm/[ddd]^{|f|}
\\
&
	|A** >< (A**)**|
		\ar[d]||{|id >< concat|}
\\
&
	|A** >< A**|
		\ar[d]||{|conc|}
\\
&
	|A**|
		\ar@@{-->}@@/^1cm/[luuuu]
}

\begin{code}
inordB_tree = cataB_tree inordB_gene
inordB_gene = either nil f
                 where f = conc.(id >< (concat .(map (conc. (singl >< id)))))
\end{code}

\newpage

\subsubsection*{Largest Block}

\xymatrix@@C=3.8cm@@R=1.8cm{
	|B_tree A|
		\ar[d]_{|largestBlock|}
		\ar@@/_0.5cm/[r]_-{|out|}
&
	|1 + B_tree A >< (A >< (Btree A))**|
		\ar@@/_0.5cm/[l]^-{|in|}
		\ar[d]||{|id + largestBlock >< map (id >< largestBlock)|}
\\
	|Nat0|
&
	|1 + Nat0 >< (A >< Nat0)**|
		\ar[l]^{|either (const 0) f|}
		\ar[dl]||{|id + id >< h|}
\\
	|1 + Nat0 >< Nat0|
		\ar[u]||{|either (const 0) (uncurry max)|}
&	
	|Nat0 >< (A >< Nat0)**|
		\ar[u]||{i2}
		\ar[d]||{|p2|}
		\ar@@/^2cm/[dddd]^{|h|}
\\
&
	|(A >< Nat0)**|
	\ar[d]||{|unzip|}
\\
&
	|A** >< (Nat0)**|
		\ar[d]||{|length >< maximum|}
\\
&
	|Nat0 >< Nat0|
		\ar[d]||{|uncurry max|}
\\
&
	|Nat0|
}

\begin{code}
largestBlock = cataB_tree ( either (const 0) f)
      where
           f = uncurry(max).(id >< h)
               where h = uncurry(max).(length >< maximum).unzip
\end{code}

\newpage

\subsubsection*{Mirror}

\xymatrix@@C=3cm@@R=1.8cm{
	|B_tree A|
		\ar@@/_0.5cm/[r]_-{|out|}
&
	|1 + B_tree A >< (A >< B_tree A)**|
		\ar@@/_0.5cm/[l]^-{|in|}
\\
	|B_tree A|
		\ar[u]||{|mirror|}
		\ar[d]||{|out|}
		\ar@@{-->}[r]
&
	|1 + B_tree A >< (A >< B_tree A)**|
		\ar[u]||{|id + mirror >< map (id >< mirror)|}
\\
	|1 + B_tree A >< (A >< B_tree A)**|
		\ar@@/_1cm/[ru]||{|id + split (head . p2 . f) g|}
}

\vspace{0.5cm}

A função \textcolor{cardinal}{|f|} irá partir do \textit{Block} da |B_tree| e transformar num par, onde o primeiro elemento da lista é uma lista de valores e o segundo é uma lista com os respetivos \textit{blocks}\footnote{Notar que \textit{Block} != \textit{block}}. Seguidamente irá aplicar o |reverse| nas respetivas listas obtidas\footnote{Será feito o |reverse| devido à definição de |mirror|}.

\xymatrix@@C=1cm{
	|f :: B_tree A >< (A >< B_tree A)**|
		\ar[r]^--{|p2|}
&
	|(A >< B_tree A)**|
		\ar[r]^--{|unzip|}
&
	|A** >< (B_tree A)**|
		\ar[r]^--{|reverse >< reverse|}
&
	|A** >< (B_tree A)**|
}

\vspace{0.5cm}

A função \textcolor{cardinal}{g} irá partir dum par, onde o primeiro elemento contem todos os elementos da árvore, e o segundo tem todos os |blocks|, juntando através de |uncurry zip| (necessário para a definição de |B_tree|).

\xymatrix@@C=1cm{
	|g :: B_tree A >< (A >< B_tree A)**|
		\ar[r]^--{|split (p1 . f) (conc . k)|}
&
	|A** >< (B_Tree A)**|
		\ar[r]^--{|zip|}
&
	|(A >< (B_tree A))**|
}

\vspace{0.5cm}

A função \textcolor{cardinal}{|k1|} irá partir da segunda lista do par produzido por |f| e aplicar o |tail|, correspondendo a todos os \textit{blocks} da arvore criada pelo |mirror| sem o \textit{leftmost}\footnote{Será agora um \textit{block}} e a sua cabeça\footnote{Que era o ultimo |block|, mas como se vez |reverse|, será a cabeça da lista.}, sendo esta o \textit{leftmost} da nova árvore.

A função \textcolor{cardinal}{|k2|} partirá do \textit{leftmost} original da |B_tree| e transformar-la numa lista de |B_tree|, através de |singl|, para ser possível mais tarde a sua junção com o resultado de |k1|.

\xymatrix@@C=1cm@@R=1.8cm{
	|B_tree A|
		\ar[dr]_{|k1|}
&
	|B_tree A >< (A >< B_tree A)**|
		\ar[l]_---{|p1|}
		\ar[r]^--{|p2|}
		\ar[d]||{|split k1 k2|}
&
	|(A >< B_tree A)**|
		\ar[ld]^{|k2|}
\\
&
	|(B_tree A)** >< (B_tree A)**|
}

\vspace{0.5cm}

\xymatrix@@C=1cm{
	|k1 :: B_tree A >< (A >< B_tree A)**|
		\ar[r]^--{|f|}
&
	|(A** >< (B_tree A)**)|
		\ar[r]^--{|p2|}
&
	|(B_tree A)**|
		\ar[r]^--{|tail|}
&
	|(B_tree A)**|
}

\xymatrix@@C=1cm{
	|k2 :: B_tree A >< (A >< B_tree A)**|
		\ar[r]^--{|p1|}
&
	|(B_tree A)|
		\ar[r]^--{|singl|}
&
	|(B_tree A)**|
}

\vspace{0.5cm}

Fazendo |split (head . p2 . f) g| obtemos o par necessário para que a função |inB_tree| construa a nova árvore (espelhada) a partir da original.

\begin{code}
mirrorB_tree = anaB_tree (( id -|- (split (head.p2.f) (g))).outB_tree)
                where
                     g = uncurry(zip).(split (p1.f) (conc. k))
                     f = (reverse >< reverse).unzip.p2
                     k = split (tail.p2.f) (singl.p1)
\end{code}

\newpage

\noindent\rule{10cm}{0.02cm}

\subsubsection*{Mirror-Testes}
\begin{code}
mirror_test :: [Int] ->  Bool
mirror_test l = f l == g l
                  where
                      f = reverse . qSortB_tree
                      g = inordB_tree. mirrorB_tree. anaB_tree (lsplitB_tree)
                      
num_gen :: Gen Int
num_gen = choose(0,1000)

intList_gen :: Gen [Int]
intList_gen =  vectorOf 50 num_gen

prop_mirror :: Property
prop_mirror = forAll intList_gen $ \x -> mirror_test x
\end{code}
%$

\subsubsection*{Quicksort}

\begin{verbatim}
lsplitB_tree (h:h2:t) = 
	([menores que h], [(h, [maiores que h e menores que h2]), 
          (h2, [maiores que h2])])
\end{verbatim}

Onde [elementos menores que h] = |leftmost|, h e h2 = são os valores, [elementos maiores que h e menores que h2] e [elementos maiores que h2] = |blocks| dos valores h e h2, respetivamente.

\vspace{0.2cm}

|lsplitB_tree :: Ord a => [a] ->| Either | () ([a],[(a,[a])])|
\begin{code}
lsplitB_tree [] = i1 ()
lsplitB_tree [e] = i2 ([],[(e,[])])
lsplitB_tree (h:h2:t) =  i2 ( ((filter (<minpar) t)) , 
                          [ ((minpar),((filter (condition) t))) , ((maxpar),((filter (>maxpar) t))) ]) 
       where
            minpar = (min h h2)
            maxpar = (max h h2)
            condition = \a -> (a>minpar) && (a<maxpar)
\end{code}

\begin{code}
qSortB_tree:: Ord a => [a] -> [a]
qSortB_tree = hyloB_tree inordB_gene lsplitB_tree
\end{code}

\xymatrix@@C=3cm@@R=2cm{
	|A**|
		\ar[r]^{|lsplitB_tree|}
		\ar[d]||{|ana lsplitB_tree|}
&
	|1 + A** >< (A >< A**)**|
		\ar[d]||{|id  + ana lsplitB_tree >< map (id >< ana lsplitB_tree)|}
\\
	|B_tree A|
		\ar[d]||{|cata inordB_gene|}
		\ar@@/^0.4cm/[r]^-{|out|}
&
	|1 + B_treeA >< (A >< B_tree A)**|
		\ar[d]||{|id + cata inordB_gene >< map (id >< cata inordB_gene)|}
		\ar@@/^0.4cm/[l]^-{|in|}
\\
	|A**|
&
	|1 + A** >< (A >< A**)**|
		\ar[l]^{|inord|}
}

\newpage

\noindent\rule{10cm}{0.02cm}

\subsubsection*{Quicksort-Testes}

\begin{code}
isSorted :: Ord a => [a] -> Bool
isSorted [] = True
isSorted (a:[]) = True
isSorted (h:hs) = if (h <= head hs) then isSorted hs
                  else False

qsort_test x = isSorted (qSortB_tree x)

prop_qsort = forAll intList_gen $ \x -> qsort_test x
\end{code}
%$

\subsubsection*{dotB\_tree}

\xymatrix@@C=3.8cm@@R=1.8cm{
	|B_tree A|
		\ar[d]_{|cB_tree2Exp|}
		\ar@@/_0.5cm/[r]_-{|out|}
&
	|1 + B_tree A >< (A >< (Btree A))**|
		\ar@@/_0.5cm/[l]^-{|in|}
		\ar[d]||{|id + cB_tree2Exp >< map (id >< cB_tree2Exp)|}
\\
	|Exp A|
&
	|1 + Exp A >< (A >< (Exp A))**|
		\ar[l]^-{|either (const (Var "nil")) cB2E_aux|}
\\
&
	|Exp A >< (A >< (Exp A))**|
		\ar[u]||{|i2|}
		\ar[d]||{|id >< unzip|}
		\ar@@/^3cm/[dddd]^{|cB2E_aux|}
\\
&
	|Exp A >< (A** >< (Exp A)**)|
		\ar[d]||{|split (p1 . p2) (split p1 (p2 . p2))|}
\\
&
	|A** >< (Exp A >< (Exp A)**)|
		\ar[d]||{|id >< cons|}
\\
&
	|A** >< (Exp A)**|
		\ar[d]||{|uncurry Term|}
\\
&
	|Exp A|
		\ar@@{-->}@@/^1cm/[uuuuul]
}


\begin{code}	
dotB_Tree :: Show a => B_tree a -> IO ExitCode
dotB_Tree = dotpict . bmap nothing (Just . init . concat . (map (++"|")) . (map show)) .   cB_tree2Exp


cB_tree2Exp = cataB_tree (either (const (Var "nil")) cB2E_aux)
            where cB2E_aux = uncurry(Term).(id >< cons).(split (p1.p2) (split p1 (p2.p2))).(id><unzip)
\end{code}

\newpage

\subsubsection*{Exemplos de mirror, dotB\_tree e qsort}

Lista para árvore para \textit{dot}
\begin{code}
listToBtreeToDot t = (dotB_Tree . anaB_tree (lsplitB_tree)) t
\end{code}

\textit{Mirror} de lista para árvore para \textit{dot}
\begin{code}
mirrorListToBtreeToDot t = (dotB_Tree .mirrorB_tree .(anaB_tree (lsplitB_tree))) t
\end{code}

Geramos a árvore a partir da seguinte lista:
\begin{verbatim}
[870,241,548,743,974,399,620,68,266,12,436,540,51,902,350,926,53,567,4,676]
\end{verbatim}

\noindent sendo a sua representação da seguinte forma:

\begin{figure}[!ht]
	\centering
	\includegraphics[width=17.5cm]{cp1617t_media/tree.png}
	\caption{Árvore exemplo}
\end{figure}

\begin{figure}[!ht]
	\centering
	\includegraphics[width=17.5cm]{cp1617t_media/tree_mirror.png}
	\caption{Árvore exemplo após mirror}
\end{figure}

Após a aplicação de |qsortB_tree|:

\begin{verbatim}
[4,12,51,53,68,241,266,350,399,436,540,548,567,620,676,743,870,902,926,974]
\end{verbatim}

\newpage

\subsection*{Problema 4}

\subsubsection*{\sl{1}}

\begin{figure}[!ht]
\begin{minipage}[t]{.5\textwidth}
\xymatrix@@C=2.7cm@@R=2cm{
	|Algae A|
		\ar[d]||{|cata_Algae_A ga gb|}
		\ar@@/_0.5cm/[r]_-{|out|}
&
	|1 + A >< B|
		\ar@@/_0.5cm/[l]^-{|in|}
		\ar[d]||{|id + cata_Algae_A ga gb >< cata_Algae_B ga gb|}
\\
	|X|
&
	|1 + X >< X|
		\ar[l]^-{|ga|}
}
\end{minipage}
\hspace{1.1cm}
\begin{minipage}[t]{.5\textwidth}
\xymatrix@@C=3.3cm@@R=2cm{
	|B|
		\ar[d]||{|cata_Algae_B ga gb|}
		\ar@@/_0.5cm/[r]_-{|out|}
&
	|1 + A|
		\ar@@/_0.5cm/[l]^-{|in|}
		\ar[d]||{|id + cata_Algae_A ga gb|}
\\
	|X|
&
	|1 + X|
		\ar[l]^-{|gb|}
}
\end{minipage}
\end{figure}
\begin{figure}[!ht]
\hspace{0.1cm}
\begin{minipage}[t]{.5\textwidth}
\xymatrix@@C=3.1cm@@R=2cm{
	|A|
		\ar@@/_0.5cm/[r]_-{|out|}
&
	|1 + A >< B|
		\ar@@/_0.5cm/[l]_-{|in|}
\\
	|X|
		\ar[u]||{|ana_Algae_A ga gb|}
		\ar[r]_{|ga|}
&
	|1 + X >< X|
		\ar[u]||{|id + ana_Algae_A ga gb >< ana_Algae_B ga gb|}
}
\end{minipage}
\hspace{1.2cm}
\begin{minipage}[t]{.5\textwidth}
\xymatrix@@C=3.3cm@@R=2cm{
	|B|
		\ar@@/_0.5cm/[r]_-{|out|}
&
	|1 + A|
		\ar@@/_0.5cm/[l]_-{|in|}
\\
	|X|
		\ar[u]||{|ana_Algae_B ga gb|}
		\ar[r]_{|gb|}
&
	|1 + X|
		\ar[u]||{|id + ana_Algae_A ga gb|}
}
\end{minipage}
\end{figure}

\begin{code}
cata_Algae_A ga gb = ga . (id -|- (cata_Algae_A ga gb) >< (cata_Algae_B ga gb) ). outA
cata_Algae_B ga gb = gb . (id -|- (cata_Algae_A ga gb)) . outB

ana_Algae_A ga gb = inA . (id -|- ((ana_Algae_A ga gb)><(ana_Algae_B ga gb)) ) . ga 
ana_Algae_B ga gb = inB . (id -|- (ana_Algae_A ga gb) ) . gb 
\end{code}


\subsubsection*{\sl{2 - Pointwise}}

\begin{code}
generateAlgaeA 0 = NA
generateAlgaeA n = A (generateAlgaeA (n-1)) (generateAlgaeB (n-1))
\end{code}

\begin{code}
generateAlgaeB 0 = NB
generateAlgaeB n = B (generateAlgaeA (n-1))
\end{code}

\begin{code}
showAlgaeA NA = "A"
showAlgaeA (A a b) = (showAlgaeA a) ++ showAlgaeB b
\end{code}

\begin{code}
showAlgaeB NB = "B"
showAlgaeB (B a) = showAlgaeA a
\end{code}

\newpage

\subsubsection*{\sl{2 - Pointfree}}

\begin{figure}[!ht]
\begin{minipage}[t]{.5\textwidth}
\xymatrix@@C=1cm@@R=2cm{
	|A|
		\ar@@/_0.5cm/[rr]_-{|out|}
&&
	|1 + A >< B|
		\ar@@/_0.5cm/[ll]_-{|in|}
\\
	|Nat0|
		\ar[u]||{|generateA|}
		\ar[r]_{|out|}
		\ar@@/_1cm/[rr]_{|ga1|}
&
	|1 + Nat0|
		\ar[r]_-{|id + (split id id)|}
&
	|1 + Nat0 >< Nat0|
		\ar[u]||{|id + generateA >< generateB|}
}
\end{minipage}
\hspace{1.3cm}
\begin{minipage}[t]{.5\textwidth}
\xymatrix@@C=4cm@@R=2cm{
	|B|
		\ar@@/_0.5cm/[r]_-{|out|}
&
	|1 + A|
		\ar@@/_0.5cm/[l]_-{|in|}
\\
	|Nat0|
		\ar[u]||{|generateB|}
		\ar[r]_{|gb1 = out|}
&
	|1 + Nat0|
		\ar[u]||{|id + generateA|}
}
\end{minipage}
\end{figure}

\begin{code}
generateAlgae = ana_Algae_A ga1 gb1
          where 
             ga1 = (id -|- (split id id)).outNat
             gb1 = outNat
\end{code}

\begin{figure}[!ht]
\begin{minipage}[t]{.5\textwidth}
\xymatrix@@C=2.5cm@@R=2cm{
	|A|
		\ar[d]||{|showA|}
		\ar@@/_0.5cm/[r]_-{|out|}
&
	|1 + A >< B|
		\ar@@/_0.5cm/[l]^-{|in|}
		\ar[d]||{|id + showA >< showB|}
\\
	|String|
&
	|1 + String >< String|
		\ar[l]^-{|ga2 = either (const "A") conc|}
}
\end{minipage}
\hspace{1cm}
\begin{minipage}[t]{.5\textwidth}
\xymatrix@@C=3.4cm@@R=2cm{
	|B|
		\ar[d]||{|showB|}
		\ar@@/_0.5cm/[r]_-{|out|}
&
	|1 + A|
		\ar@@/_0.5cm/[l]^-{|in|}
		\ar[d]||{|id + showA|}
\\
	|String|
&
	|1 + String|
		\ar[l]^-{|gb2 = either (const "B") id|}
}
\end{minipage}
\end{figure}

\begin{code}
showAlgae = cata_Algae_A ga2 gb2
       where
           ga2 = either (const "A") (conc)
           gb2 = either (const "B") (id)
\end{code}

\subsubsection*{Exemplos de \textit{generate} e \textit{show}}

\begin{verbatim}
generateAlgae 0 = NA
showAlgae (generateAlgae 0) = "A"

generateAlgae 1 = A NA NB
showAlgae (generateAlgae 1) = "AB"

generateAlgae 5 = A (A (A (A (A NA NB) (B NA)) (B (A NA NB))) (B (A (A NA NB) 
  (B NA)))) (B (A (A (A NA NB) (B NA)) (B (A NA NB))))
showAlgae (generateAlgae 5) = "ABAABABAABAAB"	
\end{verbatim}

\newpage

\noindent\rule{10cm}{0.02cm}

\subsubsection*{Testes}
Para os testes tivemos que redefinir a função \textit{fib}, pois só assim é possível alterar o seu tipo (de \textbf{Integer} para \textbf{Int}). Esta alteração deve-se ao facto de tornar compatíveis os tipos de |length| e |fib|, para que o teste seja realizável.

\begin{code}
algae_test :: Int -> Bool
algae_test n =  (length . showAlgae . generateAlgae) n == (fib . succ) n
                 where
                    fib:: Int->Int
                    fib =  hyloLTree (either (const 1) (uncurry(+))) fibd
                    
prop_algae :: Property
prop_algae = forAll (choose (0,20)) $ \x -> algae_test x
\end{code}
%$

\subsection*{Problema 5}

\subsubsection*{\sl{Pointwise}}
Mais uma vez, iremos primeiro fazer as funções em \textit{pointwise}, atingindo posteriormente os resultados finais. Para isso, começaremos por criar novas funções jogo e getR, onde o fator de aleatoriedade está ausente (sendo que isso envolve probabilidades, o que implicaria o uso de Monades). A partir destas faremos depois a sua \textcolor{cardinal}{monadificação}\footnote{Secção 4.10 - ("Monadification" of Haskell code made easy) - apontamentos teóricos}, de modo a obter as funções finais pedidas pelo enunciado. 

|jogo_| indica que ganha sempre a primeira equipa (da casa) e |getR_| tira sempre a cabeça da lista.
\begin{code}
jogo_ :: (Equipa, Equipa) ->  Equipa
jogo_ = p1
\end{code}

\begin{code}
eliminatoria_ (Leaf a) =  a
eliminatoria_ (Fork (l,r)) = jogo_(e1,e2)
                             where
                                  e1 = eliminatoria_ l
                                  e2 = eliminatoria_ r
\end{code}

\begin{code}
getR_ :: [a] -> (a, [a])
getR_ = split head tail
\end{code}
     
\begin{code}                       
permuta_ :: [a] -> [a]
permuta_ [] = []
permuta_ list = (a:permuta_ b)
                 where 
                   (a,b) = getR_ list
\end{code}

\subsubsection*{"Monadificação"}

\begin{code}
permuta [] = return []
permuta list = do { (a,b) <- getR list; c <- permuta b ; return (a:c)}
\end{code}

\begin{code}
eliminatoria (Leaf a

  ) = return a
eliminatoria (Fork (l,r)) = do { e1 <- eliminatoria l; e2 <- eliminatoria r; jogo(e1,e2) }
\end{code}

\begin{code}
eliminatorio:: LTree Equipa -> Equipa
eliminatorio = cataLTree (either id (p1.head.unD.jogo))
\end{code}


%----------------- Fim do anexo cpm soluções propostas ------------------------%

%----------------- Índice remissivo (exige makeindex) -------------------------%

\printindex

%----------------- Fim do documento -------------------------------------------%


% Hide here code that is not relevant to the essence of the problems given
\def\hiddencode{
\begin{code}
type Null   = ()
type Prod a b = (a,b)
fork = Cp.split
envia = unsafePerformIO

sep:: Char -> Bool
sep c = (c == ' ' || c == '\n' || c == '\t')

--Teste inv
--Para testar esta alternativa, iremos utilizar a prop. universal-cata e o --somatório do enunciado

-- UNIVERSAL-CATA
--(inv x).inNat = (either (const 1) (succ.((1-x)*))) . (id + (inv x))

--desenvolvendo fica:

--inv x 0 = 1
--inv x (n+1) = (succ.((1-x)*)) . (inv x n)

-- \sum_i=0^n (1-x)^i Substituem os dois inv x da segunda linha pelo somatório \sum_i=0^n (1-x)^i 
-- e mostram que a igualdade se verifica. 
-- SOMATÓRIO sum_i:
sum_i x 0 = 1
sum_i x (n+1) = (1-x)^(n+1) + (sum_i x n)

--QUICKCHECK
-- inv x (n+1) = (succ.((1-x)*)) . (inv x n) <=>
-- <=> sum_i x (n+1) = (succ.((1-x)*)) . (sum_i x n)
-- <=> sum_i x (n+1) == (succ((1-x)*(sum_i x n))) -- Def-comp (succ.((1-x)*)) . (sum_i x n)

--inv_alt_test x (n+1) =  round2(sum_i x (n+1)) == round2(succ((1-x)*(sum_i x n)))

inv_alt_test :: Double -> Bool
inv_alt_test x = round2(sum_i x (1000+1)) == round2(succ((1-x)*(sum_i x 1000))) -- n = 1000


prop_inv_alt :: Property
prop_inv_alt = forAll (choose(1,2)) $ \x -> inv_alt_test x

-- Arredonda às décimas
round2 x = fromIntegral(round (x * 100))/100
\end{code}
%$
}

\end{document}

