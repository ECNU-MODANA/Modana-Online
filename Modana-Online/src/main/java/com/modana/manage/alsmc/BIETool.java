package com.modana.manage.alsmc;
import java.util.ArrayList;
import java.util.List;
import com.modana.manage.smc.BIETAlgorithm;
import com.wls.manage.service.base.HttpService;
import com.wls.manage.service.base.impl.HttpServiceImpl;

public class BIETool {
	
	
	public static int m = 0;//the number of ReduceTree Node(node.f>0)
	public static List<TreeNode> terNodes = new ArrayList<TreeNode>();
	
	//probability result set
	public static List<double[]> rs = new ArrayList<double[]>();
	
	public static void getM(TreeNode treeNode)
	{
		if(treeNode != null) 
		{	
			for (TreeNode index : treeNode.childList) 
			{
				if (index.f>0) {
					index.num = m;
					terNodes.add(index);
					System.out.println(m+":"+index.f+","+index.n);
					m++;
				}
				if (index.childList != null && index.childList.size() > 0 ) 
				{	
					getM(index);
				}
			}
		}
	}
	public static int findEndNode(TreeNode treeNode,List<TreeNode> nodeList) {
		TreeNode curNode = treeNode;
		for (int i = 0;; i++) {
			if (curNode.childList.isEmpty()) {
				return curNode.num;
			}
			if (nodeList.size() == i) {
				if (curNode.f > 0) {
					return curNode.num;
				}
				else {
					return -1;
				}
			}
			if (nodeList.size() - 1 == i && curNode.f > 0) {
				return curNode.num;
			}
			boolean found = false;
			boolean finish = false;
			for (TreeNode index : curNode.childList) {
				if (index.nodeId!=null) {
					if (index.nodeId.equals(nodeList.get(i).nodeId)) {
						if (!index.seqList.isEmpty()) {
							for (TreeNode seqNode  : index.seqList) {
								i++;
								if (i==nodeList.size()) {
									break;
								}
								if(find(seqNode,nodeList.get(i))){
									if (index.f==0) {
										System.err.println("findEndNode-->index.f==0");
									}
									return index.num;
								}
								else if (seqNode.nodeId.equals(nodeList.get(i).nodeId)) {
									
								}
								else {
									finish = true;
									break;
								}
							}
						}
						curNode = index;
						found = true;
						break;
					}
					
				}
				else {
					if (index.endList.contains(nodeList.get(i).nodeId)) {
						curNode = index;
						found = true;
						break;
					}
					
				}
				if (finish) {
					break;
				}
			}
			if (!found) {
				return -1;
			}
		}
	}
	private static boolean find(TreeNode seqNode, TreeNode treeNode) {
		for (String str : seqNode.endList) {
			if (str.equals(treeNode.nodeId)) {
				return true;
			}
		}
		return false;
	}
	
	
	public static Integer findForClient(TreeNode treeNode,Integer extractTraceNum,Double extractTraceProbability) {
		ExeUppaal.exeIncre(extractTraceNum,extractTraceProbability);
		List<TreeNode> nodeList= selectData.nodeCreList;
		TreeNode laNode = nodeList.get(nodeList.size()-1);
		if (laNode.tcheck==0) {
			return -1;
		}
		return findEndNode(treeNode, nodeList);
	}
	
	public static double finalProbability(ArrayList<Integer> traArrayList) {
		getM(PreTree.root);
		int count = m + 1;
		double finalP = 0;
		boolean[] ends = new boolean[count];
		for (int i = 0; i < ends.length; i++) {
			ends[i] = false;
		}
		BIETAlgorithm[] bietAlgorithms = new BIETAlgorithm[count]; 
		for (int i = 0; i < bietAlgorithms.length-1; i++) {
			TreeNode treeNode = terNodes.get(i);
			bietAlgorithms[i] = new BIETAlgorithm(UserFile.learnTraceNum, treeNode.f, UserFile.bietK, UserFile.bietC);
		}
		bietAlgorithms[bietAlgorithms.length-1] = new BIETAlgorithm(UserFile.learnTraceNum, 0, UserFile.bietK, UserFile.bietC);
		while (count>0) {
			int itrace = 0;
			int num = 0;
			/*String msg="TraceNum="+"10"+"&extractTraceNum=2&extractTraceProbability=0.5";
            HttpService  httpService = new HttpServiceImpl();
			httpService.sendPost("http://localhost:8989/i/dalsmc/ClientTrace", msg,100);*/
			if (traArrayList.get(itrace)!=-1) {
				num = traArrayList.get(itrace);
//				System.out.println("-----------------------------");
//				for (int i = 0; i < nodeList.size(); i++) {
//					System.out.print(nodeList.get(i).nodeId+",");
//				}
//				System.out.println();
//				System.out.println("-----------------------------");
//				System.out.println("----->"+num);
				if (num<0) {
					bietAlgorithms[bietAlgorithms.length-1].xPlus1();
					num = bietAlgorithms.length - 1;
				}
				else {
					bietAlgorithms[num].xPlus1();
				}
			}
			for (int i = 0; i < bietAlgorithms.length; i++) {
				bietAlgorithms[i].nPlus1();
			}
			if (!ends[num]) {
				if (bietAlgorithms[num].run()) {
					ends[num] = true;
					count = count - 1;
				}
			} else {
				for (int i = 0; i < bietAlgorithms.length; i++) {
					if (!ends[i]) {
						if (bietAlgorithms[i].run()) {
							ends[i] = true;
							count = count - 1;
						}
						num = i;
						break;
					}
				}
			}
			String gammaString = bietAlgorithms[num].getGamma()+"";
			if (gammaString.length() > 7) {
				gammaString = gammaString.substring(0, 7);
			}
			itrace++;
		}

		finalP = 0;
		String[] pStrings = new String[bietAlgorithms.length];
		for (int i = 0; i < bietAlgorithms.length; i++) {
			pStrings[i] =  bietAlgorithms[i].getP()+"-->  ";
			finalP = bietAlgorithms[i].getP() + finalP;
		}
		System.out.println(finalP);
		
		
		for (int i = 0; i < bietAlgorithms.length; i++) {
			bietAlgorithms[i].run();
		}
		
		
		finalP = 0;
		for (int i = 0; i < bietAlgorithms.length; i++) {
			pStrings[i] = pStrings[i] + bietAlgorithms[i].getP()+"-->  ";
			finalP = bietAlgorithms[i].getP() + finalP;
		}
		System.out.println(finalP);
		
		finalP = 0;
		for (int i = 0; i < bietAlgorithms.length; i++) {
			if (bietAlgorithms[i].getP()<bietAlgorithms[i].getK()) {
				pStrings[i] = pStrings[i] + bietAlgorithms[i].getX()/(double)bietAlgorithms[i].getN() +",  ";
				finalP = bietAlgorithms[i].getX()/(double)bietAlgorithms[i].getN() + finalP;
			}
			else {
				pStrings[i] = pStrings[i] + bietAlgorithms[i].getP();
				finalP = bietAlgorithms[i].getP() + finalP;
			}
		}
		
		System.out.println(finalP);
		for (int i = 0; i < pStrings.length; i++) {
			System.out.println(pStrings[i]);
		}
		finalP = 0;
		//Test
		for (int i = 0; i < bietAlgorithms.length; i++) {
			double[] bRs = new double[4];
			bRs[0] = i;
			bRs[1] = bietAlgorithms[i].getX();
			bRs[2] = bietAlgorithms[i].getN();
			bRs[3] = bietAlgorithms[i].getP();
			rs.add(bRs);
			if (bietAlgorithms[i].getP()<bietAlgorithms[i].getK()) {
				finalP = bietAlgorithms[i].getX()/(double)bietAlgorithms[i].getN() + finalP;
			}
			else {
				finalP = bietAlgorithms[i].getP() + finalP;
			}
		}
		double[] bRs = new double[1];
		bRs[0] = finalP;
		rs.add(bRs);
		return finalP;
	}
}
