package com.marcelorsjr.simplex;


import java.util.Arrays;


public class Restriction {
    
    public enum Type {
        LESS_THAN_EQUAL, GREATER_THAN_EQUAL;
    }
	
	private String typedInequation;
	private String equivalentEquation;
	private double[] coefficients;
	private double freeElement;
	private Type type;
	private int size;
	
	public Restriction(String typedInequation, int size) throws Exception {
		this.typedInequation = typedInequation;
		this.size = size;
		setCoefficientsFromTypedInequation();

		setExpressionSignals();
		handleRestrictionToSimplex();
	}
	
	/**
	 * @throws Exception
	 * 
	 * Method for handling the typed inequation
	 * It extract the coefficients and the free element of the expression,
	 * preparing it to the simplex calculus
	 * 
	 */
	private void setCoefficientsFromTypedInequation() throws Exception {

		// Transform the inequation to an equation, adding or subtracting constants to mantain the proportion
		if (typedInequation.contains("<=")) {
			equivalentEquation = typedInequation.replace("<=", "+ x =");
			type = Type.LESS_THAN_EQUAL;
		} else if (typedInequation.contains(">=")) {
			equivalentEquation = typedInequation.replace(">=", "- x =");
			type = Type.GREATER_THAN_EQUAL;
		} else {
			throw new Exception("WRONG EXPRESSION FORMAT");
		}
		
		// Remove the spaces to start the "parsing"
		String function = equivalentEquation.replace(" ", "");
		
		// Split the expression to extract the coefficients
		String[] allCoefficients = function.split("(\\-)|(\\+)|(=)");
		coefficients = new double[size];
		freeElement = 0;

		Arrays.fill(coefficients, 0);
		
		// Iterate over the splitted expression
		for (int i = 0; i < allCoefficients.length; i++) {
			
			// Split the elements found by X to get only the number part
			String[] portions = allCoefficients[i].split("(x)|(X)");
			
			try {
				// Try to parse the total element as a double, to know if it is a free element in the expression
				freeElement += Double.parseDouble(allCoefficients[i]);
			} catch(NumberFormatException e) {
				
				// If the element is just an "x" it skip the iteration
				// This is the element inserted to transform the inequation to a equation
				if (allCoefficients[i].toLowerCase().equals("x")) {
					continue;
				}
				
				// Just verifying if the expression was typed correct 
				if (portions.length > 1) {
					if (allCoefficients.length == 1) {
						throw new Exception("WRONG EXPRESSION FORMAT");
					}
				} else {
					throw new Exception("WRONG EXPRESSION FORMAT");
				}
				
				
				if (portions[0].equals("")) {
					// If the left side of the X is empty, the coefficient is set to  1
					coefficients[Integer.valueOf(portions[1])-1] += 1;
				} else {
					// If not, it get the constant read
					coefficients[Integer.valueOf(portions[1])-1] += Double.parseDouble(portions[0]);
				}

			}
			
			
		}

	}
	
	
	/**
	 * 
	 * Method for handling the expression signals
	 * The signals are "lost" when handling the expression
	 * 
	 */
	private void setExpressionSignals() {
		int portionCount = 1;

		String function = equivalentEquation.replaceAll("\\s{2,}", " ").trim();
		String[] portions = function.split(" ");
		for (int i = 0; i < portions.length; i++) {
			if (coefficients.length == portionCount) {
				break;
			}
			
			if (portions[i].equals("+")) {
				portionCount++;
			} else if (portions[i].equals("-")) {
				if (coefficients[portionCount] == 0) {
					portionCount++;
					continue;
				}
				if (!portions[i+1].toLowerCase().equals("x")) {
					coefficients[portionCount] *= -1;
				}
				portionCount++;
			}
		}
		
	}
	
	/**
	 * 
	 * Method for handling the typed inequation signals to the simplex calc
	 * It multiply the coefficients based on the type of the current expression
	 * (GREATHER_THAN_EQUAL / LESS_THAN_EQUAL)
	 * 
	 */
	private void handleRestrictionToSimplex() {
		if (type == Type.GREATER_THAN_EQUAL) {
			freeElement *= -1;
			for (int i = 0; i < coefficients.length; i++) {
				coefficients[i] *= -1;
			}
		}
	}
	
	/**
	 * 
	 * Method for solving the equation based on the basic variables values
	 * 
	 */
	public double solveEquationWithBasicVariablesValues(double values[]) {
		
		double result = 0.0;
		result = freeElement;
		
		for (int i = 0; i < coefficients.length; i++) {
			result = result - coefficients[i]*values[i];
		}
	
		return result;
	}

	public String getTypedInequation() {
		return typedInequation;
	}

	public void setTypedInequation(String typedInequation) {
		this.typedInequation = typedInequation;
	}

	public String getEquivalentEquation() {
		return equivalentEquation;
	}

	public void setEquivalentEquation(String equivalentEquation) {
		this.equivalentEquation = equivalentEquation;
	}

	public double[] getCoefficients() {
		return coefficients;
	}

	public void setCoefficients(double[] coefficients) {
		this.coefficients = coefficients;
	}

	public double getFreeElement() {
		return freeElement;
	}

	public void setFreeElement(double freeElement) {
		this.freeElement = freeElement;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}