package com.modana.manage.alsmc;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class OneSimulate {
	
	public ArrayList<State> stateList = new ArrayList<State>();
	
	public OneSimulate(ArrayList<ArrayList<String>> rawDataList) {
		super();
		
		int[] indexArray = new int[State.varNum];
		for (int i = 0; i < indexArray.length; i++) {
			indexArray[i] = 0;
		}
		double currentTimeStep; //temp time
		ArrayList<Object> valueList = null; //temp valueList
		//init the first state
		valueList = new ArrayList<Object>();
		
		//The first time point is always 0
		currentTimeStep = 0;
		//get the other variables at the first time point
		for (int i = 0; i < indexArray.length; i++) {
			valueList.add( valueOfData(rawDataList.get(i).get(indexArray[i]+1), State.valueType[i]) );
			indexArray[i] += 2;
		}
		//add State to list
		stateList.add(new State(currentTimeStep, valueList));
		//iteratively adding states
		int minTimeIndex;
		boolean continueMark = true;
		do {
			minTimeIndex = getMinTime(rawDataList, indexArray);
			currentTimeStep = (Double)valueOfData(rawDataList.get(minTimeIndex).get(indexArray[minTimeIndex]), State.timeType);
			valueList = new ArrayList<Object>();
			//synthesize Data at this time point using linear interpolation
			synthesizeDataByStep(rawDataList, indexArray, valueList, minTimeIndex); //including indexArray[...] += 2
			//add this state to list
			stateList.add(new State(currentTimeStep, valueList));
			
			//check if all the indexes are the last one
			for (int i = 0; i < indexArray.length; i++) {
				if (rawDataList.get(i).size() != (indexArray[i]+2)) {
					break;
				} else {
					if (indexArray.length == (i+1)) {
						continueMark = false;
					}
				}
			}
		} while (continueMark);
		//add the last values of all variables
		currentTimeStep = (Double)valueOfData(rawDataList.get(0).get(indexArray[0]), State.timeType);
		valueList = new ArrayList<Object>();
		for (int i = 0; i < indexArray.length; i++) {
			valueList.add( valueOfData(rawDataList.get(i).get(indexArray[i]+1), State.valueType[i]));
		}
		//add the last state to list
		stateList.add(new State(currentTimeStep, valueList));
	}

	//find the minimum time point
	private int getMinTime(ArrayList<ArrayList<String>> rawDataList, int[] indexArray) {
		int index = 0;
		double min = (Double)valueOfData(rawDataList.get(0).get(indexArray[0]), State.timeType);
		for (int i = 1; i < indexArray.length; i++) {
			double temp = (Double)valueOfData(rawDataList.get(i).get(indexArray[i]), State.timeType);
			if (temp < min) {
				min = temp;
				index = i;
			}
		}
		return index;
	}

	//synthesize state-based data from string-based data for one time-step
	private void synthesizeDataByStep(ArrayList<ArrayList<String>> rawDataList, int[] indexArray, ArrayList<Object> valueList, int minIndex) {
		String time = (String)rawDataList.get(minIndex).get(indexArray[minIndex]);
		for (int i = 0; i < indexArray.length; i++) {
			String tempTime = (String)rawDataList.get(i).get(indexArray[i]);
			
			if (tempTime.equals(time)) { //get data directly without processing
				valueList.add( valueOfData(rawDataList.get(i).get(indexArray[i]+1), State.valueType[i]) );
				indexArray[i] += 2;
				
			} else if (State.valueType[i].equals("Byte")) { //process 0/1 data for Byte
				valueList.add( valueOfData(rawDataList.get(i).get(indexArray[i]+1), State.valueType[i]) );
				
			} else if (State.valueType[i].equals("Double")) { //do interpolation for Double
				valueList.add(new Double(doInterpolation(
					(Double)valueOfData(rawDataList.get(i).get(indexArray[i]-2), State.valueType[i])
					, (Double)valueOfData(rawDataList.get(i).get(indexArray[i]), State.valueType[i])
					, (Double)valueOfData(rawDataList.get(i).get(indexArray[i]-1), State.valueType[i])
					, (Double)valueOfData(rawDataList.get(i).get(indexArray[i]+1), State.valueType[i])
					, (Double)valueOfData(time, State.timeType))
					));
			} else {
				System.err.println("Unknown data type (Neither Byte nor Double)!");
			}
		}
	}
	
	//general valueOf method for Integer, Double, Byte, ...
	private final Object valueOfData(String strData, String valueType) {
		try {
			Class<?> cls = Class.forName("java.lang."+valueType);
			Method method = cls.getMethod("valueOf", String.class);
			return method.invoke(cls, strData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//linear interpolation
	private final double doInterpolation(double x1, double x2, double y1, double y2, double x) {
		if (x1 != x2 && y1 != y2) {
			//compute function y=ax+b
			double a = (y1-y2) / (x1-x2);
			double b = y1 - x1*a;
			return (a*x+b);
			
		} else {
			return y1;
		}
	}
	
}
