package com.test.reptilian.image;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;

import java.io.*;
import java.net.*;
import java.util.Calendar;

public class dbImageInterface {
    /**
     *
     * @param page 获取多少页
     * @return
     */
    public void task(String p,int page){
        for(int i = 0 ; i < page ; i ++){
            String datas =  this.Grab(i * 20,p);
            System.out.println(datas);
            JSONObject json = JSONObject.fromObject(datas);
            System.out.println(json.toString());
            JSONArray jsonArray  = (JSONArray) json.get("images");
            for(int l = 0 ; l < jsonArray.size();l++){
                JSONObject img = (JSONObject) jsonArray.get(l);
                String src = img.get("src").toString();
                this.downloadImage(src,src.substring(src.lastIndexOf("/"),src.length()));
            }
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String Grab(int start,String q){
        StringBuffer sb = new StringBuffer();
        try {
            String url = "https://www.douban.com/j/search_photo?q="+q+"&limit=20&start="+start+"";
            System.out.println(url);
            URL u = new URL(url);
            HttpURLConnection urlConnection =  (HttpURLConnection)u.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream in = urlConnection.getInputStream();
            byte[] buf = new byte[1024];
            int length = 0;
            while((length = in.read(buf,0,buf.length)) != -1){
                String s  = new String(buf,"UTF-8");
                sb.append(s);
                System.out.println(s);
            }
            System.out.println(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 对图片进行下载
     *
     */
    public void downloadImage(String url,String filename){
        FileOutputStream os = null;
        InputStream in = null;
        try {
            URL imgurl = new URL(url);
            URLConnection urlConnection = imgurl.openConnection();
            in= urlConnection.getInputStream();
            String path = "C:/xxx/image/data/"+filename;
            File file = new File(path);
           if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }

//            file.createNewFile();
            os = new FileOutputStream(file);
            int length = 0 ;
            byte[] buf = new byte[1024];
            while ((length = in.read(buf,0,buf.length)) > 0){
                os.write(buf,0,length);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e2){
            e2.printStackTrace();
        }finally {
            try {
                if(os != null){
                    os.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException e3){
                e3.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        /*dbImageInterface d = new dbImageInterface();

        d.task("美女",2);*//*dbImageInterface d = new dbImageInterface();

        d.task("美女",2);*/
       /* System.out.println(Calendar.getInstance().getTimeInMillis());;*/
        dbImageInterface d = new dbImageInterface();
        d.downloadImage("http://images.tanlinjun.xyz/stzb.png","stzb.png");


    }
}
