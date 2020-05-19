/* 
 * Programmer: David Kopp
 * Project #: Project 3
 * File Name: TestKSP.java
 * Date: 11-24-15
 * Class: Comp496ALG
 * Description: This class is the main driver for the BFS and DPM solutions to the 
 * 				knapsack problem.
 */

package project3;

public class TestKSP {

	// Test Case #1 Data
	final static int N1 = 6;
	final static int W1 = 10;
	final static int[] WEIGHTS1 = {2,4,3,4,4,1};
	final static int[] VALUES1 = {1,2,3,3,3,6};

	// Test Case #2 Data
	final static int N2 = 10;
	final static int W2 = 12;
	final static int[] WEIGHTS2 = {3,4,2,5,6,1,2,7,8,2};
	final static int[] VALUES2 = {6,7,4,3,2,6,8,7,9,6};

	// Test Case #3 Data
	final static int N3 = 20;
	final static int W3 = 18;
	final static int[] WEIGHTS3 = {2,3,4,2,6,5,3,7,2,4,3,1,5,6,2,1,1,3,4,3};
	final static int[] VALUES3 = {2,3,4,1,2,5,3,2,4,6,2,2,1,3,4,5,6,2,1,9};

	// Test Case #4 Data
	final static int N4 = 25;
	final static int W4 = 100;
	final static int[] WEIGHTS4 = {9,16,12,8,7,14,7,8,9,14,15,18,20,2,4,5,10,11,3,17,15,18,15,9,7};
	final static int[] VALUES4 = {1,7,3,4,5,5,3,4,6,2,6,6,4,2,1,1,2,2,4,5,4,3,2,1,3};

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		// Problem from lecture
		final int[] v = {2,4,5,3};
		final int[] w = {3,2,6,4};
		BFSKSP test1 = new BFSKSP(v, w, 7, 4, "User Test1");
		DPMKSP test2 = new DPMKSP(v, w, 7, 4);
		
		// Custom problem to show when dynamic programming takes long than brute force (W is very large, n is very small)
		final int[] v1 = {2,3,3,8,7,12,10,8,3,5};
		final int[] w1 = {2,3,6,9,11,12,20,7,1,30};
		BFSKSP faster = new BFSKSP(v1, w1, 1000000, 10, "User Test2");
		DPMKSP slower = new DPMKSP(v1, w1, 1000000, 10);
		
		final int[] v2 = {2,3,3,8,7,12,10,8,3,5,11,12,9,11,20,11,15,17,2,3};
		final int[] w2 = {2,3,6,9,11,12,20,7,1,30,20,40,14,19,22,12,18,2,3,30};
		BFSKSP faster2 = new BFSKSP(v2, w2, 10000000, 20, "User Test3");
		DPMKSP slower2 = new DPMKSP(v2, w2, 10000000, 20);

		final int[] v3 = {2,3,3,8,7,12,10,8,3,5,11,12,9,11,20,11,15,17,2,3,4,6};
		final int[] w3 = {2,3,6,9,11,12,20,7,1,30,20,40,14,19,22,12,18,2,3,30,20,43};
		BFSKSP faster3 = new BFSKSP(v3, w3, 10000000, 22, "User Test4");
		DPMKSP slower3 = new DPMKSP(v3, w3, 10000000, 22);
		
		final int[] v4 = {2,3,3,8,7,12,10,8,3,5,11,12,9,11,20,11,15,17,2,3,4,6,8};
		final int[] w4 = {2,3,6,9,11,12,20,7,1,30,20,40,14,19,22,12,18,2,3,30,20,43,48};
		BFSKSP faster4 = new BFSKSP(v4, w4, 10000000, 23, "User Test5");
		DPMKSP slower4 = new DPMKSP(v4, w4, 10000000, 23);
		
		// Instructor Test Cases
		BFSKSP BFTestCase1 = new BFSKSP(VALUES1, WEIGHTS1, W1, N1, "Test Case#1");
		DPMKSP DPTestCase1 = new DPMKSP(VALUES1, WEIGHTS1, W1, N1);
		BFSKSP BFTestCase2 = new BFSKSP(VALUES2, WEIGHTS2, W2, N2, "Test Case#2");
		DPMKSP DPTestCase2 = new DPMKSP(VALUES2, WEIGHTS2, W2, N2);
		BFSKSP BFTestCase3 = new BFSKSP(VALUES3, WEIGHTS3, W3, N3, "Test Case#3");
		DPMKSP DPTestCase3 = new DPMKSP(VALUES3, WEIGHTS3, W3, N3);
		BFSKSP BFTestCase4 = new BFSKSP(VALUES4, WEIGHTS4, W4, N4, "Test Case#4");
		DPMKSP DPTestCase4 = new DPMKSP(VALUES4, WEIGHTS4, W4, N4);
	}
}