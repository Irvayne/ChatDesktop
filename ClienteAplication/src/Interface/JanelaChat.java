package Interface;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Handler;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import controle.Cliente;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.HeadlessException;

public class JanelaChat {

	public JFrame frame;
	private JTextField textField_1;
	Cliente cliente;
	public List list;
	String nome;
	
	public JanelaChat(Cliente cliente, List list2,String nomeCliente) throws UnknownHostException, IOException {
		
		nome = nomeCliente;
		this.cliente = cliente;
		list = list2;
		initialize();
	}

	private void initialize() throws UnknownHostException, IOException {
		//////////////////////////
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 468);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Chat do "+nome);

		
		textField_1 = new JTextField();
		textField_1.setEditable(true);
		textField_1.setBounds(10, 377, 325, 41);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		

		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				cliente.janela = list;
				try {
					
					if(textField_1.getText().equals("") || textField_1.getText().equals("!c"))
						return;
					cliente.janela.add("Eu"+" - "+new Date().getHours()+":"+new Date().getMinutes()+" - "+textField_1.getText());
					cliente.executa(textField_1.getText());
					textField_1.setText("");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(335, 377, 89, 41);
		frame.getContentPane().add(btnNewButton);
		


		frame.getContentPane().add(list);

	}
}
