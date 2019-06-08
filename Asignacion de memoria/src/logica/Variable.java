package logica;

import java.awt.Color;

public class Variable {
	// Declaro los atributos de la clase
	private int tamaño;
	private String nombre;
	private Color color; // para saber el color de las lineas del grafico
	
	// metodo constructor parametrico de la clase
	public Variable (int tamaño, String nombre) {
		this.tamaño = tamaño;
		this.nombre = nombre;
		color = Color.RED;
	}

	// Metodos accesores y modificadores
	public int getTamaño() {
		return tamaño;
	}

	public void setTamaño(int tamaño) {
		this.tamaño = tamaño;
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
