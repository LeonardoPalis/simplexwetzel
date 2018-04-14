package com.marcelorsjr.simplex;


public class ObjectiveFunction {
    
    public enum Type {
        MINIMIZATION, MAXIMIZATION;
    }
	
	private String typedObjectiveFunction;
	private double[] coefficients;
	private double freeElement;
	private Type type;
	
	
	public ObjectiveFunction(String typedObjectiveFunction) {
		this.typedObjectiveFunction = typedObjectiveFunction;
		try {
			setType();
			setCoefficientsFromTypedObjectiveFunction();
			setExpressionSignals();
			handleObjectiveFunctionToSimplex();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	/**
	 * @throws Exception
	 * 
	 * Method for set the type of the written expression
	 * 
	 */
	private void setType() throws Exception {
		// Split the expression by a space and then check the first element (max or min)
		String[] functionType = typedObjectiveFunction.split(" ");
		if (functionType[0].toLowerCase().equals("max")) {
			this.type = Type.MAXIMIZATION;
			typedObjectiveFunction = typedObjectiveFunction.toLowerCase().replace("max ", "");
		} else if (functionType[0].toLowerCase().equals("min")) {
			this.type = Type.MINIMIZATION;
			typedObjectiveFunction = typedObjectiveFunction.toLowerCase().replace("min ", "");
		} else {
			 throw new Exception("FUNCTION TYPE (MAX/MIN) NOT FOUND");
		}
		
	}
	
	/**
	 * @throws Exception
	 * 
	 * Method for handling the typed expression
	 * It extract the coefficients and the free element of the expression,
	 * preparing it to the simplex calculus
	 * 
	 */
	private void setCoefficientsFromTypedObjectiveFunction() throws Exception {
		// Remove the spaces to start the "parsing"
		String function = typedObjectiveFunction.replace(" ", "");
		
		// Split the expression to extract the coefficients
		String[] allCoefficients = function.split("(\\-)|(\\+)");
		coefficients = new double[allCoefficients.length];
		freeElement = 0;
		for (int i = 0; i < allCoefficients.length; i++) {
			String[] portions = allCoefficients[i].split("(x)|(X)");
			if (portions.length > 1) {
				if (allCoefficients.length == 1) {
					throw new Exception("WRONG EXPRESSION FORMAT");
				} else if (portions[0].equals("")) {
					// If the left side of the X is empty, the coefficient is set to  1
					coefficients[i] = 1;
				} else {
					// If not, it get the constant read
					coefficients[i] = Double.parseDouble(portions[0]);
				}
			} else {
				try {
					// Try to parse the element as a double, to know if it is a free element in the expression
					freeElement = Double.parseDouble(portions[0]);
				} catch (NumberFormatException e) {
					throw new Exception("WRONG EXPRESSION FORMAT");
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
		int portionCount = 0;
		
		String function = typedObjectiveFunction.replaceAll("\\s{2,}", " ").trim();
		String[] portions = function.split(" ");
		for (int i = 0; i < portions.length; i++) {
			if (portions[i].equals("+")) {
				portionCount++;
			} else if (portions[i].equals("-")) {
				coefficients[portionCount] *= -1;
				portionCount++;
			}
		}
		
	}
	
	/**
	 * 
	 * Method for handling the typed inequation signals to the simplex calc
	 * It multiply the coefficients based on the type of the current expression
	 * (MINIMAZTION / MAXIMIZATION)
	 * 
	 */
	private void handleObjectiveFunctionToSimplex() {
		if (type == Type.MINIMIZATION) {
			for (int i = 0; i < coefficients.length; i++) {
				coefficients[i] *= -1;
			}
		}
	}

	public String getTypedObjectiveFunction() {
		return typedObjectiveFunction;
	}

	public void setTypedObjectiveFunction(String typedObjectiveFunction) {
		this.typedObjectiveFunction = typedObjectiveFunction;
	}

	public double[] getCoefficients() {
		return coefficients;
	}

	public void setCoefficients(double[] coefficient) {
		this.coefficients = coefficient;
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
