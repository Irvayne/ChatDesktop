package core.model;

import java.net.Socket;

public class Cliente {
	
	long id;
	String nome;
	Socket socket;
	long ultimaVisualizacao;
	
	
	public Cliente(long id,String nome, Socket socket, long ultimaVisualizacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.socket = socket;
		this.ultimaVisualizacao = ultimaVisualizacao;
	}

	public long getId(){
		return id;
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public long getUltimaVisualizacao() {
		return ultimaVisualizacao;
	}

	public void setUltimaVisualizacao(long ultimaVisualizacao) {
		this.ultimaVisualizacao = ultimaVisualizacao;
	}
	
	
}
