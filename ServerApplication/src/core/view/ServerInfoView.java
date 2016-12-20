package core.view;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import core.execute.Servidor;

public class ServerInfoView extends JFrame {

	private JPanel contentPane;
	public JLabel lblNewLabel;
	public List list;
	public Servidor server;

	public ServerInfoView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 322, 291);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblStatusDoServidor = new JLabel("Status do Servidor : LIGADO");
		lblStatusDoServidor.setBounds(10, 25, 169, 14);
		contentPane.add(lblStatusDoServidor);

		JLabel lblPorta = new JLabel("Porta :");
		lblPorta.setBounds(10, 51, 53, 14);
		contentPane.add(lblPorta);

		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(51, 51, 96, 14);
		contentPane.add(lblNewLabel);

		JButton btnDesligarServidor = new JButton("Desligar Servidor");
		btnDesligarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServerView view = new ServerView();
				view.setVisible(true);
				setVisible(false);
				
				try {
					server.getServidor().close();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDesligarServidor.setBounds(33, 206, 230, 23);
		contentPane.add(btnDesligarServidor);

		list = new List();
		list.setBounds(10, 86, 286, 114);
		contentPane.add(list);

	}
}
