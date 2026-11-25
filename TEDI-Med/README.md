# TEDI Med — Projeto final
Desenvolvemos o TEDI Med para auxiliar idosos na organização de medicamentos e horários, no contexto do projeto de extensão TEDI da UTFPR. Utilizamos Java 17, Swing e ArrayList, sem dependências externas.

**Integrantes:** Norberto Jacinto de Lemos Junior e Suzane Cristina Possidonio Vaz.

[Apresentação final em vídeo](https://youtu.be/f62n2dOkR38)

## Executar
Instale o JDK 17 ou superior. Abra a pasta no VS Code e execute `src/br/edu/tedi/Main.java`, ou abra um terminal nesta pasta e rode `java -jar TEDI-Med.jar`.

## Testes
`java -cp TEDI-Med.jar br.edu.tedi.TesteTEDIMed`

## Demonstração
Cadastre um medicamento fictício às 08:00, liste, busque por parte do nome, selecione e edite para 09:00, consulte 09:00, selecione e exclua. Teste também campos vazios e horário inválido.

## Estrutura
- model: entidade Medicamento.
- service: cadastro, busca, edição, exclusão e consulta de horários.
- view: interface Swing.
- Main: inicialização gráfica; MainTerminal: versão anterior pelo terminal.
- TesteTEDIMed: testes automatizados dos serviços.

Dados em memória são perdidos ao fechar. Lembretes são consultas por horário, não alarmes automáticos. Projeto acadêmico, não substitui orientação médica.

## Documentação
- [Diagramas UML](docs/README.md)
- [Quadro de atividades](docs/kanban.md)
- [Validação e evidências](docs/validacao.md)

## Compilar no PowerShell
```powershell
javac -encoding UTF-8 --release 17 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out br.edu.tedi.Main
java -cp out br.edu.tedi.TesteTEDIMed
```
