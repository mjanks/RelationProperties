package relationProperties;

import java.util.Scanner;
import java.util.InputMismatchException;

/*
 * Created by: Michael Janks
 * Date: Saturday, November 14th, 2020
 * Title: Properties of Relations
 * Course: COSC 314
 * This project allows the user to enter in a square boolean matrix of any size 
 * and calculate whether the relation R is reflexive, symmetric, anti-symmetric, 
 * and/or transitive. If a particular property does not hold for the relation, a 
 * message will be displayed to indicate why. This program also contains methods to 
 * raise a matrix to any power. The user defines the value of the exponent. I've 
 * commented these sections out but left the methods here as they are useful in 
 * determining if the relation is transitive or not.
 * 
 * Based on a Java program to compute the square of a boolean matrix 
 * provided by COSC 314 (Chaudhuri)
 */

public class RelationProperties {

	public static void main(String[] args) {
		RelationProperties rp = new RelationProperties();
		Scanner scan = new Scanner(System.in);
		boolean[][] a = inputMatrix(scan); // lets user set the size and elements of the matrix

		// print the user input matrix 'A'
		System.out.println("You input the following matrix 'A':");
		rp.printMatrix(a);
		System.out.println("*************************************");

		// calculate reflexive
		rp.reflexive(a);
		System.out.println("*************************************");

		// calculate symmetric
		rp.symmetric(a);
		System.out.println("*************************************");

		// calculate anti-symmetric
		rp.antiSymmetric(a);
		System.out.println("*************************************");

		// calculate transitive
		rp.transitive(a);
		System.out.println("*************************************");

		/*
		// calculate exponent
		int n = getExponent(scan); System.out.println("Original user input matrix:");
		rp.printMatrix(a); System.out.println("********************************");
		rp.calcExponent(n, a);
		*/
		
		scan.close();
		
	} // end of main

	public static boolean[][] inputMatrix(Scanner scan) {
		int num;
		int row;
		int col;
		int size = 0;
		boolean flag = true;
		
		// get the size of the square matrix 'A'
		while (flag) {
			System.out.print("Please enter the size of an m x m square boolean matrix 'A' (positive integer): ");
			try {
				size = scan.nextInt();
			} catch (InputMismatchException e) {
				flag = false;
				System.out.println("InputMismatchException! Please try again! " + e);
			}
			if (size <= 0)
				System.out.println("Bad input try again.");
			else
				flag = false;
		}
		row = size;
		col = size;
		boolean[][] m = new boolean[row][col];

		// get the elements of the square matrix 'A'
		System.out.println("Please enter the " + (int) Math.pow(size, 2) + " elements of the matrix (0 or 1), "
				+ "each followed by the enter key:");
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				flag = true;
				while (flag) {
					num = scan.nextInt();
					if (num == 1) {
						m[i][j] = true;
						flag = false;
					} else if (num == 0) {
						m[i][j] = false;
						flag = false;
					} else {
						System.out.println("Bad input!! Try again.");
					}
				}
			}
		}
		return m;
	}

	public void printMatrix(boolean[][] m) {
		int row = m.length;
		int col = m.length;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (m[i][j])
					System.out.print(1 + " ");
				else
					System.out.print(0 + " ");
			}
			System.out.println();
		}
	}

	public static int getExponent(Scanner scan) {
		boolean flag;
		int n = 0;

		flag = true;
		while (flag) {
			System.out.print("For matrix 'A^n', enter 'n' (positive integer): ");
			try {
				n = scan.nextInt();
			} catch (InputMismatchException e) {
				flag = false;
				System.out.println("InputMismatchException! Please try again! " + e);
			}
			if (n <= 0)
				System.out.println("Bad input try again!");
			else
				flag = false;
		}
		return n;
	}

	public boolean[][] calcExponent(int exponent, boolean[][] m) {
		int count = 1;
		int row = m.length;
		int col = m.length;
		boolean[][] p = new boolean[row][col]; // product matrix
		boolean[][] temp; // temp matrix, initially set to user input matrix
		temp = m;
		while (count < exponent) {
			for (int i = 0; i < row; i++)
				for (int j = 0; j < col; j++)
					for (int k = 0; k < col; k++)
						p[i][j] = p[i][j] || (m[i][k] && temp[k][j]);
			if (exponent == (count + 1)) {
				// print here to see matrix A^n
				// System.out.println("Matrix A^" + exponent + ":");
				// printMatrix(p);
				return p; // return the product matrix
			} else {
				for (int i = 0; i < row; i++)
					System.arraycopy(p[i], 0, temp[i], 0, col);
				p = new boolean[row][col];
			}
			count++;
		}
		System.out.println("SHOULD NEVER GET HERE! calcExponent()");
		return p;
	}

	public boolean reflexive(boolean[][] m) {
		for (int i = 0; i < m.length; i++) {
			if (!m[i][i]) {
				System.out.println("A[" + i + "][" + i + "] is false");
				System.out.println("Matrix A is  NOT reflexive");
				return false;
			}
		}
		System.out.println("Matrix A is reflexive");
		return true;
	}

	public boolean symmetric(boolean[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				if (!m[i][j] == m[j][i]) {
					System.out.println("A[" + i + "][" + j + "] (" + m[i][j] + ") does not " + "equal A[" + j + "][" + i
							+ "] (" + m[j][i] + ")");
					System.out.println("Matrix A is NOT symmetric");
					return false;
				}
			}
		}
		System.out.println("Matrix A is symmetric");
		return true;
	}

	public boolean antiSymmetric(boolean[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				if (m[i][j]) {
					if (m[i][j] == m[j][i] && i != j) {
						System.out.println("A[" + i + "][" + j + "] (" + m[i][j] + ") is equal to A[" + j + "][" + i
								+ "] (" + m[j][i] + ")");
						System.out.println("Matrix A is NOT anti-symmetric");
						return false;
					}
				}
			}
		}
		System.out.println("Matrix A is anti-symmetric");
		return true;
	}

	public boolean transitive(boolean[][] m) {
		boolean[][] mSquared;
		mSquared = calcExponent(2, m);
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				if (!m[i][j]) {
					if (m[i][j] != mSquared[i][j]) {
						System.out.println("A[" + i + "][" + j + "] (" + m[i][j] + ") is not equal to A^2[" + i + "][" + j + "] (" + mSquared[i][j] + ")");
						System.out.println("Matrix A is NOT transitive");
						return false;
					}
				}
			}
		}
		System.out.println("Matrix A is transitive");
		return true;
	}
	
} // end of class

/************ OUTPUT 3x3 ************
Please enter the size of an m x m square boolean matrix 'A' (positive integer): 3
Please enter the 9 elements of the matrix (0 or 1), each followed by the enter key:
0
1
0
0
1
1
1
1
0
You input the following matrix 'A':
0 1 0 
0 1 1 
1 1 0 
*************************************
A[0][0] is false
Matrix A is  NOT reflexive
*************************************
A[0][1] (true) does not equal A[1][0] (false)
Matrix A is NOT symmetric
*************************************
A[1][2] (true) is equal to A[2][1] (true)
Matrix A is NOT anti-symmetric
*************************************
A[0][2] (false) is not equal to A^2[0][2] (true)
Matrix A is NOT transitive
*************************************

************ OUTPUT 4x4 ************
Please enter the size of an m x m square boolean matrix 'A' (positive integer): 4
Please enter the 16 elements of the matrix (0 or 1), each followed by the enter key:
0
1
0
1
1
0
1
0
0
1
0
1
1
0
1
0
You input the following matrix 'A':
0 1 0 1 
1 0 1 0 
0 1 0 1 
1 0 1 0 
*************************************
A[0][0] is false
Matrix A is  NOT reflexive
*************************************
Matrix A is symmetric
*************************************
A[0][1] (true) is equal to A[1][0] (true)
Matrix A is NOT anti-symmetric
*************************************
A[0][0] (false) is not equal to A^2[0][0] (true)
Matrix A is NOT transitive
*************************************

************ OUTPUT 6x6 ************
Please enter the size of an m x m square boolean matrix 'A' (positive integer): 6
Please enter the 36 elements of the matrix (0 or 1), each followed by the enter key:
1
1
0
0
0
1
0
1
0
1
0
0
0
0
1
0
0
0
1
0
0
1
1
0
0
1
1
0
1
0
0
0
1
0
1
1
You input the following matrix 'A':
1 1 0 0 0 1 
0 1 0 1 0 0 
0 0 1 0 0 0 
1 0 0 1 1 0 
0 1 1 0 1 0 
0 0 1 0 1 1 
*************************************
Matrix A is reflexive
*************************************
A[0][1] (true) does not equal A[1][0] (false)
Matrix A is NOT symmetric
*************************************
Matrix A is anti-symmetric
*************************************
A[0][2] (false) is not equal to A^2[0][2] (true)
Matrix A is NOT transitive
*************************************
*/
