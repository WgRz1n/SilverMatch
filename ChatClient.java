
/**
 *
 * @author wilgner.gabriel
 */

package chat;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    static JFrame chatJanela = new JFrame("SilverMatch");
    static JTextArea chatMensagens = new JTextArea (22,40); // Campo Visualizar as mensagens 
    static JTextField chatNovaMensagem = new JTextField(40); // Campo Digita as mensagens
    static JButton chatBotaoEnviar = new JButton ("Enviar"); 
    
    static BufferedReader entrada;
    static PrintWriter saida; 
    static JLabel chatUsuarioLogado = new JLabel("");
    
    public ChatClient() {
        chatJanela.setLayout(new FlowLayout()); //Seta o tipo de layout
        chatJanela.add(chatUsuarioLogado);
        chatJanela.add(new JScrollPane(chatMensagens));
        chatJanela.add(chatNovaMensagem);
        chatJanela.add(chatBotaoEnviar);    
        
       chatJanela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fecha 
       chatJanela.setSize (475, 500); //tamanho da janela
       chatJanela.setVisible(true); //estado visivel
       chatMensagens.setEditable(false); //nao deixa ser editavel / mensagens gravadas apenas dentro da classe cliente
       chatNovaMensagem.setEditable(false);
       
       chatBotaoEnviar.addActionListener(new Listener());
       chatNovaMensagem.addActionListener(new Listener());
    }
    
    public void iniciarChat() throws Exception {
        String enderecoIP = JOptionPane.showInputDialog(chatJanela, "Endereço IP do Servidor", "Informação", 
                JOptionPane.PLAIN_MESSAGE);
        Socket usuario = new Socket (enderecoIP, 9086);
        
        entrada = new BufferedReader(new InputStreamReader(usuario.getInputStream()));
        saida = new PrintWriter(usuario.getOutputStream(), true);
        
        while (true) {
            String msg = entrada.readLine();
            
            if (msg.equals("NOME_REQUERIDO")){
                String nome = JOptionPane.showInputDialog(chatJanela, "Nome do usuário:", "Informação", 
                        JOptionPane.PLAIN_MESSAGE);
                
                        saida.println(nome);
        }   else if (msg.equals("NOME_EXISTENTE")){
                String nome = JOptionPane.showInputDialog(chatJanela, "Informe outro nome do usuário:", "Nome duplicado", 
                        JOptionPane.WARNING_MESSAGE);
                saida.println(nome);
        }   else if (msg.startsWith("NOME_ACEITO")) {
                chatNovaMensagem.setEditable(true); //Conexao feita e nome do usuario aceito
                chatUsuarioLogado.setText("Você esta logado como: " + msg.substring(11));
                } else {
                chatMensagens.append(msg + "\n"); 
            
        }
        
        }
    }
    
    public static void main(String[] args) throws Exception {
        ChatClient chat = new ChatClient ();
        chat.iniciarChat();
 
 }

  
}

class Listener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ChatClient.saida.println(ChatClient.chatNovaMensagem.getText());
        ChatClient.chatNovaMensagem.setText(""); //Limpa o campo para o usuario escrever uma nova mensagem 
        
    }
}