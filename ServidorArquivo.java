// DANIEL BURNIER DE CASTRO

import java.net.*;
import javax.swing.*;
import java.io.*;
import java.*;


public class ServidorArquivo {

	private ServerSocket serverSocket;
	private Socket conexao;
	private ObjectOutputStream enviarMensagem;
	private ObjectInputStream receberMensagem;  
	String endServidor = "";
	String from = "";
	String to = "";
	int porta = 0;
	String mensagem = "";
	
	public void conectaServidor () throws IOException {
		
		//JOptionPane.showMessageDialog(null,"Tentando conexao","Conectando...",JOptionPane.PLAIN_MESSAGE);
		 
		endServidor = JOptionPane.showInputDialog("Endereço do Servidor:");
		porta = Integer.parseInt(JOptionPane.showInputDialog("Porta do Servidor:"));		
		
		conexao = new Socket (endServidor, porta);
		 
		JOptionPane.showMessageDialog(null, "Conexão aceita com: "+conexao.getInetAddress().getHostName(),"Conexão",JOptionPane.PLAIN_MESSAGE);
		 
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
	
	public void forneceArquivo() throws IOException{

		InputStream in = null;
		OutputStream out = null;

		try {

		     from = JOptionPane.showInputDialog("Baixar qual arquivo? ");
		     to = JOptionPane.showInputDialog("Gravar onde? ");
		     
		     in = new FileInputStream(from);
		     out = new FileOutputStream(to);

		     while (true) {
			int data = in.read();
			if (data == -1) {break;}
			out.write(data);
		     }
		     
 		     in.close();
	             out.close();

		     } finally {
		     	if (in != null) {in.close(); }
			if (out != null) { out.close();} 
		     }
		
		}	


	public void fechaConexao (String msg) throws IOException {
		
		JOptionPane.showMessageDialog(null,msg,"Conexão",JOptionPane.WARNING_MESSAGE);
		conexao.close();	
	
		
	}
	
	
	public void estadoServidor () {
		
		try {
		
			// Inicia socket do servidor
			serverSocket = new ServerSocket(5000,1); 
		
			JOptionPane.showMessageDialog(null,"Servidor:"+serverSocket.getInetAddress()+" Porta:"+serverSocket.getLocalPort(),"Servidor",JOptionPane.PLAIN_MESSAGE);
			
			esperaConexao();
			pegaFluxo();
			forneceArquivo();
			fechaConexao("Arquivo Transferido!");
			
		} catch (IOException ioException) { JOptionPane.showMessageDialog(null,"Erro: "+ ioException.toString(),"Erro!",JOptionPane.ERROR_MESSAGE); }
			
	}
	

	public static void main(String[] args) throws IOException {
		
		ServidorArquivo aplicacao = new ServidorArquivo();
		
		aplicacao.estadoServidor();	
		
		 
	}	
		

}	
			


