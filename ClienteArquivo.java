// DANIEL BURNIER DE CASTRO

import java.net.*;
import javax.swing.*;
import java.io.*;
import java.*;


public class ClienteArquivo {

	private ServerSocket serverSocket;
	private Socket conexao;
	private ObjectOutputStream enviarMensagem;
	private ObjectInputStream receberMensagem;  
	String endServidor = "";
	int porta = 0;
	String mensagem = "";
	
	public void conectaServidor () throws IOException {
		
		//JOptionPane.showMessageDialog(null,"Tentando conexao","Conectando...",JOptionPane.PLAIN_MESSAGE);
		 
		endServidor = JOptionPane.showInputDialog("Endere�o do Servidor:");
		porta = Integer.parseInt(JOptionPane.showInputDialog("Porta do Servidor:"));		
		
		conexao = new Socket (endServidor, porta);
		 
		JOptionPane.showMessageDialog(null, "Conex�o aceita com: "+conexao.getInetAddress().getHostName(),"Conex�o",JOptionPane.PLAIN_MESSAGE);
		 
	}
	
		
	
	public void esperaConexao ()  throws IOException {
		
		JOptionPane.showMessageDialog(null,"Esperando conexao","Espera",JOptionPane.PLAIN_MESSAGE);
		
		// permite que o servidor aceite a conex�o
		conexao = serverSocket.accept();
		JOptionPane.showMessageDialog(null, "Conex�o Efetuada","Conex�o",JOptionPane.PLAIN_MESSAGE);
		
	}
	
	public void pegaFluxo () throws IOException {
		
		// configura fluxo de sa�da
		enviarMensagem = new ObjectOutputStream(conexao.getOutputStream());
		// descarrega o buffer
		enviarMensagem.flush();
		
		//configura o fluxo de entrada
		receberMensagem = new ObjectInputStream( conexao.getInputStream());
		
	}
	
	
	 
	
	public void estadoCliente () {
		
		try {
		
			conectaServidor();
			pegaFluxo();
			 
		} catch (IOException ioException) { JOptionPane.showMessageDialog(null,"Erro: "+ ioException.toString(),"Erro!",JOptionPane.ERROR_MESSAGE); }
			
	}

	public static void main(String[] args) throws IOException {
		
		ClienteArquivo aplicacao = new ClienteArquivo();
		
		aplicacao.estadoCliente();	
				
	}	
		

}	
			


