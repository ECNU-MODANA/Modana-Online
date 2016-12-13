package com.wls.manage.crawler.tengxun;

import javax.swing.text.html.HTML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import com.wls.manage.controller.InformationController;
import com.wls.manage.dao.InformationMapper;
import com.wls.manage.service.base.HttpService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by haolidong on 2016/11/21.
 */
public class genHTML {

	@Autowired
	private static HttpService httpService;
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
            
//            title,//标题
//			@RequestParam(required = false) String content,//内容
//			@RequestParam(required = false) String infocategory,//分类：“1”：科技类，“2”：互联网类，3：校园类；4：财经类；5：创业类
//			@RequestParam(required = false) String source,//来源：腾讯新闻 等等
//			@RequestParam(required = false) String coverpiclist//
            String msg="content="+HTMLcontent+"&infocategory=1&source=腾讯新闻&coverpiclist="+pic+"&content="+txsci.getTitle();
//            InformationController infoctl = new InformationController();
            httpService.sendPost("http://localhost:8080/information/addInformation", msg,2);
//            infoctl.addInformation(txsci.getTitle(), HTMLcontent, "科学类", "", pic);
//            infoctl.findInformationByID(1);
            bw.close();
            fo.close();
        } catch (Exception e){
            e.printStackTrace();
        }



    }
}
