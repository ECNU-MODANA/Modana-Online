package com.modana.manage.alsmc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class PreTree {
	public static TreeNode root  = null;
	public static TreeNode currentNode = new TreeNode();
	public static int fMin = 0;
	public static int fMax = 0;
	static int con = 0;
	public static void createPreTree(List<ArrayList<TreeNode>> traPAList) {
		for (int i = 0; i < traPAList.size(); i++) {
			currentNode = root;
			List<TreeNode> nodeList = traPAList.get(i);
			root.n++;
			int last = nodeList.size() - 1;
			if (nodeList.get(last).tcheck == 0) {
				root.f++;
			} else {
				for (TreeNode stateNode : nodeList) {
					boolean flag = false;
					for (TreeNode child : currentNode.childList) {
						if (stateNode.nodeId.equals(child.nodeId)) {
							child.n++;
							currentNode = child;
							flag = true;
							break;
						}
						
					}
					if (!flag) {
						currentNode.childList.add(stateNode);
						stateNode.n = 1;
						currentNode = stateNode;
					}
					if (stateNode.tcheck==1) {
						currentNode.f++;
					}
				}
			}
		}
		fMax = (root.n - root.f)/5;
		fMin = (root.n - root.f)/10;
	}
	public static boolean reduce_recur(TreeNode d) {
		if (d.n<fMin) {
			return true;
		}
		List<TreeNode> needList = new ArrayList<TreeNode>();
		TreeNode di = null;
		for (int i = 0; i < d.childList.size(); i++) {
		    di = d.childList.get(i);
			if (reduce_recur(di)) {
				needList.add(di);
			}
		}
		while (true) {
			if (needList.size()==0) {
				break;
			}
			if (needList.size()==1) {
				di = needList.get(0);
				if (!"root".equals(d.nodeId)&&d.childList.size()==1&&(d.f+di.f)<fMax) {
					d.f = d.n;
					d.childList.remove(di);
				}
				else if (d.childList.size()>1) {
					TreeNode dmin = null;
					int i = 0;
					for (; i < d.childList.size(); i++) {
						if (d.childList.get(i).f != 0 &&d.childList.get(i)!=di) {
							dmin = d.childList.get(i);
							break;
						}
					}
					if (i == d.childList.size()) {
						for (i = 0; i < d.childList.size(); i++) {
							if (d.childList.get(i)!=di) {
								dmin = d.childList.get(i);
								break;
							}
						}
					} else {
						for (; i < d.childList.size(); i++) {
							if (d.childList.get(i).f!=0&&d.childList.get(i).f<dmin.f&&d.childList.get(i)!=di) {
								dmin = d.childList.get(i);
							}
						}
					}
					
					if ((dmin.f+di.f)<fMax) {
//						//////////////
//						String strDmin = "NodeId:"+dmin.nodeId+",f:"+dmin.f+",n:"+dmin.n+",seqSize:"+dmin.seqList.size()+",endSize:"+dmin.endList.size();
//						String strDi = "NodeId:"+di.nodeId+",f:"+di.f+",n:"+di.n+",seqSize:"+di.seqList.size()+",endSize:"+di.endList.size();
//						/////////////
						
						dmin.f += di.n;
						dmin.n += di.n;
						if (di.nodeId!=null) {
							dmin.endList.add(di.nodeId);
						}
						else {
							dmin.endList.addAll(di.endList);
						}
						d.childList.remove(di);
						needList.remove(di);
//						////////////////////////////////////////
//						String strDmin2 = "NodeId:"+dmin.nodeId+",f:"+dmin.f+",n:"+dmin.n+",seqSize:"+dmin.seqList.size()+",endSize:"+dmin.endList.size();
//						String strDi2 = "NodeId:"+di.nodeId+",f:"+di.f+",n:"+di.n+",seqSize:"+di.seqList.size()+",endSize:"+di.endList.size();
//						iteratorTree2(PreTree.root);
//							System.out.println("strDmin-->"+strDmin);
//							System.out.println("strDmin2-->"+strDmin2);
//							System.out.println("strDi-->"+strDi);
//							System.out.println("strDi2-->"+strDi2);
//						///////////////////////////////////
					}
				}
				break;
			}
			// merge needList size > 1
			Collections.sort(needList, new Comparator<TreeNode>() {
				public int compare(TreeNode arg0, TreeNode arg1) {
					return arg0.getN().compareTo(arg1.getN());
				}
			});
			TreeNode bNode = needList.get(0);
			TreeNode sNode = needList.get(needList.size()-1);
			TreeNode mNode = new TreeNode();
//			/////////////////////////////
//			String strB = "NodeId:"+bNode.nodeId+",f:"+bNode.f+",n:"+bNode.n+",seqSize:"+bNode.seqList.size()+",endSize:"+bNode.endList.size();
//			String strS = "NodeId:"+sNode.nodeId+",f:"+sNode.f+",n:"+sNode.n+",seqSize:"+sNode.seqList.size()+",endSize:"+sNode.endList.size();
//			String strM = "NodeId:"+mNode.nodeId+",f:"+mNode.f+",n:"+mNode.n+",seqSize:"+mNode.seqList.size()+",endSize:"+mNode.endList.size();
//			int tempNeedSize = needList.size();
//			////////////////////////////
			if (bNode.nodeId!=null) {
				mNode.endList.add(bNode.nodeId);
			}
			else {
				mNode.endList.addAll(bNode.endList);
			}
			if (sNode.nodeId!=null) {
				mNode.endList.add(sNode.nodeId);
			}
			else {
				mNode.endList.addAll(sNode.endList);
			}
			mNode.f = bNode.n + sNode.n;
			mNode.n = bNode.n + sNode.n;
			
			d.childList.remove(sNode);
			d.childList.remove(bNode);
			d.childList.add(mNode);
			
			needList.remove(sNode);
			needList.remove(bNode);
			if (mNode.n<fMin) {
				needList.add(mNode);
			}
//			// //////////////////////////////////////
//			String strB2 = "NodeId:"+bNode.nodeId+",f:"+bNode.f+",n:"+bNode.n+",seqSize:"+bNode.seqList.size()+",endSize:"+bNode.endList.size();
//			String strS2 = "NodeId:"+sNode.nodeId+",f:"+sNode.f+",n:"+sNode.n+",seqSize:"+sNode.seqList.size()+",endSize:"+sNode.endList.size();
//			String strM2 = "NodeId:"+mNode.nodeId+",f:"+mNode.f+",n:"+mNode.n+",seqSize:"+mNode.seqList.size()+",endSize:"+mNode.endList.size();
//			iteratorTree2(PreTree.root);
//				System.out.println("strB-->" + strB);
//				System.out.println("strB2-->" + strB2);
//				System.out.println("strS-->" + strS);
//				System.out.println("strS2-->" + strS2);
//				System.out.println("strM-->" + strM);
//				System.out.println("strM2-->" + strM2);
//				System.out.println("needSize-->" + tempNeedSize);
//				System.out.println("needSize2-->" + needList.size());
//			// /////////////////////////////////
		}
		return false;
	}
	
	
	public static void reduce2_recur(TreeNode d){
		boolean needRecur = false;
		TreeNode dTemp = d;
		if (d.childList.size()>1) {
			needRecur = true;
		}
		else if (d.childList.size()==1) {
			do {
				dTemp = dTemp.childList.get(0);
				if (dTemp.childList.size()>1) {
					needRecur = true;
					break;
				}
				else {
					if (d.f<fMin&&(d.f+dTemp.f)<fMax) {
						//Merge
						d.f	 = d.f + dTemp.f;
						if (dTemp.childList.size()==1) {
							if (dTemp.nodeId==null) {
								System.err.println("reduce2_recur-->dTemp.nodeid==null");
							}
							d.seqList.add(dTemp);
							d.childList.remove(dTemp);
							d.childList.add(dTemp.childList.get(0));
						}
						else {
							d.childList.remove(dTemp);
						}
					}
					else {
						if (dTemp.childList.size()==1) {
							d = dTemp;
						}
						else {
							break;
						}
					}
				}
			} while (dTemp.childList.size()>0);
		}
		if (needRecur) {
			for (TreeNode di : dTemp.childList) {
				reduce2_recur(di);
			}
		}
	}
	
	public static  boolean iteratorTree2(TreeNode treeNode) {
		Queue<TreeNode> queue = new LinkedBlockingQueue<TreeNode>();
		queue.add(treeNode);
		//int sum = 0;
		while (!queue.isEmpty()) {
			
			TreeNode node = queue.poll();

			int sumF = 0;
			for (int i = 0; i < node.childList.size(); i++) {
				sumF  = sumF + node.childList.get(i).n;
			}
			System.out.println(node.nodeId+","+node.f+","+node.n);
			/*if (sumF!=(node.n-node.f)) {
				System.out.println("Wrong Tree detected:  "+sumF+"---->"+(node.n-node.f));
				System.out.println(node.nodeId+","+node.f+","+node.n);
				for (TreeNode dmin : node.childList) {
					System.out.println("NodeId:"+dmin.nodeId+",f:"+dmin.f+",n:"+dmin.n+",seqSize:"+dmin.seqList.size()+",endSize:"+dmin.endList.size()); 
				}
				return false;
			}*/
			if (node.childList.size()>0) {
				for (int i = 0; i < node.childList.size(); i++) {
					queue.add(node.childList.get(i));
				}
			}
		}
		return true;
	}
	
}
