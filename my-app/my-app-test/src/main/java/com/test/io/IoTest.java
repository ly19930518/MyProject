package com.test.io;


import com.baidu.unbiz.fluentvalidator.FluentValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileReader;
import java.util.Random;

public class IoTest {
    public static void main(String[] args) {
        try{
            BufferedReader br = new BufferedReader(new FileReader("c:/xxx/t1.txt"));
            String content = br.readLine();
            while(content != null){
                System.out.println(content);
                content = br.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
