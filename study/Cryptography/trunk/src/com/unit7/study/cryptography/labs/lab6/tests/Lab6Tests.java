package com.unit7.study.cryptography.labs.lab6.tests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.unit7.study.cryptography.labs.lab6.Alice;
import com.unit7.study.cryptography.labs.lab6.Bob;
import com.unit7.study.cryptography.labs.lab6.GraphObject;
import com.unit7.study.cryptography.labs.lab6.interfaces.Question;
import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;
import com.unit7.study.cryptography.tools.Pair;

public class Lab6Tests {
	@Test
	public void tryVerify() {
		int n = nextInt();
		int m = nextInt();
		
		int[][] g = readGraph(n, m);
		List<Pair<Integer, Integer>> cycle = readCycle(n);
		
		GraphObject object = new GraphObject();
		Alice alice = new Alice(object, cycle);
		Bob bob = new Bob();
		
		VerificationData data = alice.beginVerification();
		Question q = bob.verifySubject(data);
		
		data = alice.endVerification(q);
		boolean verified = false;
		
		try {
			verified = bob.acceptVerifying(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.println("verification: " + verified);
	}
	
	private List<Pair<Integer, Integer>> readCycle(int n) {
	    List<Pair<Integer, Integer>> cycle = new ArrayList<Pair<Integer, Integer>>();
	    for (int i = 0; i < n; ++i) {
	        cycle.add(new Pair(nextInt(), nextInt()));
	    }
	    
        return cycle;
    }

    private int[][] readGraph(int n, int m) {
		int[][] g = new int[n][m];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < m; ++j) {
				int first = nextInt() - 1;
				int second = nextInt() - 1;
				g[first][second] = 1;
				g[second][first] = 1;
			}
		}
		
		return g;
	}
	
	private int nextInt() {
	    try {
            while (tokenizer == null || !tokenizer.hasMoreTokens())
                tokenizer = new StringTokenizer(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	    
	    return Integer.parseInt(tokenizer.nextToken());
	}
	
	@Before
	public void prepare() {
	    try {
            reader = new BufferedReader(new FileReader("/home/unit7/graph.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Assert.fail(e.getLocalizedMessage());
        }
	}
	
	private PrintWriter writer = new PrintWriter(System.out);
	private BufferedReader reader;
	private StringTokenizer tokenizer;
}
