package com.myproject.cm.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.myproject.cm.modelo.Campo;
import com.myproject.cm.modelo.CampoEvento;
import com.myproject.cm.modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton 
	implements CampoObservador, MouseListener{

	
	
	private final Color BG_PADRAO = new Color(192,192,192);
	private final Color BG_MARCAR = new Color(8,179,247);
	private final Color BG_EXPLODIR = new Color(189,66,68);
	private final Color BG_TEXT = new Color(0,100,0);
	
	private Campo campo;
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		
		
		addMouseListener(this);
		campo.adicionarObeserver(this);
	}
	
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		switch(evento) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default:
			aplicarEstiloPadrao();
		}
	}

	
	
	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLODIR);
		setForeground(Color.WHITE);
		setText("X");
		
	}

	private void aplicarEstiloMarcar() {
		if(campo.isMarcado()) {
			setBackground(BG_MARCAR);
			setForeground(Color.BLACK);
			setText("M");
		}else {
			setBackground(BG_PADRAO);
			setText("");
		}
	}

	private void aplicarEstiloAbrir() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		if(campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			return;
		}
		
		switch (campo.minasVizinhanca()) {
		case 1:
			setForeground(BG_TEXT);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		
		SwingUtilities.invokeLater(()-> {
			repaint();
			validate();
		});
		
		String valor = !campo.vizinhancaSegura()?
				campo.minasVizinhanca() + "":"";
		setText(valor);
	}
	
	//Interface MOUSE Events
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			campo.abrir();
		}else {
			campo.alterarMarcacao();
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
