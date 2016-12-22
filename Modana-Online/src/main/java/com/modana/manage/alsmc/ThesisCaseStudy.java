package com.modana.manage.alsmc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class ThesisCaseStudy {

	private ArrayList<State> stateTestList = null;
	private int numOfFile;
	private int indices[];
	private ArrayList<BufferedReader> brList = new ArrayList<BufferedReader>();
	
	private Random random = null;
	
	public ThesisCaseStudy(int numOfFile) {
		this.numOfFile = numOfFile;
		indices = new int[numOfFile];
		for (int i = 0; i < indices.length; i++) {
			indices[i] = 0;
			try {
				brList.add(new BufferedReader(new InputStreamReader(
					new FileInputStream("D:/Traces/trace"+i+".txt"))));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		random = new Random();
	}
	
	public void closeAll() {
		for (int i = 0; i < indices.length; i++) {
			try {
				brList.get(i).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<State> randomGenOneTrace() {
		int i = random.nextInt(numOfFile);
		indices[i] = getTestTrace(i, indices[i]);
		return stateTestList;
	}
	
	public int getTestTrace(int fileId,int lineId) {
		stateTestList = new ArrayList<State>();
		String line = null;
		try {
			BufferedReader br = brList.get(fileId);
			while ((line=br.readLine())!=null) {
				//if (i>=lineId) {
					if (line.isEmpty()) {
						return lineId+1;
					} 
					double[] conArray = selectData.ConvertStrToArray(line);
					List<Object> valueList = new ArrayList<Object>();
					for (int j = 1; j < conArray.length; j++) {
						valueList.add(conArray[j]);
					}
					State state = new State(conArray[0], (ArrayList<Object>) valueList);
					stateTestList.add(state);
				//}
				lineId++;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static void main(String[] args) {
		ThesisCaseStudy tcs = new ThesisCaseStudy(20);
		String path = "D:/Traces/traceTest"+".txt";
		FileOutputStream f = null;
		BufferedWriter bw = null;
		try {
			f = new FileOutputStream(path);
		    bw = new BufferedWriter(new OutputStreamWriter(f));
		    System.out.println(tcs.getTestTrace(0,0));
		    for (State state : tcs.stateTestList) {
			String stastring = state.time+", ";
			for (Object object : state.values) {
				stastring = stastring +object.toString()+", ";
			}
			bw.write(stastring);
			bw.newLine();
		}
	    }
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
