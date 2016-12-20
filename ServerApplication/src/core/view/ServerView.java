package core.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import core.execute.Servidor;

public class ServerView extends JFrame {

	private JPanel contentPane;
	Servidor server;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerView frame = new ServerView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 322, 204);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDigiteAPorta = new JLabel("Digite a porta do servidor :");
		lblDigiteAPorta.setBounds(32, 52, 182, 14);
		contentPane.add(lblDigiteAPorta);

		textField = new JTextField();
		textField.setBounds(185, 49, 77, 20);
		textField.setText("12345");
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel label = new JLabel("");
		label.setBounds(101, 127, 161, 14);
		contentPane.add(label);

		JButton btnLigarServidor = new JButton("Ligar Servidor");
		btnLigarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int porta = Integer.parseInt(textField.getText());
					server = new Servidor(porta);
					server.ligarServidor();
					setVisible(false);

				} catch (Exception e1) {
					label.setText("Porta Inválida");
				}

			}
		});
		btnLigarServidor.setBounds(32, 93, 230, 23);
		contentPane.add(btnLigarServidor);

	}
}
