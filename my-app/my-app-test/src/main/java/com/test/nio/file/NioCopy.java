package com.test.nio.file;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

/**
 * 利用nio 对文件进行copy
 */
public class NioCopy {
    public void CopyFileByChannel(){
        String  sourcePtah = "C:/迅雷下载/oim-all-source-0.0.7.7z";
        String destPath  = "C:/xxx/copytest/oim-all-source-0.0.7.7z";
        try {
            File source = new File(sourcePtah);
            System.out.println( source.getName());
            File dest = new File(destPath);
            if(!dest.exists()){
                dest.getParentFile().mkdir();
                //创建文件
               /* dest.createNewFile();*/
            }
            FileChannel sourceChannel =  new FileInputStream(source).getChannel();
            FileChannel destChannel = new FileOutputStream(dest).getChannel();
            System.out.println("文件大小："+sourceChannel.size());
            System.out.println("转移开始》》》》》");
            for(long count = sourceChannel.size() ; count > 0;){
                long transferred = sourceChannel.transferTo(sourceChannel.position(),count,destChannel);
                sourceChannel.position(sourceChannel.position()+transferred);
                count -= transferred;
            }
            System.out.println("转移完成");
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        NioCopy  copy = new NioCopy();
        copy.CopyFileByChannel();
    }
}
