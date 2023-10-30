# SilverMatch
SilverMatch-UPX3

Esse código é um programa de chat em Java que consiste em um cliente e um servidor para permitir a comunicação entre vários usuários em um chat. Vou explicar o código por partes:

ChatClient.java:

Começa importando as classes e bibliotecas necessárias para o cliente Java.
Define uma classe chamada ChatClient, que é a classe principal do cliente.
Cria uma janela de chat (chatJanela) usando o framework Swing. A janela contém uma área de texto para mensagens, um campo de texto para entrada de novas mensagens, um botão "Enviar" e um rótulo para mostrar o nome de usuário logado.
Define um construtor para a classe ChatClient que configura a interface do cliente.
iniciarChat() é um método que controla a comunicação do cliente com o servidor. Ele inicia uma conexão com o servidor, lê mensagens do servidor e permite que o usuário envie mensagens.
No método main(), um objeto ChatClient é criado e o chat é iniciado.
Listener.java:

Esta é uma classe interna que implementa a interface ActionListener para lidar com eventos de ação, como pressionar o botão "Enviar" ou pressionar "Enter" no campo de texto.
Quando um evento ocorre, ele pega o texto do campo de entrada (chatNovaMensagem), envia para o servidor e limpa o campo de entrada para que o usuário possa digitar uma nova mensagem.
ChatServer.java:

Importa as classes e bibliotecas necessárias para o servidor Java.
Define uma classe chamada ChatServer, que é a classe principal do servidor.
Declara duas listas: listaUsuarios para rastrear os nomes de usuários conectados e printWriters para armazenar fluxos de saída para os clientes conectados.
O método main() do servidor cria um socket do servidor, aguarda a conexão de clientes e inicia uma nova thread ManipuladorConversa para cada cliente conectado.
ManipuladorConversa.java:

Esta é uma classe interna que estende Thread e é usada para gerenciar a comunicação com um único cliente.
No construtor, ele recebe um objeto Socket que representa a conexão com o cliente.
O método run() é onde toda a lógica de comunicação com o cliente ocorre. Ele lê e envia mensagens entre o servidor e o cliente. O cliente é solicitado a fornecer um nome de usuário, que é verificado para garantir que não seja duplicado.
Se o nome do usuário for aceito, a thread mantém uma lista de fluxos de saída (printWriters) para todos os clientes conectados e retransmite mensagens recebidas para todos os clientes.
No geral, este código é um exemplo de chat simples em Java que demonstra a comunicação entre um cliente e um servidor usando sockets e threads. Os usuários podem se conectar ao servidor, escolher um nome de usuário e trocar mensagens com outros usuários conectados.
