# Diagramas UML — TEDI Med

- `classes-principal`: aplicação gráfica, modelo e serviços.
- `classes-complementar`: terminal legado e testes automatizados.
- `casos-de-uso`: seis requisitos funcionais originais.

Os arquivos `.puml` são fontes editáveis PlantUML. As imagens SVG e PNG foram geradas com Graphviz a partir das mesmas classes e relações; o motor PlantUML não estava disponível para validar/renderizar os fontes. Os arquivos `.dot` permitem reproduzir as imagens (`dot -Tsvg arquivo.dot -o arquivo.svg`, a partir desta pasta).

Notação: + público, - privado, sublinhado estático, {readOnly} referência/atributo final, triângulo vazio herança, linha tracejada dependência, losango preenchido composição (serviços criados e mantidos pela tela), seta contínua associação navegável. A lista de medicamentos é declarada List e implementada com ArrayList; a multiplicidade é 0..*. Não há banco de dados ou alarmes automáticos. Classes anônimas e componentes auxiliares da biblioteca Swing não são detalhados.
