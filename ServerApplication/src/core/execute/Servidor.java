package core.execute;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import core.model.Cliente;
import core.util.TaskService;
import core.view.ServerInfoView;

/**
 * 
 * @author Irvayne Matheus and Juarez Guimarães
 *
 */
public class Servidor {

	private int porta;
	private Socket cliente;
	private ServerSocket servidor;
	private ServerInfoView view;
	private TaskService clientesSoliciacao;
	private List<Cliente> clientesConectados;
	private long idClientes;

	/**
	 * Construtor do objeto Servidor
	 * 
	 * @param port
	 */
	public Servidor(int port) {
		this.porta = port;
		this.idClientes = 0;
		this.clientesSoliciacao = new TaskService();
		this.clientesConectados = new ArrayList<>();
	}

	/**
	 * Método reponsável por ligar o servidor
	 * 
	 * @throws IOException
	 */
	public void ligarServidor() throws IOException {
		this.servidor = new ServerSocket(porta);
		// chama a thread principal fica esperando a solicitação de novas
		// conexões
		new Thread(threadPrincipal).start();

		// configurações da tela do servidor
		view = new ServerInfoView();
		view.lblNewLabel.setText(porta + "");
		view.setVisible(true);
		view.server = this;
	}

	/**
	 * Thread Principal Ela funciona até que a conexão do servidor seja
	 * cancelada
	 */
	private Runnable threadPrincipal = new Runnable() {
		public void run() {

			System.out.println("Porta " + porta + " aberta!");
			System.out.println("Servidor ligado");
			// inicia a thread resposável por gerenciar conexões ociosas
			new Thread(threadOperariaConexao).start();

			while (!servidor.isClosed()) {

				try {
					// solicitação de nova conexão
					cliente = servidor.accept();
					System.out.println("Nova conexão com o cliente de ip " + cliente.getInetAddress().getHostAddress());

					// instância cliente e o adiciona a lista de conectados
					Cliente clienteAux = new Cliente(idClientes++, null, cliente, new Date().getTime());
					clientesConectados.add(clienteAux);

					// adiciona o cliente que
					clientesSoliciacao.getFila().add(clienteAux);
					new Thread(threadOperariaMensagem).start();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

	};
	/**
	 * Thread Operária Mensagem Ele fica esperando solicitação de envio de
	 * mensagens para poder enviar a todos os clientes conectados
	 */
	private Runnable threadOperariaMensagem = new Runnable() {
		public void run() {

			Scanner s;

			try {
				Cliente clienteAux = clientesSoliciacao.getFirstToFila();
				s = new Scanner(clienteAux.getSocket().getInputStream());
				while (s.hasNextLine() && !servidor.isClosed()) {
					boolean msgIndividual = false;
					try {
						String mensagem = "";

						// na primeira mensagem ele salva essa mensagem no nome
						// do cliente
						if (clienteAux.getNome() == null) {
							for (int i = 0; i < clientesConectados.size(); i++) {
								if (clientesConectados.get(i).getId() == clienteAux.getId()) {
									String msg = s.nextLine();
									clienteAux.setNome(msg);
									clientesConectados.get(i).setNome(msg);
									break;
								}
							}

							for (Cliente aux : clientesConectados) {

								new PrintStream(aux.getSocket().getOutputStream())
										.println("                " + clienteAux.getNome() + " entrou na conversa");

							}

							// caso nao seja, entao funciona normalmente
						} else {

							for (Cliente aux : clientesConectados) {
								if (aux.getId() == clienteAux.getId()) {
									mensagem = s.nextLine();
									break;
								}
							}

							// verifica se a mensagem tem @ + o nome da pessoa.
							// Caso tenha ele envia a mensagem para essa pessoa
							// e para quem enviou
							for (Cliente aux : clientesConectados) {
								if (mensagem.contains("@" + aux.getNome())) {
									new PrintStream(aux.getSocket().getOutputStream())
											.println(clienteAux.getNome() + " - " + new Date().getHours() + ":"
													+ new Date().getMinutes() + " - " + mensagem);
									// new
									// PrintStream(clienteAux.getSocket().getOutputStream()).println("Eu
									// - "+mensagem);
									msgIndividual = true;
									break;
								}
							}

							// caso a mensagem nao seja !c e nem tenha sido
							// enviada para alguem individual
							if (!mensagem.contains("!c") && msgIndividual == false) {
								for (Cliente aux : clientesConectados) {
									if (aux.getId() != clienteAux.getId()) {
										new PrintStream(aux.getSocket().getOutputStream())
												.println(clienteAux.getNome() + " - " + new Date().getHours() + ":"
														+ new Date().getMinutes() + " - " + mensagem);
									}
								}
								// new
								// PrintStream(clienteAux.getSocket().getOutputStream()).println("Eu
								// - "+mensagem);
							}

						}

						// seta a hora da ultima mensagem

						for (Cliente aux : clientesConectados) {
							// verifica qual o cliente pela combinação da tupla
							// ip, porta e seta o a hora da ultima visualização
							if (aux.getSocket().getInetAddress().getHostAddress()
									.equals(clienteAux.getSocket().getInetAddress().getHostAddress())
									&& clienteAux.getSocket().getPort() == aux.getSocket().getPort()) {
								aux.setUltimaVisualizacao(new Date().getTime());
							}
						}

						atualizaLista();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	};
	/**
	 * Thread Operária Conexao Ela remove os clientes que não estão mais
	 * conectados
	 */
	private Runnable threadOperariaConexao = new Runnable() {
		public void run() {
			// roda enquanto o servidor estiver ligado
			while (!servidor.isClosed()) {
				// verifica se possui clientes conectados
				if (clientesConectados.size() != 0) {
					// percorre todos os clientes conectados
					for (int i = 0; i < clientesConectados.size(); i++) {
						// verifica se a hora da ultima mensagem foi mair q 1
						// segundo
						if (new Date().getTime() - clientesConectados.get(i).getUltimaVisualizacao() > 1000) {
							// caso seja, esse cliente é removido da lista
							removeCliente(clientesConectados.get(i));

							clientesConectados.remove(clientesConectados.get(i));

							atualizaLista();
							// se a lista for vazia, sai do loop
							if (clientesConectados.isEmpty()) {
								break;
							}
						}

					}
				}
			}

		}
	};

	/**
	 * Remove o cliente recebido como parametro, e seta na tela dos demais a
	 * informação que ele saiu da conversa
	 * 
	 * @param cliente
	 */
	public void removeCliente(Cliente cliente) {
		for (Cliente aux : clientesConectados) {
			if (aux.getId() != cliente.getId()) {
				try {
					new PrintStream(aux.getSocket().getOutputStream())
							.println("              " + cliente.getNome() + " saiu da conversa");
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Atualiza a lista de clientes conectados na tela do servidor
	 */
	public void atualizaLista() {
		view.list.removeAll();
		for (Cliente aux1 : clientesConectados) {
			view.list.add("Cliente " + aux1.getNome() + " de id " + aux1.getId() + " de ip "
					+ aux1.getSocket().getInetAddress().getHostAddress() + " de porta " + aux1.getSocket().getPort());
		}
	}

	/**
	 * 
	 * @return
	 */
	public ServerSocket getServidor() {
		return servidor;
	}

}