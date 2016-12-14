package com.wls.manage.crawler.tengxun;
import com.wls.manage.service.base.HttpService;
import com.wls.manage.service.base.impl.HttpServiceImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by haolidong on 2016/11/21.
 */
public class genHTML {
    public static void Gen(TXSciBean txsci,String filename){
    	String HTMLcontent="";
//        String HTMLcontent="<html><body>";
//        HTMLcontent+="<h1><font color=\"red\"><p align=\"center\">"+txsci.getTitle()+"</p></font></h1>\n";
//        HTMLcontent+="<p align=\"right\"><font color=\"gray\">"+txsci.getData()+"</font></p>\n";
        ArrayList<String> as = new ArrayList<String>();

        String pic="";
        int picCount=1;
        for(Content con :txsci.ac){
            if(con.isImg()){
                HTMLcontent+="<img src=\""+con.getCon()+"\" border=\"0\"/>\n";
                if(picCount<=3){
                    if(picCount==1){
                        pic+=con.getCon();
                    }else {
                        pic=pic+";"+con.getCon();
                    }

                }

            }else{
                if(con.getCon().equals(""))continue;
                HTMLcontent+="<p>"+con.getCon()+"</p>\n";
            }
        }
        try{
            //"main\TxData\Test.html"
            FileOutputStream fo = new FileOutputStream(new File(filename));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fo));
            bw.write(HTMLcontent);
            System.out.println(pic);
            HttpService httpService = new HttpServiceImpl();
            String msg="content="+HTMLcontent+"&infocategory=1&source=腾讯新闻&coverpiclist="+pic+"&title="+txsci.getTitle();
            System.out.println(msg);
//            InformationController infoctl = new InformationController();
            httpService.sendPost("http://localhost:8989/i/information/addInformation", msg,1);
//            infoctl.addInformation(txsci.getTitle(), HTMLcontent, "科学类", "", pic);
//            infoctl.findInformationByID(1);
            bw.close();
            fo.close();
        } catch (Exception e){
            e.printStackTrace();
        }



    }
}
