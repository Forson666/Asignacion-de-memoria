package logica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import javax.swing.JPanel;

public class Memoria {
	// Declaro los atributos de la clase
	private int numEspacios;
	private Espacio[] espacios;
	private int tama�o;
	
	// Metodo constructor parametrico de la clase
	public Memoria(Espacio[] espacios) {
		this.numEspacios = espacios.length;
		this.espacios = espacios;
		tama�o = 0;
		for (int i = 0; i < numEspacios; i++) {
			tama�o += espacios[i].getTama�o();
		}
	}
	
	// Metodo para a�adir por first fit
	public int firstFit(Variable var) {
		for (int i = 0; i < numEspacios; i++) {
			if (espacios[i].getMemDisponible() >= var.getTama�o()) {
				espacios[i].add(var);
				espacios[i].setMemUsada(espacios[i].getMemUsada() + var.getTama�o());
				espacios[i].setMemDisponible(espacios[i].getMemDisponible() - var.getTama�o());
				return i;
			}
		}
		return -1;
	}
	
	// Metodo para a�adir por next fit
	public int nextFit(Variable var, int ulpos){
		for(int i = ulpos; i < 2*numEspacios; i++){
			if (espacios[i % numEspacios].getMemDisponible() >= var.getTama�o()) {
				espacios[i % numEspacios].add(var);
				espacios[i % numEspacios].setMemUsada(espacios[i % numEspacios].getMemUsada() + var.getTama�o());
				espacios[i % numEspacios].setMemDisponible(espacios[i % numEspacios].getMemDisponible() - var.getTama�o());
				return i % numEspacios;
			}
		}
		return -1;
	}

	// Metodo para a�adir por best fit
	public int bestFit(Variable var){
		int mejorPos = -1; 
		for (int i = 0; i < numEspacios; i++) {
			if (espacios[i].getMemDisponible() >= var.getTama�o() && mejorPos == -1) {
				mejorPos = i;
			}else if (espacios[i].getMemDisponible() >= var.getTama�o() && espacios[i].getMemDisponible() - var.getTama�o() < espacios[mejorPos].getMemDisponible() - var.getTama�o()){
				mejorPos = i;
			}
		}
		if (mejorPos != -1) {
			espacios[mejorPos].add(var);
			espacios[mejorPos].setMemUsada(espacios[mejorPos].getMemUsada() + var.getTama�o());
			espacios[mejorPos].setMemDisponible(espacios[mejorPos].getMemDisponible() - var.getTama�o());
			return mejorPos;
		}
		return -1;
	}
	
	// Metodo para a�adir por worst fit
	public int worstFit(Variable var){
		int peorPos = -1; 
		for (int i = 0; i < numEspacios; i++) {
			if (espacios[i].getMemDisponible() >= var.getTama�o() && peorPos == -1) {
				peorPos = i;
			}else if (espacios[i].getMemDisponible() >= var.getTama�o() && espacios[i].getMemDisponible() - var.getTama�o() > espacios[peorPos].getMemDisponible() - var.getTama�o()){
				peorPos = i;
			}
		}
		if (peorPos != -1) {
			espacios[peorPos].add(var);
			espacios[peorPos].setMemUsada(espacios[peorPos].getMemUsada() + var.getTama�o());
			espacios[peorPos].setMemDisponible(espacios[peorPos].getMemDisponible() - var.getTama�o());
			return peorPos;
		}
		return -1;
	}
	
	// Metodo para graficar la memoria
	public JPanel graficarMem (){
		JPanel panel = new JPanel(){
			@Override
			public void paint(Graphics g) {
				// dibujo la memoria
				int xpoints[] = { 20, 20, 140, 140};
				int ypoints[] = { 0, 15 * tama�o,15 * tama�o, 0 };
				int npoints = 4;
				g.drawPolygon(xpoints, ypoints, npoints);
				
				// dibujo las lineas de separacion de los espacios de la memoria
				int altura = 0;
				int sumaEspacios = 0;
				g.drawString(sumaEspacios + "", 0, 15);
				for(int i = 0; i < numEspacios; i++){
					altura += espacios[i].getTama�o() * 15;
					sumaEspacios += espacios[i].getTama�o();
					g.drawString(sumaEspacios + "", 0, altura);
					g.drawString(espacios[i].getTama�o() + "", 140, altura - (espacios[i].getTama�o() * 15) / 2);
					g.drawLine(20, altura, 140, altura);
				}
				
				// dibujo las variables de los espacios
				int altura1 = 0;
				BasicStroke Bs  = new BasicStroke(2f);
				Graphics2D g2D = (Graphics2D) g;
				g2D.setStroke(Bs);
				for (int i = 0; i < numEspacios; i++) {
					int tamV = 0;
					int tamE = espacios[i].getTama�o() * 15;
					int alturaV = altura1; 
					for (int j = 0; j < espacios[i].getVarEnMem().size(); j++) {
						tamV = espacios[i].getVarEnMem().get(j).getTama�o() * 15;
						int puntosY[] = { alturaV, alturaV + tamV, alturaV + tamV, alturaV};
						g2D.setColor(espacios[i].getVarEnMem().get(j).getColor());
						if (espacios[i].getVarEnMem().get(j).getTama�o() > 1) {
							g2D.drawString(espacios[i].getVarEnMem().get(j).getNombre() + " " + espacios[i].getVarEnMem().get(j).getTama�o() + "M", 90, alturaV + (tamV / 2));
						}else {
							g2D.drawString(espacios[i].getVarEnMem().get(j).getNombre() + " " + espacios[i].getVarEnMem().get(j).getTama�o() + "M", 90, alturaV + tamV);
						}
						
						g2D.drawPolygon(xpoints, puntosY, npoints);
						alturaV += tamV; 
					}
					altura1 += tamE;
				}
				g2D.setColor(Color.BLACK);
			}
		};
		
		panel.setPreferredSize(new Dimension(160, 15*this.getTama�o() + 10));
		
		return panel;
	}
	
	// Metodos accesores y modificadores
	public int getNumEspacios() {
		return numEspacios;
	}

	public void setNumEspacios(int numEspacios) {
		this.numEspacios = numEspacios;
	}

	public Espacio[] getEspacios() {
		return espacios;
	}

	public void setEspacios(Espacio[] espacios) {
		this.espacios = espacios;
	}

	public int getTama�o() {
		return tama�o;
	}

	public void setTama�o(int tama�o) {
		this.tama�o = tama�o;
	}
}
