package com.wls.manage.crawler.general;

import java.io.*;
import java.util.regex.Matcher;

public class Recorder {
    protected static String defaultPath = "." + File.separator + "default";
    protected String mPath;
    protected File mFile;

    public Recorder() {
        mPath = this.formatPath(defaultPath);
        mFile = this.createFile(mPath, true);
    }

    public Recorder(String path) {
        mPath = this.formatPath(path);
        mFile = this.createFile(mPath, true);
    }

    public Recorder(String path, boolean coverage) {
        mPath = this.formatPath(path);
        mFile = this.createFile(mPath, coverage);
    }

    protected String formatPath(String path) {
        path = path.replaceAll(Matcher.quoteReplacement(File.separator), "defaultSep");
        path = path.replaceAll(" |:|\\*|\\?|\"|<|>|/|\\\\|", "");
        path = path.replaceAll("defaultSep", Matcher.quoteReplacement(File.separator));
        int count = 0;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == File.separatorChar) {
                count++;
            }
        }
        if (path == null || path.equals("") || count == path.length()) {
            return defaultPath;
        }
        return path;
    }

    public File getFile() {
        return mFile;
    }

    protected File createFile(String path, boolean coverage) {
        path = this.formatPath(path);
        File file = null;
        try {
            if (path.lastIndexOf(File.separator) >= 0) {
                file = new File(path.substring(0, path.lastIndexOf(File.separator)));
                file.mkdirs();
            }
            file = new File(path);
            if (!file.exists())
                file.createNewFile();
            else if (coverage) {
                file.renameTo(new File(rename(file.getCanonicalPath())));
                file.createNewFile();
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return file;
    }

    protected String rename(String fileName) {
        if (fileName.contains(".")) {
            int dot = fileName.lastIndexOf(".");
            fileName = fileName.substring(0, dot) + System.currentTimeMillis() + fileName.substring(dot);
        }
        return fileName;
    }

    public void writeRecordUTF8(String message) {
        try {
            Writer out = new BufferedWriter((new OutputStreamWriter(new FileOutputStream(mFile, true), "UTF-8")));
            out.write(message);
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeRecord(String message) {
        try {
            Writer out = new BufferedWriter((new OutputStreamWriter(new FileOutputStream(mFile, true), "UTF-8")));
            out.write(message);
            out.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void writeRecord(String message, Boolean coverage) {
        try {
            Writer out = new BufferedWriter((new OutputStreamWriter(new FileOutputStream(mFile, !coverage), "UTF-8")));
            out.write(message);
            out.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public static String formatFileName(String path) {
        String filename = path;
        if (path.lastIndexOf(File.separator) >= 0) {
            filename = path.substring(path.lastIndexOf(File.separator) + 1);
            path = path.substring(0, path.lastIndexOf(File.separator) + 1);
        }
        filename = filename.replaceAll("\\W", "-");

        while (filename.indexOf("--") >= 0) {
            filename = filename.replaceAll("\\-\\-", "-");
        }
        return path + filename;
    }

}
