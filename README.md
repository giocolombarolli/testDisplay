# testDisplay

Avaliação Técnica Gertec

## Descrição

App desenvolvido como parte da avaliação técnica da Gertec, com o objetivo de realizar um teste de display.

### Funcionalidades

- **Tela inicial simples** com um único botão: `Iniciar teste`.
- **Tela de teste interativa**:
    - Exibe uma grade com quadrados representando o display do dispositivo.
    - O usuário deve tocar em todos os quadrados para completar o teste.
    - Conforme os quadrados são acionados, eles mudam de cor para indicar progresso.
- **Conclusão do teste**:
    - Ao acionar todos os quadrados, o botão `Passou` fica visível.
    - Ao clicar, é exibido um diálogo de sucesso e o aplicativo retorna para a tela inicial.
- **Timeout automático**:
    - O teste possui um tempo limite de 10 segundos.
    - Caso o tempo se esgote antes do teste ser finalizado, é exibido um toast informando que o teste falhou e o app retorna à tela inicial.

## Tecnologias utilizadas

- **Linguagem:** Kotlin
- **Arquitetura:** MVVM (Model-View-ViewModel)
- **Navigation Component:** Para navegação entre as telas
- **ViewBinding:** Para acesso seguro às views
- **Coroutines + Flow:** Para gerenciamento reativo de estado e fluxo de dados

## Como executar o projeto

1. Clone este repositório.
2. Abra o projeto no Android Studio.
3. Conecte um dispositivo ou use um emulador.
4. Compile e execute o aplicativo.

---