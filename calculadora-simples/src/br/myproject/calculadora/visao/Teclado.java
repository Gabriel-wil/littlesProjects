package br.myproject.calculadora.visao;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import br.myproject.calculadora.modelo.Memoria;

@SuppressWarnings("serial")
public class Teclado extends JPanel implements ActionListener{

	private final Color COR_CINZA_ESCURO = new Color(68,68,68);
	private final Color COR_CINZA_CLARO = new Color(97,100,99);
	private final Color COR_LARANJA = new Color(242,163,60);
	
	public Teclado() {
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(layout);
		
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		
		//Linha1
		c.gridwidth = 2;
		adicioarBotao("AC", COR_CINZA_ESCURO, c,0,0);
		c.gridwidth = 1;
		adicioarBotao("%", COR_CINZA_ESCURO, c,2,0);
		adicioarBotao("/", COR_LARANJA, c,3,0);
		
		//Linha2
		adicioarBotao("7", COR_CINZA_CLARO,c,0,1);
		adicioarBotao("8", COR_CINZA_CLARO,c,1,1);
		adicioarBotao("9", COR_CINZA_CLARO,c,2,1);
		adicioarBotao("*", COR_LARANJA,c,3,1);
		
		//Linha3
		adicioarBotao("6", COR_CINZA_CLARO,c,0,2);
		adicioarBotao("5", COR_CINZA_CLARO,c,1,2);
		adicioarBotao("4", COR_CINZA_CLARO,c,2,2);
		adicioarBotao("+", COR_LARANJA,c,3,2);
		
		//Linha4
		adicioarBotao("3", COR_CINZA_CLARO,c,0,3);
		adicioarBotao("2", COR_CINZA_CLARO,c,1,3);
		adicioarBotao("1", COR_CINZA_CLARO,c,2,3);
		adicioarBotao("-", COR_LARANJA,c,3,3);
		
		//Linha5
		c.gridwidth = 2;
		adicioarBotao("0", COR_CINZA_CLARO,c,0,4);
		c.gridwidth = 1;
		adicioarBotao(",", COR_CINZA_CLARO,c,2,4);
		adicioarBotao("=", COR_LARANJA,c,3,4);
	}

	private void adicioarBotao(String texto, Color cor, GridBagConstraints c, int i, int j) {
		c.gridx = i;
		c.gridy = j;
		Botao botao = new Botao(texto,cor);
		botao.addActionListener(this);
		add(botao,c);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton botao = (JButton) e.getSource();
			Memoria.getInstancia().processarValor(botao.getText());
		}
	}
}
