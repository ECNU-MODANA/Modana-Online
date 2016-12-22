package com.modana.manage.smc;
public class APMCAlgorithm {
	private  int n = 0; // number of traces drawn so far
	private  int x = 0;// number of traces satisfying PBLTL so far
	private double delta = 0.02;
	private double epsilong = 0.02;
	private double p = 0.0;
	private double maxN = 0.0;
	
	public APMCAlgorithm(int n, int x, double delta, double epsilong) {
		this.n = n;
		this.x = x;
		this.delta = delta;
		this.epsilong = epsilong;
		maxN = 4*Math.log(2/delta)/(epsilong*epsilong);
	}
	public  boolean run() {
		p = (double)x/n;
		if (n<maxN) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void xPlus1() {
		x++;
	}
	
	public void nPlus1() {
		n++;
	}
	
	public int getN() {
		return n;
	}
	public int getX() {
		return x;
	}
	public double getDelta() {
		return delta;
	}
	public double getEpsilong() {
		return epsilong;
	}
	public double getP() {
		return p;
	}
	public double getMaxN() {
		return maxN;
	}
	
	public void setN(int n) {
		this.n = n;
	}

	public void setX(int x) {
		this.x = x;
	}
}
