package com.modana.manage.smc;

import cern.jet.stat.Probability;

public class ACIAlgorithm {
	private double width = 0.01;//-->k
	private double confidence = 0.1;//-->1-c
	private int n;
	private int x;
	private double p = 0.0;
	
	public ACIAlgorithm(int n, int x, double width, double confidence) {
		this.n = n;
		this.x = x;
		this.width = width;
		this.confidence = confidence;
	}
	
	public boolean run() {
		p = x/(double)n;
		if (n < 2)
			return false;
		if (getVariance(n,x) <= 0.0) {
			return false;
		}
		double quantile = 0.0;
		quantile = Probability.normalInverse(1.0 - confidence / 2.0);
		double squaredQuantile = quantile * quantile;
		if (getVariance(n,x) > 0.0 && (n + 1) < getVariance(n,x) * squaredQuantile / (width * width))
			return false;
		return true;
	}
	
	private double getVariance(int numSamples,int numTrue)
	{
		if (numSamples <= 1) {
			return 0.0;
		} else {
			return (numTrue * ((double) numSamples - numTrue) / ( numSamples * (numSamples - 1.0))); 
		}
	}
	
	public void xPlus1() {
		x++;
	}
	
	public void nPlus1() {
		n++;
	}
	
	public double getWidth() {
		return width;
	}
	public double getConfidence() {
		return confidence;
	}
	public int getN() {
		return n;
	}
	public int getX() {
		return x;
	}
	public double getP() {
		return p;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setX(int x) {
		this.x = x;
	}
}
