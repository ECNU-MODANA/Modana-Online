package com.modana.manage.alsmc;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;

import Jama.Matrix;
/**
 * 
 * @author JKQ
 *
 * 2016年7月8日下午12:53:13
 */
public class ExeUppaal {
	public static Matrix principalMatrix = null;//主成分矩阵
	//public static ThesisCaseStudy tcs = new ThesisCaseStudy(5);//使用仿真trace进行实验
	public static OneSimulate osim = null;//使用UPPAAL,生成一条仿真的原始trace
	public static int len = 0;
	public static List<ArrayList<String>> traList = new ArrayList<ArrayList<String>>();//预处理阶段，仿真生成的SplitTrace的集合
	public static List<String> staCreList = null;//生成增量trace阶段，产生一条增量trace
	public static ThesisCaseStudy tcs = new ThesisCaseStudy(1);
	
	public static void exe(Integer learnTraceNum,Integer extractTraceNum,Double extractTraceProbability) {
		generatePCASplitTrace(learnTraceNum);
		generatePCATrace();
		List<ArrayList<TreeNode>> traPAList = selectData.getPAData(extractTraceNum,extractTraceProbability);
		PreTree.root =  new TreeNode("root");
		PreTree.createPreTree(traPAList);
		PreTree.reduce_recur(PreTree.root);
		PreTree.reduce2_recur(PreTree.root);
		}
	public static void exeIncre(Integer extractTraceNum,Double extractTraceProbability) {
		generateCrePCASplitTrace();
		generateCrePCATrace();
		selectData.getCrePAData(extractTraceNum,extractTraceProbability);
	}
	public static List<Integer> clientTraceFlag(Integer traNum,Integer extractTraceNum,Double extractTraceProbability){
		List<Integer> traClientTra = new ArrayList<Integer>();
		for (int i = 0; i < traNum; i++) {
			traClientTra.add(BIETool.findForClient(PreTree.root, extractTraceNum, extractTraceProbability));
		}
		return traClientTra;
	}
	public static Double getFinalResult(ArrayList<Integer> traArrayList) {
		return BIETool.finalProbability(traArrayList);
	}
	/**
	 * 预处理阶段1：生产预处理阶段的split trace-->预处理抽象阶段1
	 * @param progressValue
	 * @param n:生成trace数目
	 */
	public static void generatePCASplitTrace(int n) {
		//BufferedWriter bw = null;
		// int count = 0;
		traList.clear();
		len = 0;
		//try {
			for (int m = 0; m < 1; m++) {
				
				/*String tracePath = UserFile.pathPrefix + "trace1" + ".txt";
				FileWriter fw = new FileWriter(tracePath);*/
				
				for (int h = 0; h < n; h++) {
					List<String> staList = new ArrayList<String>();//PCASplitStateList
					//XXX generate new trace
					/*generateNewTrace();
					ArrayList<State> sl = osim.stateList;*/
					
					ArrayList<State> sl = tcs.randomGenOneTrace();
					
					////////////////////////
					//-update progress value-//
					//-----------------------------//
					
					//bw = new BufferedWriter(fw);
					
					//get state list (old)
					
					////////////////////
					/**
					 * First check state and set the result as dimension(state)
					 * Then split state according all dimension(state)
					 */
					int sCheck = 0;
					int sCheck2 = 0;
					for (int i = 0; i < sl.size() - 1; i++) {
						StringBuffer stateString1 = new StringBuffer();
						State prestate = sl.get(i);
						State state = sl.get(i + 1);
						boolean flag = false;
						sCheck = (Check.checkState(prestate,
								UserFile.properties)) ? 1 : 0;
						
						sCheck2 = (Check.checkState(state,
								UserFile.properties)) ? 1 : 0;
						if (i == 0) {
							//bw.write(prestate.time + "");
							stateString1.append(prestate.time+"");
							
							for (int j = 0; j < prestate.values.size(); j++) {
								//bw.write(", "+ prestate.values.get(j).toString());
								stateString1.append(", "+ prestate.values.get(j).toString());
							}
							//bw.write(", " + sCheck);
							stateString1.append(", " + sCheck);
							//bw.write(", " + sCheck);
							stateString1.append(", " + sCheck);
							staList.add(stateString1.toString());
							//bw.write(System.getProperty("line.separator"));
							len++;
						}
						for (int j = State.doubleNum-1; j < state.values.size(); j++) {
							if (!state.values.get(j).toString()
									.equals(prestate.values.get(j).toString())||sCheck!=sCheck2) {
								flag = true;
							} else {
							}
						}
						if (flag&&sCheck2==0) {
							StringBuffer stateString2 = new StringBuffer();
							//bw.write(state.time + "");
							stateString2.append(state.time + "");
							for (int j = 0; j < state.values.size(); j++) {
								//bw.write(", " + state.values.get(j).toString());
								stateString2.append(", " + state.values.get(j).toString());
							}
							//bw.write(", " + sCheck2);
							stateString2.append(", " + sCheck2);
							//bw.write(", " + sCheck2);
							stateString2.append(", " + sCheck2);
							staList.add(stateString2.toString());
							//bw.write(System.getProperty("line.separator"));
							len++;
						} else if(flag&&sCheck2==1){
							StringBuffer stateString2 = new StringBuffer();
							//bw.write(state.time + "");
							stateString2.append(state.time + "");
							for (int j = 0; j < state.values.size(); j++) {
								//bw.write(", " + state.values.get(j).toString());
								stateString2.append(", " + state.values.get(j).toString());
							}
							//bw.write(", " + sCheck2);
							stateString2.append(", " + sCheck2);
							//bw.write(", " + sCheck2);
							stateString2.append(", " + sCheck2);
							staList.add(stateString2.toString());
							//bw.write(System.getProperty("line.separator"));
							len++;
							break;
						}

					}
					traList.add((ArrayList<String>) staList);
					//bw.flush();
				}
			}
			// BIETAlgorithm.run();
		/*} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}*/
		System.out.println();
		System.out.println("---------------PCASplittrace finish!---------------");
	}
	private static void generatePCATrace() {
		selectData selectData = new selectData();
		PCA pca = new PCA();
		BufferedWriter pcabw = null;
		// 获取原始数据
		double[][] primaryArray = selectData.getPCAData();
		// 均值中心化后的矩阵
		double[][] averageArray = pca.changeAverageToZero(primaryArray);
		// 协方差矩阵
		double[][] varMatrix = pca.getVarianceMatrix(averageArray);
		// 特征值矩阵
		double[][] eigenvalueMatrix = pca.getEigenvalueMatrix(varMatrix);
		// 特征向量矩阵
		double[][] eigenVectorMatrix = pca.getEigenVectorMatrix(varMatrix);

		// 主成分矩阵
		//System.out.println("--------------------------------------------");
		principalMatrix = pca.getPrincipalComponent(primaryArray,
				eigenvalueMatrix, eigenVectorMatrix);
		//System.out.println("主成分矩阵: ");
		//principalMatrix.print(6, 3);

		// 降维后的矩阵
		Matrix resultMatrix = pca.getResult(primaryArray, principalMatrix);
		//resultMatrix.print(6, 3);
		double[][] rsArray = resultMatrix.getArray();
		try {
			String pcatracePath = UserFile.pathPrefix + "pcatrace.txt";
			pcabw = new BufferedWriter(new FileWriter(pcatracePath));
			for (int i = 0; i < rsArray.length; i++) {
				for (int j = 0; j < rsArray[0].length; j++) {
					String rString = Double.toString(rsArray[i][j]);
					pcabw.write(rString + ",");
				}
				pcabw.write(i+"");
				pcabw.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pcabw != null)
					pcabw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
 
	}

	/**
	 * 产生增量trace阶段1：增量split trace的产生-->抽象阶段1
	 */
	private static void generateCrePCASplitTrace() {
		len = 0;
		staCreList = new ArrayList<String>();
		/*generateNewTrace();
		ArrayList<State> sl = osim.stateList;*/
		ArrayList<State> sl = tcs.randomGenOneTrace(); //使用仿真trace进行实验
		int sCheck = 0;
		int sCheck2 = 0;
		for (int i = 0; i < sl.size() - 1; i++) {
			StringBuffer stateString1 = new StringBuffer();
			State prestate = sl.get(i);
			State state = sl.get(i + 1);
			boolean flag = false;
			sCheck = (Check.checkState(prestate, UserFile.properties)) ? 1
					: 0;

			sCheck2 = (Check.checkState(state, UserFile.properties)) ? 1 : 0;
			if (i == 0) {
				stateString1.append(prestate.time + "");
				for (int j = 0; j < prestate.values.size(); j++) {
					stateString1.append(", "
							+ prestate.values.get(j).toString());
				}
				stateString1.append(", " + sCheck);
				stateString1.append(", " + sCheck);
				staCreList.add(stateString1.toString());
				len++;
			}
			for (int j = State.doubleNum-1; j < state.values.size(); j++) {
				if (!state.values.get(j).toString()
						.equals(prestate.values.get(j).toString())
						|| sCheck != sCheck2) {
					flag = true;
				} else {
				}
			}
			if (flag && sCheck2 == 0) {
				StringBuffer stateString2 = new StringBuffer();
				stateString2.append(state.time + "");
				for (int j = 0; j < state.values.size(); j++) {
					stateString2.append(", " + state.values.get(j).toString());
				}
				stateString2.append(", " + sCheck2);
				stateString2.append(", " + sCheck2);
				staCreList.add(stateString2.toString());
				len++;
			} else if (flag && sCheck2 == 1) {
				StringBuffer stateString2 = new StringBuffer();
				stateString2.append(state.time + "");
				for (int j = 0; j < state.values.size(); j++) {
					stateString2.append(", " + state.values.get(j).toString());
				}
				stateString2.append(", " + sCheck2);
				stateString2.append(", " + sCheck2);
				staCreList.add(stateString2.toString());
				len++;
				break;
			}

		}
	}
	
	/**
	 * 产生增量trace阶段2：增量PCAtrace的产生-->抽象阶段2+pca学习
	 */
	private static void generateCrePCATrace() {
		selectData selectData = new selectData();
		PCA pca = new PCA();
		BufferedWriter pcabw = null;
		// 获取原始数据
		double[][] primaryArray = selectData.getCrePCAData();
		// 降维后的矩阵
		Matrix resultMatrix = pca.getResult(primaryArray, principalMatrix);
		double[][] rsArray = resultMatrix.getArray();
		try {
			String pcatracePath = UserFile.pathPrefix +"pcacretrace.txt";
			pcabw = new BufferedWriter(new FileWriter(pcatracePath));
			for (int i = 0; i < rsArray.length; i++) {
				for (int j = 0; j < rsArray[0].length; j++) {
					String rString = Double.toString(rsArray[i][j]);
					pcabw.write(rString + ",");
				}
				pcabw.write(i+"");
				pcabw.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pcabw != null)
					pcabw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		primaryArray = null;
	}
	
	
	
	/**
	 * 调用UPPAAL,生成一条新的原始trace
	 */
	public static void generateNewTrace() {
		BufferedReader strCon = null;
		//运行uppaal
		try {
			String st = UserFile.verifytaPath+" "+UserFile.modelPath+" "+UserFile.queryPath;
			Process process = Runtime.getRuntime().exec(st);
			strCon = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			// read pre-information(unnecessary)
			while (!strCon.readLine().endsWith("is satisfied."))
				;
			ArrayList<ArrayList<String>> rawDataList = new ArrayList<ArrayList<String>>();
			new State(0, null); // force State to initialize the static
								// variables
			// ////linux/////
			// FileWriter fw1 = new
			// FileWriter("/home/cb/uppaal64-4.1.18/origin-trace.txt");
			// BufferedWriter bw1 = new BufferedWriter(fw1);
			// ////windows////
			// FileWriter fw1 = new
			// FileWriter("D:\\Program Files\\uppaal-4.1.18\\origin-trace.txt");
			// BufferedWriter bw1 = new BufferedWriter(fw1);
			// ////////
			for (int i = 0; i < State.varNum; ++i) {
				strCon.readLine();
				line = strCon.readLine();
				// /////////
				// bw1.write(line);
				// bw1.write(System.getProperty("line.separator"));
				// /////////
				String[] strArray = splitDataLine(line);
				ArrayList<String> tempList = new ArrayList<String>();
				// find the last value of time '0'
				assert (strArray[0].equals("0"));

				int j = 0;
				while (strArray[j].equals("0")) {
					j += 2;
				}
				// add the first pair of data
				tempList.add(strArray[j - 2]);
				tempList.add(strArray[j - 1]);
				String lastTime = strArray[j - 2];
				for (; j < strArray.length; j += 2) {
					if (!lastTime.equals(strArray[j])) { // remove duplicate
															// data
						tempList.add(strArray[j]);
						tempList.add(strArray[j + 1]);
						lastTime = strArray[j];

					}
				}
				rawDataList.add(tempList);
			}
			// SimulateList simList = new SimulateList();
			// simList.addNewTraceToList(new OneSimulate(rawDataList));
			osim = new OneSimulate(rawDataList);
			// ////////
			// bw1.close();
			// /////////
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (strCon != null)
					strCon.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
/**
 * 对原始trace进行基础预处理
 * @param line:trace
 * @return
 */
	private static String[] splitDataLine(String line) {
		line = line.substring(6, line.length() - 1); // remove the prefix "[0]: (" and the suffix ")"
		return line.split("\\) \\(|,");
	}

}
