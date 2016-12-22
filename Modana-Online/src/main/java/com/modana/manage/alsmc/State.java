package com.modana.manage.alsmc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class State {
	public double time;
	public ArrayList<Object> values;
	public static String timeType;
	public static String[] valueType;

	// simple 2-rooms
	/*
	 * public static int doubleNum = 3;//1+2 = 3 public static int intNum =
	 * 9;//7+2 public static int sumNum = 12;//3+9 public static int varNum = 9;
	 * //2+7=9 public static String[] valueNames = new
	 * String[]{"T0","T1","Heater.OFF","Heater.ON_0","Heater.ON_1","Room0.OFF",
	 * "Room0.ON","Room1.OFF","Room1.ON"};
	 */

	// smart building
	
	public static int doubleNum = UserFile.stateDoubleNum+1;// +time
	public static int intNum = UserFile.stateIntNum+2;// + scheck tcheck
	public static int sumNum = doubleNum+intNum;// 16+45
	public static int varNum = UserFile.stateDoubleNum+UserFile.stateIntNum; // 43+15=58

	public static String[] valueNames =  /*String[] valueNames = getValueNames();*/
			new String[] { "T[1]", "T[2]", "T[3]", "T[4]", "T[5]", "bvec[1]", "bvec[2]",
			"bvec[3]", "bvec[4]", "bvec[5]", "Heater(1).c", "Heater(2).c", "Heater(3).c", "u", "discomfort",
			"ControlGet.idle", "ControlGet.choosing", "user(1).absent", "user(1).arrive", "user(1).morning",
			"user(1).afternoon", "user(1).leave", "user(2).absent", "user(2).arrive", "user(2).morning",
			"user(2).afternoon", "user(2).leave", "user(3).absent", "user(3).arrive", "user(3).morning",
			"user(3).afternoon", "user(3).leave", "user(4).absent", "user(4).arrive", "user(4).morning",
			"user(4).afternoon", "user(4).leave", "user(5).absent", "user(5).arrive", "user(5).morning",
			"user(5).afternoon", "user(5).leave", "Heater(1).Off", "Heater(1).On", "Heater(2).Off", "Heater(2).On",
			"Heater(3).Off", "Heater(3).On", "need[1]", "need[2]", "need[3]", "need[4]", "need[5]", "cold[1]",
			"cold[2]", "cold[3]", "cold[4]", "cold[5]" };

	{
	
		timeType = "Double";
		// smart building
		valueType = getValueType(UserFile.stateDoubleNum, UserFile.stateIntNum);
				/*new String[] { "Double", "Double", "Double", "Double", "Double", "Double", "Double", "Double",
				"Double", "Double", "Double", "Double", "Double", "Double", "Double", // Double
				"Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte",
				"Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte",
				"Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte", "Byte",
				"Byte", "Byte", "Byte", // Byte 43
				"Byte" };*/
		// simple 2-rooms
		/*
		 * valueType = new String[]{"Double", "Double", //Double 2 "Byte",
		 * "Byte", "Byte", "Byte", "Byte", "Byte", //Byte 7 "Byte"};
		 */
	}

	public State(double time, ArrayList<Object> values) {
		super();
		this.time = time;
		this.values = values;
		
	}

	public Object getValue(String Valuename) {
		Object object = new Object();
		for (int i = 0; i < valueNames.length; i++) {
			if (valueNames[i].equals(Valuename)) {
				object = values.get(i);
			}
		}
		return object;
	}
	public static String[] getValueType(int initDoubleNum,int initIntNum) {
		String[] strs = new String[initDoubleNum+initIntNum];
		for (int i = 0; i < initDoubleNum; i++) {
			strs[i] = "Double";
		}
		for (int j = initDoubleNum; j < initDoubleNum+initIntNum; j++) {
			strs[j] = "Byte";
		}
		return strs;
	}
	public static String[] getValueNames() {
		String path = UserFile.queryPath;
		FileInputStream f = null;
		String[] strs = null;
		try {
			f = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(f));
		try {
			String strQuery = br.readLine();
			strs = strQuery.split("\\{");
			strQuery = strs[1];
			strs = strQuery.split("\\}");
			strQuery = strs[0];
			strs = strQuery.split(",");
			
		/*    for (int i = 0; i < valueNames.length; i++) {
				System.out.print(valueNames[i]+"  ");
			}*/
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return strs;
	}
}
