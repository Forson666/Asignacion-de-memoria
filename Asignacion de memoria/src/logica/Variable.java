package logica;

import java.awt.Color;

public class Variable {
	// Declaro los atributos de la clase
	private int tama�o;
	private String nombre;
	private Color color; // para saber el color de las lineas del grafico
	
	// metodo constructor parametrico de la clase
	public Variable (int tama�o, String nombre) {
		this.tama�o = tama�o;
		this.nombre = nombre;
		color = Color.RED;
	}

	// Metodos accesores y modificadores
	public int getTama�o() {
		return tama�o;
	}

	public void setTama�o(int tama�o) {
		this.tama�o = tama�o;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
