package com.myproject.cm.modelo;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Campo {

	private int linha;
	private int coluna;
	
	private boolean aberto = false;
	private boolean marcado = false;
	private boolean minado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observers = new ArrayList<>();
	
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public void adicionarObeserver(CampoObservador observer) {
		observers.add(observer);
	}
	
	public void notificarObserver(CampoEvento event) {
		observers.stream()
			.forEach(o -> o.eventoOcorreu(this, event));
	}
		
	boolean adicionarVizinhos(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else{
			return false;
		}
	}
	
	public void alterarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
			
			if(marcado) {
				notificarObserver(CampoEvento.MARCAR);
			}else {
				notificarObserver(CampoEvento.DESMARCAR);
			}
		}
	}
		
	public boolean abrir(){
		if(!aberto && !marcado) {
			aberto = true;
			
			if(minado) {
				notificarObserver(CampoEvento.EXPLODIR);
				return true;
			}
			setAberto(true);
			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
			return false;
		}
	}
	
	public boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	public boolean isMarcado() {
		return marcado;
	}

	void minar() {
		minado = true;
	}
	
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean isAberto() {
		return aberto;
	}

	public boolean isFechado() {
		return !aberto;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	void setAberto(boolean aberto) {
		this.aberto = aberto;
		
		if(aberto) {
			notificarObserver(CampoEvento.ABRIR);
		}
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	public int minasVizinhanca() {
		return (int) vizinhos.stream().filter(v-> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObserver(CampoEvento.REINICIAR);
	}
	
}
