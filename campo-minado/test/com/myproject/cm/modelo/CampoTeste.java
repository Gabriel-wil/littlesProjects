package com.myproject.cm.modelo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myproject.cm.excecao.ExplosionException;

public class CampoTeste {

	private Campo campo;
	
	@BeforeEach
	void iniciarTeste() {
		campo = new Campo (3,3);
	}
	
	@Test
	void testeVizinhoValidoDistanciaDireita1() {
		Campo vizinho = new Campo(3,4);
		boolean result = campo.adicionarVizinhos(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoValidoDistanciaEsquerda1() {
		Campo vizinho = new Campo(3,2);
		boolean result = campo.adicionarVizinhos(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoValidoDistanciaCima1() {
		Campo vizinho = new Campo(2,3);
		boolean result = campo.adicionarVizinhos(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoValidoDistanciaBaixo1() {
		Campo vizinho = new Campo(4,3);
		boolean result = campo.adicionarVizinhos(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoValidoDistancia2() {
		Campo vizinho = new Campo(4,4);
		boolean result = campo.adicionarVizinhos(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoInvalido() {
		Campo vizinho = new Campo(5,4);
		boolean result = campo.adicionarVizinhos(vizinho);
		assertFalse(result);
	}

	@Test
	void testeValorPadraoMarcado() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAlterarMarcacao() {
		campo.alterarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlterarMarcacaoDuasVezes() {
		campo.alterarMarcacao(); 
		campo.alterarMarcacao();
		assertFalse(campo.isMarcado());
	}

	@Test
	void testeAbrirCampoNMarcadoNMinado() {
		assertTrue(campo.abrir());
	}
	
	@Test
	void testeAbrirCampoNMinadoMarcado(){
		campo.alterarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirCampoMinadoMarcado() {
		campo.alterarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirCampoMinadoNMarcado() {
		campo.minar();
		
		assertThrows(ExplosionException.class, () -> {
			campo.abrir();
		});
	}
	
	@Test
	void testeAbrirComVizinho1() {
		Campo campo11 = new Campo(1,1);
		Campo campo22 = new Campo(2,2);
		campo22.adicionarVizinhos(campo11);
		
		campo.adicionarVizinhos(campo22);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void testeAbrirComVizinho2() {
		Campo campo11 = new Campo(1,1);
		Campo campo12 = new Campo(1,2);
		campo12.minar();
		
		Campo campo22 = new Campo(2,2);
		campo22.adicionarVizinhos(campo11);
		campo22.adicionarVizinhos(campo12);
		
		campo.adicionarVizinhos(campo22);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isFechado());
	}
}
