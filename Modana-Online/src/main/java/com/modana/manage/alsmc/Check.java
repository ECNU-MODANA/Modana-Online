package com.modana.manage.alsmc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
	/*public static boolean checkTrace(ArrayList<State> stateList,String property) {
		 //match whole property
		 Pattern pattern = Pattern.compile("([PR]=\\?)\\s?\\[\\s?([FG]<?=?0?.?[0-9]*)\\s([a-zA-EH-OQS-Z_0-9]\\w*[><!=]=?-?[0-9]?.?[0-9]*( [&|] [a-zA-EH-OQS-Z_0-9]\\w*[><!=]=?-?[0-9]?.?[0-9]*)*)\\s? ]");
	     Matcher matcher = pattern.matcher(property);
	     //match and find like PR=? and send value to  frStringBuffer
	     Pattern prPattern = Pattern.compile("([PR]=\\?)"); 
	     Matcher prMatcher = prPattern.matcher(property);
	     StringBuffer prStringBuffer = new StringBuffer();
	     //match and find like FG<=3 and send value to  fgStringBuffer
	     Pattern fgPattern = Pattern.compile("[FG]<?=?(0?.?[0-9]*)");
	     Matcher fgMatcher = fgPattern.matcher(property);
	     StringBuffer fgStringBuffer = new StringBuffer();
	     //match and find like $or| and send value to opList
	     Pattern opPattern = Pattern.compile("[&|]");
	     Matcher opMatcher = opPattern.matcher(property);
	     List<String> opList = new ArrayList<String>();
	     //match and find like s!=5 and send value to exVList exOList exNList
	     Pattern exPattern = Pattern.compile("([a-zA-EH-OQS-Z_0-9]\\w*)([><!=]=?)(-?[01]?.?[0-9]*)");
	     Matcher exMatcher = exPattern.matcher(property);
	     List<String> exVList = new ArrayList<String>();
	     List<String> exOList = new ArrayList<String>();
	     List<String> exNList = new ArrayList<String>();
	     //match and find number in like FG<=3 and send value to step
	     Pattern fgNPattern = Pattern.compile("([FG]<?=?)(0?.?[0-9]*)");
		 if (matcher.matches()) {
			while (prMatcher.find()) {
				prStringBuffer.append(prMatcher.group());
			}
			while (fgMatcher.find()) {
				fgStringBuffer.append(fgMatcher.group());
			}
			Matcher fgNMatcher = fgNPattern.matcher(fgStringBuffer.toString());
		    double fgN = 0.0;
			while (opMatcher.find()) {
				opList.add(opMatcher.group());
			}
			while (exMatcher.find()) {
				exVList.add(exMatcher.group(1));
				exOList.add(exMatcher.group(2));
				exNList.add(exMatcher.group(3));
			}	
			if (prStringBuffer.toString().startsWith("P")) {
				if (fgStringBuffer.toString().startsWith("F")) {
//					if (fgStringBuffer.toString().length()<=1) {
//					 System.out.println(exVList+"============����");
//					 System.out.println(exOList+"============������");
//					 System.out.println(exNList+"============����ֵ");
				
					 List<Boolean> flag = null;
					     	for (int i = 0; i < stateList.size(); i++) {
					     		flag = new ArrayList<Boolean>();
					     		for (int j = 0; j < exVList.size(); j++) {
//					     		 System.out.println(stateList.get(i).getValue(exVList.get(j))+"============��ӡ����ֵ"+exVList.get(j));
								 String op =  exOList.get(j);
								 if (op.equals("==")) {
									 if (Double.valueOf(exNList.get(j))==Double.valueOf(stateList.get(i).getValue(exVList.get(j)).toString())) {
											flag.add(true);
									 }
									 else{
										 flag.add(false);
									 }
								 } 
								 else if (op.equals("<")) {
									if (Double.valueOf(stateList.get(i).getValue(exVList.get(j)).toString())<Double.valueOf(exNList.get(j))) {
										flag.add(true);
									}
									else{
										flag.add(false);
									 }
								 }
								 else if (op.equals("<=")) {
										if (Double.valueOf(stateList.get(i).getValue(exVList.get(j)).toString())<=Double.valueOf(exNList.get(j))) {
											flag.add(true);
										}
										else{
											flag.add(false);
										 }
									}
								 else if(op.equals(">")){
									 if ((Double.valueOf(stateList.get(i).getValue(exVList.get(j)).toString())>Double.valueOf(exNList.get(j))) {
										 flag.add(true);
										}
									 else{
										 flag.add(false);
									 }
								  }
								 else if(op.equals(">=")){
									 if (Double.valueOf(stateList.get(i).getValue(exVList.get(j)).toString())>=Double.valueOf(exNList.get(j))) {
										 flag.add(true);
										}
									 else{
										 flag.add(false);
									 }
								  }
								 else if(op.equals("!=")){
									 if (Double.valueOf(stateList.get(i).getValue(exVList.get(j)).toString())!=Double.valueOf(exNList.get(j))) {
										 flag.add(true);
										}
									 else{
										 flag.add(false);
									 }
								  } 
								 else {
									
								  }
//								 System.out.println(flag);
							  }			     	
					     		int count = 0;
					     		for (int k = 0; k < flag.size(); k++) {
									if (flag.get(k)==true) {
										count++;
									}
							if (opList.size()!=0) {
								if (opList.get(0).equals("&")) {
						     		  if (count==flag.size()) {
										return true;
									  }
						     		  else{
						     			
						     		  }
									}
									else if (opList.get(0).equals("|")) {
										 if (count>=1) {
												return true;
											  }
								     		  else{
								     			
								     	}
									}
							}
							else {
								 if (count>=1) {
										return true;
									  }
						     		  else{
						     			
						     	}
							 }
							
							}
					     }
					     		
				 }
					else if(fgStringBuffer.toString().startsWith("G")) {
//						if (fgStringBuffer.toString().length()<=1) {
						 List<Boolean> flag = null;

					     	for (int i = 0; i < stateList.size(); i++) {
					     		flag = new ArrayList<>();
					     		for (int j = 0; j < exVList.size(); j++) {
//					     		 System.out.println(trace.GetVar(i, exVList.get(j))+"============��ӡ����ֵ"+exVList.get(j));
								 String op =  exOList.get(j);
								 if (op.equals("==")) {
									 if (Double.valueOf(exNList.get(j))==(double)stateList.get(i).getValue(exVList.get(j))) {
											flag.add(true);
									 }
									 else{
										 flag.add(false);
									 }
								 } 
								 else if (op.equals("<")) {
									if ((double)stateList.get(i).getValue(exVList.get(j))<Double.valueOf(exNList.get(j))) {
										flag.add(true);
									}
									else{
										flag.add(false);
									 }
								 }
								 else if (op.equals("<=")) {
										if ((double)stateList.get(i).getValue(exVList.get(j))<=Double.valueOf(exNList.get(j))) {
											flag.add(true);
										}
										else{
											flag.add(false);
										 }
									}
								 else if(op.equals(">")){
									 if ((double)stateList.get(i).getValue(exVList.get(j))>Double.valueOf(exNList.get(j))) {
										 flag.add(true);
										}
									 else{
										 flag.add(false);
									 }
								  }
								 else if(op.equals(">=")){
									 if ((double)stateList.get(i).getValue(exVList.get(j))>=Double.valueOf(exNList.get(j))) {
										 flag.add(true);
										}
									 else{
										 flag.add(false);
									 }
								  }
								 else if(op.equals("!=")){
									 if ((double)stateList.get(i).getValue(exVList.get(j))!=Double.valueOf(exNList.get(j))) {
										 flag.add(true);
										}
									 else{
										 flag.add(false);
									 }
								  } 
								 else {
									
								  }
//								 System.out.println(flag);
							  }			     	
					     		int count = 0;
					     		for (int k = 0; k < flag.size(); k++) {
									if (flag.get(k)==true) {
										count++;
									}
								if (opList.get(0).equals("&")) {
					     		  if (count!=flag.size()) {
									return false;
								  }
					     		  else{
					     			return true;
					     		  }
								}
								else if (opList.get(0).equals("|")) {
//									 System.out.println(opList+"============&or|");
									 if (count==0) {
											return false;
										  }
							     		  else{	
							     			 return true;
							     	}
								}
							}
					     }
//						else if(fgStringBuffer.toString().length()>2){
//							while (fgNMatcher.find()) {
//								fgN = Double.valueOf(fgNMatcher.group(2));
////								System.out.println(fgStringBuffer.toString());
//								System.out.println("���F��bound"+fgN);
//							}
//							
//						  }
					}
				}
			else {
				System.out.println("Reward Algorithm");
			}
//       	     System.out.println(fgStringBuffer.toString());
//	       	 System.out.println(opList);
//			 System.out.println(exVList);
//			 System.out.println(exOList);
//			 System.out.println(exNList);
		 }
	     else
	    	 System.out.println("expresion syntax error");
		 return false;
	}*/
	
	
	
	public static boolean checkState(State state,String property) {
		 if (property==null) {
				new Exception("property is null");
			 }
		 //match whole property
		 Pattern pattern = Pattern.compile("([PR]=\\?)\\s?\\[\\s?([FG]<?=?0?.?[0-9]*)\\s([a-zA-EH-OQS-Z_0-9]\\w*[><!=]=?-?[0-9]?.?[0-9]*( [&|] [a-zA-EH-OQS-Z_0-9]\\w*[><!=]=?-?[0-9]?.?[0-9]*)*)\\s? ]");
	     Matcher matcher = pattern.matcher(property);
	     //match and find like PR=? and send value to  frStringBuffer
	     Pattern prPattern = Pattern.compile("([PR]=\\?)"); 
	     Matcher prMatcher = prPattern.matcher(property);
	     StringBuffer prStringBuffer = new StringBuffer();
	     //match and find like FG<=3 and send value to  fgStringBuffer
	     Pattern fgPattern = Pattern.compile("[FG]<?=?(0?.?[0-9]*)");
	     Matcher fgMatcher = fgPattern.matcher(property);
	     StringBuffer fgStringBuffer = new StringBuffer();
	     //match and find like $or| and send value to opList
	     Pattern opPattern = Pattern.compile("[&|]");
	     Matcher opMatcher = opPattern.matcher(property);
	     List<String> opList = new ArrayList<String>();
	     //match and find like s!=5 and send value to exVList exOList exNList
	     Pattern exPattern = Pattern.compile("([a-zA-EH-OQS-Z_0-9]\\w*)([><!=]=?)(-?[01]?.?[0-9]*)");
	     Matcher exMatcher = exPattern.matcher(property);
	     List<String> exVList = new ArrayList<String>();
	     List<String> exOList = new ArrayList<String>();
	     List<String> exNList = new ArrayList<String>();
	     //match and find number in like FG<=3 and send value to step
	     Pattern fgNPattern = Pattern.compile("([FG]<?=?)(0?.?[0-9]*)");
		 if (matcher.matches()) {
			while (prMatcher.find()) {
				prStringBuffer.append(prMatcher.group());
			}
			while (fgMatcher.find()) {
				fgStringBuffer.append(fgMatcher.group());
			}
			Matcher fgNMatcher = fgNPattern.matcher(fgStringBuffer.toString());
		    double fgN = 0.0;
			while (opMatcher.find()) {
				opList.add(opMatcher.group());
			}
			while (exMatcher.find()) {
				exVList.add(exMatcher.group(1));
				exOList.add(exMatcher.group(2));
				exNList.add(exMatcher.group(3));
			}	
			if (prStringBuffer.toString().startsWith("P")) {
				if (fgStringBuffer.toString().startsWith("F")||fgStringBuffer.toString().startsWith("G")) {
					 List<Boolean> flag = null;
					     		flag = new ArrayList<Boolean>();
					     		for (int j = 0; j < exVList.size(); j++) {
//					     		 System.out.println(stateList.get(i).getValue(exVList.get(j))+"============��ӡ����ֵ"+exVList.get(j));
								 String op =  exOList.get(j);
								 if (op.equals("==")) {
									 if (Double.valueOf(exNList.get(j))==Double.valueOf(state.getValue(exVList.get(j)).toString())) {
											flag.add(true);
									 }
									 else{
										 flag.add(false);
									 }
								 } 
								 else if (op.equals("<")) {
									if (Double.valueOf(state.getValue(exVList.get(j)).toString())<Double.valueOf(exNList.get(j))) {
										flag.add(true);
									}
									else{
										flag.add(false);
									 }
								 }
								 else if (op.equals("<=")) {
										if (Double.valueOf(state.getValue(exVList.get(j)).toString())<=Double.valueOf(exNList.get(j))) {
											flag.add(true);
										}
										else{
											flag.add(false);
										 }
									}
								 else if(op.equals(">")){
									 if (Double.valueOf(state.getValue(exVList.get(j)).toString())>Double.valueOf(exNList.get(j))) {
										 flag.add(true);
										}
									 else{
										 flag.add(false);
									 }
								  }
								 else if(op.equals(">=")){
									 if (Double.parseDouble(state.getValue(exVList.get(j)).toString())>=Double.valueOf(exNList.get(j))) {
										 flag.add(true);
										}
									 else{
										 flag.add(false);
									 }
								  }
								 else if(op.equals("!=")){
									 if (Double.valueOf(state.getValue(exVList.get(j)).toString())!=Double.valueOf(exNList.get(j))) {
										 flag.add(true);
										}
									 else{
										 flag.add(false);
									 }
								  } 
								 else {
									
								  }
//								 System.out.println(flag);
							  }			     	
					     		int count = 0;
					     		for (int k = 0; k < flag.size(); k++) {
									if (flag.get(k)==true) {
										count++;
									}
							if (opList.size()!=0) {
								if (opList.get(0).equals("&")) {
						     		  if (count==flag.size()) {
										return true;
									  }
						     		  else{
						     			
						     		  }
									}
									else if (opList.get(0).equals("|")) {
										 if (count>=1) {
												return true;
											  }
								     		  else{
								     			
								     	}
									}
							}
							else {
								 if (count>=1) {
										return true;
									  }
						     		  else{
						     			
						     	}
							 }
							
							}		     		
/*						}
					else if(fgStringBuffer.toString().length()>2){
						while (fgNMatcher.find()) {
							fgN = Double.valueOf(fgNMatcher.group(2));
//							System.out.println(fgStringBuffer.toString());
							System.out.println("���F��bound"+fgN);
						}
						
					  }*/
				   }		
				}
			else {
				System.out.println("Reward Algorithm");
			}
//      	     System.out.println(fgStringBuffer.toString());
//	       	 System.out.println(opList);
//			 System.out.println(exVList);
//			 System.out.println(exOList);
//			 System.out.println(exNList);
		 }
	     else
	    	 System.out.println("expresion syntax error");
		 return false;
	}
}
