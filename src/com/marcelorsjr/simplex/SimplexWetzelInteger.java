package com.marcelorsjr.simplex;

import java.util.ArrayList;
import java.util.Arrays;

import com.marcelorsjr.simplex.ObjectiveFunction.Type;
import com.marcelorsjr.simplex.SimplexWetzel.SolutionResponse;
import com.marcelorsjr.simplex.integer.Node;
import com.marcelorsjr.simplex.integer.Node.PrunedReason;
import com.marcelorsjr.simplex.integer.Tree;

public class SimplexWetzelInteger {
	
	public double solution;
	public double variablesValues[];
	public Tree tree;
	private SimplexWetzel simplexWetzel;
	
	public SimplexWetzelInteger(Restriction[] restrictions, ObjectiveFunction of) throws Exception {
		
		simplexWetzel = new SimplexWetzel(restrictions, of);
		
		double results[] = simplexWetzel.solve();
		solution = results[0];
		variablesValues = Arrays.copyOfRange(results, 1, results.length);
		tree = new Tree();
	}
	
	public void printSolution() {		
		
		if (simplexWetzel.of.getType() == ObjectiveFunction.Type.MAXIMIZATION) {
			System.out.println("FO(x) -> MAX Z = "+tree.bestNode.solution);
		} else {
			System.out.println("FO(x) -> MIN Z = "+tree.bestNode.solution);
		}
		
		
		for (int i = 0; i < tree.bestNode.results.length; i++)
			System.out.println("x"+(i+1)+" = "+ tree.bestNode.results[i]);

		
	}
	private void solveForInteger(Node node, SimplexWetzel simplexWetzel) throws Exception {

		// Solve the simplex and get the results
		double results[] = simplexWetzel.solve();
		
		// If the response is not the optimal, it prunes the tree by infeasibility
		if (simplexWetzel.solutionResponse != SolutionResponse.OPTIMAL_SOLUTION) {
			node.pruned = true;
			node.prunedReason = PrunedReason.INFEASIBLE;
			return;
		}
		

		// Get the problem solution, returned at the firs position of the array
		solution = results[0];
		node.solution = solution;
		
		double result;
		
		// Set the result to infinit just to facilitate some comparisons
		if (simplexWetzel.of.getType() == Type.MAXIMIZATION) {
			result = Double.MIN_VALUE;
		} else {
			result = Double.MAX_VALUE;
		}
		
		int iterator = 0;
		
		// Check if the solutions are integer
		for (iterator = 1; iterator < results.length; iterator++) {
			double r = results[iterator];
			if (!((r == Math.floor(r)) && !Double.isInfinite(r))) {
				result = r;
			    break;
			}
		}
	
		// If the solutions are not integer, the result will be different than infinit
		if (result != Double.MIN_VALUE && result != Double.MAX_VALUE) {
			
			// Create the new restrictions based on the chosen value
			String leftRestriction = "x"+iterator+" <= "+ Math.floor(result);
			String rightRestriction = "x"+iterator+" >= " + Math.ceil(result);	

			// Convert the array of restrictions to a list, to insert new elemets
			ArrayList<Restriction> list = new ArrayList(Arrays.asList(simplexWetzel.restrictions));

			// Add the new restrictions
			list.add(new Restriction(leftRestriction, simplexWetzel.of.getCoefficients().length));
			
			// Convert the list back to an array
			Restriction[] newRestrictions = list.toArray(new Restriction[0]);
			
			// Instantiate a new left node
			node.left = new Node(newRestrictions.clone());
			
			// Convert the array of restrictions to a list, to insert new elemets
			list = new ArrayList(Arrays.asList(simplexWetzel.restrictions));
			
			// Add the new restrictions
			list.add(new Restriction(rightRestriction, simplexWetzel.of.getCoefficients().length));
			
			// Convert the list back to an array
			newRestrictions = list.toArray(new Restriction[0]);
			
			// Instantiate a new left node
			node.right = new Node(newRestrictions.clone());

		} else {
			
			// The tree is pruned by optimal solution
			node.pruned = true;
			node.prunedReason = PrunedReason.OPTIMALITY;
			node.results = results;
		
			// Verify the objective function type to choose the best solution for each case
			if (simplexWetzel.of.getType() == Type.MAXIMIZATION) {
				if (tree.bestNode.solution < node.solution)
					tree.bestNode = node;
			} else {
				if (tree.bestNode.solution > node.solution)
					tree.bestNode = node;
			}
			
			// Set the left and right nodes to null, to indicate the pruning
			node.left = null;
			node.right = null;
			
		}
		
		// If the child nodes are not null it makes a recursive call
		// Making a search using the Depth-first search (DFS) approach
		if (node.left != null) {
			solveForInteger(node.left, new SimplexWetzel(node.left.restrictions.clone(), simplexWetzel.of));
		}
		if (node.right != null) {
			solveForInteger(node.right, new SimplexWetzel(node.right.restrictions.clone(), simplexWetzel.of));
		}			
		
	}
	
	
	public void solve() throws Exception {

		int iterator = 0;
		
		// Analyze the first calculated simplex if it is already an integer response
		for (iterator = 0; iterator < variablesValues.length; iterator++) {
			double r = variablesValues[iterator];
			if (!((r == Math.floor(r)) && !Double.isInfinite(r))) {
			    break;
			}
		}
		
		
		if (iterator != variablesValues.length) {
			
			iterator++;
			
			// Create the new restrictions based on the chosen value
			String leftRestriction = "x"+iterator+" <= "+ Math.floor(variablesValues[iterator-1]);
			String rightRestriction = "x"+iterator+" >= " + Math.ceil(variablesValues[iterator-1]);	

			// Convert the array of restrictions to a list, to insert new elemets
			ArrayList<Restriction> list = new ArrayList(Arrays.asList(simplexWetzel.restrictions));

			// Add the new restrictions
			list.add(new Restriction(leftRestriction, simplexWetzel.of.getCoefficients().length));
			
			// Convert the list back to an array
			Restriction[] newRestrictions = list.toArray(new Restriction[0]);
			
			// Instantiate a new root left node
			tree.root.left = new Node(newRestrictions.clone());
			
			// Convert the array of restrictions to a list, to insert new elemets
			list = new ArrayList(Arrays.asList(simplexWetzel.restrictions));
			
			// Add the new restrictions
			list.add(new Restriction(rightRestriction, simplexWetzel.of.getCoefficients().length));
			
			// Convert the list back to an array
			newRestrictions = list.toArray(new Restriction[0]);
			
			// Instantiate a new root right node
			tree.root.right = new Node(newRestrictions.clone());
			
			tree.root.restrictions = simplexWetzel.restrictions;
			
			// Make the calls for searching the best integer solution as a Depth-first search (DFS) approach
			solveForInteger(tree.root.left, new SimplexWetzel(tree.root.left.restrictions.clone(), simplexWetzel.of));
			solveForInteger(tree.root.right, new SimplexWetzel(tree.root.right.restrictions.clone(), simplexWetzel.of));

			
		} else {
			
			// Already got the best integer solutions
			tree.bestNode.solution = solution;
			tree.bestNode.results = variablesValues.clone();
		}
		
		
	}
	
	
	
	
	

}
