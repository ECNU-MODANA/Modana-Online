package com.wls.manage.crawler.general;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class ErrorRecorder extends Recorder {
    protected int mCount;

    public ErrorRecorder(String path) {
        super(path);
        mCount = 0;
        writeHead();
    }

    public ErrorRecorder() {
        super("." + File.separator + "ErrorRecorder.log");
        mCount = 0;
        writeHead();
    }

    private void writeHead() {
        try {
            Writer out = new FileWriter(mFile);
            //out.write("////////////////////////\r\n//  Head of the file  //\r\n////////////////////////\r\n");
            out.write("////////////////////////\r\n"
                    + "//    Error Record    //\r\n"
                    + "//      myCrawlerV3.0 //\r\n"
                    + "////////////////////////\r\n");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeRecord(String message) {
        try {
            Writer out = new FileWriter(mFile, true);
            message = message.replaceAll("\r\n", "\r\n         ");
            String m = String.format("%8d ErrorTime: %s \r\n         ErrorMessage: %s\r\n", mCount + 1, NowTime.getNowTime(), message);
            out.write(m);
            System.out.println(m);
            out.close();
            mCount++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeEnd() {
        try {
            Writer out = new FileWriter(mFile, true);
            out.write("Total Error count: " + mCount + "\r\n");
            System.out.print("Total Error count: " + mCount + "\r\n");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

