/* 
 * Programmer: David Kopp
 * Project #: Project 2
 * File Name: TestETSP.java
 * Date: 10-25-15
 * Class: Comp496ALG
 * Description: This class is the main driver for the BFS, GS, and MSTS. 
 * 				It times the method runs as well.
 */

// Eclipse project package (comment out if not using eclipse project manager)
package project2;

// Import Statements
import java.io.FileNotFoundException;

// Class for Main Driver to run BFS, GS, and MSTS
public class TestETSP 
{
	public static void main(String args[]) throws FileNotFoundException 
	{
		long startTime;
		long stopTime;
		long elapsedTime;
				
		/* Run these test cases 1 at a time and not all together. If you run them in sequence
		** you will get bad estimates because of JVM optimization and CPU caching. 
		** (First of every group of the same objects take longer than subsequent objects of
		** the same type, thus you have to run them 1 at a time or in an alternating pattern.)
		*/ 
		
		startTime = System.currentTimeMillis();
		ETSPBruteForce bf1 = new ETSPBruteForce("sampleCase1.txt");
		System.out.println(bf1.toString());
		stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
		
		startTime = System.currentTimeMillis();
		ETSPBruteForce bf4 = new ETSPBruteForce("sampleCase2.txt");
		System.out.println(bf4.toString());
		stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
	    
	    startTime = System.currentTimeMillis();
		ETSPBruteForce bf2 = new ETSPBruteForce("instructorCase1.txt");
		System.out.println(bf2.toString());
		stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
		
		startTime = System.currentTimeMillis();
		ETSPBruteForce bf3 = new ETSPBruteForce("instructorCase2.txt");
		System.out.println(bf3.toString());
		stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
		
		startTime = System.currentTimeMillis();
		ETSPGreedy g1 = new ETSPGreedy("sampleCase1.txt", 0);
		System.out.println(g1.toString());
	    stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
		
		startTime = System.currentTimeMillis();
		ETSPGreedy g4 = new ETSPGreedy("sampleCase2.txt", 0);
		System.out.println(g4.toString());
	    stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
		
		startTime = System.currentTimeMillis();
		ETSPGreedy g5 = new ETSPGreedy("sampleCase3.txt", 0);
		System.out.println(g5.toString());
	    stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
	    
		startTime = System.currentTimeMillis();
		ETSPGreedy g2 = new ETSPGreedy("instructorCase1.txt", 0);
		System.out.println(g2.toString());
	    stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
		
		startTime = System.currentTimeMillis();
		ETSPGreedy g3 = new ETSPGreedy("instructorCase2.txt", 0);
		System.out.println(g3.toString());
	    stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
		
		startTime = System.currentTimeMillis();
		ETSPMinSpanTree p1 = new ETSPMinSpanTree("sampleCase1.txt", 0);
		System.out.println(p1.toString());
	    stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
	    
		startTime = System.currentTimeMillis();
		ETSPMinSpanTree p4 = new ETSPMinSpanTree("sampleCase2.txt", 0);
		System.out.println(p4.toString());
	    stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
		
		startTime = System.currentTimeMillis();
		ETSPMinSpanTree p5 = new ETSPMinSpanTree("sampleCase3.txt", 0);
		System.out.println(p5.toString());
	    stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
		startTime = System.currentTimeMillis();
		
		ETSPMinSpanTree p2 = new ETSPMinSpanTree("instructorCase1.txt", 0);
		System.out.println(p2.toString());
	    stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
		
		startTime = System.currentTimeMillis();
		ETSPMinSpanTree p3 = new ETSPMinSpanTree("instructorCase2.txt", 0);
		System.out.println(p3.toString());
	    stopTime = System.currentTimeMillis();
	    elapsedTime = stopTime - startTime;
	    System.out.print("Run Time was " + elapsedTime +"msecs\n\n");
	}
}