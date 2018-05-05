// DANIEL BURNIER DE CASTRO

import java.net.*;
import javax.swing.*;
import java.io.*;
import java.*;


public class ClienteTrocaFrase {

	private ServerSocket serverSocket;
	private Socket conexao;
	private ObjectOutputStream enviarMensagem;
	private ObjectInputStream receberMensagem;  
	String endServidor = "";
	int porta = 0;
	String mensagem = "";
	
	public void conectaServidor () throws IOException {
		
		//JOptionPane.showMessageDialog(null,"Tentando conexao","Conectando...",JOptionPane.PLAIN_MESSAGE);
		 
		endServidor = JOptionPane.showInputDialog("Endereço do Servidor:");
		porta = Integer.parseInt(JOptionPane.showInputDialog("Porta do Servidor:"));		
		
		conexao = new Socket (endServidor, porta);
		 
		JOptionPane.showMessageDialog(null, "Conexão aceita com: "+conexao.getInetAddress().getHostName(),"Conexão",JOptionPane.PLAIN_MESSAGE);
		 
	}
	
	public void trocaMensagemCliente () throws IOException {
		
		try {
		
			mensagem = (String) receberMensagem.readObject();
			JOptionPane.showMessageDialog(null,mensagem,"Mensagem",JOptionPane.PLAIN_MESSAGE);
										
			do {
				
				mensagem = JOptionPane.showInputDialog("mensagem?");
				enviarMensagem.writeObject(mensagem);
				enviarMensagem.flush();
				
				// RECEBENDO

				mensagem = (String) receberMensagem.readObject();
				JOptionPane.showMessageDialog(null,mensagem,"Mensagem",JOptionPane.PLAIN_MESSAGE);
				
			
			} while (!mensagem.equalsIgnoreCase("fim"));
		
		} catch (ClassNotFoundException cnfe) { JOptionPane.showMessageDialog(null,"Erro: "+ cnfe.toString(),"Erro!",JOptionPane.ERROR_MESSAGE); }
	}
		
	
	public void esperaConexao ()  throws IOException {
		
		JOptionPane.showMessageDialog(null,"Esperando conexao","Espera",JOptionPane.PLAIN_MESSAGE);
		
		// permite que o servidor aceite a conexão
		conexao = serverSocket.accept();
		JOptionPane.showMessageDialog(null, "Conexão Efetuada","Conexão",JOptionPane.PLAIN_MESSAGE);
		
	}
	
	public void pegaFluxo () throws IOException {
		
		// configura fluxo de saída
		enviarMensagem = new ObjectOutputStream(conexao.getOutputStream());
		// descarrega o buffer
		enviarMensagem.flush();
		
		//configura o fluxo de entrada
		receberMensagem = new ObjectInputStream( conexao.getInputStream());
		
	}
	
	public void fechaConexao (String msg) throws IOException {
		
		JOptionPane.showMessageDialog(null,msg,"Conexão",JOptionPane.WARNING_MESSAGE);
		enviarMensagem.close();
		receberMensagem.close();
		conexao.close();	
		
	}
	
	public void trocaMensagens () throws IOException {
		
		String retornoMensagem = "";
		enviarMensagem.writeObject("Conexao Estabelecida");
		enviarMensagem.flush();
						
		try {
			
			mensagem = (String) receberMensagem.readObject();
					
			do {
								
				// teste de transmissao com mensagem recebida - bit alternado				
				//mensagem = (String) receberMensagem.readObject();
								
				JOptionPane.showMessageDialog(null,mensagem,"Mensagem",JOptionPane.PLAIN_MESSAGE);
				for (int o = 0; o < mensagem.length(); o++) {
					if (mensagem.charAt(o) != ' ') {
						retornoMensagem = retornoMensagem + mensagem.charAt(o);
					} else {
						retornoMensagem = retornoMensagem + "CARACTERISTICAS";
					}	
				}
				enviarMensagem.writeObject(retornoMensagem);
				enviarMensagem.flush();
				
				mensagem = (String) receberMensagem.readObject();
			
			}while (!mensagem.equalsIgnoreCase("fim"));
			
		} catch (ClassNotFoundException cnfe) { JOptionPane.showMessageDialog(null,"Erro: "+ cnfe.toString(),"Erro!",JOptionPane.ERROR_MESSAGE); 
		}
			
	}
	 
	
	public void estadoCliente () {
		
		try {
		
			conectaServidor();
			pegaFluxo();
			trocaMensagemCliente();
				
			fechaConexao("Envio Concluído\n Conexão Terminada");
		
		} catch (IOException ioException) { JOptionPane.showMessageDialog(null,"Erro: "+ ioException.toString(),"Erro!",JOptionPane.ERROR_MESSAGE); }
			
	}

	public static void main(String[] args) throws IOException {
		
		ClienteTrocaFrase aplicacao = new ClienteTrocaFrase();
		
		aplicacao.estadoCliente();
			
				
	}	
		

}	
			


