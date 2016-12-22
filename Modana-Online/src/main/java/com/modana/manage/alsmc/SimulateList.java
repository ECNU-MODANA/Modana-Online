package com.modana.manage.alsmc;

import java.util.ArrayList;

public class SimulateList {
	
	public ArrayList<OneSimulate> simulateResult;
	
	public SimulateList() {
		
		super();
		this.simulateResult = new ArrayList<OneSimulate>();
	}

	public SimulateList(ArrayList<OneSimulate> simulateResult) {
		
		super();
		this.simulateResult = simulateResult;
	}
	
	public void addNewTraceToList(OneSimulate os) {
		simulateResult.add(os);
	}
	
	
}
