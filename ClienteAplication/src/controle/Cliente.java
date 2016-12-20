package controle;

import java.awt.List;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 
 * @author Irvayne Matheus and Juarez Guimarães
 *
 */
public class Cliente {

	private String host;
	private int porta;
	public List janela;
	String msg;
	Socket cliente;

	/**
	 * No construtor, no momento de instanciar o objeto cliente, ele chama as
	 * threads de recebimento de mensagem e de manutanção de conexao
	 * 
	 * @param host
	 * @param porta
	 * @param list
	 */
	public Cliente(String host, int porta, List list) {
		this.host = host;
		this.porta = porta;
		janela = list;
		try {
			// cria o socket
			cliente = new Socket(host, porta);
			System.out.println("O cliente se conectou ao servidor!");
			// chama a thread que espera mensagens do servidor
			new Thread(threadOperariaMensagem).start();
			// chama a thread que mantem a conexao ativa
			new Thread(threadOperariaConexao).start();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Método responsavel por enviar a mensagem
	 * 
	 * @param msg
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void executa(String msg) throws UnknownHostException, IOException {
		this.msg = msg;
		// chama a thread que realiza o envio da mensagem
		new Thread(threadOperaria).start();

	}

	/**
	 * Thread Operaria reponsavel por enviar a mensagem para o servidor ela
	 * envia a mensagem que ta salva na variavel msg
	 */
	private Runnable threadOperaria = new Runnable() {

		public void run() {

			try {
				// msgs do teclado e manda pro servidor
				PrintStream saida = new PrintStream(cliente.getOutputStream());
				saida.println(msg);
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
	};
	/**
	 * Thread Operaria Mensagem Recebe a mensagem do sevidor e imprime na tela
	 */
	private Runnable threadOperariaMensagem = new Runnable() {

		public void run() {
			// recebe msgs do servidor e imprime na tela
			Scanner s;
			try {
				s = new Scanner(cliente.getInputStream());
				while (s.hasNextLine()) {
					String msg = s.nextLine();
					System.out.println(msg);
					janela.add(msg);
				}
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

	};
	/**
	 * Thread Operaria Conexao Ela envia uma mensagem para o servidor a casa 100
	 * milissegundos para que o servidor tenha controle de quais clientes estao
	 * conectados e ativos
	 */
	private Runnable threadOperariaConexao = new Runnable() {

		public void run() {

			while (true) {

				try {
					Thread.sleep(100);
					executa("!c");
				} catch (IOException e) {

					e.printStackTrace();
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}

		}
	};

}