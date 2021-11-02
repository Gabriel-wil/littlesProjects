package br.myproject.calculadora.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

	private enum TipoComando {
		ZERAR,NUMERO,DIVISAO,MULTIPLICAR,SUBTRACAO,SOMA,IGUAL,VIRGULA
	};
	
	private static Memoria instancia = new Memoria();
	
	private TipoComando ultimaOperacao = null;
	private boolean substituir = false;
	private String text = "";
	private String buffer = "";
	
	private final List<MemoriaObserver> observers = 
			new ArrayList<>();

	private Memoria() {
		
	}
	
	public static Memoria getInstancia() {
		return instancia;
	}
	
	public String getText() {
		return text.isEmpty()?"0" : text;
	}
	
	public void adicionarObserver(MemoriaObserver o) {
		observers.add(o);
	}
	
	public void processarValor(String valor) {
		TipoComando tipoComando = detectarTipoComando(valor);
		
		if(tipoComando == null) {
			return;
		}else if(tipoComando == TipoComando.ZERAR) {
			text = "";
			buffer = "";
			substituir = false;
			ultimaOperacao = null;
		}else if(tipoComando == TipoComando.NUMERO || 
					tipoComando == TipoComando.VIRGULA) {
			text = substituir ? valor : text + valor;
			substituir = false;
		}
		else {
			substituir = true;
			text = obterResultadoOperacao();
			buffer = text;
			ultimaOperacao = tipoComando;
		}
		
		observers.forEach(o -> o.valorAlterado(getText()));

	}

	private String obterResultadoOperacao() {
		if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return text;
		}
		
		double numBuffer = Double.parseDouble(buffer.replace(",", "."));
		double numText = Double.parseDouble(text.replace(",", "."));
		
		double result = 0;
		
		if(ultimaOperacao == TipoComando.SOMA) {
			result = numBuffer + numText;
		}
		else if(ultimaOperacao == TipoComando.SUBTRACAO) {
			result = numBuffer - numText;
		}
		else if(ultimaOperacao == TipoComando.MULTIPLICAR) {
			result = numBuffer * numText;
		}
		else if(ultimaOperacao == TipoComando.DIVISAO) {
			result = numBuffer / numText;
		}
		
		String resultToString = Double.toString(result).replace(".",",");
		boolean inteiro = resultToString.endsWith(",0");
		return inteiro? resultToString.replace(",0", ""):
			resultToString;
	}

	private TipoComando detectarTipoComando(String valor) {
		if(valor.isEmpty() && valor == "0") {
			return null;
		}
		
		try {
			Integer.parseUnsignedInt(valor);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {
			// Quando não for número
			if("AC".equals(valor)) {
				return TipoComando.ZERAR;
			}else if("/".equals(valor)) {
				return TipoComando.DIVISAO;
			}else if("*".equals(valor)) {
				return TipoComando.MULTIPLICAR;
			}else if("+".equals(valor)) {
				return TipoComando.SOMA;
			}else if("-".equals(valor)) {
				return TipoComando.SUBTRACAO;
			}else if("=".equals(valor)) {
				return TipoComando.IGUAL;
			}else if(",".equals(valor) && !text.contains(",")) {
				return TipoComando.VIRGULA;
			}
		
		return null;
		}	
	}
}
