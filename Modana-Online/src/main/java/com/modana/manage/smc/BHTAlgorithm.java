package com.modana.manage.smc;

import java.math.MathContext;

public class BHTAlgorithm {
	static MathContext mc = new MathContext(10);
	private double T = 10000;
	private double theta = 0.0;
	private String H0 = "undone";
	private double gamma = 0.0;
	private int a = 1;
	private int b = 1;
	private double PI0 = 0.0;// H0
	private double PI1 = 0.0;// H1
	private int n = 0; //number of traces drawn so far
	private int x = 0; //number of traces satisfying PBLTL so far
	
	public BHTAlgorithm(double theta, double T) {
		this.theta = theta;
		this.T = T;
		
		PI0 = 1-theta;// H0
		PI1 = 1 - PI0;// H1
	}
	
	public boolean run()// H0:>=p
	{
		double F = (BIETAlgorithm.DefiniteIntegral(0, theta, x + a, n - x + b).divide(BIETAlgorithm.DefiniteIntegral(0, 1, x + a, n - x + b),mc)).doubleValue();
		gamma = (PI1 / PI0) * (1 / F - 1);
		if (gamma > T) {
			H0 = "true";
		} else if (gamma < (1 / T)) {
			H0 = "false";
		}
		
		if (gamma < T && gamma > (1 / T)) {
			return false;
		}
		return true;
	}
	
	public void xPlus1() {
		x++;
	}
	
	public void nPlus1() {
		n++;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public double getT() {
		return T;
	}

	public double getTheta() {
		return theta;
	}

	public String getH0() {
		return H0;
	}

	public double getGamma() {
		return gamma;
	}

	public double getPI0() {
		return PI0;
	}

	public double getPI1() {
		return PI1;
	}

	public int getN() {
		return n;
	}

	public int getX() {
		return x;
	}
	
	public void setN(int n) {
		this.n = n;
	}

	public void setX(int x) {
		this.x = x;
	}
}
