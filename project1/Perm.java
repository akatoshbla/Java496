//package project1;

import java.util.ArrayList;

public class Perm {
    public static void main(String... args) {
        final int N = 11;
        int[] sequence = new int[N];
        for (int i = 0; i < N; i++) {
            sequence[i] = i + 1;
        }

        printSequence(sequence);
        permutations(sequence);
    }

	// This Algorithm was found at http://www.programcreek.com/2013/02/leetcode-permutations-java/
	public ArrayList<ArrayList<Integer>> bruteForceSolution(int[] num) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
	 
		//start from an empty list
		result.add(new ArrayList<Integer>());
	 
		for (int i = 0; i < num.length; i++) {
			//list of list in current iteration of the array num
			ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();
	 
			for (ArrayList<Integer> l : result) {
				// # of locations to insert is largest index + 1
				for (int j = 0; j < l.size()+1; j++) {
					// + add num[i] to different locations
					l.add(j, num[i]);
	 
					ArrayList<Integer> temp = new ArrayList<Integer>(l);
					current.add(temp);
					l.remove(j);
				}
			}
			
			System.out.println(current.size());
			result = new ArrayList<ArrayList<Integer>>(current);
			System.out.println(current);
			System.out.println(result);
		}
		return result;
	}
}