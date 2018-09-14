package com.test.reptilian.image;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 实现对网络资源中的图片进行抓取
 */
public class ImageGrab {
    // 编码
    private static final String ECODING = "UTF-8";
    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";
    public void Grab(String url) {
        URL urlObject = null;
        URLConnection  urlConnection;
        StringBuffer sb = null;
        try{
            urlObject = new URL(url);
            urlConnection = urlObject.openConnection();
            InputStream in = urlConnection.getInputStream();//获取输出流
            //接收数据
            byte[] buf = new byte[1024];
            sb = new StringBuffer();
            int length = 0 ;
            while((length = in.read(buf,0,buf.length)) > 0){
                sb.append(new String(buf,ECODING));
            }
            System.out.println(sb.toString());
        }catch (MalformedURLException e){
            System.out.println("创建链接出现异常");
        }catch (IOException e2){
            System.out.println("创建连接失败");
        }


        List<String> imagehtmls = this.getImageURLS(sb.toString());
        List<String> imageurls  = this.getImageSrc(imagehtmls);
        for(int i = 0 ; i < imageurls.size(); i ++){
            System.out.println(imageurls.get(i));
        }
    }



    /**
     * 获取image 地址
     * @param str
     * @return
     */
    public List<String> getImageURLS(String str){
        List<String> imageUrls =  new ArrayList<String>();
        Matcher matcher = compile(IMGURL_REG).matcher(str);
        while (matcher.find()){
            imageUrls.add(matcher.group());
        }
        return imageUrls;
    }

    public List<String> getImageSrc(List<String> images){
        List<String> imageurls = new ArrayList<String>();
        for(int i = 0 ; i < images.size();i++){
            String img = images.get(i);
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(img);
            while(matcher.find()){
                imageurls.add(matcher.group());
            }
        }
        return imageurls;
    }


    public static void main(String[] args) {
        String url = "https://www.douban.com/j/search_photo?q=%E7%BE%8E%E5%A5%B3&limit=20&start=30";
        ImageGrab grab = new ImageGrab();
        grab.Grab(url);
    }
}
