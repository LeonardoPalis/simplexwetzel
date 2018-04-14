package com.marcelorsjr.simplex;


public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		ObjectiveFunction of = new ObjectiveFunction("MAX 21x1 + 11x2");
		Restriction[] r = new Restriction[1];
		r[0] = new Restriction("7x1 + 4x2 <= 13", 2);
		
		SimplexWetzel sw = new SimplexWetzel(r, of);
		sw.solve();
		sw.printSolution();
		
//		SimplexWetzelInteger si = new SimplexWetzelInteger(r, of);
//		si.solve();
//		si.printSolution();

	}

}
