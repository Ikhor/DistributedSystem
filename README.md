# DistributedSystem
A Proposal for Solving the Exclusion Problem Mutual, Distributed Consensus and Epidemic Algorithm in the scope of Distributed Systems

# Abstract.
The objective of this paper is to present a solution to the problem
of distributed mutual exclusion, the problem of distributed consensus, and the
epidemic algorithm using as a base the election algorithm Oliveira-Castro.

#1. Introdução
O rápido avanço da capacidade de processamento dos computadores e da comunicação
entre estes por meio das redes de computadores, tornou possível a composição de sistemas
de computação formados por um alto número de computadores que se comunicam entre
si. Esses sistemas são denominados Sistemas Distribuídos (SDs).
Como ainda não há uma consenso geral para uma definição formal do que são os
sistemas distribuídos, iremos considerar neste artigo a definição dada por Tanenbaum e
Steen (2007), que definem da seguinte forma: "Um sistema distribuído é um conjunto de
computadores independentes que se apresenta a seus usuários como um sistema único e
coerente". Logo, partindo do pressuposto que um SD é formado por computadores independentes, podemos inicialmente perceber alguns possíveis problemas dentre os quais
podemos citar:
1. Como organizar vários computadores independentes de forma que o cliente só
enxergue um único sistema?
2. Como garantir que a requisição de um computador seja atendida por outro?
3. Em caso de falha na computação, como recuperar o estado anterior?
Ao longo do tempo, vários pesquisadores de renomadas instituições tem dedicado
seu tempo em estudos no intuito de solucionar os problemas na tomada de decisões, verificação de falhas, informação de disponibilidade via Gossip e outros. Neste trabalho,
apresentamos uma proposta para a solução da exclusão mútua, consenso distribuído e
informação de disponibilidade via fofoca (Gossip).
Este artigo está organizado da seguinte forma: A Seção 2 explana a solução apresentada para o problema da exclusão mútua, a Seção 3 descreve a elucidação para o problema do consenso distribuído, e, por fim, a Seção 4 aborda os métodos de solução para
o problema do algoritmo epidêmico.
2. Exclusão Mutua
2.1. Definição do Problema
A condição de disputa entre processos concorrendo por recursos compartilhados do sistema, já se tornou de fato, um dos problemas clássicos nos sistemas operacionais. No
entanto, este tipo de problema não restringe somente aos sistemas operacionais, mas também aos sistemas distribuídos.
Quando dois computadores concorrem entre si para a utilização de um dado recurso na rede, temos então uma condição de disputa no âmbito dos sistemas distribuídos.
Para solucionar este problema, o middleware deve garantir a exclusão mútua no intuito de
evitar a inconsistência ou o corrompimentos dos recursos da rede.
Tanenbaum (2009) afirma que um bom algoritmo de exclusão mútua entre processos deve garantir estas quatro condições:
• Nunca dois processos podem estar simultaneamente em suas regiões críticas;
• Nada pode ser afirmado sobre a velocidade ou sobre o número de CPUs;
• Nenhum processo executando fora de sua região crítica pode bloquear outro processo;
• Nenhum processo deve esperar eternamente para entrar em sua região crítica.
Logo, adaptando estas condições para os sistemas distribuídos, iremos considerar
a utilização de computadores ao invés de processos.
2.2. Solução Proposta
A solução proposta neste trabalho para a solução da exclusão mútua num ambiente distribuído, utiliza como base o algoritmo de eleição Oliveira-Castro que é baseado na teoria
dos Conjuntos Disjuntos. Considere a Figura abaixo como a forma de organização hierárquica duma possível rede e que será utilizada para exemplo.

Vamos considerar que cada nó oferece um recurso compartilhado para a rede,
sendo assim, qualquer nó pode requisitar uma operação a outro nó, sendo o requisitante
coordenador ou não. A solução proposta considera as seguintes condições:
1. Um nó só pode realizar uma chamada de sessão se o mesmo não estiver em sessão
crítica;
2. Cada nó tem uma lista de prioridades que armazenam as requisições das chamadas
de operação dos recursos compartilhados;
3. Enquanto a chamada do requisitante não for terminada, este deverá ficar em sessão
crítica;
4. Quando a operação requisitada for terminada, o nó requisitante poderá sair de sua
região crítica.
Quanto a lista de prioridades, esta funcionará de maneira dinâmica permitindo aos
nós atualizarem as prioridades de acordo com situações críticas, visando assim, prevenir
a inanição de processos. Cada requisição conterá uma prioridade que, quanto maior for,
mais rápido deverá ser atendida. A estrutura da lista de prioridades é formada por uma
heap binária, e que para livrá-la da inanição, todos os valores da heap após o atendimento de uma sessão critica serão incrementados com um valor aleatório entre 1 e 10,
reorganizando-a, e deixando-a assim como uma estrutura auto-ajustável. Desta forma, requisições criticas poderão acontecer ainda que somente sejam requisitadas sessões críticas
de alta prioridade
Vejamos agora o que aconteceria se o nó 3 requisitasse uma operação ao nó 2.
Para isso, iremos invocar a operação RequisitarOperação(3,2), onde o nó 3 está
requisitando a execução de uma operação ao nó 2. Vamos analisar agora os passos seguintes:
1. O nó 3 está em sessão crítica? Não. Logo ele poderá realizar uma chamada e
entrar em seguida em sessão crítica.
2. O nó 2 deverá então colocar a chamada do nó 3 em uma lista para ser atendida.
Sendo assim, a estrutura ficará da seguinte forma:
Figura 2. Organização da rede após a requisição do nó 3 ao nó 2.
Perceba que agora, o nó 2 colocou a requisição do nó 3 numa lista, e o nó 3 entrou
em sessão crítica. Neste momento, o nó 3 está proibido de chamar uma nova operação
compartilhada, no entanto ele poderá atender solicitação de outros da rede. Como é o
próximo caso que ilustraremos.
Vejamos uma requisição do nó 0 ao nó 3 que está em sessão crítica -
RequisitarOperação(0,3). Sendo assim, o nó 0 entrará em região crítica e o
nó 3 irá colocar a requisição efetuada numa lista para ser atendida. Ao final da chamada,
teremos a seguinte estrutura:
Figura 3. Organização da rede após a requisição do nó 0 ao nó 3.
Quando um nó termina de executar uma operação que foi chamada, o nó requisitante sai então de sua região crítica. Vejamos quando o nó 2 termina a computação do nó
3.
Figura 4. Organização da rede após o término da computação do nó 2.
Note que agora, o nó 3 saiu da região crítica, no entanto o nó 0 ainda permanece
nela, pois sua requisição ao nó 3 ainda não foi atendida.
Podemos perceber que este algoritmo utiliza do artifício de que um PC só irá fazer
uma chamada por vez dos recursos compartilhados, garantindo assim sua associação de
apenas um recurso por cada entrada em sessão crítica. Vale também ressaltar que nesta
proposta um PC não bloqueia outros estando fora de sua região crítica (Segurança), e nem
vai esperar eternamente para ser atendido (Imparcialidade).
3. Consenso Distribuído
Um outro problema clássico no âmbito dos sistemas distribuídos, é quanto a forma da
realização de um consenso entre as máquinas que compõe o SD. Considere um recurso
compartilhado X para toda a rede, e que este recurso constantemente é disputado entre as
máquinas, então como decidir se uma máquina pode ter acesso aquele recurso ou não.
Neste caso, um dos métodos mais democráticos para resolver esse problema seria
o de uma votação. Assim, poderíamos garantir que todas as máquinas teriam sua participação de voto, estabelecendo assim um consenso geral, de modo que decidiria a permissão
de acesso ao recurso ou não. No entanto, há cenários onde não seria interessante incluir
todos os participantes da rede na votação, por aumentar a complexidade do consenso e
o tempo do consenso. E também, não seria muito bom permitir o peso igual dos votos,
visto que uns PCs carregam sobre si uma maior responsabilidade do que outros.
O algoritmo proposto neste trabalho, utiliza para contagem de votos somente aqueles que são os coordenadores da rede. Ou seja, quando o algoritmo do consenso distribuídos é chamado, somente os PCs que são coordenadores poderão votar para que exista um
consenso. Vejamos o exemplo a seguir: O nó 5 realiza uma chamada ao recurso que está
sendo compartilhado na rede pelo nó 4. Até o momento, os nós 5, 6 e 10 são coordenadores. Para fins deste exemplo, considere a hierarquia da rede que está ilustrada na Figura a
seguir.
Figura 5. Organização da rede no momento em que o nó 5 requisita uma operação ao nó 4.
Após o nó 4 receber a mensagem de requisição do nó 5, o nó 4 por sua vez, que
não é coordenador de si mesmo, informa o pedido de requisição ao nó 6 que é o seu
coordenador, e este resolve convocar um consenso distribuído para resolver se sua folha
irá ceder o recurso para o nó 5 ou não.
O algoritmo do consenso irá convocar todos os coordenadores da rede (nós 4, 5 e
10) e realizar a contabilização dos votos de cada um. Vale ressaltar que o peso dos votos
podem ser diferentes, pois este é calculado de acordo com sua disponibilidade/importância na rede. Para este exemplo, vamos considerar os seguintes pesos de voto: nó 4 - peso
3; nó 5 - peso 2; nó 10 - peso 1.
Quando um consenso é chamado, existem somente três casos possíveis. São eles:
1. O consenso permitir a chamada do recurso compartilhado pelo requisitante;
2. O não permitir a chamada, e dessa forma, o requisitante ter a permissão negada
para o uso do recurso;
3. O resultado do consenso der empate;
O primeiro caso, é quando o consenso decidir que o requisitante terá a permissão
para acessar o recurso desejado. Com base no exemplo acima, se os nós 5 e 10 votarem
em sim e o nó 6 votar em não. Teremos o seguinte resultado: Votos de sim igual a 4(nó
5 - peso 3 + nó 10 - peso 1); Votos de não igual a 2 (nó 6 - peso 2). Dessa forma, o nó
6 deverá informar ao nó 4 que este poderá ceder o recurso ao nó 5, pois a decisão foi
tomada pelo consenso distribuído.
O segundo caso é justamente o oposto ao primeiro, se no consenso distribuído o
número dos votos de não excederem ao de sim, logo o nó 6 informará ao nó 4 que este
não deve permitir o uso do recurso.
Já no terceiro caso, vejamos como o algoritmo irá tratar o caso de empate. Para
isto, vamos considerar que o nó 5 votou em sim e os nós 6 e 10 votaram em não. Logo,
teremos o seguinte resultado: Sim com três votos (nó 5 - peso 3); Não com três votos (nó 6
- peso 2 + nó 10 - peso 1). Neste caso, quando acontece um empate, o middleware deverá
ordenar os votos por ordem crescente e identificar a mediana. Dessa forma, teremos: 1 2
3 (votos em ordem crescente) e mediana sendo igual a 2. Em seguida, basta verificar se o
peso do nó requisitante é maior que o da mediana. Vejamos: i) Quem é o nó requisitante?
O nó 5; ii) Qual o peso do seu voto? Peso 3; iii) O peso do seu voto é maior que o da
mediana? Sim. Pois 3 é maior que 2. Logo, como o peso do voto do nó 5 é maior que
o da mediana, então o nó 6 deverá informar a permissão ao nó 4 de que o nó 5 poderá
utilizar o recurso.
Vale ressaltar que em caso de empate, a permissão ao recurso só será permitida
se o peso de seu voto for maior que o da mediana, em caso de valor menor ou igual ao
da mediana, a permissão será negada. Para fins de análise, a estrutura ficará da seguinte
forma após a chamada de requisição do nó 5 ao nó 4, levando em conta o consenso
distribuído e a exclusão-mútua.
Perceba que após o consenso ter permitido a utilização do recurso compartilhado,
o nó 4 coloca a requisição do nó 5 numa lista, e o nó 5 entra em sessão crítica. Assim,
como foi explanado na secção 2. Em caso de crash (queda) por parte dos coordenadores,
note que o algoritmo trata este problema automaticamente, pois o consenso é realizado
somente entre os coordenadores que estão ativos. E o algoritmo de que trata do problema
da informação de disponibilidade ou não de um coordenador, é o algoritmo baseado em
boatos, que abordaremos na secção a seguir.
Figura 6. Organização da rede no após o consenso distribuído.
4. Algoritmos Baseados em Boatos
Dada a definição de que um SD é composto por computadores independentes que estão
interconectados(Tanenbaum e Steen, 2007), esse sistema deve continuar operando ainda
que um de seus computadores independentes apresentem falhas. Ou seja, se um sistemas
é composto por n computadores operacionais, e um número p de computadores estão
apresentando problemas, o sistema deverá identificar os PCs com problemas e excluí-los
do SD enquanto apresentarem erros.
Umas das formas mais simples de realizar a detecção se um PC está disponível
ou não, é a utilização da informação periódica de sua disponibilidade para a rede. Os
algoritmos que propõe uma solução para este tipo de caso, são comumente denominados
de algoritmos epidêmicos ou da fofoca (Gossip).
Vejamos o algoritmo epidêmico proposto neste trabalho. Partindo do pressuposto
de que o middleware tem o conhecimento dos PCs que estão disponíveis na rede. Então,
este deverá armazena numa lista somente os coordenadores que estão disponíveis. Para
exemplo, vamos considerar dois coordenadores numa rede, e que cada um está no maior
nível da hierarquia de sua rede.
Perceba que na Figura 7, os coordenadores que estão no topo da hierarquia são
os nós 5 e 6. Logo, estes devem constantemente informar a rede que estão ativos. Se
um nó não noticiar a rede de que está ativo, a própria rede irá excluir-lo da lista dos
coordenadores ativos.
Vejamos agora algumas possíveis situações. Caso o nó 5 realize uma chamada de
operação a outro nó que está dentro de sua hierarquia, o algoritmo de eleição irá tratar esta
requisição. No entanto, se o nó 5 realizar uma chamada ao nó 4 que está no conjunto do nó
6. Segundo o algoritmo de eleição Oliveira-Castro, o nó 4 irá requisitar as informações
do nó 6, pois o último não é coordenador de si mesmo. Neste caso teremos então três
possíveis cenários, são eles:
1. O nó 6 está ativo e atende a requisição;
2. O nó 6 não está ativo e não atende a requisição;
3. O nó 6 está ativo e não atende a requisição.
Figura 7. Nós 5 e 6 devem informar periodicamente sua disponibilidade à rede.
No primeiro caso, se o nó 6 atende a requisição ele irá informar seus dados ao
nó 4. Ou então, o nó 6 pode ter estar desativado e não informará seus dados, em ambos
os casos o algoritmo de eleição irá tratá-los. No entanto, o se o nó 6 estiver ativo e
não puder atender a requisição, por exemplo, por motivos de congestionamento da rede.
Nesta condição, o nó 4 irá atender a requisição do nó 5 e dar uma segunda chance para
seu coordenador. Caso na próxima vez o nó 6 estiver ativo e ainda assim não atender uma
requisição do nó 4. Este irá invocar o algoritmo de eleição, e possivelmente poderá sair
da rede do nó 6 como coordenador.

#Referências
Tanenbaum, Andrew S. Sistemas Operacionais Modernos. 3a Ed., Prentice Hall,
2009.
Tanenbaum, Andrew S. e Steen, Maarten Van. Sistemas Distribuídos: Princípios
e Paradigmas. 2a Ed., São Paulo: Prentice Hall, 2007.

