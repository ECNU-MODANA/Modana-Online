package com.modana.manage.alsmc;

public class UserFile {
	//---------------file path prefix--------------------
	
	//public static String pathPrefix = "C:/Users/cb219/Desktop/temp/";
	
	public static String pathPrefix = "D:\\modana-online\\traceFolder\\";
	
	//-----------------------------------------------------
	//-----------------------------------------------------
	//--------------------Uppaal file--------------------
	
	/*public static String verifytaPath = "D:\\Progra~1\\uppaal-4.1.19\\bin-Win32\\verifyta";
	public static String modelPath = "C:\\Users\\cb219\\Desktop\\2016硕士论文-程贝\\model\\2room.xml";
	public static String queryPath = "C:\\Users\\cb219\\Desktop\\2016硕士论文-程贝\\model\\room.q";*/
	
	public static String verifytaPath = "D:\\modana-online\\uppaal-4.1.19\\bin-Win32";
	
	/*public static String modelPath = "C:\\Users\\JKQ\\Desktop\\model\\2room.xml";
	public static String queryPath = "C:\\Users\\JKQ\\Desktop\\model\\room.q";*/
	
/*	public static String modelPath = "C:/Users/JKQ/Desktop/model/bluetooth.cav.xml";
	public static String queryPath = "C:\\Users\\JKQ\\Desktop\\model\\bluetooth.cav.q";*/
	
	public static String modelPath = "D:\\modana-online\\demo\\bluetooth.cav.xml";
	public static String queryPath = "D:\\modana-online\\demo\\bluetooth.cav.q";
	
	/**
	 * smartbuilding P=?[F discomfort>=15 ]
	 */

	public static String properties = "P=?[F discomfort>=15 ]";
//	public static String properties = null;
	
	//------------------------state doubleNum intNum    -----------------------------
	
	/**
	 * smartbuilding 15/43 1/6
	 * robot 4/9
	 * bluetooth 2/6
	 * room 2/7
	 */
	public static int stateDoubleNum = 15;
	public static int stateIntNum = 43;
	//------------------------BIET k c    -----------------------------
	public static double bietK = 0.02;
	public static double bietC = 0.95;
	//------------------------EXEuppaal LearnTraceNum extractTraceNum extractTraceProbability   -----------------------------
    public static int learnTraceNum = 250;
    public static int extractTraceNum = 2;
    public static double extractTraceProbability = 0.5;
    //------------------------PCAthreshold    -----------------------------
  	public static double PCAthreshold = 0.5;
    //------------------------PCAthreshold    -----------------------------
  	public static boolean fileSwitch = false;  //False:do not write file, True:write file. Files are used to analysis the trace  
}

