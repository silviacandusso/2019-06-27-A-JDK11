package it.polito.tdp.crimes.model;

public class Adiacenza {
	String tipo1;
	String tipo2;
	int peso;
	public Adiacenza(String tipo1, String tipo2, int peso) {
		super();
		this.tipo1 = tipo1;
		this.tipo2 = tipo2;
		this.peso = peso;
	}
	public String getTipo1() {
		return tipo1;
	}
	public void setTipo1(String tipo1) {
		this.tipo1 = tipo1;
	}
	public String getTipo2() {
		return tipo2;
	}
	public void setTipo2(String tipo2) {
		this.tipo2 = tipo2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return  tipo1 +" - "+ tipo2 + " : " + peso;
	}
	
	

}
