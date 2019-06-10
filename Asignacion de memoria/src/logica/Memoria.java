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
	private int tamaño;
	
	// Metodo constructor parametrico de la clase
	public Memoria(Espacio[] espacios) {
		this.numEspacios = espacios.length;
		this.espacios = espacios;
		tamaño = 0;
		for (int i = 0; i < numEspacios; i++) {
			tamaño += espacios[i].getTamaño();
		}
	}
	
	// Metodo para añadir por first fit
	public int firstFit(Variable var) {
		for (int i = 0; i < numEspacios; i++) {
			if (espacios[i].getMemDisponible() >= var.getTamaño()) {
				espacios[i].add(var);
				espacios[i].setMemUsada(espacios[i].getMemUsada() + var.getTamaño());
				espacios[i].setMemDisponible(espacios[i].getMemDisponible() - var.getTamaño());
				return i;
			}
		}
		return -1;
	}
	
	// Metodo para añadir por next fit
	public int nextFit(Variable var, int ulpos){
		for(int i = ulpos; i < 2*numEspacios; i++){
			if (espacios[i % numEspacios].getMemDisponible() >= var.getTamaño()) {
				espacios[i % numEspacios].add(var);
				espacios[i % numEspacios].setMemUsada(espacios[i % numEspacios].getMemUsada() + var.getTamaño());
				espacios[i % numEspacios].setMemDisponible(espacios[i % numEspacios].getMemDisponible() - var.getTamaño());
				return i % numEspacios;
			}
		}
		return -1;
	}

	// Metodo para añadir por best fit
	public int bestFit(Variable var){
		int mejorPos = -1; 
		for (int i = 0; i < numEspacios; i++) {
			if (espacios[i].getMemDisponible() >= var.getTamaño() && mejorPos == -1) {
				mejorPos = i;
			}else if (espacios[i].getMemDisponible() >= var.getTamaño() && espacios[i].getMemDisponible() - var.getTamaño() < espacios[mejorPos].getMemDisponible() - var.getTamaño()){
				mejorPos = i;
			}
		}
		if (mejorPos != -1) {
			espacios[mejorPos].add(var);
			espacios[mejorPos].setMemUsada(espacios[mejorPos].getMemUsada() + var.getTamaño());
			espacios[mejorPos].setMemDisponible(espacios[mejorPos].getMemDisponible() - var.getTamaño());
			return mejorPos;
		}
		return -1;
	}
	
	// Metodo para añadir por worst fit
	public int worstFit(Variable var){
		int peorPos = -1; 
		for (int i = 0; i < numEspacios; i++) {
			if (espacios[i].getMemDisponible() >= var.getTamaño() && peorPos == -1) {
				peorPos = i;
			}else if (espacios[i].getMemDisponible() >= var.getTamaño() && espacios[i].getMemDisponible() - var.getTamaño() > espacios[peorPos].getMemDisponible() - var.getTamaño()){
				peorPos = i;
			}
		}
		if (peorPos != -1) {
			espacios[peorPos].add(var);
			espacios[peorPos].setMemUsada(espacios[peorPos].getMemUsada() + var.getTamaño());
			espacios[peorPos].setMemDisponible(espacios[peorPos].getMemDisponible() - var.getTamaño());
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
				int ypoints[] = { 0, 15 * tamaño,15 * tamaño, 0 };
				int npoints = 4;
				g.drawPolygon(xpoints, ypoints, npoints);
				
				// dibujo las lineas de separacion de los espacios de la memoria
				int altura = 0;
				int sumaEspacios = 0;
				g.drawString(sumaEspacios + "", 0, 15);
				for(int i = 0; i < numEspacios; i++){
					altura += espacios[i].getTamaño() * 15;
					sumaEspacios += espacios[i].getTamaño();
					g.drawString(sumaEspacios + "", 0, altura);
					g.drawString(espacios[i].getTamaño() + "", 140, altura - (espacios[i].getTamaño() * 15) / 2);
					g.drawLine(20, altura, 140, altura);
				}
				
				// dibujo las variables de los espacios
				int altura1 = 0;
				BasicStroke Bs  = new BasicStroke(2f);
				Graphics2D g2D = (Graphics2D) g;
				g2D.setStroke(Bs);
				for (int i = 0; i < numEspacios; i++) {
					int tamV = 0;
					int tamE = espacios[i].getTamaño() * 15;
					int alturaV = altura1; 
					for (int j = 0; j < espacios[i].getVarEnMem().size(); j++) {
						tamV = espacios[i].getVarEnMem().get(j).getTamaño() * 15;
						int puntosY[] = { alturaV, alturaV + tamV, alturaV + tamV, alturaV};
						g2D.setColor(espacios[i].getVarEnMem().get(j).getColor());
						if (espacios[i].getVarEnMem().get(j).getTamaño() > 1) {
							g2D.drawString(espacios[i].getVarEnMem().get(j).getNombre() + " " + espacios[i].getVarEnMem().get(j).getTamaño() + "M", 90, alturaV + (tamV / 2));
						}else {
							g2D.drawString(espacios[i].getVarEnMem().get(j).getNombre() + " " + espacios[i].getVarEnMem().get(j).getTamaño() + "M", 90, alturaV + tamV);
						}
						
						g2D.drawPolygon(xpoints, puntosY, npoints);
						alturaV += tamV; 
					}
					altura1 += tamE;
				}
				g2D.setColor(Color.BLACK);
			}
		};
		
		panel.setPreferredSize(new Dimension(160, 15*this.getTamaño() + 10));
		
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

	public int getTamaño() {
		return tamaño;
	}

	public void setTamaño(int tamaño) {
		this.tamaño = tamaño;
	}
}
