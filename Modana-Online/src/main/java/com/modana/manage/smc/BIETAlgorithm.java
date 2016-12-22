package com.modana.manage.smc;
import static java.lang.Math.abs;
import java.math.BigDecimal;
import java.math.MathContext;

public class BIETAlgorithm {
	static MathContext mc = new MathContext(6);
	private  int n = 0; // number of traces drawn so far
	private  int x = 0;// number of traces satisfying PBLTL so far

	private double a = 1.0;
	private double b = 1.0;
	private double k = 0.02;
	private double c = 0.95;
	private double gamma = 0.0;
	private double p = 0.0;
	private double t0 = 0.0;
	private double t1 = 0.0;
	
	public BIETAlgorithm(int n, int x, double k, double c) {
		this.n = n;
		this.x = x;
		this.k = k;
		this.c = c;
	}
	public  boolean run() {
		p = (x + a) / (n + a + b);
		t0 = p - k;
		t1 = p + k;
		if (t1 > 1) {
			t1 = 1;
			t0 = 1 - 2 * k;
		} else if (t0 < 0) {
			t0 = 0;
			t1 = 2 * k;
		}
		
		gamma = (DefiniteIntegral(0, t1, x + a, n - x + b).add(DefiniteIntegral(0, t0, x + a, n - x + b).negate())).
				divide(DefiniteIntegral(0, 1, x + a, n - x + b), mc).doubleValue();
		if(gamma < c) {
			return false;
		}
		return true;
	}
	
	public static BigDecimal DefiniteIntegral(double x0, double xn,double alpha, double beta)
	{
		int n =1000;
		
		BigDecimal sum = new BigDecimal(0.0,mc);
		BigDecimal h = new BigDecimal(abs(xn-x0)/n,mc);
		
		for (double xi =x0;xi<xn;xi=xi+h.doubleValue())
		{
			sum = sum.add(BetaDistribution(xi+h.doubleValue(),alpha,beta).multiply(h, mc), mc);
		}
		return sum;		
	}
	
	public static BigDecimal BetaDistribution(double x, double alpha, double beta )
	{
		BigDecimal b1 = new BigDecimal(x,mc);
		BigDecimal b2 = new BigDecimal(1-x, mc);
		BigDecimal p1 = b1.pow((int)(alpha-1),mc);
		BigDecimal p2 = b2.pow((int)(beta-1),mc);
		BigDecimal f = p1.multiply(p2,mc);
		return f;
	}
	
	public void xPlus1() {
		x++;
	}
	
	public void nPlus1() {
		n++;
	}
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	public int getN() {
		return n;
	}
	public int getX() {
		return x;
	}
	public double getK() {
		return k;
	}
	public double getC() {
		return c;
	}
	public double getGamma() {
		return gamma;
	}
	public double getP() {
		return p;
	}
	public double getT0() {
		return t0;
	}
	public double getT1() {
		return t1;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setX(int x) {
		this.x = x;
	}
}

