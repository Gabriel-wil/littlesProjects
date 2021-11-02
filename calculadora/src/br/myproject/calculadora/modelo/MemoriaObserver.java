package br.myproject.calculadora.modelo;

@FunctionalInterface
public interface MemoriaObserver {
	
	void valorAlterado(String novoValor);
}
