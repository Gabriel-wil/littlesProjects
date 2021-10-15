package com.myproject.cm;

import com.myproject.cm.modelo.Tabuleiro;
import com.myproject.cm.visao.TabuleiroConsole;

public class Aplicacao {

	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(7,7,4);
		
		new TabuleiroConsole(tabuleiro);
		
	}
}
