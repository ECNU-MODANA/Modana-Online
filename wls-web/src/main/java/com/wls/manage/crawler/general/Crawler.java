package com.wls.manage.crawler.general;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    private static String charset = "UTF-8";
    private static int retry = 3;

    private String mUrl;
    private HttpClientBuilder mClientBuilder;

    public Crawler() {
        mUrl = "ERROR  :Wrong URL.";
        mClientBuilder = HttpClientBuilder.create();
    }

    public Crawler(String charset) {
        mUrl = "ERROR  :Wrong URL.";
        Crawler.setCharset(charset);
        mClientBuilder = HttpClientBuilder.create();
    }

    public static void setCharset(String charset) {
        Crawler.charset = charset;
    }

    public static void setRetry(int retry) {
        Crawler.retry = retry;
    }

    public static boolean checkUrl(String url) {
        String regEx = "^(http|www|ftp|)(://)(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    public static String formatPlain(String message) {
        message = message.replaceAll("</br>", "\n").replaceAll("<br>", "\n")
                .replaceAll("<br/>", "\n").replaceAll("</BR>", "\n")
                .replaceAll("<BR>", "\n").replaceAll("<BR/>", "\n")
                .replaceAll("<div[\\S\\s]*?/div>", "")
                .replaceAll("<DIV[\\S\\s]*?/DIV>", "")
                .replaceAll("<script[\\S\\s]*?/script>", "")
                .replaceAll("<SCRIPT[\\S\\s]*?/SCRIPT>", "")
                .replaceAll("<style[\\S\\s]*?/style>", "")
                .replaceAll("<STYLE[\\S\\s]*?/STYLE>", "")
                .replaceAll("<!--.*?-->", "").replaceAll("<.*?>", "")
                .replaceAll("&nbsp;", " ").replaceAll("&nbsp", " ")
                .replaceAll("\\f|\\r|\\t| |　", "");
        while (message.indexOf("\n\n") >= 0)
            message = message.replaceAll("\n\n", "\n");
        return message.replaceAll("\n", "\r\n");
    }

    public void setUrl(String url) {
        if (checkUrl(url)) {
            mUrl = url;
        } else {
            mUrl = "ERROR  :Wrong URL.";
        }
    }

    public void setUrlnoCheck(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getContent(String encoding) {
        if (mUrl.length() < 8 || mUrl.substring(0, 5).toUpperCase().equals("ERROR"))
            return mUrl;
        String content = "ERROR  :Crawl failed. All " + Crawler.retry + " tries failed.";
        String error = "";
        int count = 0;
        while (true) {
            try {
                CloseableHttpClient client = mClientBuilder.build();
                HttpGet get = new HttpGet(mUrl);
//模拟浏览器的访问请求
                get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
                get.setHeader("Cache-Control", "max-age=0");
                get.setHeader("Connection", "keep-alive");
                get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36");
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).setCircularRedirectsAllowed(true).build();
                get.setConfig(requestConfig);
                HttpResponse response = client.execute(get);

                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String lines = "";
                    HttpEntity entity = response.getEntity();
                    InputStream instream = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream, encoding));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        lines += "\r\n";
                        lines += line;
                    }
                    client.close();

                    if (!lines.equals("") && !lines.contains("抱歉，您所访问的页面不存在")) {
                        String regEx = "charset=(.*?)\"";
                        Pattern pattern = Pattern.compile(regEx);
                        Matcher matcher = pattern.matcher(lines);
                        if (matcher.find()) {
                            if (!matcher.group(1).equals(Crawler.charset)) {
                                Crawler.setCharset(matcher.group(1));

                                continue;
                            }
                        }
                        content = lines;
                        return content;
                    } else {
                        content = "ERROR: Empty page.";
                        return content;
                    }
                } else if (response.getStatusLine().getStatusCode() == 404) {
                    count++;
                    if (count >= 3) {
                        content += " " + error + "\r\n";
                        break;
                    } else {
                        continue;
                    }
                }
            } catch (Exception e) {
                if (e.toString().substring(0, e.toString().indexOf(":")).equals("java.net.UnknownHostException") || e.toString().substring(0, e.toString().indexOf(":")).equals("java.net.NoRouteToHostException")) {
                    System.out.println("Error");
                    continue;
                }
            }
        }
            return content;
    }

    public String getContent() {
        if (mUrl.length() < 8 || mUrl.substring(0, 5).toUpperCase().equals("ERROR"))
            return mUrl;
        String content = "ERROR  :Crawl failed. All " + Crawler.retry + " tries failed.";
        String error = "";
        int count = 0;
        while (true) {
            try {
                CloseableHttpClient client = mClientBuilder.build();
                HttpGet get = new HttpGet(mUrl);
                get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
                get.setHeader("Cache-Control", "max-age=0");
                get.setHeader("Connection", "keep-alive");
                get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36");
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(300000).setConnectTimeout(300000).setCircularRedirectsAllowed(true).build();
                get.setConfig(requestConfig);
                HttpResponse response = client.execute(get);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String lines = "";
                    HttpEntity entity = response.getEntity();
                    InputStream instream = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream,"GBK"));
                    //BufferedReader reader = new BufferedReader(new InputStreamReader(instream,"UTF8"));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        lines += "\r\n";
                        lines += line;
                    }
                    client.close();

                    if (!lines.equals("") && !lines.contains("抱歉，您所访问的页面不存在")) {
                        String regEx = "charset=(.*?)\"";
                        Pattern pattern = Pattern.compile(regEx);
                        Matcher matcher = pattern.matcher(lines);
                        if (matcher.find()) {
                            if (!matcher.group(1).equals(Crawler.charset)) {
                                Crawler.setCharset(matcher.group(1));

                                continue;
                            }
                        }
                        content = lines;
                        return content;
                    } else {
                        content = "ERROR: Empty page.";
                        return content;
                    }
                } else if (response.getStatusLine().getStatusCode() == 404) {
                    count++;
                    if (count >= 3) {
                        content += " " + error + "\r\n";
                        break;
                    } else {
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                    System.out.println("Error");
                    continue;

            }
        }
        return content;
    }
}
