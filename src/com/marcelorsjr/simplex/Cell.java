package com.marcelorsjr.simplex;


public class Cell {
	

	public Subcell topSubcell;
	public Subcell bottomSubcell;
	
	public Cell() {
		
		topSubcell = new Subcell();
		bottomSubcell = new Subcell();

	}

}
