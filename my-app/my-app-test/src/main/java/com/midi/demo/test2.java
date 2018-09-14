package com.midi.demo;

import com.util.HexUtil;
import org.apache.shiro.codec.Hex;

import java.io.UnsupportedEncodingException;

public class test2 {
    public static void main(String[] args) {
        long s = 27985;
        System.out.println(Long.toHexString(s));

        //[4, -46, -80, -41, -45]
       /* byte[] a = {4};
        byte[] b = {-46,-80, -41, -45};
        try {
            System.out.println(HexUtil.BinaryToHexString(a));
            System.out.println(new String(b,"GB2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        //[3, 65, 109, 57]

        byte[] a = {3};
        byte[] b = {65, 109, 57};
        try {
            System.out.println(HexUtil.BinaryToHexString(a));
            System.out.println(new String(b,"GB2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //D
    }
}
