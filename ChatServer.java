/**
 *
 * @author wilgner.gabriel
 */

package chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ChatServer {
    static ArrayList<String> listaUsuarios = new ArrayList <String>(); 
    static ArrayList<PrintWriter> printWriters = new ArrayList <PrintWriter>(); // Desinatarios de mensagem 
    
    public static void main (String[] args) throws Exception {
    System.out.println("Aguardando novos usuários...");
    ServerSocket ss = new ServerSocket(9086);
    while (true) {
        Socket usuario = ss.accept();
        System.out.println("Usuário conectado!");
        
        ManipuladorConversa conversa = new ManipuladorConversa(usuario);
                conversa.start();
  }
 }
}

    class ManipuladorConversa extends Thread {     
        Socket usuarioConversa;
        BufferedReader entrada;     // Dados recebidos, dados enviados pelo cliente para o servidor.//
        PrintWriter saida;          // Enviar dados para o cliente, usuario que estiver conectado ao servidor.//
        String nome; 
        
        public ManipuladorConversa (Socket usuarioConversa) throws IOException {
            this.usuarioConversa = usuarioConversa;
     }
        public void run() {
            try {
                entrada = new BufferedReader(new InputStreamReader(usuarioConversa.getInputStream()));
                saida = new PrintWriter(usuarioConversa.getOutputStream(), true);
                
                int contador = 0;
                while (true)  {
                    if (contador > 0) {
                        saida.println("NOME_EXISTENTE");
                    } else {
                        saida.println("NOME_REQUERIDO");
                    }
                    nome = entrada.readLine();
                    
                    if (nome == null){
                        return;
                    }
                    
                    if (!ChatServer.listaUsuarios.contains(nome)){ //se o nome de usuario nao estiver na entrada ele pode ser utilizado
                        ChatServer.listaUsuarios.add(nome);
                        break;
                    }
                    
                    contador++; 
                }
                
                saida.println("NOME_ACEITO" + nome);
                ChatServer.printWriters.add(saida);
                
                while(true) {
                    String msg = entrada.readLine();
                    
                    if (msg == null) {
                        return;
                    }
                    
                    for(PrintWriter writer : ChatServer.printWriters) {
                        writer.println(nome + ": " + msg);
                    }
                }
                
            } catch (Exception e) { 
               System.out.println("Erro no servidor: " + e.getMessage());
               e.printStackTrace();
            }   
        }
    }