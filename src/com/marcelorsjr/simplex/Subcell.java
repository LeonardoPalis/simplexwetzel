package com.marcelorsjr.simplex;



public class Subcell {
	

	private enum SubcellState {	
		COLORED, UNCOLORED;
	}
	
	private SubcellState cellState;
	private double value;
	
	public Subcell() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Subcell(SubcellState cellState, double value) {
		super();
		this.cellState = cellState;
		this.value = value;
	}
	
	public SubcellState getCellState() {
		return cellState;
	}
	public void setCellState(SubcellState cellState) {
		this.cellState = cellState;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	

}
