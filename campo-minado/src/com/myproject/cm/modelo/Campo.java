package com.myproject.cm.modelo;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.myproject.cm.excecao.ExplosionException;

@SuppressWarnings("unused")
public class Campo {

	private int linha;
	private int coluna;
	
	private boolean aberto = false;
	private boolean marcado = false;
	private boolean minado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
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
	
	void alterarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
		}
	}
		
	boolean abrir(){
		if(!aberto && !marcado) {
			aberto = true;
			
			if(minado) {
				throw new ExplosionException();
			}
			
			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
			return false;
		}
	}
	
	boolean vizinhancaSegura() {
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
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	long minasVizinhanca() {
		return vizinhos.stream().filter(v-> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "x";
		} else if(aberto && minado){
			return "*";
		}else if(aberto && minasVizinhanca() > 0) {
			return Long.toString(minasVizinhanca());
		}else if(aberto) {
			return " ";
		}else {
			return "?";
		}
	}
}
