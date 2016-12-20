package core.util;

import java.util.LinkedList;
import java.util.Queue;

import core.model.Cliente;

public class TaskService {
	private Queue<Cliente> fila;
	
	public TaskService() {
		this.setFila(new LinkedList<>());
	}

	public Queue<Cliente> getFila() {
		return fila;
	}

	public void setFila(Queue<Cliente> fila) {
		this.fila = fila;
	}
	
	public Cliente getFirstToFila() {
		return this.fila.poll();
	}
	

}
