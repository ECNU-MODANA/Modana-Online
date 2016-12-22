package com.modana.manage.alsmc;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class selectData {
	public static int kPCA = State.sumNum - 1;
	public static int kDoubleNum = State.doubleNum;
	public static List<TreeNode> nodeCreList = null;

	public double[][] getPCAData() {
		double[][] primaryData = new double[ExeUppaal.len][kPCA - kDoubleNum];
		String line = null;
		int count = 0;
		// System.out.println(ExeUppaal.staCreList.size());
		for (int i = 0; i < ExeUppaal.traList.size(); i++) {
			List<String> states = ExeUppaal.traList.get(i);
			for (int j = 0; j < states.size(); j++) {
				line = states.get(j);
				double[] conArray = ConvertStrToArray(line);
				for (int k = kDoubleNum; k < kPCA; k++) {
					primaryData[count][k - kDoubleNum] = conArray[k];
				}
				count++;
			}

		}
		return primaryData;
	}

	public double[][] getCrePCAData() {
		double[][] primaryData = new double[ExeUppaal.len][kPCA - kDoubleNum];
		String line = null;
		for (int i = 0; i < ExeUppaal.staCreList.size(); i++) {
			line = ExeUppaal.staCreList.get(i);
			double[] conArray = ConvertStrToArray(line);
			for (int j = kDoubleNum; j < kPCA; j++) {
				primaryData[i][j - kDoubleNum] = conArray[j];
			}
		}
		return primaryData;
	}

	public static void getCrePAData(double n, double p) {
		nodeCreList = new ArrayList<TreeNode>();
		String tracePath = UserFile.pathPrefix + "pcacretrace.txt";
		//String tracePAPath = UserFile.pathPrefix + "pacretrace.txt";
		Map<List<Double>, Integer> map = new HashMap<List<Double>, Integer>();
		FileInputStream f = null;
//		FileOutputStream o = null;
		String line = null;
		try {
			f = new FileInputStream(tracePath);
//			o = new FileOutputStream(tracePAPath);
		} catch (FileNotFoundException e3) {
			e3.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(f));
//		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(o));
		List<double[]> conList = new ArrayList<double[]>();
		List<Double> indexList = new ArrayList<Double>();
		try {
			while ((line = br.readLine()) != null && !line.isEmpty()) {
				double[] conArray = ConvertStrToArray(line);
				conList.add(conArray);
				List<Double> list1 = new ArrayList<Double>();
				for (int i = 0; i < conArray.length - 1; i++) {
					list1.add(conArray[i]);
				}
				if (map.containsKey(list1)) {
					int value = map.get(list1);
					value++;
					map.put(list1, value);
				} else {
					map.put(list1, 1);
				}
			}
			int c = 0;
			double pro = 0.0;
			double length = ExeUppaal.len;
			map = sortMapByValue(map);
			// System.out.println("------------------------------------------------------------------");
			for (Map.Entry<List<Double>, Integer> entry : map.entrySet()) {
				if (c >= n || pro > p) {
					break;
				}
				// System.out.println(entry.getValue());
				List<Double> list2 = new ArrayList<Double>();
				list2 = entry.getKey();
				for (int i = 0; i < conList.size(); i++) {
					double[] conArray = conList.get(i);
					boolean flag = true;
					for (int j = 0; j < list2.size(); j++) {
						if (conArray[j] != list2.get(j)) {
							flag = false;
						}
					}
					if (flag) {
						indexList.add(conArray[conArray.length - 1]);
					}
				}
				c++;
				pro = pro + entry.getValue() / length;
			}
			int count = 0;
			int skipCount = 0;
			for (int j = 0; j < ExeUppaal.staCreList.size(); j++) {
				double[] state = ConvertStrToArray(ExeUppaal.staCreList.get(j));

				if (indexList.contains((double) count) || state[state.length - 1] == 1) {
					TreeNode node = new TreeNode();
					List<Integer> nodeId = new ArrayList<Integer>();
					for (int k = kDoubleNum; k < state.length - 2; k++) {
						nodeId.add((int) state[k]);
					}
					node.nodeId = nodeId.toString();
					node.tcheck = (int) state[state.length - 1];
//					bw.write(ExeUppaal.staCreList.get(j));
//					bw.newLine();
					nodeCreList.add(node);
					skipCount = 0;
				} else {
					if (skipCount == 0) {
						TreeNode node = new TreeNode();
						node.nodeId = "skip";
						nodeCreList.add(node);
//						bw.write("skip");
//						bw.newLine();
					}
					skipCount++;
				}
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
//				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<ArrayList<TreeNode>> getPAData(double n, double p) {
		String tracePath = UserFile.pathPrefix + "pcatrace.txt";
		List<ArrayList<TreeNode>> traPAList = new ArrayList<ArrayList<TreeNode>>();
		traPAList.clear();
		// String tracePAPath = UserFile.pathPrefix + "trace3.txt";
		Map<List<Double>, Integer> map = new HashMap<List<Double>, Integer>();
		FileInputStream f = null;
		// FileOutputStream o = null;
		String line = null;
		try {
			f = new FileInputStream(tracePath);
			// o = new FileOutputStream(tracePAPath);
		} catch (FileNotFoundException e3) {
			e3.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(f));
		// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(o));
		List<double[]> conList = new ArrayList<double[]>();
		// List<Double> indexList = new ArrayList<>();
		Map<Double, Integer> indexMap = new HashMap<Double, Integer>();
		try {
			while ((line = br.readLine()) != null && !line.isEmpty()) {
				double[] conArray = ConvertStrToArray(line);
				conList.add(conArray);
				List<Double> list1 = new ArrayList<Double>();
				for (int i = 0; i < conArray.length - 1; i++) {
					list1.add(conArray[i]);
				}
				if (map.containsKey(list1)) {
					int value = map.get(list1);
					value++;
					map.put(list1, value);
				} else {
					map.put(list1, 1);
				}
			}
			int c = 0;
			double pro = 0.0;
			double length = ExeUppaal.len;
			int sortNum1 = 1;
			map = sortMapByValue(map);
			// System.out.println("------------------------------------------------------------------");
			for (Map.Entry<List<Double>, Integer> entry : map.entrySet()) {
				if (c >= n || pro > p) {
					break;
				}
				// System.out.println(entry.getValue());
				List<Double> list2 = new ArrayList<Double>();
				list2 = entry.getKey();
				for (int i = 0; i < conList.size(); i++) {
					double[] conArray = conList.get(i);
					boolean flag = true;
					for (int j = 0; j < list2.size(); j++) {
						if (conArray[j] != list2.get(j)) {
							flag = false;
						}
					}
					if (flag) {
						// indexList.add(conArray[conArray.length-1]);
						indexMap.put(conArray[conArray.length - 1], sortNum1);
					}
				}
				c++;
				pro = pro + entry.getValue() / length;
				sortNum1++;
			}
			int count = 0;
			int skipCount = 0;
			// System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			for (int i = 0; i < ExeUppaal.traList.size(); i++) {
				List<String> staList = ExeUppaal.traList.get(i);
				List<TreeNode> nodeList = new ArrayList<TreeNode>();
				for (int j = 0; j < staList.size(); j++) {
					double[] state = ConvertStrToArray(staList.get(j));
					/*
					 * for (int k = 0; k < nodeId.size(); k++) {
					 * System.out.print(nodeId.get(k)); } System.out.println();
					 */

					boolean flag = false;
					int sortNum2 = 0;
					for (Map.Entry<Double, Integer> entry : indexMap.entrySet()) {
						if (entry.getKey() == count) {
							flag = true;
							sortNum2 = entry.getValue();
							break;
						}
					}

					// if
					// (indexList.contains((double)count)||state[state.length-1]==1)
					// {
					if (flag || state[state.length - 1] == 1) {
						TreeNode node = new TreeNode();
						List<Integer> nodeId = new ArrayList<Integer>();
						for (int k = kDoubleNum; k < state.length - 2; k++) {
							nodeId.add((int) state[k]);
						}
						node.nodeId = nodeId.toString();
						node.tcheck = (int) state[state.length - 1];
						// bw.write(staList.get(j));
						// bw.write(","+sortNum2);
						// bw.newLine();
						if (!node.nodeId.equals("null")) {
							nodeList.add(node);
						}
						skipCount = 0;
					} else {
						if (skipCount == 0) {
							TreeNode node = new TreeNode();
							node.nodeId = "skip";
							nodeList.add(node);
							// bw.write("skip");
							// bw.newLine();
						}
						skipCount++;
					}
					count++;
				}
				traPAList.add((ArrayList<TreeNode>) nodeList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				// bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
              return traPAList;
	}

	public static Map<List<Double>, Integer> sortMapByValue(Map<List<Double>, Integer> oriMap) {
		Map<List<Double>, Integer> sortedMap = new LinkedHashMap<List<Double>, Integer>();
		if (oriMap != null && !oriMap.isEmpty()) {
			List<Map.Entry<List<Double>, Integer>> entryList = new ArrayList<Map.Entry<List<Double>, Integer>>(
					oriMap.entrySet());
			Collections.sort(entryList, new Comparator<Map.Entry<List<Double>, Integer>>() {
				public int compare(Entry<List<Double>, Integer> entry1, Entry<List<Double>, Integer> entry2) {
					int value1 = 0, value2 = 0;
					try {
						value1 = entry1.getValue();
						value2 = entry2.getValue();
					} catch (NumberFormatException e) {
						value1 = 0;
						value2 = 0;
					}
					return value2 - value1;
				}
			});
			Iterator<Map.Entry<List<Double>, Integer>> iter = entryList.iterator();
			Map.Entry<List<Double>, Integer> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		return sortedMap;
	}

	public static double[] ConvertStrToArray(String str) {
		String[] strArray = null;
		strArray = str.split(",");
		double[] douArray = new double[strArray.length];
		for (int i = 0; i < strArray.length; i++) {
			douArray[i] = Double.parseDouble(strArray[i].toString());
		}
		return douArray;
	}
}
