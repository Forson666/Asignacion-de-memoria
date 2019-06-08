package logica;

import java.util.ArrayList;

public class Espacio implements Cloneable{
	// Declaro los atributos de la clase
	private int tama�o;
	private int memUsada;
	private int memDisponible;
	private ArrayList<Variable> varEnMem;
	
	// metodo constructor parametrico de la clase
	public Espacio (int tama�o) {
		this.tama�o = tama�o;
		memDisponible = tama�o;
		memUsada = 0;
		varEnMem = new ArrayList<Variable>();
	}
	
	// Metodo para a�adir una variable a un espacio de memoria
	public void add(Variable var) {
		varEnMem.add(var);
	}
	
	// Metodo para clonar una memoria
	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			// No deberia ocurrir
		}
		return clone;
	}

	// Metodos accesores y modificadores
	public int getTama�o() {
		return tama�o;
	}

	public void setTama�o(int tama�o) {
		this.tama�o = tama�o;
	}

	public int getMemUsada() {
		return memUsada;
	}

	public void setMemUsada(int memUsada) {
		this.memUsada = memUsada;
	}

	public int getMemDisponible() {
		return memDisponible;
	}

	public void setMemDisponible(int memDisponible) {
		this.memDisponible = memDisponible;
	}

	public ArrayList<Variable> getVarEnMem() {
		return varEnMem;
	}

	public void setVarEnMem(ArrayList<Variable> varEnMem) {
		this.varEnMem = varEnMem;
	}
}
