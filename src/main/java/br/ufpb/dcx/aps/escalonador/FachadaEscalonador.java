package br.ufpb.dcx.aps.escalonador;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FachadaEscalonador {

	private final int quantum;
	private int tick;
	private final TipoEscalonador tipoEscalonador;
	private final Queue<String> listaProcesso;
	private String rodando;
	private final ArrayList<String> processoBloqueado;
	private int controlador;
	private String aFinalizar;

	public FachadaEscalonador(TipoEscalonador tipoEscalonador) {
		this.quantum = 3;
		this.tick = 0;
		this.tipoEscalonador = tipoEscalonador;
		this.listaProcesso = new LinkedList<>();
		this.processoBloqueado = new ArrayList<>();
	}

	public FachadaEscalonador(TipoEscalonador roundrobin, int quantum) {
		this.quantum = quantum;
		this.tick = 0;
		this.tipoEscalonador = roundrobin;
		this.listaProcesso = new LinkedList<>();
		this.processoBloqueado = new ArrayList<>();
	}

	public String getStatus() {

		String resultado = "";

		resultado += "Escalonador " + this.tipoEscalonador + ";";

		resultado += "Processos: {";

		if (this.rodando != null) {
			resultado += "Rodando: " + this.rodando;

		}
		if (this.listaProcesso.size() > 0) {
			if (this.rodando != null) {
				resultado += ", ";
			}
			resultado += "Fila: " + this.listaProcesso.toString();
			if (this.processoBloqueado.size() > 0) {
				resultado += ", Bloqueados: " + this.processoBloqueado.toString();
			}

		}
		resultado += "};Quantum: " + this.quantum + ";";

		resultado += "Tick: " + this.tick;

		return resultado;
	}

	public void tick() {
		this.tick++;
		if (this.controlador > 0 && (this.controlador + this.quantum) == this.tick) {
			this.listaProcesso.add(this.rodando);
			this.rodando = this.listaProcesso.poll();
			this.controlador = this.tick;
		}

		if (this.aFinalizar != null) {
			if (this.rodando == this.aFinalizar) {
				this.rodando = null;

			} else {
				this.listaProcesso.remove(this.aFinalizar);
			}
		}

		if (this.rodando == null) {
			if (this.listaProcesso.size() != 0) {
				this.rodando = this.listaProcesso.poll();
				if (this.listaProcesso.size() > 0) {
					this.controlador = this.tick;
				}

			}
		}

		if (this.controlador == 0 && this.rodando != null && this.listaProcesso.size() > 0) {
			this.controlador = this.tick;
		}

	}

	public void adicionarProcesso(String nomeProcesso) {
		this.listaProcesso.add(nomeProcesso);

	}

	public void adicionarProcesso(String nomeProcesso, int prioridade) {
	}

	public void finalizarProcesso(String nomeProcesso) {
		this.aFinalizar = nomeProcesso;
	}

	public void adicionarProcessoTempoFixo(String nomeProcesso, int duracao) {
	}

	public void bloquearProcesso(String nomeProcesso) {
		for (int k = 0; k < this.listaProcesso.size(); k++) {
			if (this.listaProcesso.size() > 0) {
				this.listaProcesso.remove(nomeProcesso);
				this.processoBloqueado.add(nomeProcesso);
			}
		}
	}

	public void retomarProcesso(String nomeProcesso) {

	}
}
