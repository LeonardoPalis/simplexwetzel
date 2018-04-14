package com.marcelorsjr.simplex.integer;

import com.marcelorsjr.simplex.Restriction;
import com.marcelorsjr.simplex.ObjectiveFunction.Type;

public class Node {
	
	public enum PrunedReason {
		INFEASIBLE, OPTIMALITY, QUALITY
	}
	
	public Restriction[] restrictions;

	public Node right;
	public Node left;
	public double solution;
	public double results[];
	public boolean pruned;
	public PrunedReason prunedReason;
	
	public Node() {
		this.right = null;
		this.left = null;
		this.restrictions = null;
		prunedReason = null;
		solution = Double.MIN_VALUE;
	}
	
	public Node(Restriction[] restrictions) {
		this.right = null;
		this.left = null;
		this.restrictions = restrictions.clone();
		prunedReason = null;
		solution = Double.MIN_VALUE;
	}
	
	
}
