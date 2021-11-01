package com.myproject.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class Tabuleiro implements CampoObservador {

	private final int linhas;
	private final int colunas;

	private final int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observers = new ArrayList<>();
	
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampo();
		associarVizinhos();
		sortearMinas();
	}
	
	public void paraCada(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
	
	public int getLinhas() {
		return linhas;
	}
	
	public int getColunas() {
		return colunas;
	}
	
	public void adicionarObservador(Consumer<ResultadoEvento> observer) {
		observers.add(observer);
	}

	private void notificarObservador(boolean resultado) {
		observers.stream()
			.forEach(o -> o.accept(new ResultadoEvento(resultado)));
	}
	
	public void abrir(int linha,int coluna) {
			campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
	}

	public void alterarMarcacao(int linha,int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alterarMarcacao());
	}

	private void gerarCampo() {
		for(int l = 0; l < linhas; l++) {
			for(int c = 0; c < colunas; c++) {
				Campo campo = new Campo(l,c);
				campo.adicionarObeserver(this);
				campos.add(campo);
			}
		}
	}
	
	private void associarVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinhos(c2);
			}
		}
	}

	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		}while(minasArmadas < minas);
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	public void eventoOcorreu(Campo campo, CampoEvento event) {
		if(event == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservador(false);
		}else if (objetivoAlcancado()){
			notificarObservador(true);
		}
	}
	
	private void mostrarMinas() {
		campos.stream()
		.filter(c -> c.isMinado())
		.filter(c -> !c.isMarcado())
		.forEach(c -> c.setAberto(true));
	}
	
}
