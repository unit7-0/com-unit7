package com.unit7.study.cryptography.labs.lab6.tests;

import java.io.PrintWriter;

import org.junit.Test;

import com.unit7.study.cryptography.labs.lab6.Alice;
import com.unit7.study.cryptography.labs.lab6.Bob;
import com.unit7.study.cryptography.labs.lab6.GraphObject;
import com.unit7.study.cryptography.labs.lab6.interfaces.Question;
import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;

public class Lab6Tests {
	@Test
	public void tryVerify() {
		int n = 0;
		int m = 0;
		// read
		
		int[][] g = readGraph(n, m);
		GraphObject object = new GraphObject();
		Alice alice = new Alice(object);
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
	
	private int[][] readGraph(int n, int m) {
		int[][] g = new int[n][m];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < m; ++j) {
				
			}
		}
		
		return g;
	}
	
	private PrintWriter writer = new PrintWriter(System.out);
}
