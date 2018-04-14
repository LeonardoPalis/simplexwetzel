# Simplex Wetzel

This is an implementation of the Simplex algorithm purposed by Wetzel. This project is part of the Systems Optimization course, offered in the program of computer science at the  Pontifical Catholic University of Minas Gerais, Brazil (PUC Minas).

## Getting Started
### Requirements

This project was written in Java, so you will need the JDK installed to compile the project.

### Development Environment

This is an eclipse project. So, after cloning the project, export to the pre-defined Eclipse Workspace, or get the .java files in the /src directory.

## Usage

Using is simple, just have to define, using the right format, your "Objective Function" and an array of "Restrictions", passing it to the “SimplexWetzel” constructor.

### Expressions format
* Objective Function
    * "MAX Ax1 + Bx2 + Cx3 ..."
    * "MIN Ax1 + Bx2 + Cx3 ..."
* Restriction
    * "Ax1 + Bx2 ... <= C"
    * "Ax1 + Bx2 ... >= C"

### Objective Function

The objective function only needs the formatted expression in its constructor
```java
public ObjectiveFunction(String typedObjectiveFunction)
```

### Restriction
The Restriction Constructor needs an integer parameter called "size".
```java
public Restriction(String typedInequation, int size)
```
This is the number of terms contained in the objective function

### Example

```java
ObjectiveFunction of = new ObjectiveFunction("MAX 21x1 + 11x2");
Restriction[] r = new Restriction[1];
r[0] = new Restriction("7x1 + 4x2 <= 13", 2);
		
SimplexWetzel simplex = new SimplexWetzel(r, of);
simplex.solve();
simplex.printSolution();
```

Example results:
```
********** OPTIMAL SOLUTION FOUND **********

FO(x) -> MAX Z = 39.0
x1 = 1.857142857142857
x2 = 0.0
x3 = 1.7763568394002505E-15
```

## Usage for Integer Linear Programming (ILP) problems

This solution was implemented by a branch and bound approach. An integer programming problem is a mathematical optimization or feasibility program in which some or all of the variables are restricted to be integers.

Running is very simple, you just have to call the "SimplexWetzelInteger" like the code below.

### Example

```java
ObjectiveFunction of = new ObjectiveFunction("MAX 21x1 + 11x2");
Restriction[] r = new Restriction[1];
r[0] = new Restriction("7x1 + 4x2 <= 13", 2);

SimplexWetzelInteger si = new SimplexWetzelInteger(r, of);
si.solve();
si.printSolution();
```

Example results:
```
********** OPTIMAL SOLUTION FOUND **********

FO(x) -> MAX Z = 33.0
x1 = 33.0
x2 = 0.0
x3 = 3.0
```

