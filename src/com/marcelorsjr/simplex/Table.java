package com.marcelorsjr.simplex;


public class Table {


	
	public int selectedCol;
	public int selectedRow;
	public int[] nonBasicVariables;
	public int[] basicVariables;
	public Cell[][] cells;
	
	public Table(int numberOfRows, int numberOfCols) {
		
		selectedCol = 0;
		selectedRow = 0;
		
		nonBasicVariables = new int[numberOfCols];
		for (int i = 0; i < numberOfCols; i++) {
			nonBasicVariables[i] = i+1;
		}
		
		basicVariables = new int[numberOfRows];
		for (int i = 0; i < numberOfRows; i++) {
			basicVariables[i] = nonBasicVariables[numberOfCols-1]+i+1;
		}
		
		cells = new Cell[numberOfRows+1][numberOfCols+1];
		
		for (int i = 0; i < numberOfRows+1; i++) {
			for (int j = 0; j < numberOfCols+1; j++) {
					cells[i][j] = new Cell();
			}
		}
		
	}
	
	public void showBasic() {
		for (int i = 0; i < basicVariables.length; i++) {
				System.out.print(basicVariables[i]);
				System.out.print("    ");
		}
		System.out.println();
		
	}
	
	
	public void showNoBasic() {
		for (int i = 0; i < nonBasicVariables.length; i++) {
				System.out.print(nonBasicVariables[i]);
				System.out.print("    ");
		}
		System.out.println();
		
	}
	
	public void showTable() {
		
		for (int i = 0; i <= basicVariables.length; i++) {
			for (int j = 0; j <= nonBasicVariables.length; j++) {
					System.out.print(cells[i][j].topSubcell.getValue()+" / "+cells[i][j].bottomSubcell.getValue());
					System.out.print("    ");
			}
			System.out.println();
			System.out.println();
		}
		
	}
	
}
