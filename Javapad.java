// Daniel Burnier de Castro - JavaPad
import java.io.*;
import javax.swing.*;  
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.filechooser.*;
import javax.swing.JTextArea;
import java.text.*;

class Javapad extends JFrame {
	private JTextArea texto;
	
	JFrame frame = new JFrame();

	private JFileChooser fc  = null;
	private int returnVal = 0;

	private FileInputStream  inputStream  = null;
	private PrintWriter outputStream = null;
	
	private StringBuffer  buffer = null;
   	private int data = 0;
	
	private String  text = null;

	private String filename = null;

	File file;


	private JMenu menuArquivo, menuEditar, /*menuPesquisar,*/ menuAjuda;
	
	private JMenuItem itemNovo, itemAbrir, itemSalvar, itemImprimir, itemSair, 			  
			  //itemRecortar, itemCopiar, itemColar, itemExcluir,
			  itemSelTudo, itemHoraData, itemFonte,
			  /*itemLocalizar, */ itemSobre;

	
	private String s;  		// Auxiliar
	private String achaPalavra;	// Para procurar e achar palavras
	 
	public Javapad() 
	{
		
		setTitle("JAVA Editor - Um Editor Simples feito em JAVA");

		Container c = getContentPane();
		
		texto = new JTextArea();
		c.add(texto);
		c.add( new JScrollPane( texto ) );


        	JMenuBar bar = new JMenuBar();  //Cria a barra de menus
        	setJMenuBar(bar);		// Configura a barra de menus para JFrame

        	//Cria menu Arquivo e possibilita sua escolha com "ALT+A"
		JMenu menuArquivo = new JMenu("Arquivo");
       		menuArquivo.setMnemonic('A');
        	
			// Cria subMenus de Arquivo
			
			JMenuItem itemNovo = new JMenuItem("Novo");
        		itemNovo.setMnemonic('N');
			itemNovo.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{
    					texto.selectAll ();
      					texto.replaceSelection ( null );
      					texto.repaint ();
   
	 			}
			});	
			menuArquivo.add(itemNovo);
			
			



			
			JMenuItem itemAbrir = new JMenuItem("Abrir");
        		itemAbrir.setMnemonic('A');
			itemAbrir.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{

      					fc = new JFileChooser ();
      					returnVal   = fc.showOpenDialog (texto);
         
      					if ( JFileChooser.APPROVE_OPTION == returnVal )
      					{
         					file = fc.getSelectedFile ();

					         try
         					{
            						inputStream = new FileInputStream ( file );
        					 }

					         catch ( FileNotFoundException ex )
         					{
          					  	System.out.println ( ex.getMessage () );
            				          	System.exit ( 1 );
         					}
		
 			        buffer = new StringBuffer ();

			        try
         			{         
           				data = inputStream.read ();
         			}

         			catch ( IOException ex )
         			{
            				System.out.println ( ex.getMessage () );
            				System.exit ( 1 );
         			}

         			while ( -1 != data )
         			{
            			buffer.append ( ( char ) data );

            			try
            			{
               				data = inputStream.read ();
            			}
  
            			catch ( IOException ex )
            			{
               				System.out.println ( ex.getMessage () );
               				System.exit ( 1 );
            			}
         			}

         			texto.setText ( buffer.toString () );

         			try
         			{
            				inputStream.close ();
         			}

         			catch ( IOException ex )
         			{
            				System.out.println ( ex.getMessage () );
            				System.exit ( 1 );
        			 }	
      			}
			}
			});				

			menuArquivo.add(itemAbrir);	





			JMenuItem itemSalvar = new JMenuItem("Salvar");
        		itemSalvar.setMnemonic('S');
			itemSalvar.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{                		  
					fc = new JFileChooser();
					if ( null != file )
         				fc.setSelectedFile ( file );
      				
	
      					returnVal = fc.showSaveDialog (texto);
         				if ( JFileChooser.APPROVE_OPTION == returnVal )
      					{
         					file = fc.getSelectedFile ();

        	 				try {
	             					outputStream = new PrintWriter ( 
                   					new FileOutputStream ( fc.getSelectedFile ().getName () ) );
         					}

        	 				catch ( FileNotFoundException ex ) {
	            					System.out.println ( ex.getMessage () );
            						System.exit ( 1 );
         					}
						text = texto.getText ();
         					outputStream.print ( text );
						outputStream.close ();
      					}         					 					 
        			}
			});	
			menuArquivo.add(itemSalvar);	
		



			//menuArquivo.addSeparator(); //Separador de menu


			//JMenuItem itemImprimir = new JMenuItem("Imprimir");
        		//itemImprimir.setMnemonic('I');
			//menuArquivo.add(itemImprimir);	

			
			// SAIR DO PROGRAMA
			menuArquivo.addSeparator(); //Separador de menu
			JMenuItem itemSair = new JMenuItem("Sair");
        		itemSair.setMnemonic('R');
			itemSair.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{
                			System.exit( 0 );
            			}
        		});
			menuArquivo.add(itemSair);	



		//Cria menu Editar e possibilita sua escolha com "ALT+E" 
		JMenu menuEditar = new JMenu("Editar");
        	menuEditar.setMnemonic('E');        
        	
			// Cria subMenus de Editar	

			

			//RECORTAR TRECHO DE TEXTO
			JMenuItem itemRecortar = new JMenuItem("Recortar");
        		itemRecortar.setMnemonic('R');
			itemRecortar.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{
                			texto.cut();
				}
			});
			menuEditar.add(itemRecortar);	

			

			//COPIAR TRECHO DE TEXTO
			JMenuItem itemCopiar = new JMenuItem("Copiar");
        		itemCopiar.setMnemonic('C');
			itemCopiar.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{
                			texto.copy();
				}
			});
			menuEditar.add(itemCopiar);	
		
		
			
			//COLAR TRECHO DE TEXTO
			JMenuItem itemColar = new JMenuItem("Colar");
        		itemColar.setMnemonic('O');
			itemColar.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{        			
					texto.paste();
				}
			});
			menuEditar.add(itemColar);	
			 


			JMenuItem itemExcluir = new JMenuItem("Excluir");
        		itemExcluir.setMnemonic('X');
			itemExcluir.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{        			
					texto.replaceSelection ( null );	
				}
			});		
			menuEditar.add(itemExcluir); 

			

			menuEditar.addSeparator(); //Separador de menu			

			


			//SELECIONAR TUDO
			JMenuItem itemSelTudo = new JMenuItem("Selecionar Tudo");
        		itemSelTudo.setMnemonic('T');
			itemSelTudo.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{        			
					texto.selectAll(); 	
				}
			});
			menuEditar.add(itemSelTudo);	



			//FORNECE A DATA E HORA ATUAIS
			JMenuItem itemHoraData = new JMenuItem("Hora / Data");
        		itemHoraData.setMnemonic('R');
			itemHoraData.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{        			
					Date data = new Date(); 	
						
					SimpleDateFormat formataData = 
						new SimpleDateFormat ("dd/MM/yy");
					SimpleDateFormat formataHora = 
						new SimpleDateFormat("HH:mm");

					String dataStr = formataData.format(data);
					String horaStr = formataHora.format(data);
				
					texto.append(horaStr);
					texto.append(" ");
					texto.append(dataStr);

				}
			});
			menuEditar.add(itemHoraData);	




			//menuEditar.addSeparator(); //Separador de menu	


			/*JMenuItem itemFonte = new JMenuItem("Definir fonte...");
        		itemFonte.setMnemonic('F');
			itemFonte.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{        			
					 
				}		

			});
			menuEditar.add(itemFonte); */						


	/*	Cria menu Pesquisar e possibilita sua escolha com "ALT+P"
		JMenu menuPesquisar = new JMenu("Pesquisar");
        	menuPesquisar.setMnemonic('P');
        	
			// Cria subMenus de Pesquisar
			
			


			//LOCALIZAR.....
			JMenuItem itemLocalizar = new JMenuItem("Localizar...");
        		itemLocalizar.setMnemonic('L');
			menuPesquisar.add(itemLocalizar);									
	*/
			


		//Cria menu Ajuda e possibilita sua escolha com "ALT+U"
		JMenu menuAjuda = new JMenu("Ajuda");
        	menuAjuda.setMnemonic('U');
	
			
			
			// SOBRE O PROGRAMA
			JMenuItem itemSobre = new JMenuItem("Sobre o JAVA Editor");
        		itemSobre.setMnemonic('B');
			itemSobre.addActionListener(new ActionListener() {
 	         		public void actionPerformed(ActionEvent e)
       		   		{
                			JOptionPane.showMessageDialog(null, 
					"Editor de Texto Simples\n Criado por\n Daniel Burnier de Castro");
            			}
        		});
			menuAjuda.add(itemSobre);	


		
		bar.add(menuArquivo);   //Adiciona menu Arquivo à barra de menu
        	
		bar.add(menuEditar);    //Adiciona menu Editar à barra de menu
        	
		//bar.add(menuPesquisar); //Adiciona menu Pesquisar à barra de menu
		
		bar.add(menuAjuda);     //Adiciona menu Ajuda à barra de menu
        	
		
		 	 
		 
	}

 
	public static void main( String args[] )
	{
		Javapad i = new Javapad();
		
		i.setSize(600, 400);
		i.show();			
		i.addWindowListener(
			new WindowAdapter() {
				public void windowClosing( WindowEvent e )
				{
					System.exit( 0 );
				}
			}
		);
		 
	}
	 
} //Fim


 