package com.modana.manage.smc;

public class SPRTAlgorithm {
	private String H0 = "undone";
	private int x = 0;
	private int x_last = 0;
	private int n = 0; //number of traces drawn so far
	private double theta = 0.0;
	
	private double alpha = 0.01;
	private double beta = 0.01;
	private double gama = 0.0;
	private double delta = 0.01;
	
	private double T1 = 0.0;
	private double T2 = 0.0;
	private double p1 = 0.0;
	private double p0 = 0.0;
	
	public SPRTAlgorithm(double theta,	double alpha, double beta, double delta) {
		this.theta = theta;
		this.alpha = alpha;
		this.beta = beta;
		this.delta = delta;
		
		T1 = Math.log(beta/(1-alpha));
		T2 = Math.log((1-beta)/alpha);
		p1 = theta - delta;
		p0 = theta + delta;
	}
	
	public boolean run()// H0:>=p
	{
		gama = gama + (x-x_last)*Math.log(p1/p0)+(1-x+x_last)*Math.log((1-p1)/(1-p0));
		//record this x;
		x = x_last;
		
		if (gama <= T1) {
			H0 = "true";
		} else {
			H0 = "false";
		}
		if (gama >= T1 && gama <= T2) {
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

	public String getH0() {
		return H0;
	}

	public int getX() {
		return x;
	}

	public int getN() {
		return n;
	}

	public double getTheta() {
		return theta;
	}

	public double getAlpha() {
		return alpha;
	}

	public double getBeta() {
		return beta;
	}

	public double getGama() {
		return gama;
	}

	public double getDelta() {
		return delta;
	}

	public double getT1() {
		return T1;
	}

	public double getT2() {
		return T2;
	}

	public double getP1() {
		return p1;
	}

	public double getP0() {
		return p0;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setX(int x) {
		this.x = x;
	}
}
