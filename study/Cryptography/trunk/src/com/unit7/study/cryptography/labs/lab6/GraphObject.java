package com.unit7.study.cryptography.labs.lab6;

import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;


public class GraphObject implements VerificationData {
	public int[][] getG() {
		return G;
	}

	public void setG(int[][] g) {
		G = g;
	}

	public int[][] getH() {
		return H;
	}

	public int[][] getF() {
		return F;
	}
	
	public void setH(int[][] h) {
		H = h;
	}

	public void setF(int[][] f) {
		F = f;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		GraphObject other = new GraphObject();
		int[][] arr = null;
		if (G != null) {
			arr = new int[G.length][G[0].length];
			for (int i = 0; i < G.length; ++i) {
				for (int j = 0; j < G[0].length; ++j) {
					arr[i][j] = G[i][j];
				}
			}
		}
			
		other.setG(arr);
		
		arr = null;
		if (H != null) {
			arr = new int[H.length][H[0].length];
			for (int i = 0; i < H.length; ++i) {
				for (int j = 0; j < H[0].length; ++j) {
					arr[i][j] = H[i][j];
				}
			}
		}
			
		other.setH(arr);
		
		arr = null;
		if (F != null) {
			arr = new int[F.length][F[0].length];
			for (int i = 0; i < F.length; ++i) {
				for (int j = 0; j < F[0].length; ++j) {
					arr[i][j] = F[i][j];
				}
			}
		}
			
		other.setF(arr);
		
		return other;
	}

	private int[][] G;
	private int[][] H;
	private int[][] F;
}
