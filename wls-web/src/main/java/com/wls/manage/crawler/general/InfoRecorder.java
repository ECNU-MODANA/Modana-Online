package com.wls.manage.crawler.general;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class InfoRecorder extends Recorder {
    protected int mCount;
    protected int mErrorCount;

    public InfoRecorder(String path) {
        super(path);
        mCount = 0;
        writeHead();
    }

    public InfoRecorder() {
        super("." + File.separator + "InfoRecorder.log");
        mCount = 0;
        writeHead();
    }

    private void writeHead() {
        try {
            Writer out = new FileWriter(mFile);
            //out.write("////////////////////////\r\n//  Head of the file  //\r\n////////////////////////\r\n");
            out.write("////////////////////////\r\n"
                    + "// Information Record //\r\n"
                    + "//      myCrawlerV3.0 //\r\n"
                    + "////////////////////////\r\n");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeRecord(String message) {
        writeRecord(true, message, false);
    }

    public void writeRecord(String message, boolean isError) {
        writeRecord(true, message, isError);
    }

    public void writeRecord(Boolean needprint, String message) {
        writeRecord(needprint, message, false);
    }

    public void writeRecord(Boolean needprint, String message, boolean isError) {
        try {
            Writer out = new FileWriter(mFile, true);
            String m = String.format("%8d Time: %s Info: %s\r\n", mCount + 1, NowTime.getNowTime(), message);
            out.write(m);
            if (needprint)
                System.out.print(m);
            out.close();
            mCount++;
            if (isError) {
                mErrorCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeEnd() {
        try {
            Writer out = new FileWriter(mFile, true);
            out.write("Total   count: " + mCount + "\r\n");
            out.write("Success count:" + (mCount - mErrorCount) + "\r\n");
            out.write("Error   count: " + mErrorCount + "\r\n");
            out.close();
            System.out.print("Total   count: " + mCount + "\r\n");
            System.out.print("Success count:" + (mCount - mErrorCount) + "\r\n");
            System.out.print("Error   count: " + mErrorCount + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
