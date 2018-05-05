//package pacote;
// imports
import java.net.*;
import javax.swing.*;
import java.io.*;
import java.*;



public class ProtocoloBitAlternado {

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
	
	public void fechaConexao (String msg) throws IOException {
		
		JOptionPane.showMessageDialog(null,msg,"Conex�o",JOptionPane.WARNING_MESSAGE);
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
	
	public void estadoServidor () {
		
		try {
		
			// Inicia socket do servidor
			serverSocket = new ServerSocket(5000,1); 
		
			JOptionPane.showMessageDialog(null,"Servidor:"+serverSocket.getInetAddress()+" Porta:"+serverSocket.getLocalPort(),"Servidor",JOptionPane.PLAIN_MESSAGE);
			
			esperaConexao();
			pegaFluxo();
			trocaMensagens();
			fechaConexao("M�ximo numero de pedidos para mesmo dado!");
			
		} catch (IOException ioException) { JOptionPane.showMessageDialog(null,"Erro: "+ ioException.toString(),"Erro!",JOptionPane.ERROR_MESSAGE); }
			
	}
	
	public void estadoCliente () {
		
			try {
		
				conectaServidor();
				pegaFluxo();
				trocaMensagemCliente();
				
				fechaConexao("Envio Conclu�do\n Conex�o Terminada");
			
			} catch (IOException ioException) { JOptionPane.showMessageDialog(null,"Erro: "+ ioException.toString(),"Erro!",JOptionPane.ERROR_MESSAGE); }
			
		}

	public static void main(String[] args) throws IOException {
		
		ProtocoloBitAlternado aplicacao = new ProtocoloBitAlternado();
		
		String resposta = JOptionPane.showInputDialog("Digite Servidor ou cliente dependo da ap��o desejada:");
		
		if (resposta.equalsIgnoreCase("servidor")) {
			
			aplicacao.estadoServidor();	
		} else if (resposta.equalsIgnoreCase("cliente")) {
			
			aplicacao.estadoCliente();
			
		} else {
		
			JOptionPane.showMessageDialog(null,"Valor n�o v�lido, tente servidor ou cliente!","Servidor",JOptionPane.ERROR_MESSAGE);
			
		}	
			
		System.exit (0);
		
	}	
		

}	
			


