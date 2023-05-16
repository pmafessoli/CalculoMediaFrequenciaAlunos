# Informações do Projeto
name: CalculoMediaFrequenciaAlunos
description: Projeto em Java para cálculo de média e frequência dos alunos

# Tempo Gasto
time: 15 horas

# Dificuldades
difficulties:
  - Requisição aos serviços web
  - Manipulação dos dados recebidos

# Dependências
dependencies:
  - name: gson
    version: 2.8.7
    url: https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.7
  - name: httpclient
    version: 4.5.13
    url: https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.5.13

# Configurações de Compilação
build:
  - command: javac -d bin src/*.java
    directory: CalculoMediaFrequenciaAlunos
  - command: jar -cvf CalculoMediaFrequenciaAlunos.jar -C bin .
    directory: CalculoMediaFrequenciaAlunos

# Comandos para Execução
run:
  - command: java -jar CalculoMediaFrequenciaAlunos.jar
    directory: CalculoMediaFrequenciaAlunos

# Tarefas Futuras
todos:
  - Implementar geração do relatório em texto com o resultado dos alunos
