package Interface;

import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import controle.Cliente;

import javax.swing.JLabel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class PedirIP {

	private JFrame frame;
	private JTextField textField;
	private JTextField txtJuarez;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PedirIP window = new PedirIP();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PedirIP() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()  {
		frame = new JFrame();
		frame.setBounds(100, 100, 368, 168);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setText("localhost");
		textField.setBounds(79, 21, 238, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(37, 24, 46, 14);
		frame.getContentPane().add(lblIp);

		txtJuarez = new JTextField();
		txtJuarez.setText("Juarez");
		txtJuarez.setBounds(79, 53, 238, 20);
		frame.getContentPane().add(txtJuarez);
		txtJuarez.setColumns(10);

		JLabel lblNome = new JLabel("Nome:\r\n");
		lblNome.setBounds(37, 59, 46, 14);
		frame.getContentPane().add(lblNome);

		JButton btnNewButton = new JButton("Conectar\r\n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				List list = new List();
				list.setBounds(10, 10, 414, 361);
				frame.setVisible(false);
				// JanelaChat.main(args);
				Cliente cliente = new Cliente(textField.getText(), 12345,list);
				
				try {
					cliente.executa(txtJuarez.getText());
					JanelaChat view = new JanelaChat(cliente,list,txtJuarez.getText());
					view.frame.setVisible(true);
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		
		
		btnNewButton.setBounds(142, 84, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}
}
